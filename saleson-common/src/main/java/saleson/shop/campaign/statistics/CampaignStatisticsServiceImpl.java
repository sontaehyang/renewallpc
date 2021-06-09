package saleson.shop.campaign.statistics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import saleson.common.Const;
import saleson.common.notification.UmsApiService;
import saleson.common.notification.domain.*;
import saleson.common.notification.support.StatisticsParam;
import saleson.common.utils.CommonUtils;
import saleson.model.campaign.*;
import saleson.shop.campaign.CampaignRepository;
import saleson.shop.campaign.CampaignSendLogRepository;
import saleson.shop.campaign.support.CampaignSendLogDto;
import saleson.shop.eventcode.EventCodeRepository;
import saleson.shop.campaign.CampaignUserRepository;
import saleson.shop.campaign.messageLog.KakaoLogTempRepository;
import saleson.shop.campaign.messageLog.MmsLogTempRepository;
import saleson.shop.campaign.messageLog.PushLogTempRepository;
import saleson.shop.campaign.messageLog.SmsLogTempRepository;
import saleson.shop.campaign.statistics.domain.StatisticsInfo;
import saleson.shop.campaign.support.CampaignDto;
import saleson.shop.order.OrderMapper;

import java.math.BigDecimal;
import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service("campaignStatisticsService")
public class CampaignStatisticsServiceImpl implements CampaignStatisticsService{


