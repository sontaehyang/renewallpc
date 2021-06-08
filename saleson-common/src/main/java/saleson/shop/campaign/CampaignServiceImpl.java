package saleson.shop.campaign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.notification.message.OpMessage;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import saleson.common.Const;
import saleson.common.config.SalesonProperty;
import saleson.common.enumeration.eventcode.EventCodeType;
import saleson.common.notification.UmsApiService;
import saleson.common.notification.domain.CampaignStatistics;
import saleson.common.notification.domain.CampaignTemplate;
import saleson.common.notification.domain.UmsStatistics;
import saleson.common.notification.domain.UmsStatisticsTable;
import saleson.common.notification.micesoft.MiceMapper;
import saleson.common.notification.sender.MessageSender;
import saleson.common.notification.sender.PushSender;
import saleson.common.notification.support.StatisticsParam;
import saleson.common.utils.EventViewUtils;
import saleson.common.utils.LocalDateUtils;
import saleson.model.campaign.*;
import saleson.shop.campaign.statistics.CampaignStatisticsService;
import saleson.shop.campaign.statistics.domain.MessageInfo;
import saleson.shop.campaign.support.*;
import saleson.shop.coupon.CouponService;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.eventcode.EventCodeRepository;
import saleson.shop.eventcode.EventCodeService;
import saleson.shop.group.GroupService;
import saleson.shop.group.domain.Group;
import saleson.shop.user.UserService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;


@Service("campaignService")
public class CampaignServiceImpl implements CampaignService {

    private static final Logger logger = LoggerFactory.getLogger(CampaignServiceImpl.class);

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CampaignUserRepository campaignUserRepository;

    @Autowired
    private CampaignBatchRepository campaignBatchRepository;

    @Autowired
    private EventCodeRepository eventCodeRepository;

    @Autowired
    private MessageSender smsSender;

    @Autowired
    private PushSender pushSender;

    @Autowired
    Environment environment;

    @Autowired
    MiceMapper miceMapper;

    @Autowired
    CouponService couponService;

    @Autowired
    UserService userService;

    @Autowired
    UmsApiService umsApiService;

    @Autowired
    RestTemplate customRestTemplate;

    @Autowired
    private ApplicationInfoRepository applicationInfoRepository;

    @Autowired
    GroupService groupService;

    @Autowired
    private CampaignRegularRepository campaignRegularRepository;

    @Autowired
    private EventCodeService eventCodeService;

    @Autowired
    private CampaignSendLogRepository campaignSendLogRepository;

    @Override
    public Optional<Campaign> getCampaign(Long id) {
        return campaignRepository.findById(id);
    }

    @Override
    public Page<Campaign> getCampaignList(Predicate predicate, Pageable pageable) {
        return campaignRepository.findAll(predicate, pageable);
    }

    @Override
    public void insertCampaign(Campaign campaign, CampaignUserParam campaignUserParam) {
        Campaign saveCampaign = campaignRepository.save(campaign);

        // 즉시 발송일 경우에만 CAMPAIGN_USER에 insert (등록시점과 발송시점 사이에 탈퇴회원, 수신정보가 변경될 수 있음)
        if ("0".equals(campaign.getReserve()) && saveCampaign.getId() != null && saveCampaign.getId() > 0) {
            insertCampaignUser(saveCampaign.getId(), campaignUserParam, campaign.getSendType());
        }
    }

    @Override
    public long getCampaignBatchListCount(Predicate predicate) {
        return campaignBatchRepository.count(predicate);
    }

    @Override
    public Page<CampaignBatch> getCampaignBatchList(Predicate predicate, Pageable pageable) {
        return campaignBatchRepository.findAll(predicate, pageable);
    }

    @Override
    public void deleteCampaign(Long id) throws Exception {

        Campaign storeCampaign = campaignRepository.findById(id)
                .orElseThrow(() -> new UserException("캠페인 정보가 없습니다.", "/opmanager/ums/list"));

        campaignRepository.deleteById(id);

        if (storeCampaign.getUrlList().size() > 0) {
            eventCodeRepository.deleteCampaignUrlByCampaignId(storeCampaign.getId());
        }

        List<Long> userIds = campaignUserRepository.getCampaignUserByCampaignId(id);
        if (userIds.size() > 0) {
            campaignUserRepository.deleteCampaignUserById(id);
        }
    }

