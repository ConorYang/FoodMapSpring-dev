package Food.service.vendor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import Food.repository.lookHistory.LookHistoryRepository;
import Food.repository.member.FavoriteRepository;

@Service
public class ChartService {

	private final FavoriteRepository favoriteRepository;
	private final LookHistoryRepository lookHistoryRepository;

	public ChartService(FavoriteRepository favoriteRepository, LookHistoryRepository lookHistoryRepository) {
		this.favoriteRepository = favoriteRepository;
		this.lookHistoryRepository = lookHistoryRepository;
	}

	// 取得指定店家被收藏次數
	public int getFavoriteCountByVendor(int vendorID) {
		return (int) favoriteRepository.findAll().stream().filter(f -> f.getVendorID() == vendorID).count();
	}

	// 取得指定店家總瀏覽次數

public int getViewCountByVendor(int vendorId) {
    return lookHistoryRepository.countByVendorId(vendorId);
}

//日報表瀏覽次數（補齊本月所有日期）
	public List<Map<String, Object>> getViewCountByDate(int vendorId) {
		// 1. 從資料庫取得有資料的日期
		List<Object[]> rawData = lookHistoryRepository.countByVendorIdGroupByDate(vendorId);

		// 2. 轉換成 Map 方便查找 (日期 -> 瀏覽數)
		Map<String, Integer> dataMap = rawData.stream()
				.collect(Collectors.toMap(
						row -> row[0].toString(),
						row -> ((Number) row[1]).intValue()));

		// 3. 生成本月 1 號到今天的所有日期
		LocalDate today = LocalDate.now();
		LocalDate startOfMonth = today.withDayOfMonth(1);
		List<Map<String, Object>> result = new ArrayList<>();

		LocalDate current = startOfMonth;
		while (!current.isAfter(today)) {
			Map<String, Object> dayData = new HashMap<>();  // 👈 改成 dayData
			String dateStr = current.toString();
			
			// 有資料就用實際數字，沒資料就補 0
			dayData.put("date", dateStr);
			dayData.put("viewCount", dataMap.getOrDefault(dateStr, 0));
			result.add(dayData);
			
			current = current.plusDays(1);
		}

		return result;
	}
}

