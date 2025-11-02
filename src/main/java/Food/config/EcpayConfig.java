package Food.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 綠界金流配置類別
 * 從 Spring Boot 配置檔 (e.g., application.properties) 讀取金鑰和 URL 資訊。
 */
@Component
@ConfigurationProperties(prefix = "ecpay")
public class EcpayConfig {

    // 您的特店編號 (MerchantID)
    // 預設值為您提供的測試環境編號
    private String merchantId;

    // 您的串接金鑰 HashKey
    // 預設值為您提供的 HashKey
    private String hashKey;

    // 您的串接金鑰 HashIV
    // 預設值為您提供的 HashIV
    private String hashIv;

    // 綠界金流發送 URL
    // 預設為測試環境 URL，正式環境為 https://payment.ecpay.com.tw/Cashier/AioCheckOut/V5
    private String paymentUrl;

    // 綠界交易結果回傳 (Return URL) 給後端的網址
    // 請務必替換成您服務器的對外公開網址！
    private String returnUrl; 

    // 交易完成後導向給前端頁面的網址 (ClientBackURL)
    private String clientBackUrl;


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getHashIv() {
        return hashIv;
    }

    public void setHashIv(String hashIv) {
        this.hashIv = hashIv;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
    
    public String getClientBackUrl() {
        return clientBackUrl;
    }

    public void setClientBackUrl(String clientBackUrl) {
        this.clientBackUrl = clientBackUrl;
    }
}
