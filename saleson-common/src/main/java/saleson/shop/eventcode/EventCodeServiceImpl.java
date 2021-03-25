package saleson.shop.eventcode;

import com.onlinepowers.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import saleson.common.Const;
import saleson.common.config.SalesonProperty;
import saleson.common.enumeration.eventcode.EventCodeLogType;
import saleson.common.enumeration.eventcode.EventCodeType;
import saleson.common.utils.CommonUtils;
import saleson.common.utils.EventViewUtils;
import saleson.common.utils.LocalDateUtils;
import saleson.common.utils.RandomStringUtils;
import saleson.model.eventcode.EventCode;
import saleson.model.eventcode.EventCodeLog;
import saleson.shop.campaign.CampaignUserRepository;
import saleson.shop.campaign.CampaignUserService;
import saleson.shop.eventcode.domain.EventItemStatistics;
import saleson.shop.eventcode.domain.EventStatistics;
import saleson.shop.eventcode.support.EventCodeDto;
import saleson.shop.eventcode.support.EventCodeLogDto;
import saleson.shop.eventcode.support.EventStatisticsParam;
import saleson.shop.featured.domain.Featured;
import saleson.shop.featured.support.FeaturedItem;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShippingInfo;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service("eventCodeService")
public class EventCodeServiceImpl implements EventCodeService{

    private static final Logger log = LoggerFactory.getLogger(EventCodeServiceImpl.class);

    @Autowired
    EventCodeRepository eventCodeRepository;

    @Autowired
    CampaignUserRepository campaignUserRepository;

    @Autowired
    CampaignUserService campaignUserService;

    @Autowired
    Environment environment;

    @Autowired
    ItemService itemService;

    @Autowired
    EventCodeLogRepository eventCodeLogRepository;

    @Override
    public EventCode getEventCode(String code) throws Exception {

        EventCodeDto dto = new EventCodeDto();
        dto.setEventCode(code);

        EventCode campaignUrl = eventCodeRepository.findOne(dto.getPredicate())
                .orElseThrow(() -> new IllegalArgumentException("not event code"));

        return campaignUrl;
    }

    @Override
    public void saveEventCode(EventCode eventCode) {
        eventCodeRepository.save(eventCode);
    }

    @Override
    public String saveEventCode(Featured featured, String utmScource, String utmMedium) {

        String code = "";

        try {

            if (featured != null) {

                code = generateEventCode();

                String utmQueryString = getUtmQueryString(
                        utmScource,
                        utmMedium,
                        featured.getFeaturedName(),
                        "",
                        ""
                );

                // 1. SalesOn 자체 기획전
                String link = SalesonProperty.getSalesonUrlShoppingmall() + featured.getPageLink();

                if (isApiView()) {

                    link = SalesonProperty.getSalesonUrlFrontend()
                            + "/featured/detail.html?pages="
                            + featured.getFeaturedUrl();
                }

                // 2. 외부 링크가 있을경우 변경
                if (!StringUtils.isEmpty(featured.getLink())) {
                    link = featured.getLink();
                }

                EventCode eventCode = new EventCode();
                eventCode.setType(EventCodeType.FEATURED);
                eventCode.setEventCode(code);
                eventCode.setUtmQueryString(utmQueryString);
                eventCode.setContents(link);

                saveEventCode(eventCode);
            }

        } catch (Exception ignore) {
            log.error("saveEventCode Featured {}", ignore.getMessage(), ignore );
        }

        return code;
    }

    @Override
    public List<String> generateEventCodeList(int size) {

        List<String> codes = new ArrayList<>();

        int length = 4;

        if (size > 0) {
            for (int i = 0; i < size; i++) {
                String code;
                do {
                    code = RandomStringUtils.getRandomString("", 0, length);
                    codes.add(code);
                } while (!validEventCode(code, codes));

            }
        }

        return codes;
    }

    @Override
    public String generateEventCode() {
        return generateEventCodeList(1).get(0);
    }

    private boolean validEventCode(String code, List<String> codes) {

        if (codes != null && !codes.isEmpty()) {
            if (codes.contains(code)) {
                return true;
            }
        }

        EventCodeDto dto = new EventCodeDto();
        dto.setEventCode(code);
        long count = eventCodeRepository.count(dto.getPredicate());

        return count > 0;
    }

    @Override
    public boolean validEventCodes(List<String> codes) {

        EventCodeDto dto = new EventCodeDto();
        dto.setEventCodes(codes);

        long count = eventCodeRepository.count(dto.getPredicate());
        return count > 0;
    }

