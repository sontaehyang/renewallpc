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
import saleson.common.notification.domain.CampaignStatistics;
import saleson.common.notification.support.StatisticsParam;
import saleson.common.utils.CommonUtils;
import saleson.model.campaign.*;
import saleson.shop.campaign.CampaignRepository;
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
    RestTemplate customRestTemplate;

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

    @Override
    public void updateCampaignSentBatch(String batchDate) throws Exception {
        String statisticsDate = DateUtils.getToday(Const.DATETIME_FORMAT);
        String month1 = batchDate.substring(0,6);
        String month2 = DateUtils.addMonth(batchDate, 1).substring(0,6);

        List<String> smsTables = getLogTables("sms_log_", month1, month2);
        List<String> kakaoSmsTables = getLogTables("ms_kko_sms_msg_log_", month1, month2);
        List<String> mmsTables = getLogTables("mms_log_", month1, month2);
        List<String> kakaoMmsTables = getLogTables("ms_kko_mms_msg_log_", month1, month2);

        List<String> pushTables = getLogTables("tmb_pushmsg_log_", month1, month2);
        List<String> pushBatchTables = getLogTables("tmb_pushmsg_batch_log_", month1, month2);
        List<String> kakaoTables = getLogTables("ms_kko_msg_log_", month1, month2);

        Map<String, StatisticsInfo> statisticsInfoMap = new HashMap<>();

        updateSent(statisticsInfoMap, "sms", smsTables);
        updateSent(statisticsInfoMap, "mms", mmsTables);
        updateSent(statisticsInfoMap,"push", pushTables);
        updateSent(statisticsInfoMap,"push-batch", pushBatchTables);
        updateSent(statisticsInfoMap, "kakao", kakaoTables);
        updateSent(statisticsInfoMap, "kakao-sms", kakaoSmsTables);
        updateSent(statisticsInfoMap, "kakao-mms", kakaoMmsTables);

        Map<String, StatisticsInfo> autoStatisticsInfoMap = new HashMap<>();
        Campaign campaign = getAutoMonthCampaign(batchDate);

        updateAutoSent(autoStatisticsInfoMap, "sms", smsTables, campaign);
        updateAutoSent(autoStatisticsInfoMap, "mms", mmsTables, campaign);
        updateAutoSent(autoStatisticsInfoMap,"push", pushTables, campaign);
        updateAutoSent(autoStatisticsInfoMap,"push-batch", pushBatchTables, campaign);
        updateAutoSent(autoStatisticsInfoMap, "kakao", kakaoTables, campaign);
        updateAutoSent(autoStatisticsInfoMap, "kakao-sms", kakaoSmsTables, campaign);
        updateAutoSent(autoStatisticsInfoMap, "kakao-mms", kakaoMmsTables, campaign);

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

        insertSentForUser("sms", smsTables);
        insertSentForUser("mms", mmsTables);
        insertSentForUser("push", pushTables);
        insertSentForUser("push-batch", pushBatchTables);
        insertSentForUser("kakao", kakaoTables);
        insertSentForUser("kakao-sms", kakaoSmsTables);
        insertSentForUser("kakao-mms", kakaoMmsTables);

        insertAutoSentForUser("sms", smsTables, campaign);
        insertAutoSentForUser("mms", mmsTables, campaign);
        insertAutoSentForUser("push", pushTables, campaign);
        insertAutoSentForUser("push-batch", pushBatchTables, campaign);
        insertAutoSentForUser("kakao", kakaoTables, campaign);
        insertAutoSentForUser("kakao-sms", kakaoSmsTables, campaign);
        insertAutoSentForUser("kakao-mms", kakaoMmsTables, campaign);

        // 사용자별 통계 업데이트
        updateCampaignUser(statisticsDate);

        // 사용자별 주문통계 업데이트
        updateOrderInfoForUser(batchDate, statisticsDate);
    }

    private void updateAutoSent(Map<String, StatisticsInfo> statisticsInfoMap, String sentType, List<String> tables, Campaign campaign) {

        if (!tables.isEmpty()) {

            String key = StringUtils.long2string(campaign.getId());

            StatisticsParam param = new StatisticsParam();
            param.setTables(tables);
            param.setTableType(sentType);
            param.setAutoFlag(true);
            param.setCampaignKey(key);
            param.setId(environment.getProperty("ums.sub.id"));

            setStatisticsInfoMap(statisticsInfoMap, sentType, param);

        }

    }

    private void updateSent(Map<String, StatisticsInfo> statisticsInfoMap, String sentType, List<String> tables) {

        if (!tables.isEmpty()) {

            StatisticsParam param = new StatisticsParam();
            param.setTables(tables);
            param.setTableType(sentType);
            param.setId(environment.getProperty("ums.sub.id"));

            setStatisticsInfoMap(statisticsInfoMap, sentType, param);

        }
    }

    private void setStatisticsInfoMap(Map<String, StatisticsInfo> statisticsInfoMap, String sentType, StatisticsParam param) {
        String url = environment.getProperty("ums.api.url") + "/api/statistics/list";

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

        ResponseEntity<List<CampaignStatistics>> response = customRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<CampaignStatistics>>() {});

        List<CampaignStatistics> statistics = response.getBody();
        logger.debug("statistics {}", JsonViewUtils.objectToJson(statistics));

        if (statistics != null && !statistics.isEmpty()) {

            for (CampaignStatistics s : statistics ) {

                String id = s.getKey();

                try {
                    long sent = s.getSent();
                    String type = s.getType();

                    StatisticsInfo statisticsInfo = statisticsInfoMap.get(id);

                    if (statisticsInfo == null) {
                        statisticsInfo = new StatisticsInfo();
                        statisticsInfo.setKey(id);
                    }

                    if (statisticsInfo != null) {

                        switch (sentType) {
                            case "sms":
                            case "kakao-sms":

                                if ("sent".equals(type)) {
                                    statisticsInfo.setSmsSent(statisticsInfo.getSmsSent() + sent);
                                } else if ("success".equals(type)) {
                                    statisticsInfo.setSmsSuccess(statisticsInfo.getSmsSuccess() + sent);
                                }

                                break;
                            case "mms":
                            case "kakao-mms":

                                if ("sent".equals(type)) {
                                    statisticsInfo.setMmsSent(statisticsInfo.getMmsSent() + sent);
                                } else if ("success".equals(type)) {
                                    statisticsInfo.setMmsSuccess(statisticsInfo.getMmsSuccess() + sent);
                                }

                                break;
                            case "push":
                            case "push-batch":

                                if ("sent".equals(type)) {
                                    statisticsInfo.setPushSent(statisticsInfo.getPushSent() + sent);
                                } else if ("success".equals(type)) {
                                    statisticsInfo.setPushSuccess(statisticsInfo.getPushSuccess() + sent);
                                } else if ("open".equals(type)) {
                                    statisticsInfo.setPushReceive(statisticsInfo.getPushReceive() + sent);
                                }

                                break;

                            case "kakao":

                                if ("sent".equals(type)) {
                                    statisticsInfo.setKakaoSent(statisticsInfo.getKakaoSent() + sent);
                                } else if ("success".equals(type)) {
                                    statisticsInfo.setKakaoSuccess(statisticsInfo.getKakaoSuccess() + sent);
                                }

                                break;

                            default:
                        }

                        statisticsInfoMap.put(id, statisticsInfo);
                    }

                } catch (Exception e) {
                    logger.error("updateCampaignSentBatch not CampaignId [{}] {}", id, e.getMessage(), e);
                }
            }

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

    private List<String> getLogTables(String prefix, String... months) throws JsonProcessingException {

        String schema = "MSAGENT";

        List<String> list = new ArrayList<>();

        HttpHeaders headers = umsApiService.getHttpHeaders();
        String url = environment.getProperty("ums.api.url") + "/api/statistics/count";

        for (String month : months) {

            String tableName = prefix+month;

            URI uri = UriComponentsBuilder
                    .fromHttpUrl(url)
                    .queryParam("schema", schema)
                    .queryParam("name", tableName)
                    .build()
                    .toUri();

            RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET, uri);

            ResponseEntity<String> response = customRestTemplate.exchange(requestEntity, String.class);

            HashMap<String, Object> body = (HashMap<String, Object>) JsonViewUtils.jsonToObject(response.getBody(), new TypeReference<HashMap<String, Object>>() {});
            String tableCount = body.get("tableCount").toString();

            if (Integer.parseInt(tableCount) > 0) {
                list.add(month);
            }
        }
        return list;
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
            param.setId(environment.getProperty("ums.sub.id"));

            setStatisticsInfoMapForUser(sentType, param);
        }
    }

    private void insertAutoSentForUser(String sentType, List<String> tables, Campaign campaign) {

        if (!tables.isEmpty()) {

            String key = StringUtils.long2string(campaign.getId());

            StatisticsParam param = new StatisticsParam();
            param.setTables(tables);
            param.setTableType(sentType);
            param.setAutoFlag(true);
            param.setCampaignKey(key);
            param.setId(environment.getProperty("ums.sub.id"));

            setStatisticsInfoMapForUser(sentType, param);
        }

    }

    private void setStatisticsInfoMapForUser(String sentType, StatisticsParam param) {
        String url = environment.getProperty("ums.api.url") + "/api/statistics/user-list";

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

        ResponseEntity<List<CampaignStatistics>> response = customRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<CampaignStatistics>>() {});

        List<CampaignStatistics> statistics = response.getBody();
        logger.debug("statistics {}", JsonViewUtils.objectToJson(statistics));

        if (statistics != null && !statistics.isEmpty()) {
            List<SmsLogTemp> smsLogTemp = new ArrayList<>();
            List<MmsLogTemp> mmsLogTemp = new ArrayList<>();
            List<PushLogTemp> pushLogTemp = new ArrayList<>();
            List<KakaoLogTemp> kakaoLogTemp = new ArrayList<>();

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
                }

                smsLogTempRepository.saveAll(smsLogTemp);
                mmsLogTempRepository.saveAll(mmsLogTemp);
                pushLogTempRepository.saveAll(pushLogTemp);
                kakaoLogTempRepository.saveAll(kakaoLogTemp);

            } catch (Exception e) {
                logger.error("setStatisticsInfoMapForUser Error [{}] {}", param, e.getMessage(), e);
            }
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

}
