package saleson.shop.eventcode;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import saleson.common.enumeration.eventcode.EventCodeLogType;
import saleson.common.enumeration.eventcode.EventCodeType;
import saleson.model.eventcode.EventCode;
import saleson.model.eventcode.EventCodeLog;
import saleson.shop.eventcode.domain.EventItemStatistics;
import saleson.shop.eventcode.domain.EventStatistics;
import saleson.shop.eventcode.support.EventCodeLogDto;
import saleson.shop.eventcode.support.EventStatisticsParam;
import saleson.shop.featured.domain.Featured;
import saleson.shop.featured.support.FeaturedItem;
import saleson.shop.order.domain.Order;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EventCodeService {

    /**
     * 이벤트 코드 조회
     * @param code
     * @return
     * @throws Exception
     */
    EventCode getEventCode(String code) throws Exception;

    /**
     * 이벤트 코드 저장
     * @param eventCode
     */
    void saveEventCode(EventCode eventCode);

    /**
     * 기획전 이벤트 코드 저장 및 생성
     * @param featured
     * @param utmScource
     * @param utmMedium
     * @return
     */
    String saveEventCode(Featured featured, String utmScource, String utmMedium);

    /**
     * 이벤트 코드 유효성체크
     * @param codes
     * @return
     */
    boolean validEventCodes(List<String> codes);

    /**
     * 신규 이벤트 코드 생성
     * @return
     */
    String generateEventCode();

    /**
     * 신규 이벤트 코드 목록 생성
     * @param size
     * @return
     */
    List<String> generateEventCodeList(int size);

    /**
     * 이벤트 코드별 Redirect
     * @param code
     * @return
     */
    String getRedirect(String code, long sourceUserId);

    /**
     * 캠페인 이벤트 코드별 Redirect
     * @param code
     * @param userId
     * @return
     */
    String getCampaignRedirect(String code, long userId);

    /**
     * 상품 코드별 Redirect
     * @param channel
     * @param itemUserCode
     * @return
     */
    String getItemRedirect(String channel, String itemUserCode, long sourceUserId);


    /**
     * utm query string
     * @param utmSource 발송타입
     * @param utmMedium 매체 및 광고형태
     * @param utmCampaign 목적
     * @param utmItem 유료 키워드 설정
     * @param utmContent 유료 광고일 경우 구분 수단
     * @return
     * @throws Exception
     */
    String getUtmQueryString(String utmSource, String utmMedium, String utmCampaign, String utmItem, String utmContent) throws Exception;

    /**
     * 이벤트 코드 로그 조회
     * @param dto
     * @return
     */
    EventCodeLog getLog(EventCodeLogDto dto);

    /**
     * 이벤트 로그 저장
     * @param eventCodeLog
     */
    void insertLog(EventCodeLog eventCodeLog);

    /**
     * 이벤트 로그 저장
     * @param eventCodeLogs
     */
    void insertLog(List<EventCodeLog> eventCodeLogs);

    /**
     * 이벤트 로그 저장
     * @param request
     * @param userId
     * @param codeType
     * @param logType
     */
    void insertLog(HttpServletRequest request, long userId, EventCodeType codeType, EventCodeLogType logType);

    /**
     *
     * @param request
     * @param userId
     * @param orderCode
     * @param items
     */
    void insertLog(HttpServletRequest request, long userId, String orderCode, List<String> items);

    /**
     * 이벤트 로그 저장
     * @param request
     * @param userId
     * @param itemUserCode
     */
    void insertLog(HttpServletRequest request, long userId, String itemUserCode);

    /**
     * 이벤트 로그 저장
     * @param request
     * @param logType
     * @param userId
     * @param items
     */
    void insertLog(HttpServletRequest request, EventCodeLogType logType, long userId, List<String> items);
    
    /**
     * 이벤트 로그 업데이트
     * @param uid
     * @param userId
     */
    void updateLogForUserId(String uid, long userId);

    /**
     * 이벤트 로그 통계 목록
     * @param param
     * @param pageable
     * @return
     */
    Page<Map<String, Object>> getEventStatisticsList(EventStatisticsParam param, Pageable pageable);

    /**
     * 이벤트 로그 전체 통계
     * @param param
     * @return
     */
    EventStatistics getTotalEventStatistics(EventStatisticsParam param);

    /**
     * 이벤트 통계 이벤트 내용 목록
     * @param eventCodes
     * @return
     */
    List<Map<String, Object>> getEventCodeLogContents(List<String> eventCodes);

    /**
     * 상품 이벤트 로그 통계 목록
     * @param param
     * @param pageable
     * @return
     */
    Page<Map<String, Object>> getEventItemStatisticsList(EventStatisticsParam param, Pageable pageable);

    /**
     * 상품 이벤트 로그 전체 통계
     * @param param
     * @return
     */
    EventItemStatistics getTotalEventItemStatistics(EventStatisticsParam param);
}