    @Override
    public String getCampaignRedirect(String code, long userId) {

        String redirect = getBaseRedirect();

        try {

            EventCode eventCode = getEventCode(code);

            if (eventCode != null) {

                long urlId = eventCode.getId();
                long campaignId = eventCodeRepository.getCampaignId(urlId);

                redirect = getRedirect(eventCode, campaignId, userId);
                campaignUserService.updateCampaignUserRedirection(campaignId, userId);
            }

        } catch (Exception ignore) {
            log.error("getCampaignRedirect error {}", code, ignore);
        }

        return redirect;
    }

    @Override
    public String getRedirect(String code, long sourceUserId) {

        try {
            EventCode eventCode = getEventCode(code);

            if (eventCode != null) {
                return getRedirect(eventCode, sourceUserId);
            }
        } catch (Exception ignore) {
            log.error("getCampaignRedirect error {}", code, ignore);
        }


        return getBaseRedirect();
    }

    private String getBaseRedirect() {
        String redirect = SalesonProperty.getSalesonUrlShoppingmall();

        if (isApiView()) {
            redirect = SalesonProperty.getSalesonUrlFrontend();
        }

        return redirect;
    }

    private String getRedirect(EventCode eventCode, long sourceUserId) {
        return getRedirect(eventCode, 0, sourceUserId);
    }

    private String getRedirect(EventCode eventCode, long campaignId, long sourceUserId) {

        try {

            if (eventCode != null) {
                String url = eventCode.getContents();
                String utmQueryString = eventCode.getUtmQueryString();

                if (!StringUtils.isEmpty(url)) {

                    if (!eventCode.isNotRedirectionFlag()) {
                        long redirection = CommonUtils.longNvl(eventCode.getRedirection());
                        eventCode.setRedirection(redirection + 1);
                        saveEventCode(eventCode);
                    }

                    StringBuffer sb = new StringBuffer();

                    sb.append(url);
                    sb.append(eventCode.getContents().contains("?") ? "&" : "?");

                    sb.append("ec="+eventCode.getEventCode());

                    sb.append("&source_user_id="+sourceUserId);

                    String uid = EventViewUtils.getUid();

                    sb.append("&uid="+uid);

                    if (campaignId > 0) {
                        sb.append("&campaign_code=" + campaignId);
                    }

                    if (!StringUtils.isEmpty(utmQueryString)) {
                        sb.append(utmQueryString);
                    }

                    return sb.toString();
                }
            }
        } catch (Exception ignore) {
            log.error("event code make redirect error", ignore);
        }

        return getBaseRedirect();
    }

    @Override
    public String getItemRedirect(String channel, String itemUserCode, long sourceUserId) {

        try {

            Item item = itemService.getItemByItemUserCode(itemUserCode);

            if (item == null) {
                return getBaseRedirect();
            }

            String link = SalesonProperty.getSalesonUrlShoppingmall() + item.getLink();

            if (isApiView()) {
                link = SalesonProperty.getSalesonUrlFrontend()
                        + "/items/details.html?code="
                        + item.getItemUserCode();
            }

            String utmQueryString = getUtmQueryString(
                    channel,
                    "view",
                    item.getItemName(),
                    "",
                    ""
            );

            EventCode eventCode = new EventCode();
            eventCode.setEventCode(item.getItemUserCode());
            eventCode.setContents(link);
            eventCode.setUtmQueryString(utmQueryString);
            eventCode.setNotRedirectionFlag(true);

            return getRedirect(eventCode, sourceUserId);
            
        } catch (Exception igroe) {
            log.error("getItemRedirect error [{}][{}]", channel,itemUserCode, igroe);
        }

        return getBaseRedirect();
    }

    private boolean isApiView() {
        return "api".equals(environment.getProperty("saleson.view.type"));
    }

    @Override
    public String getUtmQueryString(String utmSource, String utmMedium, String utmCampaign, String utmItem, String utmContent) throws Exception{

        StringBuffer sb = new StringBuffer();
        String enc = "UTF-8";

        if (!StringUtils.isEmpty(utmSource)) {
            sb.append("&utm_source="+ URLEncoder.encode(utmSource, enc));
        }

        if (!StringUtils.isEmpty(utmMedium)) {
            sb.append("&utm_medium="+URLEncoder.encode(utmMedium, enc));
        }

        if (!StringUtils.isEmpty(utmCampaign)) {
            sb.append("&utm_campaign="+URLEncoder.encode(utmCampaign, enc));
        }

        if (!StringUtils.isEmpty(utmItem)) {
            sb.append("&utm_item="+URLEncoder.encode(utmItem, enc));
        }

        if (!StringUtils.isEmpty(utmContent)) {
            sb.append("&utm_content="+URLEncoder.encode(utmContent, enc));
        }

        return sb.toString();
    }