    @Override
    public void insertCampaignUser(Long campaignId, CampaignUserParam campaignUserParam, String sendType) {
        String loginId = campaignUserParam.getLoginId();
        String userName = campaignUserParam.getUserName();
        List<String> groupCodes = campaignUserParam.getGroupCodes();
        List<Integer> levelIds = campaignUserParam.getLevelIds();
        int startOrderAmount1 = campaignUserParam.getStartOrderAmount1();
        int endOrderAmount1 = campaignUserParam.getEndOrderAmount1();
        int startOrderAmount2 = campaignUserParam.getStartOrderAmount2();
        int endOrderAmount2 = campaignUserParam.getEndOrderAmount2();
        String lastLoginDate = campaignUserParam.getLastLoginDate();
        int cartCount = campaignUserParam.getCartCount();
        int startCartAmount = campaignUserParam.getStartCartAmount();
        int endCartAmount = campaignUserParam.getEndCartAmount();
        List<String> receiveSms = campaignUserParam.getReceiveSmss();
        List<String> receivePush = campaignUserParam.getReceivePushs();

        if ("%%".equals(userName) || "%%".equals(loginId)) {
            userName = userName.replaceAll("%%", "");
            loginId = loginId.replaceAll("%%", "");
        }

        // PUSH 우선
        if ("2".equals(sendType)) {
            if (!StringUtils.isEmpty(loginId)) {

                campaignUserRepository.insertCampaignUserByBatch2
                        (LocalDateTime.now(), loginId, campaignId, groupCodes, levelIds, startOrderAmount1, endOrderAmount1,
                                startOrderAmount2, endOrderAmount2, lastLoginDate, cartCount, startCartAmount, endCartAmount, receiveSms, receivePush);
            } else if (!StringUtils.isEmpty(userName)) {
                campaignUserRepository.insertCampaignUserByBatch3
                        (LocalDateTime.now(), userName, campaignId, groupCodes, levelIds, startOrderAmount1, endOrderAmount1,
                                startOrderAmount2, endOrderAmount2, lastLoginDate, cartCount, startCartAmount, endCartAmount, receiveSms, receivePush);
            } else {
                campaignUserRepository.insertCampaignUserByBatch1
                        (LocalDateTime.now(), campaignId, groupCodes, levelIds, startOrderAmount1, endOrderAmount1,
                                startOrderAmount2, endOrderAmount2, lastLoginDate, cartCount, startCartAmount, endCartAmount, receiveSms, receivePush);
            }

        } else {
            if (!StringUtils.isEmpty(loginId)) {

                campaignUserRepository.insertCampaignUserByBatchWhereLoginId
                        (LocalDateTime.now(), loginId, campaignId, groupCodes, levelIds, startOrderAmount1, endOrderAmount1,
                                startOrderAmount2, endOrderAmount2, lastLoginDate, cartCount, startCartAmount, endCartAmount, receiveSms, receivePush);
            } else if (!StringUtils.isEmpty(userName)) {
                campaignUserRepository.insertCampaignUserByBatchWhereUserName
                        (LocalDateTime.now(), userName, campaignId, groupCodes, levelIds, startOrderAmount1, endOrderAmount1,
                                startOrderAmount2, endOrderAmount2, lastLoginDate, cartCount, startCartAmount, endCartAmount, receiveSms, receivePush);
            } else {
                campaignUserRepository.insertCampaignUserByBatchBase
                        (LocalDateTime.now(), campaignId, groupCodes, levelIds, startOrderAmount1, endOrderAmount1,
                                startOrderAmount2, endOrderAmount2, lastLoginDate, cartCount, startCartAmount, endCartAmount, receiveSms, receivePush);
            }
        }
    }