    private static final Logger logger = LoggerFactory.getLogger(CampaignStatisticsServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private EventCodeRepository eventCodeRepository;

    @Autowired
    Environment environment;

    @Autowired
    UmsApiService umsApiService;

    @Autowired
    RestTemplate umsAgentRestTemplate;

    @Autowired
    private CampaignUserRepository campaignUserRepository;

    @Autowired
    private SmsLogTempRepository smsLogTempRepository;

    @Autowired
    private MmsLogTempRepository mmsLogTempRepository;

    @Autowired
    private KakaoLogTempRepository kakaoLogTempRepository;

    @Autowired
    private PushLogTempRepository pushLogTempRepository;

    @Autowired
    private CampaignSendLogRepository campaignSendLogRepository;

    @Override
    public void updateCampaignSentBatch(String batchDate) throws Exception {
        String statisticsDate = DateUtils.getToday(Const.DATETIME_FORMAT);
        String month1 = batchDate.substring(0,6);
        String month2 = DateUtils.addMonth(batchDate, 1).substring(0,6);

        List<UmsStatisticsTable> statisticsTables = getLogTables(month1, month2);

        List<String> tables = new ArrayList<>();
        tables.add(month1);
        tables.add(month2);

        Map<String, StatisticsInfo> statisticsInfoMap = new HashMap<>();
        updateSent(statisticsInfoMap, tables);

        Map<String, StatisticsInfo> autoStatisticsInfoMap = new HashMap<>();
        Campaign campaign = getAutoMonthCampaign(batchDate);

        updateAutoSent(autoStatisticsInfoMap, tables, campaign);

        statisticsInfoMap.putAll(autoStatisticsInfoMap);

        Set<String> keys = statisticsInfoMap.keySet();

        //logger.debug("statisticsInfoMap >>>>>>>>>\n {}", JsonViewUtils.objectToJson(statisticsInfoMap));

        for (String key : keys) {

            try {
                StatisticsInfo statisticsInfo = statisticsInfoMap.get(key);
                long id = StringUtils.string2long(statisticsInfo.getKey());

                campaignRepository.updateSent(id,
                        statisticsInfo.getSmsSent(),
                        statisticsInfo.getSmsSuccess(),
                        statisticsInfo.getMmsSent(),
                        statisticsInfo.getMmsSuccess(),
                        statisticsInfo.getKakaoSent(),
                        statisticsInfo.getKakaoSuccess(),
                        statisticsInfo.getPushSent(),
                        statisticsInfo.getPushSuccess(),
                        statisticsInfo.getPushReceive(),
                        statisticsDate);

            } catch (Exception e) {
                logger.error("updateSent Error [{}] {}", key, e.getMessage(), e);
            }
        }

        // 주문통계 업데이트
        updateOrderInfo(batchDate, statisticsDate);

        // 유정 통계 업데이트
        updateRedirection(batchDate, statisticsDate);


        // 캠페인 사용자별 통계 - 기존 temp 데이터 삭제 후 insert
        smsLogTempRepository.deleteAll();
        mmsLogTempRepository.deleteAll();
        pushLogTempRepository.deleteAll();
        kakaoLogTempRepository.deleteAll();

        if (statisticsTables != null && !statisticsTables.isEmpty()) {
            statisticsTables.forEach(statistics -> {
                insertSentForUser(statistics.getType(), statistics.getTable());
            });
        }

        if (statisticsTables != null && !statisticsTables.isEmpty()) {

            statisticsTables.forEach(statistics -> {
                insertAutoSentForUser(statistics.getType(), statistics.getTable(), campaign);
            });
        }

        // 사용자별 통계 업데이트
        updateCampaignUser(statisticsDate);

        // 사용자별 주문통계 업데이트
        updateOrderInfoForUser(batchDate, statisticsDate);
    }

    private String getId() {
        return environment.getProperty("ums.sub.id");
    }

    private String getHost() {
        return environment.getProperty("ums.api.url");
    }

    private void updateAutoSent(Map<String, StatisticsInfo> statisticsInfoMap, List<String> tables, Campaign campaign) {

        if (!tables.isEmpty()) {

            String key = StringUtils.long2string(campaign.getId());

            StatisticsParam param = new StatisticsParam();
            param.setTables(tables);
            param.setAutoFlag(true);
            param.setCampaignKey(key);
            param.setId(getId());

            setStatisticsInfoMap(statisticsInfoMap, param);

        }

    }

    private void updateSent(Map<String, StatisticsInfo> statisticsInfoMap, List<String> tables) {

        if (!tables.isEmpty()) {

            StatisticsParam param = new StatisticsParam();
            param.setTables(tables);
            param.setId(getId());

            setStatisticsInfoMap(statisticsInfoMap, param);

        }
    }

    private List<UmsSendInfo> getUmsSendInfos(StatisticsParam param) {

        try {
            String url = getHost() + "/api/statistics/send-info?";

            HttpHeaders headers = umsApiService.getHttpHeaders();

            URI uri = UriComponentsBuilder
                    .fromHttpUrl(url)
                    .queryParam("tableType", param.getTableType())
                    .queryParam("tables", String.join(",", param.getTables()))
                    .queryParam("campaignKey", param.getCampaignKey())
                    .queryParam("autoFlag", param.isAutoFlag())
                    .queryParam("openFlag", param.isOpenFlag())
                    .queryParam("beginSearchDate", param.getBeginSearchDate())
                    .queryParam("endSearchDate", param.getEndSearchDate())
                    .queryParam("id", param.getId())
                    .build()
                    .toUri();

            RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET, uri);

            ResponseEntity<Map<String, Map<String, Map<String, Long>>>> response = umsAgentRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<Map<String, Map<String, Map<String, Long>>>>() {});

            Map<String, Map<String, Map<String, Long>>> map = response.getBody();

            List<UmsSendInfo> list = new ArrayList<>();

            if (map != null) {

                Set<String> keySet = map.keySet();

                keySet.forEach(s -> {
                    list.add(new UmsSendInfo(s, map.get(s)));
                });
            }

            return list;
        } catch (Exception e) {
            logger.error("getUmsSendInfos error {}",e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private void setStatisticsInfoMap(Map<String, StatisticsInfo> statisticsInfoMap, StatisticsParam param) {

        try {

            List<UmsSendInfo> list = getUmsSendInfos(param);

            if (!list.isEmpty()) {
                list.forEach(umsSendInfo -> {
                    String id = umsSendInfo.getCampaignKey();

                    StatisticsInfo statisticsInfo = statisticsInfoMap.get(id);

                    if (statisticsInfo == null) {
                        statisticsInfo = new StatisticsInfo();
                        statisticsInfo.setKey(id);
                    }

                    if (statisticsInfo != null) {

                        List<String> sendTypes = umsSendInfo.sendTypes();

                        StatisticsInfo finalStatisticsInfo = statisticsInfo;
                        sendTypes.forEach(sendType -> {
                            switch (sendType) {
                                case "sms":
                                case "kakao-sms":
                                    finalStatisticsInfo.setSmsSent(umsSendInfo.appendCount(finalStatisticsInfo.getSmsSent(), sendType, "sent"));
                                    finalStatisticsInfo.setSmsSuccess(umsSendInfo.appendCount(finalStatisticsInfo.getSmsSuccess(), sendType, "success"));
                                    break;
                                case "mms":
                                case "kakao-mms":
                                    finalStatisticsInfo.setMmsSent(umsSendInfo.appendCount(finalStatisticsInfo.getMmsSent(), sendType, "sent"));
                                    finalStatisticsInfo.setMmsSuccess(umsSendInfo.appendCount(finalStatisticsInfo.getMmsSuccess(), sendType, "success"));
                                    break;
                                case "push":
                                case "push-batch":

                                    finalStatisticsInfo.setPushSent(umsSendInfo.appendCount(finalStatisticsInfo.getPushSent(), sendType, "sent"));
                                    finalStatisticsInfo.setPushSuccess(umsSendInfo.appendCount(finalStatisticsInfo.getPushSuccess(), sendType, "success"));
                                    finalStatisticsInfo.setPushReceive(umsSendInfo.appendCount(finalStatisticsInfo.getPushReceive(), sendType, "open"));

                                    break;

                                case "kakao":
                                    finalStatisticsInfo.setKakaoSent(umsSendInfo.appendCount(finalStatisticsInfo.getKakaoSent(), sendType, "sent"));
                                    finalStatisticsInfo.setKakaoSuccess(umsSendInfo.appendCount(finalStatisticsInfo.getKakaoSuccess(), sendType, "success"));
                                    break;

                                default:
                            }
                        });

                        statisticsInfoMap.put(id, statisticsInfo);
                    }
                });
            }

        } catch (Exception e) {
            logger.error("setStatisticsInfoMap error {}",e.getMessage(), e);
        }

    }

    private void updateRedirection(String batchDate, String statisticsDate) {

        Map<String, StatisticsInfo> statisticsInfoMap = new HashMap<>();
        List<Map<String,Object>> list = eventCodeRepository.getSummaryEventRedirection();

        if (list != null && !list.isEmpty() ) {

            for (Map<String,Object> map : list) {

                String id = CommonUtils.dataNvl(map.get("CAMPAIGN_ID"));

                try {

                    if (!StringUtils.isEmpty(id)) {
                        StatisticsInfo statisticsInfo = statisticsInfoMap.get(id);

                        if (statisticsInfo == null ) {
                            statisticsInfo = new StatisticsInfo();
                            statisticsInfo.setKey(id);
                        }

                        BigDecimal bigDecimal = CommonUtils.bigDecimalNvl(map.get("REDIRECTION"));
                        long redirection = bigDecimal.longValue();
                        statisticsInfo.setRedirection(statisticsInfo.getRedirection() + redirection);

                        statisticsInfoMap.put(id, statisticsInfo);
                    }


                } catch (Exception e) {
                    logger.error("updateRedirection error [{}] {}", id, e.getMessage(), e);
                }
            }

        }

        //logger.debug("updateRedirection >>>>>>>>>\n {}", JsonViewUtils.objectToJson(statisticsInfoMap));

        Set<String> keys = statisticsInfoMap.keySet();

        for (String key : keys) {

            try {
                StatisticsInfo statisticsInfo = statisticsInfoMap.get(key);
                long id = StringUtils.string2long(statisticsInfo.getKey());


                campaignRepository.updateRedirection(id,
                        statisticsInfo.getRedirection(),
                        statisticsDate);

            } catch (Exception e) {
                logger.error("updateRedirection Error [{}] {}", key, e.getMessage(), e);
            }
        }
    }

    private void updateOrderInfo(String batchDate, String statisticsDate) {

        Map<String, StatisticsInfo> statisticsInfoMap = new HashMap<>();

        int month = -6;
        String searchDate = DateUtils.addMonth(batchDate, month);
        StatisticsParam param = new StatisticsParam();
        param.setBeginSearchDate(searchDate+"000000");

        List<CampaignStatistics> list = orderMapper.getCampaignOrderStatisticsListByParam(param);

        if (list != null && !list.isEmpty() ) {

            for (CampaignStatistics s : list) {

                String id = s.getKey();

                try {

                    if (!StringUtils.isEmpty(id)) {
                        StatisticsInfo statisticsInfo = statisticsInfoMap.get(id);

                        if (statisticsInfo == null ) {
                            statisticsInfo = new StatisticsInfo();
                            statisticsInfo.setKey(id);
                        }

                        long orderCount = s.getOrderCount();
                        long orderAmount = s.getOrderAmount();
                        statisticsInfo.setOrderCount(statisticsInfo.getOrderCount() + orderCount);
                        statisticsInfo.setOrderAmount(statisticsInfo.getOrderAmount() + orderAmount);

                        statisticsInfoMap.put(id, statisticsInfo);
                    }


                } catch (Exception e) {
                    logger.error("updateOrderInfo not CampaignId [{}] {}", id, e.getMessage(), e);
                }
            }
        }

        //logger.debug("updateOrderInfo >>>>>>>>>\n {}", JsonViewUtils.objectToJson(statisticsInfoMap));

        Set<String> keys = statisticsInfoMap.keySet();

        for (String key : keys) {

            try {
                StatisticsInfo statisticsInfo = statisticsInfoMap.get(key);
                long id = StringUtils.string2long(statisticsInfo.getKey());


                campaignRepository.updateOrderInfo(id,
                        statisticsInfo.getOrderAmount(),
                        statisticsInfo.getOrderCount(),
                        statisticsDate);

            } catch (Exception e) {
                logger.error("updateOrderInfo Error [{}] {}", key, e.getMessage(), e);
            }
        }
    }

    @Override
    public List<UmsStatisticsTable> getLogTables(String... months) {

        HttpHeaders headers = umsApiService.getHttpHeaders();
        String url = getHost() + "/api/statistics/statistics-tables";

        URI uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("tables", String.join(",", months))
                .build()
                .toUri();

        RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET, uri);

        ResponseEntity<List<UmsStatisticsTable>> response
                = umsAgentRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<UmsStatisticsTable>>() {});
        return response.getBody();
    }