    @Override
    public EventCodeLog getLog(EventCodeLogDto dto) {
        return eventCodeLogRepository.findOne(dto.getPredicate()).orElse(null);
    }

    @Override
    public void insertLog(EventCodeLog eventCodeLog) {

        String uid = "";
        long userId = 0;

        try {

            if (eventCodeLog != null && !StringUtils.isEmpty(eventCodeLog.getEventCode())) {

                uid = eventCodeLog.getUid();
                userId = eventCodeLog.getUserId();

                EventCodeLogDto dto = new EventCodeLogDto();

                dto.setEventCode(eventCodeLog.getEventCode());
                dto.setCodeType(eventCodeLog.getCodeType());
                dto.setLogType(eventCodeLog.getLogType());
                dto.setUid(eventCodeLog.getUid());
                dto.setUserId(eventCodeLog.getUserId());
                dto.setItemUserCode(eventCodeLog.getItemUserCode());
                dto.setOrderCode(eventCodeLog.getOrderCode());

                EventCodeLog storeLog = getLog(dto);

                if (storeLog == null) {
                    eventCodeLogRepository.save(eventCodeLog);
                }
            }
        }  catch (Exception ignore) {
            log.error("insertLog Error {} / {}", uid, userId, ignore);
        }

    }

    private EventCodeLog getBaseEventCodeLog(HttpServletRequest request, long userId, EventCodeType codeType, EventCodeLogType logType) {

        String uid = EventViewUtils.getUidCookieValue(request);

        if (StringUtils.isEmpty(uid)) {
            uid = EventViewUtils.getUid();
        }

        EventCodeLog eventCodeLog = EventViewUtils.getEventCodeLog(request, codeType, logType);
        eventCodeLog.setUid(uid);
        eventCodeLog.setUserId(userId);

        return eventCodeLog;
    }

    @Override
    public void insertLog(List<EventCodeLog> eventCodeLogs) {
        if (eventCodeLogs != null && !eventCodeLogs.isEmpty()) {
            eventCodeLogs.forEach(l -> {
                this.insertLog(l);
            });
        }
    }

    @Override
    public void insertLog(HttpServletRequest request, long userId, EventCodeType codeType, EventCodeLogType logType) {

        String uid = "";
        try {
            EventCodeLog eventCodeLog = this.getBaseEventCodeLog(request, userId, codeType, logType);
            uid = eventCodeLog.getUid();
            this.insertLog(eventCodeLog);
        }  catch (Exception ignore) {
            log.error("insertLog Error {} / {}", uid, userId, ignore);
        }
    }

    @Override
    public void insertLog(HttpServletRequest request, long userId, String orderCode, List<String> items) {

        String uid = "";

        try {

            if (items != null && !items.isEmpty()) {
                Set<String> insertItemUserCodeSet = new HashSet<>();
                uid = EventViewUtils.getUidCookieValue(request);

                if (!StringUtils.isEmpty(uid)) {
                    List<EventCodeLog> logs = getEventCodeLogsByUid(uid);

                    if (logs != null && !logs.isEmpty()) {

                        for (EventCodeLog log : logs) {
                            insertItemUserCodeSet.add(log.getEventCode());

                            if (!StringUtils.isEmpty(log.getItemUserCode())) {
                                insertItemUserCodeSet.add(log.getItemUserCode());
                            }
                        }
                    }
                }

                items.forEach(i -> {

                    if(insertItemUserCodeSet.contains(i)) {
                        EventCodeLog eventCodeLog
                                = this.getBaseEventCodeLog(request, userId, EventCodeType.NONE, EventCodeLogType.ORDER);

                        eventCodeLog.setOrderCode(orderCode);
                        eventCodeLog.setItemUserCode(i);
                        this.insertLog(eventCodeLog);
                    }
                });

            }

        } catch (Exception ignore) {
            log.error("insertLog Error {} / {}", uid, userId, ignore);
        }
    }

