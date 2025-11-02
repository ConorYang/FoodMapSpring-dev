package Food.service.shopping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Food.config.EcpayConfig;

@Service
public class EcpayService {

    @Autowired
    private EcpayConfig ecpayConfig;

    public String generatePaymentForm(int totalAmount, String itemName, String orderNo) {
        System.out.println("EcpayService: Generating payment form for orderNo: " + orderNo); // 註解：記錄生成
        String merchantTradeDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        String clientBackUrl = ecpayConfig.getClientBackUrl() + "?orderNo=" + orderNo; // 註解：附加 orderNo
        String merchantTradeNo = orderNo;

        // 註解：準備參數以計算 CheckMacValue
        TreeMap<String, String> params = new TreeMap<>();
        params.put("ChoosePayment", "Credit");
        params.put("ClientBackURL", clientBackUrl);
        params.put("EncryptType", "1");
        params.put("ItemName", itemName);
        params.put("MerchantID", ecpayConfig.getMerchantId());
        params.put("MerchantTradeDate", merchantTradeDate);
        // 需使用相同的MerchantTradeNo
        params.put("MerchantTradeNo", merchantTradeNo);
        params.put("PaymentType", "aio");
        params.put("ReturnURL", ecpayConfig.getReturnUrl());
        params.put("TotalAmount", String.valueOf(totalAmount));
        params.put("TradeDesc", "Test Order");

        // 註解：計算 CheckMacValue
        String checkMacValue = generateCheckMacValue(params);

        // 註解：生成表單，包含 CheckMacValue
        String form = "<form id='ecpayForm' action='" + ecpayConfig.getPaymentUrl() + "' method='post'>" +
                "<input type='hidden' name='MerchantID' value='" + ecpayConfig.getMerchantId() + "'>" +
                "<input type='hidden' name='MerchantTradeNo' value='" + merchantTradeNo + "'>" +
                "<input type='hidden' name='MerchantTradeDate' value='" + merchantTradeDate + "'>" +
                "<input type='hidden' name='PaymentType' value='aio'>" +
                "<input type='hidden' name='TotalAmount' value='" + totalAmount + "'>" +
                "<input type='hidden' name='TradeDesc' value='Test Order'>" +
                "<input type='hidden' name='ItemName' value='" + itemName + "'>" +
                "<input type='hidden' name='ReturnURL' value='" + ecpayConfig.getReturnUrl() + "'>" +
                "<input type='hidden' name='ClientBackURL' value='" + clientBackUrl + "'>" +
                "<input type='hidden' name='ChoosePayment' value='Credit'>" +
                "<input type='hidden' name='EncryptType' value='1'>" +
                "<input type='hidden' name='CheckMacValue' value='" + checkMacValue + "'>" +
                "</form>";
        System.out.println("Generated payment form with CheckMacValue: " + checkMacValue);
        return form;
    }

    public boolean verifyCheckMacValue(Map<String, String> params) {
        String received = params.get("CheckMacValue");
        if (received == null)
            return false;

        // 1️⃣ 過濾掉 CheckMacValue 自身
        Map<String, String> filtered = new TreeMap<>();
        for (Map.Entry<String, String> e : params.entrySet()) {
            if (!"CheckMacValue".equals(e.getKey()) && e.getValue() != null) {
                filtered.put(e.getKey(), e.getValue());
            }
        }

        // 2️⃣ 用回傳參數產生 CheckMacValue
        String calculated = generateCheckMacValue(filtered);

        System.out.println("Calculated: " + calculated);
        System.out.println("Received : " + received);

        // 3️⃣ 忽略大小寫比對
        return calculated.equalsIgnoreCase(received);
    }

    private String generateCheckMacValue(Map<String, String> params) {
        // ✅ 不需要 remove("CheckMacValue")，外層已處理
        TreeMap<String, String> sortedParams = new TreeMap<>(params);

        StringBuilder data = new StringBuilder();
        data.append("HashKey=").append(ecpayConfig.getHashKey());
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            data.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        data.append("&HashIV=").append(ecpayConfig.getHashIv());

        String urlEncoded = URLEncoder.encode(data.toString(), StandardCharsets.UTF_8)
                .toLowerCase();

        // ✅ 正確的取代規則（官方指定）
        urlEncoded = urlEncoded
                .replace("%21", "!")
                .replace("%28", "(")
                .replace("%29", ")")
                .replace("%2a", "*")
                .replace("%2d", "-")
                .replace("%2e", ".")
                .replace("%5f", "_")
                .replace("%20", "+"); // ← 注意：%20 → +

        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(urlEncoded.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString().toUpperCase();
        } catch (Exception e) {
            System.out.println("Error calculating CheckMacValue: " + e.getMessage());
            return "";
        }
    }

    /** 組裝 HTML 表單 */
    private String buildFormHtml(String postUrl, Map<String, String> params) {
        StringBuilder html = new StringBuilder();
        html.append("<form id=\"ecpayForm\" method=\"post\" action=\"").append(postUrl).append("\">");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            html.append("<input type=\"hidden\" name=\"").append(entry.getKey())
                    .append("\" value=\"").append(entry.getValue()).append("\">");
        }

        html.append("</form>");
        html.append("<script>document.getElementById('ecpayForm').submit();</script>");

        return html.toString();
    }

    /** 生成訂單編號 (不超過 20 字元) */
    public String generateTradeNo() {
        // 格式: T + 時間戳 + 隨機碼
        String timestamp = String.valueOf(System.currentTimeMillis());
        String random = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        return "T" + timestamp.substring(timestamp.length() - 10) + random;
    }

    /** 取得綠界要求的日期格式 (yyyy/MM/dd HH:mm:ss) */
    private String getCurrentDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}