    private Campaign getAutoMonthCampaign(String batchDate) {
        String autoMonth = batchDate.substring(0, 6);

        CampaignDto autoDto = new CampaignDto();
        autoDto.setAutoMonth(autoMonth);
        AtomicLong autoId = new AtomicLong(0L);
        Iterable<Campaign> autoIterable = campaignRepository.findAll(autoDto.getPredicate());

        autoIterable.forEach(c ->{
            if(autoId.get() == 0) {
                autoId.set(c.getId());
            }
        });

        Campaign autoCampaign = null;

        if (autoId.get() > 0) {
            autoCampaign = campaignRepository.getOne(autoId.get());
        } else {

            Campaign newAutoCampaign = new Campaign();
            StringBuffer sb = new StringBuffer();
            sb.append(autoMonth.substring(0,4)+ "년 ");
            sb.append(autoMonth.substring(4,6)+ "월 ");
            sb.append("자동발송");
            newAutoCampaign.setTitle(sb.toString());
            newAutoCampaign.setContent(sb.toString());
            newAutoCampaign.setSendType("0");
            newAutoCampaign.setMessageType("0");
            newAutoCampaign.setBatchFlag(true);
            newAutoCampaign.setAutoMonth(autoMonth);
            newAutoCampaign.setLevelId(-1);

            autoCampaign = campaignRepository.save(newAutoCampaign);
        }

        return autoCampaign;
    }

