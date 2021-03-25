package saleson.shop.eventcode;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import saleson.model.eventcode.EventCodeLog;
import saleson.shop.eventcode.domain.EventStatistics;
import saleson.shop.eventcode.query.EventCodeLogQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EventCodeLogRepository extends JpaRepository<EventCodeLog, Long>, QuerydslPredicateExecutor<EventCodeLog> {

    /**
     * 이벤트 통계 목록
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query(
            value= EventCodeLogQuery.GET_BASE_EVENT_STATISTICS,
            countQuery = EventCodeLogQuery.GET_BASE_EVENT_STATISTICS_COUNT,
            nativeQuery = true
    )
    Page<Map<String, Object>> getEventStatisticsList(@Param("beginDate")LocalDateTime beginDate, @Param("endDate")LocalDateTime endDate, Pageable pageable);

    /**
     * 이벤트 통계 목록
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query(value= EventCodeLogQuery.GET_TOTAL_EVENT_STATISTICS, nativeQuery = true)
    Map<String, Object> getTotalEventStatistics(@Param("beginDate")LocalDateTime beginDate, @Param("endDate")LocalDateTime endDate);

    /**
     * 이벤트 통계 이벤트 내용 목록
     * @param eventCodes
     * @return
     */
    @Query(value= EventCodeLogQuery.GET_EVENT_CODE_LOG_CONTENTS, nativeQuery = true)
    List<Map<String, Object>> getEventCodeLogContents(@Param("eventCodes") List<String> eventCodes);

    /**
     * 이벤트 통계 목록
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query(
            value= EventCodeLogQuery.GET_BASE_EVENT_ITEM_STATISTICS,
            countQuery = EventCodeLogQuery.GET_BASE_EVENT_ITEM_STATISTICS_COUNT,
            nativeQuery = true
    )
    Page<Map<String, Object>> getEventItemStatisticsList(@Param("beginDate")LocalDateTime beginDate, @Param("endDate")LocalDateTime endDate, Pageable pageable);

    /**
     * 이벤트 통계 목록
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query(value= EventCodeLogQuery.GET_TOTAL_EVENT_ITEM_STATISTICS, nativeQuery = true)
    Map<String, Object> getTotalEventItemStatistics(@Param("beginDate")LocalDateTime beginDate, @Param("endDate")LocalDateTime endDate);
}