    @Override
    public void insertCampaignBatch() {

        // 1. 기존데이터 삭제
        campaignBatchRepository.deleteCampaignBatch();
        campaignBatchRepository.deleteCampaignBatchPoint();

        String today = DateUtils.getToday(Const.DATE_FORMAT);
        String interval = DateUtils.addMonth(today, -3);
        String intervalDate = interval + "000000";


        // 2. 데이터 입력
        campaignBatchRepository.insertCampaignBatch(LocalDateTime.now(), intervalDate, interval);

        campaignBatchRepository.insertCampaignBatchPoint(today);

        String vendor = SalesonProperty.getConfigDatabaseVendor();

        switch (vendor) {
            case "mysql": campaignBatchRepository.updateCampaignBatchForPointByMysql(); break;
            case "oracle": campaignBatchRepository.updateCampaignBatchForPointByOracle(); break;
            case "postgres": campaignBatchRepository.updateCampaignBatchForPointByPostgres(); break;
            default:
        }

        campaignBatchRepository.deleteCampaignBatchPoint();

    }

    @Override
    public void sendCampaign(Campaign campaign) {
        CampaignUserDto dto = new CampaignUserDto();
        dto.setCampaignId(campaign.getId());

        // PUSH 우선 - PUSH 수신거부자는 SMS 전송
        if ("2".equals(campaign.getSendType())) {
            dto.setReceivePush("0");
            campaign.setSendType("1");

            getCampaignUserForSendMessage(campaign, dto);

            dto.setReceivePush("1");
            dto.setReceiveSms("0");
            campaign.setSendType("0");

            getCampaignUserForSendMessage(campaign, dto);

        } else {

            getCampaignUserForSendMessage(campaign, dto);

        }
    }

    public void getCampaignUserForSendMessage(Campaign campaign, CampaignUserDto dto) {
        String callbackNumber = environment.getProperty("ums.callback.number");
        int pageSize = 500;

        Predicate predicate = dto.getPredicate();
        Sort sortBy = Sort.by("pk").descending();

        Page<CampaignUser> firstPage = campaignUserRepository.findAll(predicate, PageRequest.of(0, pageSize, sortBy));
        long totalPages = firstPage.getTotalPages();

        if (totalPages > 0) {
            for (int i = 0; i < totalPages; i++) {
                Page<CampaignUser> page = campaignUserRepository.findAll(predicate, PageRequest.of(i, pageSize, sortBy));
                sendCampaignMessage(campaign, page.getContent(), callbackNumber);
            }
        }
    }

    private void sendCampaignMessage(Campaign campaign, List<CampaignUser> campaignUsers, String callbackNumber) {
        List<OpMessage> messages = new ArrayList<>();
        String eventUrl = EventViewUtils.getPrefixUrl(EventCodeType.CAMPAIGN);

        for (CampaignUser user : campaignUsers) {
            messages.add(new CampaignTemplate(campaign, user, callbackNumber, eventUrl));
        }

        try {

            if (messages != null && !messages.isEmpty()) {
                String sendType = campaign.getSendType();

                if ("0".equals(sendType)) {
                    smsSender.sendSmsList(messages);
                } else if ("1".equals(sendType)) {
                    pushSender.sendMessages(messages);
                }
            }

        } catch (Exception e) {
            logger.error("sendCampaignMessage [{}] {}", campaign, e.getMessage(), e);
        }
    }