    private void insertSentForUser(String sentType, List<String> tables) {

        if (!tables.isEmpty()) {

            StatisticsParam param = new StatisticsParam();
            param.setTables(tables);
            param.setTableType(sentType);
            param.setId(getId());

            List<UmsSendInfo> umsSendInfos = getUmsSendInfos(param);

            umsSendInfos.forEach(umsSendInfo -> {
                param.setCampaignKey(umsSendInfo.getCampaignKey());
                setStatisticsInfoMapForUser(sentType, param, false);
            });
        }
    }

    private void insertAutoSentForUser(String sentType, List<String> tables, Campaign campaign) {

        if (!tables.isEmpty()) {

            String key = StringUtils.long2string(campaign.getId());

            List<String> autoTables = new ArrayList<>();
            autoTables.add(tables.get(0));

            StatisticsParam param = new StatisticsParam();
            param.setTables(autoTables);
            param.setTableType(sentType);
            param.setAutoFlag(true);
            param.setCampaignKey(key);
            param.setId(getId());

            setStatisticsInfoMapForUser(sentType, param, true);
        }

    }

    private void saveStatisticsInfoTempForUser(UmsStatistics searchUmsStatistics,
                                               StatisticsParam param, String sentType, boolean isSaveLogFlag) {
        if (searchUmsStatistics != null) {
            List<CampaignStatistics> statistics = searchUmsStatistics.getList();

            if (statistics != null && !statistics.isEmpty()) {

                List<SmsLogTemp> smsLogTemp = new ArrayList<>();
                List<MmsLogTemp> mmsLogTemp = new ArrayList<>();
                List<PushLogTemp> pushLogTemp = new ArrayList<>();
                List<KakaoLogTemp> kakaoLogTemp = new ArrayList<>();

                List<Long> msgkeys = new ArrayList<>();

                try {
                    for (CampaignStatistics s : statistics) {
                        if ("sms".equals(sentType)) {
                            smsLogTemp.add(new SmsLogTemp(s));

                        } else if ("mms".equals(sentType)) {
                            mmsLogTemp.add(new MmsLogTemp(s));

                        } else if ("push".equals(sentType) || "push-batch".equals(sentType)) {
                            pushLogTemp.add(new PushLogTemp(s));

                        } else if ("kakao".equals(sentType) || "kakao-sms".equals(sentType) || "kakao-mms".equals(sentType)) {
                            kakaoLogTemp.add(new KakaoLogTemp(s));
                        }

                        msgkeys.add(s.getMsgkey());
                    }

                    smsLogTempRepository.saveAll(smsLogTemp);
                    mmsLogTempRepository.saveAll(mmsLogTemp);
                    pushLogTempRepository.saveAll(pushLogTemp);
                    kakaoLogTempRepository.saveAll(kakaoLogTemp);

                    if (isSaveLogFlag) {
                        CampaignSendLogDto sendLogDto = new CampaignSendLogDto();
                        sendLogDto.setCampaignKey(param.getCampaignKey());
                        sendLogDto.setMsgkeys(msgkeys);

                        Iterable<CampaignSendLog> iterable = campaignSendLogRepository.findAll(sendLogDto.getPredicate());
                        List<CampaignSendLog> sendLogs = new ArrayList<>();
                        iterable.iterator().forEachRemaining(sendLogs::add);

                        for (CampaignStatistics s : statistics) {

                            CampaignSendLog sendLog = sendLogs.stream()
                                    .filter(l-> s.getMsgkey().equals(l.getMsgkey()))
                                    .findFirst().orElse(null);

                            if (sendLog != null) {
                                sendLog.setType(sentType);
                                sendLog.setSent(s.getSent());
                                sendLog.setSuccess(s.getSuccess());
                                sendLog.setPushReceive(s.getPushReceive());
                            } else {
                                sendLogs.add(new CampaignSendLog(sentType, s));
                            }
                        }
                        campaignSendLogRepository.saveAll(sendLogs);
                    }

                } catch (Exception e) {
                    logger.error("setStatisticsInfoMapForUser Error [{}] {}", param, e.getMessage(), e);
                }
            }

        }
    }

