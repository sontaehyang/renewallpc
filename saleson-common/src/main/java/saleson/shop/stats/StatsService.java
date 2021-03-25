package saleson.shop.stats;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import saleson.shop.stats.domain.DayStats;
import saleson.shop.stats.domain.MonthStats;
import saleson.shop.stats.domain.Visit;
import saleson.shop.stats.support.StatsSearchParam;

public interface StatsService {
	
	/**
	 * 접속 정보를 저장한다.
	 */
	public void saveVisitData(HttpServletRequest request, HttpServletResponse response);
	
	
	/**
	 * 접속 통계 요약정보를 가져옴.
	 * @return
	 */
	public HashMap<String, Object> getVisitSummary();

	
	/**
	 * 월별 접속 통계
	 * @param searchParam
	 * @return
	 */
	List<MonthStats> getMonthStatsList(StatsSearchParam searchParam);
	
	
	/**
	 * 일별 접속 통계
	 * @param yearMonth
	 * @return
	 */
	List<DayStats> getDayStatsList(String yearMonth);
	
	
	/**
	 * 접속경로 통계
	 * @param searchParam
	 * @return
	 */
	List<HashMap<String, Object>> getVisitCountByDomain(StatsSearchParam searchParam);

	
	/**
	 * 브라우저 통계
	 * @param searchParam
	 * @return
	 */
	List<HashMap<String, Object>> getVisitCountByBrowser(StatsSearchParam searchParam);

	
	/**
	 * OS 통계 
	 * @param searchParam
	 * @return
	 */
	List<HashMap<String, Object>> getVisitCountByOS(StatsSearchParam searchParam);

	/**
	 * API용 접속 정보 저장.
	 */
	public void saveVisitDataForApi(Visit visit);
}