    @Override
    public void insertCampaignMessageBatch() {
        CampaignRegularDto regularDto = new CampaignRegularDto();
        Iterable<CampaignRegular> regularIterable = campaignRegularRepository.findAll(regularDto.getSendPredicate());

        List<Long> regularIds = new ArrayList<>();
        List<Campaign> newCampaign = new ArrayList<>();

        if (regularIterable != null) {
            regularIterable.forEach(campaignRegulars -> {
                try {
                    setEventCodesForRegular(campaignRegulars);

                } catch (Exception e) {
                    logger.error("setEventCodes [{}] {}", campaignRegulars, e.getMessage(), e);
                }

                regularIds.add(campaignRegulars.getId());
                newCampaign.add(new Campaign(campaignRegulars));
            });

            campaignRepository.saveAll(newCampaign);
        }

        CampaignDto dto = new CampaignDto();

        Iterable<Campaign> iterable = campaignRepository.findAll(dto.getSendPredicate());
        AtomicBoolean pushSmsFlag = new AtomicBoolean(false);

        List<Group> groups = groupService.getGroupsAndUserLevelsAll();
        CampaignBatchDto campaignBatchDto = new CampaignBatchDto();

        iterable.forEach(campaign -> {
            campaignBatchDto.setWhere(campaign.getSearchWhere());
            campaignBatchDto.setQuery(campaign.getQuery());
            campaignBatchDto.setGroupCode(campaign.getGroupCode());
            campaignBatchDto.setLevelId(campaign.getLevelId());
            campaignBatchDto.setStartOrderAmount1(campaign.getStartOrderAmount1());
            campaignBatchDto.setEndOrderAmount1(campaign.getEndOrderAmount1());
            campaignBatchDto.setStartOrderAmount2(campaign.getStartOrderAmount2());
            campaignBatchDto.setEndOrderAmount2(campaign.getEndOrderAmount2());
            campaignBatchDto.setLastLoginDate(campaign.getLastLoginDate());
            campaignBatchDto.setLastLoginDateType(campaign.getLastLoginDateType());
            campaignBatchDto.setCartCount(campaign.getCartCount());
            campaignBatchDto.setStartCartAmount(campaign.getStartCartAmount());
            campaignBatchDto.setEndCartAmount(campaign.getEndCartAmount());

            if ("0".equals(campaign.getSendType())) {
                campaignBatchDto.setReceiveSms("0");
            } else if ("1".equals(campaign.getSendType())) {
                campaignBatchDto.setReceivePush("0");
            } else if ("2".equals(campaign.getSendType())) {
                campaignBatchDto.setReceiveSms("0");
                campaignBatchDto.setReceivePush("0");
            }

            CampaignUserParam campaignUserParam = new CampaignUserParam(groups, campaignBatchDto);
            insertCampaignUser(campaign.getId(), campaignUserParam, campaign.getSendType());

            if ("2".equals(campaign.getSendType())) {
                pushSmsFlag.set(true);
            }

            // 메시지 발송
            sendCampaign(campaign);

            if (pushSmsFlag.get() == true) {
                campaign.setSendType("2");
            }

            try {
                if (campaign.getCouponId() > 0) {
                    // 쿠폰 발행
                    publishCouponByCampaign(campaign);
                }
            } catch (Exception e) {
                logger.error("insertCampaignMessageBatch not CampaignId [{}] {}", campaign.getId(), e.getMessage(), e);
            }

            // 발송 상태 업데이트
            campaign.setStatus("1");

            // 예약/즉시 발송일시 업데이트
            campaign.setSendDate(null);
            campaign.setSentDate(DateUtils.getToday(Const.DATETIME_FORMAT));

            // 배치 flag 업데이트
            campaign.setBatchFlag(true);
            campaignRepository.save(campaign);
        });

        // 정기발송 상태 업데이트
        List<CampaignRegular> campaignRegular = campaignRegularRepository.findAllById(regularIds);
        campaignRegular.forEach(regular -> {
            regular.setStatus("1");
            campaignRegularRepository.save(regular);
        });

        // 기간이 만료 된 정기발송 상태 업데이트
        Iterable<CampaignRegular> expireRegular = campaignRegularRepository.findAll(regularDto.getExpireSendPredicate());
        expireRegular.forEach(regular -> {
            regular.setStatus("3");
            campaignRegularRepository.save(regular);
        });
    }


    @Override
    public void publishCouponByCampaign(Campaign campaign) throws Exception {

        Coupon coupon = couponService.getCouponById(campaign.getCouponId());

        if (coupon != null) {

            String today = DateUtils.getToday(Const.DATE_FORMAT);
            String dateTime = DateUtils.getToday(Const.DATETIME_FORMAT);

            int couponId = coupon.getCouponId();
            String couponType = coupon.getCouponType();
            String couponName = coupon.getCouponName();
            String couponComment = coupon.getCouponComment();
            String couponApplyType = coupon.getCouponApplyType();
            String couponApplyStartDate = coupon.getCouponApplyStartDate();
            String couponApplyEndDate = coupon.getCouponApplyEndDate();
            int couponPayRestriction = coupon.getCouponPayRestriction();
            String couponConCurrently = coupon.getCouponConcurrently();
            String couponPayType = coupon.getCouponPayType();
            int couponPay = Integer.parseInt(coupon.getCouponPay());
            int couponDiscountLimitPrice = coupon.getCouponDiscountLimitPrice();
            String couponTargetItemType = coupon.getCouponTargetItemType();
            String couponDownloadDate = dateTime;
            String createDate = dateTime;

            String couponMultipleDownloadFlag = coupon.getCouponMulitpleDownloadFlag();

            if ("2".equals(couponApplyType)) {
                couponApplyType = "1";
                couponApplyStartDate = today + "000000";
                couponApplyEndDate = DateUtils.addDay(today, coupon.getCouponApplyDay()) + "235959";
            }

            List<Long> userIds = campaignRepository.selectCouponUserWhereDataStatusCode(campaign.getId(), couponId);

            // 1. INSERT
            campaignRepository.insertCouponByCampaign(campaign.getId(), couponId, couponType,
                    couponName, couponComment, couponApplyType, couponApplyStartDate, couponApplyEndDate,
                    couponPayRestriction, couponConCurrently, couponPayType, couponPay, couponDiscountLimitPrice,
                    couponTargetItemType, couponDownloadDate, createDate);

            // 2. DELETE 할 대상
            if ("N".equals(couponMultipleDownloadFlag) && userIds.size() > 0) {

                campaignRepository.deleteCouponUserWhereMultipleDownloadFlag(userIds);

            }

        }

    }

