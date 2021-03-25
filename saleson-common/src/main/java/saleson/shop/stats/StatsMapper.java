package saleson.shop.stats;

import java.util.HashMap;
import java.util.List;

import saleson.shop.stats.domain.DayStats;
import saleson.shop.stats.domain.MonthStats;
import saleson.shop.stats.domain.Visit;
import saleson.shop.stats.support.StatsSearchParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import com.onlinepowers.framework.web.domain.SearchParam;

@Mapper("statsMapper")
public interface StatsMapper {


	void insertVisit(Visit visit);

	void updateVisitCount(Visit visit);

	int selectVisitCountToday(Visit visit);


	/**
	 * 접속통계 - SUMMARY
	 * @return
	 */
	HashMap<String, Object> getVisitSummary();

	
	/**
	 * 접속통계 - 월별
	 * @param searchParam
	 * @return
	 */
	List<MonthStats> getMonthStatsList(StatsSearchParam searchParam);
	
	
	/**
	 * 접속통계 - 일별
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
	 * 로그인 통계 로그 
	 * @param string
	 */
	void insertLoginCount(String loginType);
	
	
	List<HashMap<String, Object>> getVisitCountByOS(HashMap<String, Object> map);

	List<HashMap<String, Object>> getVisitCountByWeekday(
			HashMap<String, Object> map);

	List<HashMap<String, Object>> getVisitCountByTime(
			HashMap<String, Object> map);

	int getVisitCount(SearchParam searchParam);

	List<Visit> getVisitList(SearchParam searchParam);

	List<HashMap<String, Object>> getVisitSummaryAll(HashMap<String, String> param);

	List<HashMap<String, Object>> getVisitCountByOS(StatsSearchParam searchParam);



	
}