    private void setStatisticsInfoMapForUser(String sentType, StatisticsParam param, boolean isSaveLogFlag) {

        // 최초 1회 페이징 정보를 조회를 위해 조회
        int pageSize = 1000;

        try {
            param.setPageSize(pageSize);
            param.setPage(1);
            List<String> tables = param.getTables();
            param.setLogTable(tables.get(0));

            // 로그 테이블 조회
            tables.forEach(table -> {
                List<String> logTables = new ArrayList<>();
                logTables.add(table);

                int currentPage = 1;

                param.setTables(logTables);
                param.setLogTableFlag(true);

                UmsStatistics baseUmsStatistics = getUserList(param);
                if (baseUmsStatistics != null) {
                    UmsStatisticsPage statisticsPage = baseUmsStatistics.getPage();

                    for (int i=0; i<statisticsPage.getTotalPages(); i++) {
                        param.setPage(currentPage);
                        saveStatisticsInfoTempForUser(baseUmsStatistics, param, sentType, isSaveLogFlag);
                    }


                    currentPage++;
                }

            });


            // 메인 테이블 조회
            param.setLogTableFlag(false);

            int currentPage = 1;
            UmsStatistics baseUmsStatistics = getUserList(param);

            if (baseUmsStatistics != null) {
                UmsStatisticsPage statisticsPage = baseUmsStatistics.getPage();

                for (int i=0; i<statisticsPage.getTotalPages(); i++) {
                    param.setPage(currentPage);
                    saveStatisticsInfoTempForUser(baseUmsStatistics, param, sentType, isSaveLogFlag);
                }

                currentPage++;
            }

        } catch (Exception e) {
            logger.error("setStatisticsInfoMapForUser Error [{}] {}", param, e.getMessage(), e);
        }

    }