    @Override
    public List<Map<String, Object>> getUserApplicationInfo(CampaignUserParam campaignUserParam) throws Exception {

        String loginId = campaignUserParam.getLoginId();
        String userName = campaignUserParam.getUserName();

        if (StringUtils.isNotEmpty(loginId)) {
            return applicationInfoRepository.selectApplicationInfoByLoginId(loginId);
        } else if (StringUtils.isNotEmpty(userName)) {
            return applicationInfoRepository.selectApplicationInfoByUserName(userName);
        }

        return applicationInfoRepository.selectApplicationInfo();
    }


    public void updateCampaign(Long campaignId, Campaign campaign, CampaignUserParam campaignUserParam, String status) {
        Campaign storeCampaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new UserException("캠페인 정보가 없습니다.", "/opmanager/ums/list"));

        campaign.setId(storeCampaign.getId());
        campaign.setCreated(storeCampaign.getCreated());
        campaign.setCreatedBy(storeCampaign.getCreatedBy());
        campaign.setVersion(storeCampaign.getVersion());

        if (storeCampaign.getUrlList().size() > 0 && "update".equals(status)) {
            eventCodeRepository.deleteCampaignUrlByCampaignId(storeCampaign.getId());
        }

        campaignRepository.save(campaign);

        // 즉시 발송일 경우에만 CAMPAIGN_USER에 insert (등록시점과 발송시점 사이에 탈퇴회원, 수신정보가 변경될 수 있음)
        if ("1".equals(campaign.getStatus())) {
            insertCampaignUser(campaign.getId(), campaignUserParam, campaign.getSendType());
        }
    }

    private void setEventCodesForRegular(CampaignRegular campaignRegular) throws Exception {
        List<CampaignRegularUrl> orgUrls = campaignRegular.getUrlList();

        if (orgUrls != null && !orgUrls.isEmpty()) {

            boolean reGenerateFlag = false;
            List<String> evnetCodes = new ArrayList<>();

            // DB에서 EVENT CODE 검증
            for (CampaignRegularUrl url : orgUrls) {
                String code = url.getEventCode();

                if (StringUtils.isEmpty(code)) {
                    reGenerateFlag = true;
                    break;
                }

                evnetCodes.add(code);
            }

            if (!reGenerateFlag && evnetCodes != null && !evnetCodes.isEmpty()) {
                reGenerateFlag = eventCodeService.validEventCodes(evnetCodes);
            } else {
                reGenerateFlag = true;
            }

            // DB에 EVENT CODE가 존재하면 재 생성
            if (reGenerateFlag) {
                List<String> eventCodes = eventCodeService.generateEventCodeList(orgUrls.size());
                List<CampaignRegularUrl> urls = new ArrayList<>();
                int urlIndex = 0;
                for (CampaignRegularUrl url : orgUrls) {
                    url.setEventCode(eventCodes.get(urlIndex));
                    urls.add(url);
                    urlIndex++;
                }

                campaignRegular.setUrlList(urls);
            }
        }
    }

    @Override
    public Page<CampaignSendLog> getCampaignSendLogs(Predicate predicate, Pageable pageable) {
        return campaignSendLogRepository.findAll(predicate,pageable);
    }
}
