package saleson.shop.stats;

import com.onlinepowers.framework.exception.SystemException;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.CommonUtils;
import com.onlinepowers.framework.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.common.Const;
import saleson.common.utils.AgentUtil;
import saleson.shop.stats.domain.DayStats;
import saleson.shop.stats.domain.MonthStats;
import saleson.shop.stats.domain.Visit;
import saleson.shop.stats.support.StatsSearchParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service("statsService")
public class StatsServiceImpl implements StatsService {
	private static final Logger log = LoggerFactory.getLogger(StatsServiceImpl.class);

	@Autowired
	private StatsMapper statsMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Override
	public void saveVisitData(HttpServletRequest request,
			HttpServletResponse response) {
		/*
		 String protocol = request.getProtocol(); //사용중인 프로토콜 리턴
		 String server = request.getServerName(); //서버의 도메인네임 리턴
		 int port = request.getServerPort(); //서버의 port번호 리턴
		 String clientIp = CommonUtils.getRemoteAddr(request); //클라이언트의 IP를 리턴
		 String clientHost = request.getRemoteHost(); //클라이언트의 Host를 리턴 
		 String methodType = request.getMethod(): //요청방식(Get, Post, Put)을 리턴
		 String uri = request.getRequestURI(); //요청에 사용된 URL로 부터 URI를 리턴
		 String url = new String(request.getRequestURL()): //요청에 사용된 URL을 리턴
		 String ContextPath = request.getContextPath(); //해당 jsp페이지가 속한 웹어플리케이션의 컨텍스트 경로 리턴
		 String browser = request.getHeader("User-Agent"); //사용한 브라우저
		 String mediaType = request.getHeader("Accept"); //웹이 지원하는 매체(media)의 타입
		 String query = request.getQueryString(); //요청에 사용된 query문 리턴
*/		
				
		Cookie[] cookies = request.getCookies();
		boolean isTodayVisited = false;
		
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				log.debug(" ck =  {}", cookies[i].getName());
				if (cookies[i].getName().equals("IS_VISITED"))
					isTodayVisited = true;
			}
		}
		
		//isTodayVisited = false;
		// 오늘 방문한 기록이 없다면 통계데이터 등록하기
		if (!isTodayVisited) {
		   
		   // 방문자 통계에 저장
		   
		   try {
		
		   	String agent = request.getHeader("User-Agent");
		   	String referer = request.getHeader("referer");
		   	
		   	String browser = AgentUtil.getBrowser(agent);
		   	String os = AgentUtil.getOS(agent);
		   	String domain = AgentUtil.findDomain(referer);
		   	String domainName = AgentUtil.getDomainName(domain);
		
		
				Calendar oCalendar = Calendar.getInstance( );  // 현재 날짜/시간 등의 각종 정보 얻기
				String[] week = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };
		
		
		   	Visit visit = new Visit();
		   		visit.setVisitId(sequenceService.getId("OP_VISIT"));
			    visit.setVisitDate(DateUtils.getToday(Const.DATE_FORMAT));
			    visit.setVisitTime(DateUtils.getToday("HHmmss"));
			    visit.setLanguage(CommonUtils.getLanguage());
			    visit.setRemoteAddr(saleson.common.utils.CommonUtils.getClientIp(request));
			    visit.setReferer(referer);
			    visit.setAgent(agent);
			    visit.setDomain(domain);
			    visit.setDomainName(domainName);
			    visit.setBrowser(browser);
			    visit.setOs(os);
			    visit.setWeekday(week[oCalendar.get(Calendar.DAY_OF_WEEK) - 1]);
			    
			    log.debug(visit.toString());
			    
			    
			    
			    statsMapper.insertVisit(visit);
			    
			    // 통계 카운트 UPDATE
			    statsMapper.updateVisitCount(visit);


			   // 쿠키 저장
			   int MAX_AGE = 86400; // 1일 (60*60*24);

			   Cookie cookie = new Cookie("IS_VISITED", "1");
			   cookie.setHttpOnly(true);
			   cookie.setMaxAge(MAX_AGE);				// 쿠키 유지 기간 - 1일
			   cookie.setPath("/");					// 모든 경로에서 접근 가능하도록
			   response.addCookie(cookie);				// 쿠키저장
			   log.debug(new StringBuilder()
					   .append("*********************")
					   .append("**** 방문자 통계에 저장 ")
					   .append("*********************").toString());
			    
			} catch (Exception e) {
			   log.warn("통계 저장 중 오류 발생: {}", e.getMessage(), e);
				throw new SystemException("통계 저장 중 오류 발생");
			}
		}
		else {
			log.debug(new StringBuilder()
			.append("*********************")
			.append("**** 이미 방문했음")
			.append("*********************").toString());
		}
		
	}
	
	@Override
	public HashMap<String, Object> getVisitSummary() {
		return statsMapper.getVisitSummary();
	}

	@Override
	public List<MonthStats> getMonthStatsList(StatsSearchParam searchParam) {
		return statsMapper.getMonthStatsList(searchParam);
	}

	@Override
	public List<DayStats> getDayStatsList(String yearMonth) {
		List<DayStats> list = statsMapper.getDayStatsList(yearMonth);
		
		for (String date : DateUtils.getDateArray(yearMonth + "01", DateUtils.getLastDateOfMonth(yearMonth + "01"))) {
			String day = date.substring(6, 8);
			
			int containCount = 0;
			for (DayStats dayStats : list) {
				if (dayStats.toString().equals(day)) {
					containCount++;
				}
			}
			
			if (containCount == 0) {
				list.add(new DayStats(day));
			}
    		
		}
		
		Collections.sort(list);
		
		return list;
	}

	@Override
	public List<HashMap<String, Object>> getVisitCountByDomain(
			StatsSearchParam searchParam) {
		
		return statsMapper.getVisitCountByDomain(searchParam);
	}

	@Override
	public List<HashMap<String, Object>> getVisitCountByBrowser(
			StatsSearchParam searchParam) {
		return statsMapper.getVisitCountByBrowser(searchParam);
	}

	@Override
	public List<HashMap<String, Object>> getVisitCountByOS(
			StatsSearchParam searchParam) {
		return statsMapper.getVisitCountByOS(searchParam);
	}

	@Override
	public void saveVisitDataForApi(Visit visit) {

		// 방문자 통계에 저장
		try {
			Calendar oCalendar = Calendar.getInstance( );  // 현재 날짜/시간 등의 각종 정보 얻기
			String[] week = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };


			visit.setVisitId(sequenceService.getId("OP_VISIT"));
			visit.setVisitDate(DateUtils.getToday(Const.DATE_FORMAT));
			visit.setVisitTime(DateUtils.getToday("HHmmss"));
			visit.setLanguage(CommonUtils.getLanguage());
			visit.setRemoteAddr(visit.getRemoteAddr());
			visit.setReferer(visit.getReferer());
			visit.setAgent(visit.getAgent());
			visit.setDomain(visit.getDomain());
			visit.setDomainName(visit.getDomainName());
			visit.setBrowser(visit.getBrowser());
			visit.setOs(visit.getOs());
			visit.setWeekday(week[oCalendar.get(Calendar.DAY_OF_WEEK) - 1]);

			log.debug(visit.toString());


			statsMapper.insertVisit(visit);

			// 통계 카운트 UPDATE
			statsMapper.updateVisitCount(visit);

			log.debug(new StringBuilder()
					.append("*********************")
					.append("**** 방문자 통계에 저장 ")
					.append("*********************").toString());

		} catch (Exception e) {
			log.warn("통계 저장 중 오류 발생: {}", e.getMessage(), e);
			throw new SystemException("통계 저장 중 오류 발생");
		}

	}

}