    private void updateCampaignUser(String statisticsDate) {

        try {
            campaignUserRepository.updateCampaignUserSmsSent(statisticsDate);
            campaignUserRepository.updateCampaignUserMmsSent(statisticsDate);
            campaignUserRepository.updateCampaignUserKakaoSent(statisticsDate);
            campaignUserRepository.updateCampaignUserPushSent(statisticsDate);

        } catch (Exception e) {
            logger.error("updateCampaignUser Error [{}] {}", e);
        }
    }

    private void updateOrderInfoForUser(String batchDate, String statisticsDate) {
        int month = -6;
        String searchDate = DateUtils.addMonth(batchDate, month);
        StatisticsParam param = new StatisticsParam();
        param.setBeginSearchDate(searchDate+"000000");

        List<CampaignStatistics> list = orderMapper.getCampaignOrderStatisticsListByParamForUser(param);

        if (list != null && !list.isEmpty() ) {

            for (CampaignStatistics s : list) {

                try {

                    campaignRepository.updateOrderInfoForUser(
                            StringUtils.string2long(s.getKey()),
                            s.getUserId(),
                            s.getOrderAmount(),
                            s.getOrderCount(),
                            statisticsDate);

                } catch (Exception e) {
                    logger.error("updateOrderInfo Error [{}] {}", s.getKey(), e.getMessage(), e);
                }
            }
        }

    }

    @Override
    public UmsStatistics getUserList(StatisticsParam param) {

        try {

            String url = getHost() + "/api/statistics/page-user-list";

            HttpHeaders headers = umsApiService.getHttpHeaders();

            URI uri = UriComponentsBuilder
                    .fromHttpUrl(url)
                    .queryParam("tableType", param.getTableType())
                    .queryParam("tables", String.join(",", param.getTables()))
                    .queryParam("campaignKey", param.getCampaignKey())
                    .queryParam("autoFlag", param.isAutoFlag())
                    .queryParam("openFlag", param.isOpenFlag())
                    .queryParam("batchFlag", param.isBatchFlag())
                    .queryParam("beginSearchDate", param.getBeginSearchDate())
                    .queryParam("endSearchDate", param.getEndSearchDate())
                    .queryParam("id", param.getId())
                    .queryParam("page", param.getPage())
                    .queryParam("pageSize", param.getPageSize())
                    .queryParam("logTableFlag",param.isLogTableFlag())
                    .queryParam("logTable",param.getLogTable())
                    .build()
                    .toUri();

            RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET, uri);

            ResponseEntity<UmsStatistics> response = umsAgentRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<UmsStatistics>() {});
            return response.getBody();
        } catch (Exception e) {
            logger.error("getUserList error {}", e.getMessage(), e);
            return null;
        }
    }

}