    @Override
    public void insertLog(HttpServletRequest request, long userId, String itemUserCode) {
        String uid = "";
        try {
            EventCodeLog eventCodeLog
                    = this.getBaseEventCodeLog(request, userId, EventCodeType.NONE, EventCodeLogType.ITEM);

            eventCodeLog.setItemUserCode(itemUserCode);

            uid = eventCodeLog.getUid();
            this.insertLog(eventCodeLog);

        }  catch (Exception ignore) {
            log.error("insertLog Error {} / {}", uid, userId, ignore);
        }
    }

    @Override
    public void insertLog(HttpServletRequest request, EventCodeLogType logType, long userId, List<String> items) {

        String uid = "";

        try {

            uid = EventViewUtils.getUidCookieValue(request);

            if (items != null && !items.isEmpty()) {

                items.forEach(i -> {
                    EventCodeLog eventCodeLog
                            = this.getBaseEventCodeLog(request, userId, EventCodeType.NONE, logType);

                    eventCodeLog.setItemUserCode(i);
                    this.insertLog(eventCodeLog);
                });

            }

        }  catch (Exception ignore) {
            log.error("insertLog Error {} / {}", uid, userId, ignore);
        }

    }

    @Override
    public void updateLogForUserId(String uid, long userId) {

        try {

            if (!StringUtils.isEmpty(uid) && userId > 0) {

                List<EventCodeLog> logs = getEventCodeLogsByUid(uid);

                if (logs != null && !logs.isEmpty()) {

                    logs.forEach(l -> {
                        l.setUserId(userId);
                    });

                    eventCodeLogRepository.saveAll(logs);
                }
            }
        } catch (Exception ignore) {
            log.error("updateLogForUserId Error {} / {}", uid, userId, ignore);
        }

    }

    private List<EventCodeLog> getEventCodeLogsByUid (String uid) {
        EventCodeLogDto dto = new EventCodeLogDto();
        dto.setUid(uid);

        Iterable<EventCodeLog> iterable = eventCodeLogRepository.findAll(dto.getPredicate());

        List<EventCodeLog> logs = StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());

        return logs;
    }

    @Override
    public Page<Map<String, Object>> getEventStatisticsList(EventStatisticsParam param, Pageable pageable) {

        LocalDateTime beginDate = initSearchLocalDateTime(param.getBeginDateTime(),"000000");
        LocalDateTime endDate = initSearchLocalDateTime(param.getEndDateTime(),"235959");

        return eventCodeLogRepository.getEventStatisticsList(beginDate, endDate, pageable);
    }

    @Override
    public EventStatistics getTotalEventStatistics(EventStatisticsParam param) {

        LocalDateTime beginDate = initSearchLocalDateTime(param.getBeginDateTime(),"000000");
        LocalDateTime endDate = initSearchLocalDateTime(param.getEndDateTime(),"235959");

        Map<String, Object> map = eventCodeLogRepository.getTotalEventStatistics(beginDate, endDate);

        return new EventStatistics(map);
    }

    private LocalDateTime initSearchLocalDateTime(LocalDateTime dateTime, String suffix) {
        String today = LocalDateUtils.localDateTimeToString(LocalDateTime.now(), Const.DATE_FORMAT);
        return dateTime == null ? LocalDateUtils.getLocalDateTime(today+suffix) : dateTime;
    }

    @Override
    public List<Map<String, Object>> getEventCodeLogContents(List<String> eventCodes) {

        if (eventCodes != null && !eventCodes.isEmpty()) {
            return eventCodeLogRepository.getEventCodeLogContents(eventCodes);
        }

        return null;
    }

    @Override
    public Page<Map<String, Object>> getEventItemStatisticsList(EventStatisticsParam param, Pageable pageable) {

        LocalDateTime beginDate = initSearchLocalDateTime(param.getBeginDateTime(),"000000");
        LocalDateTime endDate = initSearchLocalDateTime(param.getEndDateTime(),"235959");

        return eventCodeLogRepository.getEventItemStatisticsList(beginDate,endDate,pageable);
    }

    @Override
    public EventItemStatistics getTotalEventItemStatistics(EventStatisticsParam param) {

        LocalDateTime beginDate = initSearchLocalDateTime(param.getBeginDateTime(),"000000");
        LocalDateTime endDate = initSearchLocalDateTime(param.getEndDateTime(),"235959");

        Map<String, Object> map = eventCodeLogRepository.getTotalEventItemStatistics(beginDate, endDate);

        return new EventItemStatistics(map);
    }
}
