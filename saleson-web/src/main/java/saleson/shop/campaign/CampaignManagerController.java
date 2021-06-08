package saleson.shop.campaign;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FlashMapUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.Const;
import saleson.common.enumeration.eventcode.EventCodeType;
import saleson.common.enumeration.mapper.EnumMapper;
import saleson.common.notification.domain.CampaignTemplate;
import saleson.common.notification.micesoft.MiceApiService;
import saleson.common.notification.micesoft.MiceMessageSender;
import saleson.common.notification.micesoft.MicePushSender;
import saleson.common.notification.micesoft.domain.api.Calculate;
import saleson.common.notification.micesoft.domain.api.FilePath;
import saleson.common.notification.micesoft.domain.api.ResponseImage;
import saleson.common.notification.micesoft.support.CalculateParam;
import saleson.common.utils.EventViewUtils;
import saleson.common.utils.ShopUtils;
import saleson.model.campaign.*;
import saleson.model.eventcode.EventCode;
import saleson.shop.campaign.statistics.domain.MessageInfo;
import saleson.shop.campaign.support.*;
import saleson.shop.coupon.CouponService;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.eventcode.EventCodeService;
import saleson.shop.group.GroupService;
import saleson.shop.group.domain.Group;
import saleson.shop.group.support.GroupSearchParam;
import saleson.shop.user.UserService;
import saleson.shop.userlevel.UserLevelMapper;
import saleson.shop.userlevel.UserLevelService;
import saleson.shop.userlevel.domain.UserLevel;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/opmanager/campaign")
@RequestProperty(title = "UMS 캠페인", layout = "default", template = "opmanager")
public class CampaignManagerController {
    private static final Logger log = LoggerFactory.getLogger(CampaignManagerController.class);
    private final int SMS_BYTE_LENGTH = 80;

    @Autowired
    CampaignService campaignService;

    @Autowired
    GroupService groupService;

    @Autowired
    UserService userService;

    @Autowired
    UserLevelMapper userLevelMapper;

    @Autowired
    EnumMapper enumMapper;

    @Autowired
    MiceMessageSender smsSender;

    @Autowired
    MicePushSender pushSender;

    @Autowired
    Environment environment;

    @Autowired
    MiceApiService miceApiService;

    @Autowired
    CouponService couponService;

    @Autowired
    private UserLevelService userLevelService;

    @Autowired
    CampaignUserService campaignUserService;

    @Autowired
    CampaignUserRepository campaignUserRepository;

    @Autowired
    CampaignRegularService campaignRegularService;

    @Autowired
    EventCodeService eventCodeService;

    /**
     * UMS 캠페인 목록
     */
    @GetMapping("/list")
    public String getUmsList(Model model, CampaignBatchDto campaignBatchDto,
                             String groupCode,
                             @PageableDefault(sort = "userId", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<CampaignBatch> pageContent = campaignService.getCampaignBatchList(campaignBatchDto.getPredicate(), pageable);

        setInitModelData(model);

        // 그룹/등급 조건 검색 후
        model.addAttribute("groupList", groupService.getAllGroupList());
        model.addAttribute("userLevelGroup", userLevelService.getUserLevelListByGroupCode(groupCode));

        model.addAttribute("campaignBatchDto", campaignBatchDto);
        model.addAttribute("pageContent", pageContent);

        return "view:/campaign/list";
    }

    @GetMapping("/levelList")
    public JsonView setLevelList(String groupCode) {
        return JsonViewUtils.success(userLevelService.getUserLevelListByGroupCode(groupCode));
    }

    @GetMapping({"/create", "/create/{campaignId}", "/list/create", "/list/create/{campaignId}"})
    public String getCreate(@PathVariable(value = "campaignId", required = false) Long campaignId,
                            Model model, CampaignBatchDto campaignBatchDto, EventCode eventCode) throws Exception {

        Campaign campaign = new Campaign();
        String couponName = "";

        if (campaignId != null) {
            // 1. 수정 시 데이터 조회
            campaign = campaignService.getCampaign(campaignId).orElseThrow(()
                    -> new UserException("캠페인 정보가 없습니다.", "/opmanager/campaign/send-list"));

            Coupon coupon = couponService.getCouponById(campaign.getCouponId());
            if (coupon != null) {
                couponName = coupon.getCouponName();
            }

            campaignBatchDto.setCampaignBatchDto(campaign);

        }

        setGroupLevelName(campaignBatchDto, model);

        long count = campaignService.getCampaignBatchListCount(campaignBatchDto.getPredicate());
        int eventCodeSize = 10;

        long smsReceiveCount = smsPushReceiveCount(campaignBatchDto, "0", "");
        long pushReceiveCount = smsPushReceiveCount(campaignBatchDto,"", "0");

        campaignBatchDto.setSendType("2");
        long smsPushReceiveCount = smsPushReceiveCount(campaignBatchDto, "0", "0");

        model.addAttribute("prefixEventView", EventViewUtils.getPrefixUrl(EventCodeType.CAMPAIGN));
        model.addAttribute("eventCodes", eventCodeService.generateEventCodeList(eventCodeSize));
        model.addAttribute("utmQueryString",eventCode);
        model.addAttribute("campaign", campaign);
        model.addAttribute("campaignUserCount", count);
        model.addAttribute("smsReceiveCount", smsReceiveCount);
        model.addAttribute("pushReceiveCount", pushReceiveCount);
        model.addAttribute("smsPushReceiveCount", smsPushReceiveCount);
        model.addAttribute("campaignBatchDto", campaignBatchDto);
        model.addAttribute("couponName", couponName);

        return "view:/campaign/form";
    }

    /**
     * 캠페인 대체코드 팝업
     * @param model
     * @return
     */
    @GetMapping("/code-list")
    @RequestProperty(layout = "base")
    public String codeListPopup(Model model) {

        HashMap<String, String> codeList = new HashMap<>();

        codeList.put("{user_name}", "이름");
        codeList.put("{point}", "포인트");
        codeList.put("{batch_date}", "날짜");

        model.addAttribute("codeList", codeList);

        return "view:/campaign/code-list";

    }

    @PostMapping({"/create", "/create/{campaignId}"})
    public String postCampaignCreate(@PathVariable(value = "campaignId", required = false) Long campaignId,
                             Campaign campaign, CampaignBatchDto campaignBatchDto, EventCode eventCode) {
        try {

            List<Group> groups = groupService.getGroupsAndUserLevelsAll();
            String sendType = campaign.getSendType();

            if ("0".equals(sendType)) {
                campaignBatchDto.setReceiveSms("0");

            } else if ("1".equals(sendType)) {
                campaignBatchDto.setReceivePush("0");

            } else if ("2".equals(sendType)) {
                campaignBatchDto.setReceiveSms("0");
                campaignBatchDto.setReceivePush("0");
            }

            // GA 캠페인 설정
            int contentLength = campaign.getContent().getBytes().length;
            String imageUrl = campaign.getImageUrl();

            String utmSource = "";
            String utmMedium = !StringUtils.isEmpty(campaign.getUtmMedium()) ? "&utm_medium=" + campaign.getUtmMedium() : "";
            String utmCampaign = !StringUtils.isEmpty(campaign.getUtmCampaign()) ? "&utm_campaign=" + campaign.getUtmCampaign() : "";
            String utmItem = !StringUtils.isEmpty(campaign.getUtmItem()) ? "&utm_item=" + campaign.getUtmItem() : "";
            String utmContent =!StringUtils.isEmpty(campaign.getUtmContent()) ? "&utm_content=" + campaign.getUtmContent() : "";

            if (!StringUtils.isEmpty(utmMedium) || !StringUtils.isEmpty(utmCampaign) || !StringUtils.isEmpty(utmItem) || !StringUtils.isEmpty(utmContent)) {

                if ("0".equals(sendType)) {
                    utmSource = "sms";

                    if (contentLength > SMS_BYTE_LENGTH || !StringUtils.isEmpty(imageUrl)) {
                        utmSource = "mms";
                    }

                } else {
                    utmSource = "push";
                }

                campaign.setUtmSource(utmSource);
                utmSource = "&utm_source=" + utmSource;
            }

            String utmQueryString = utmMedium + utmCampaign + utmItem + utmContent + utmSource;
            eventCode.setUtmQueryString(utmQueryString.toLowerCase());

            campaign.setUtmCampaign(campaign.getUtmCampaign().toLowerCase());
            campaign.setUtmContent(campaign.getUtmContent().toLowerCase());
            campaign.setUtmItem(campaign.getUtmItem().toLowerCase());
            campaign.setUtmMedium(campaign.getUtmMedium().toLowerCase());

            CampaignUserParam campaignUserParam = new CampaignUserParam(groups, campaignBatchDto);

            String sendDateTime = null;
            String reserve = campaign.getReserve();

            // 캠페인 즉시발송
            if ("0".equals(reserve)) {
                campaign.setSentDate(DateUtils.getToday(Const.DATETIME_FORMAT));

             // 캠페인 예약발송
            } else if ("1".equals(reserve)) {
                String sendDate = campaign.getSendDate();
                String sendTime = campaign.getSendTime();

                sendDateTime = sendDate + sendTime + "0000";
                campaign.setSendDate(sendDateTime);
            }

            campaign.setSendDate(sendDateTime);

            if (campaign.getUrlList() != null && !campaign.getUrlList().isEmpty()) {
                campaign.getUrlList().forEach(ec->{
                    ec.setType(EventCodeType.CAMPAIGN);
                });
            }

            setEventCodes(campaign, eventCode);

            campaign.setBatchFlag(false);
            campaign.setStatus("0");

            if ("0".equals(reserve)) {
                campaign.setStatus("1");
            }

            if (campaignId != null) {
                // 발송 수단이 MMS에서 PUSH로 변경됐을 시 imageUrl 제거
                if (!StringUtils.isEmpty(campaign.getImageUrl()) && "1".equals(campaign.getSendType())) {
                    campaign.setImageUrl("");
                }

                String status = "update";
                campaignService.updateCampaign(campaignId, campaign, campaignUserParam, status);
            } else {
                campaign.setRegularFlag(false);
                campaignService.insertCampaign(campaign, campaignUserParam);
            }

            // 즉시 발송일 경우만 메시지 발송, 쿠폰 다운로드 처리
            if ("0".equals(reserve)) {
                campaignService.sendCampaign(campaign);
                campaignService.publishCouponByCampaign(campaign);
            }

            if (campaignId != null) {
                FlashMapUtils.setMessage("수정되었습니다.");

                StringBuilder sb = new StringBuilder()
                        .append("redirect:/opmanager/campaign/send-list/");


                return sb.toString();
            }

        } catch (Exception e) {
            log.error("캠페인 등록 에러 : {}", e.getMessage(), e);
        }

        return "redirect:/opmanager/campaign/list";
    }

    private void setEventCodes(Campaign campaign, EventCode eventCode) throws Exception {
        List<EventCode> orgUrls = campaign.getUrlList();

        if (orgUrls != null && !orgUrls.isEmpty()) {

            boolean reGenerateFlag = false;
            List<String> evnetCodes = new ArrayList<>();

            // DB에서 EVENT CODE 검증
            for (EventCode url : orgUrls) {
                String code = url.getEventCode();

                url.setUtmQueryString(eventCode.getUtmQueryString());

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
                List<EventCode> urls = new ArrayList<>();
                int urlIndex = 0;
                for (EventCode url : orgUrls) {
                    url.setEventCode(eventCodes.get(urlIndex));
                    url.setUtmQueryString(eventCode.getUtmQueryString());
                    urls.add(url);
                    urlIndex++;
                }

                    campaign.setUrlList(urls);
            }
        }
    }

    private void setEventCodesForRegular(CampaignRegular campaignRegular, CampaignRegularUrl campaignRegularUrl) throws Exception {
        List<CampaignRegularUrl> orgUrls = campaignRegular.getUrlList();

        if (orgUrls != null && !orgUrls.isEmpty()) {

            boolean reGenerateFlag = false;
            List<String> evnetCodes = new ArrayList<>();

            // DB에서 EVENT CODE 검증
            for (CampaignRegularUrl url : orgUrls) {
                String code = url.getEventCode();

                url.setUtmQueryString(campaignRegularUrl.getUtmQueryString());

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
                    url.setUtmQueryString(campaignRegularUrl.getUtmQueryString());
                    urls.add(url);
                    urlIndex++;
                }

                    campaignRegular.setUrlList(urls);
                }
            }
        }

    @PostMapping("/create/send-test")
    public JsonView sendTest(Campaign campaign, CampaignUser campaignUser) {
        try {

            String callbackNumber = environment.getProperty("ums.callback.number");
            String eventUrl = EventViewUtils.getPrefixUrl(EventCodeType.CAMPAIGN);

            campaign.setSendDate("");
            campaign.setId(0L);

            campaignUser.setPk(new CampaignUserPk(0L, 0L));

            if ("2".equals(campaign.getSendType()) && StringUtils.isEmpty(campaignUser.getPushToken())) {
                campaign.setSendType("0");
            }

            if ("0".equals(campaign.getSendType())) {
                smsSender.sendSms(new CampaignTemplate(campaign, campaignUser, callbackNumber, eventUrl));
            } else {
                pushSender.sendMessage(new CampaignTemplate(campaign, campaignUser, callbackNumber, eventUrl));
            }

        } catch (Exception e) {
            log.error("테스트 발송 에러 : {}", e.getMessage(), e);
            return JsonViewUtils.failure(e.getMessage());
        }

        return JsonViewUtils.success();
    }

    @GetMapping("/create/application-info")
    @RequestProperty(layout = "base")
    public String applicationInfoList(Model model, ApplicationInfoDto applicationInfoDto, CampaignUserParam campaignUserParam,
                                      @PageableDefault(sort = "userId", direction = Sort.Direction.DESC) Pageable pageable) throws Exception {

        List<Map<String, Object>> pageContent = new ArrayList<>();

        String where = applicationInfoDto.getWhere();
        String query = applicationInfoDto.getQuery();

        if (StringUtils.isNotEmpty(where) || StringUtils.isNotEmpty(query)) {
            if (("loginId".equals(where))) {
                campaignUserParam.setLoginId("%" + query + "%");
            } else if ("userName".equals(where)) {
                campaignUserParam.setUserName("%" + query + "%");
            }

            pageContent = campaignService.getUserApplicationInfo(campaignUserParam);
        }

        model.addAttribute("applicationInfoDto", applicationInfoDto);
        model.addAttribute("pageContent", pageContent);
        model.addAttribute("query", query);


        return "view:/campaign/application-info";
    }

    @GetMapping("/create/send-message-list")
    @RequestProperty(layout = "base")
    public String getCampaignSendMessageList(Model model, CampaignDto campaignDto,
                                             @RequestParam(value = "groupCode", required = false) String groupCode,
                                             @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Campaign> pageContent = campaignService.getCampaignList(campaignDto.getSentCampaignPredicate(), pageable);

        setInitModelData(model);
        model.addAttribute("userLevelGroup", userLevelService.getUserLevelListByGroupCode(groupCode));
        model.addAttribute("campaignDto", campaignDto);
        model.addAttribute("pageContent", pageContent);

        return "view:/campaign/send-message-list";
    }

    @GetMapping("/send-list")
    public String getCampaignSendList(Model model, CampaignDto campaignDto,
                                      @RequestParam(value = "groupCode", required = false) String groupCode,
                                      @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Campaign> pageContent = campaignService.getCampaignList(campaignDto.getPredicate(), pageable);

        setInitModelData(model);
        
        // 그룹/등급 조건 검색 후
        model.addAttribute("groupList", groupService.getAllGroupList());
        model.addAttribute("userLevelGroup", userLevelService.getUserLevelListByGroupCode(groupCode));

        model.addAttribute("campaignDto", campaignDto);
        model.addAttribute("pageContent", pageContent);

        return "view:/campaign/send-list";
    }

    /**
     * 발송내역 상세 POPUP
     * @param
     * @return
     */
    @GetMapping("/send-list-detail/{campaignId}")
    @RequestProperty(layout = "base")
    public String getCampaignSendListDetail(Model model, CampaignDto campaignDto,
                                            @PathVariable("campaignId") Long campaignId,
                                            @PageableDefault(sort = "pk", direction = Sort.Direction.DESC) Pageable pageable) {

        CampaignUserDto dto = new CampaignUserDto();
        dto.setCampaignId(campaignId);

        Page<CampaignUser> pageContent = campaignUserService.getCampaignUserList(dto.getPredicate(), pageable);
        String couponName = "";

        if (campaignId != null) {
            Campaign campaign = campaignService.getCampaign(campaignId).orElseThrow(()
                    -> new UserException("정보가 없습니다.", "/opmanager/campaign/send-list"));

            campaignDto.setWhere(campaign.getSearchWhere());
            campaignDto.setGroupCode(campaign.getGroupCode());
            campaignDto.setLevelId(campaign.getLevelId());

            Coupon coupon = couponService.getCouponById(campaign.getCouponId());
            if (coupon != null) {
                couponName = coupon.getCouponName();
            }

            List<EventCode> urls = campaign.getUrlList();

            if (urls != null && !urls.isEmpty()) {

                String prefix = EventViewUtils.getPrefixUrl(EventCodeType.CAMPAIGN);
                String content = campaign.getContent();

                for (EventCode url : urls) {
                    String fieldValue = prefix + "/" + url.getEventCode();
                    String pattern = "{" + StringUtils.convertToUnderScore(url.getChangeCode()) + "}";

                    content = content.replace(pattern, fieldValue);
                }

                campaign.setContent(content);
            }

            model.addAttribute("campaign", campaign);
        }

        GroupSearchParam groupSearchParam = new GroupSearchParam();
        String searchWhere = "";
        String groupName = "전체 그룹";
        String levelName = "전체 등급";

        if ("userName".equals(campaignDto.getWhere())) {
            searchWhere = "이름";
        }
        if ("loginId".equals(campaignDto.getWhere())) {
            searchWhere = "아이디";
        }

        if ("0".equals(campaignDto.getGroupCode())) {
            groupName = "그룹 미지정 회원";

        } else if (!StringUtils.isEmpty(campaignDto.getGroupCode())) {
            groupSearchParam.setGroupCode(campaignDto.getGroupCode());
            List<Group> group = groupService.getGroupList(groupSearchParam);
            groupName = group.get(0).getGroupName();
        }

        if (campaignDto.getLevelId() == 0) {
            levelName = "레벨 미지정 회원";

        } else if (campaignDto.getLevelId() > 0) {
            UserLevel userLevel = userLevelMapper.getUserLevelById(campaignDto.getLevelId());
            levelName = userLevel.getLevelName();
        }

        model.addAttribute("pageContent", pageContent);
        model.addAttribute("searchWhere", searchWhere);
        model.addAttribute("groupName", groupName);
        model.addAttribute("levelName", levelName);
        model.addAttribute("couponName", couponName);

        return "view:/campaign/send-list-detail";
    }

    @PostMapping("/create/mms-image-url")
    public JsonView getMmsImageUrl(@RequestParam(name = "encodingFile", defaultValue = "") String encodingFile) {

        try {

            if (StringUtils.isEmpty(encodingFile)) {
                return JsonViewUtils.failure("인코딩된 파일이 존재하지 않습니다.");
            }

            ResponseImage responseImage = miceApiService.getMmsImageUrl(encodingFile);

            if (responseImage == null) {
                throw new Exception("responseImage null 통신 실패");
            }

            if (!"000".equals(responseImage.getResultCd())) {
                StringBuffer sb = new StringBuffer();
                sb.append("[" + responseImage.getResultCd() + "] ");
                sb.append(responseImage.getResultMsg());

                return JsonViewUtils.failure(sb.toString());
            }

            String url = "";

            List<FilePath> filePaths = responseImage.getList();

            if (filePaths != null && !filePaths.isEmpty()) {
                url = filePaths.get(0).getFilePath();
            }

            return JsonViewUtils.success(url);
        } catch (Exception e) {
            log.error("MMS Image Upload Api Error >>> {}", e.getMessage(), e);
            return JsonViewUtils.failure("업로드에 실패 했습니다.");
        }
    }

    /**
     * 캠페인 예약발송 취소처리
     *
     * @param campaignId
     * @return
     */
    @PostMapping(value = "cancel/{campaignId}")
    public JsonView edit(@PathVariable("campaignId") Long campaignId) throws Exception {

        try {
            Campaign campaign = campaignService.getCampaign(campaignId)
                    .orElseThrow(() -> new UserException("캠페인 정보가 없습니다.", "/opmanager/campaign/send-list"));

            campaign.setStatus("2");
            CampaignUserParam campaignUserParam = new CampaignUserParam();

            String status = "cancel";
            campaignService.updateCampaign(campaignId, campaign, campaignUserParam, status);

        } catch (Exception e) {
            log.error("캠페인 취소 에러 : {}", e.getMessage(), e);
            return JsonViewUtils.failure(e.getMessage());
        }

        return JsonViewUtils.success();
    }

    /**
     * 캠페인 삭제 처리
     *
     * @param campaignId
     * @return
     */
    @PostMapping(value = "delete/{campaignId}")
    public JsonView delete(@PathVariable("campaignId") Long campaignId) throws Exception {

        try {
            campaignService.getCampaign(campaignId)
                    .orElseThrow(() -> new UserException("캠페인 정보가 없습니다.", "/opmanager/campaign/send-list"));

            campaignService.deleteCampaign(campaignId);

        } catch (Exception e) {
            log.error("캠페인 삭제 에러 : {}", e.getMessage(), e);
            return JsonViewUtils.failure(e.getMessage());
        }

        return JsonViewUtils.success();
    }

    private void setInitModelData(Model model) {
        List<Group> groups = groupService.getGroupsAndUserLevelsAll();

        List<Map> amounts = new ArrayList<>();

        amounts.add(getAmountMap("5만원", 50000));
        amounts.add(getAmountMap("10만원", 100000));
        amounts.add(getAmountMap("50만원", 500000));
        amounts.add(getAmountMap("100만원", 1000000));

        model.addAttribute("amounts", amounts);
        model.addAttribute("groups", groups);
    }

    private Map getAmountMap(String label, int value) {
        Map<String, Object> map = new LinkedHashMap();

        map.put("label", label);
        map.put("value", value);

        return map;
    }

    @GetMapping("/calculate")
    public String calculate(Model model, CalculateParam calculateParam) {

        // 초기 진입 오늘날짜 세팅
        if (calculateParam.getSearchYear() == null && calculateParam.getSearchMonth() == null) {
            String today = DateUtils.getToday(Const.DATE_FORMAT);
            String prevDate = DateUtils.addMonth(today, -1);

            calculateParam.setSearchYear(prevDate.substring(0, 4));
            calculateParam.setSearchMonth(prevDate.substring(4, 6));
        }

        String endYear = DateUtils.getToday(Const.YEAR_FORMAT);            //  초기 진입 오늘날짜 년도
        String beginYear = String.valueOf(Integer.parseInt(endYear) - 10); //  -10년 전 년도까지

        model.addAttribute("beginYear", beginYear);
        model.addAttribute("endYear", endYear);
        model.addAttribute("calculateParam", calculateParam);

        return "view";
    }

    // 정산 API 요청
    @GetMapping("/calculate/api")
    public JsonView getCalculateList(CalculateParam calculateParam) {

        try {

            Calculate calculate = miceApiService.getCalculateList(calculateParam.getSearchDate());

            if (calculate == null) {
                throw new Exception("responseImage null 통신 실패");
            }

            if (!"000".equals(calculate.getResultCd())) {
                StringBuffer sb = new StringBuffer();
                sb.append("[" + calculate.getResultCd() + "] ");
                sb.append(calculate.getResultMsg());

                return JsonViewUtils.failure(sb.toString());
            }

            return JsonViewUtils.success(calculate);

        } catch (Exception e) {

            log.error("MMS Api Error >>> {}", e.getMessage(), e);
            return JsonViewUtils.failure("실패 했습니다.");

        }

    }

    // UMS 정기발송 내역
    @GetMapping("/regular/list")
    public String getCampaignRegularSendList(Model model, CampaignRegularDto campaignRegularDto,
                                            @RequestParam(value = "groupCode", required = false) String groupCode,
                                            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<CampaignRegular> pageContent = campaignRegularService.getCampaignRegularList(campaignRegularDto.getPredicate(), pageable);

        setInitModelData(model);

        // 그룹/등급 조건 검색 후
        model.addAttribute("groupList", groupService.getAllGroupList());
        model.addAttribute("userLevelGroup", userLevelService.getUserLevelListByGroupCode(groupCode));

        model.addAttribute("campaignRegularDto", campaignRegularDto);
        model.addAttribute("pageContent", pageContent);
        model.addAttribute("weekDays", ShopUtils.getDayOfWeekList("01234567"));

        return "view:/campaign/regular-list";
    }

    @GetMapping({"/regular/create", "/regular/create/{id}", "/regular/list/create", "/regular/list/create/{id}"})
    public String regularCreate(@PathVariable(value="id", required=false) Long id,
                                Model model, CampaignBatchDto campaignBatchDto, EventCode eventCode) {

        CampaignRegular campaignRegular = new CampaignRegular();
        String couponName = "";

        // 1. 수정 시 데이터 조회
        if (id != null) {
            campaignRegular = campaignRegularService.getCampaignRegular(id).orElseThrow(()
                    -> new UserException("정기발송 정보가 없습니다.", "/opmanager/campaign/regular-list"));

            Coupon coupon = couponService.getCouponById(campaignRegular.getCouponId());
            if (coupon != null) {
                couponName = coupon.getCouponName();
            }

            campaignBatchDto.setCampaignBatchDto(campaignRegular);

        }

        setGroupLevelName(campaignBatchDto, model);

        long count = campaignService.getCampaignBatchListCount(campaignBatchDto.getPredicate());
        int eventCodeSize = 10;

        long smsReceiveCount = smsPushReceiveCount(campaignBatchDto, "0", "");
        long pushReceiveCount = smsPushReceiveCount(campaignBatchDto,"", "0");

        campaignBatchDto.setSendType("2");
        long smsPushReceiveCount = smsPushReceiveCount(campaignBatchDto, "0", "0");

        model.addAttribute("prefixEventView", EventViewUtils.getPrefixUrl(EventCodeType.CAMPAIGN));
        model.addAttribute("eventCodes", eventCodeService.generateEventCodeList(eventCodeSize));
        model.addAttribute("campaignRegular", campaignRegular);
        model.addAttribute("campaignUserCount", count);
        model.addAttribute("utmQueryString", eventCode);
        model.addAttribute("smsReceiveCount", smsReceiveCount);
        model.addAttribute("pushReceiveCount", pushReceiveCount);
        model.addAttribute("smsPushReceiveCount", smsPushReceiveCount);
        model.addAttribute("campaignBatchDto", campaignBatchDto);
        model.addAttribute("couponName", couponName);
        model.addAttribute("weekDays", ShopUtils.getDayOfWeekList("01234567"));

        return "view:/campaign/regular-form";
    }

    @PostMapping({"/regular/create", "/regular/create/{id}"})
    public String postRegularCreate(@PathVariable(value = "id", required = false) Long id,
                                    CampaignBatchDto campaignBatchDto, CampaignRegular campaignRegular, CampaignRegularUrl campaignRegularUrl) {
        try {

            List<Group> groups = groupService.getGroupsAndUserLevelsAll();
            String sendType = campaignRegular.getSendType();

            if (sendType.equals("0")) {
                campaignBatchDto.setReceiveSms("0");

            } else if ("1".equals(sendType)) {
                campaignBatchDto.setReceivePush("0");

            } else if ("2".equals(sendType)) {

                campaignBatchDto.setReceiveSms("0");
                campaignBatchDto.setReceivePush("0");
            }

            // GA 캠페인 설정
            int contentLength = campaignRegular.getContent().getBytes().length;
            String imageUrl = campaignRegular.getImageUrl();

            String utmSource = "";
            String utmMedium = !StringUtils.isEmpty(campaignRegular.getUtmMedium()) ? "&utm_medium=" + campaignRegular.getUtmMedium() : "";
            String utmCampaign = !StringUtils.isEmpty(campaignRegular.getUtmCampaign()) ? "&utm_campaign=" + campaignRegular.getUtmCampaign() : "";
            String utmItem = !StringUtils.isEmpty(campaignRegular.getUtmItem()) ? "&utm_item=" + campaignRegular.getUtmItem() : "";
            String utmContent =!StringUtils.isEmpty(campaignRegular.getUtmContent()) ? "&utm_content=" + campaignRegular.getUtmContent() : "";

            if (!StringUtils.isEmpty(utmMedium) || !StringUtils.isEmpty(utmCampaign) || !StringUtils.isEmpty(utmItem) || !StringUtils.isEmpty(utmContent)) {

                if ("0".equals(sendType)) {
                    utmSource = "sms";

                    if (contentLength > SMS_BYTE_LENGTH || !StringUtils.isEmpty(imageUrl)) {
                        utmSource = "mms";
                    }

                } else {
                    utmSource = "push";
                }

                campaignRegular.setUtmSource(utmSource);
                utmSource = "&utm_source=" + utmSource;
            }

            String utmQueryString = utmMedium + utmCampaign + utmItem + utmContent + utmSource;
            campaignRegularUrl.setUtmQueryString(utmQueryString.toLowerCase());

            campaignRegular.setUtmCampaign(campaignRegular.getUtmCampaign().toLowerCase());
            campaignRegular.setUtmContent(campaignRegular.getUtmContent().toLowerCase());
            campaignRegular.setUtmItem(campaignRegular.getUtmItem().toLowerCase());
            campaignRegular.setUtmMedium(campaignRegular.getUtmMedium().toLowerCase());

            CampaignUserParam campaignUserParam = new CampaignUserParam(groups, campaignBatchDto);

            String startSendDateTime = null;
            String endSendDateTime = null;

            String startSendDate = campaignRegular.getStartSendDate();
            String endSendDate = campaignRegular.getEndSendDate();
            String sendTime = campaignRegular.getSendTime();

            startSendDateTime = startSendDate + sendTime + "0000";
            endSendDateTime = endSendDate + sendTime + "0000";

            campaignRegular.setStartSendDate(startSendDateTime);
            campaignRegular.setEndSendDate(endSendDateTime);

            setEventCodesForRegular(campaignRegular, campaignRegularUrl);

            campaignRegular.setBatchFlag(false);
            campaignRegular.setStatus("0");

            if (id != null) {
                String status = "update";
                campaignRegularService.updateCampaignRegular(id, campaignRegular, campaignUserParam, status);

            } else {
                // 정기발송일 경우 OP_CAMPAIGN_REGULAR에 insert 후 캠페인 예약발송 배치가 실행될 때 OP_CAMPAIGN에 insert
                campaignRegularService.insertCampaignRegular(campaignRegular);
            }

            if (id != null) {
                FlashMapUtils.setMessage("수정되었습니다.");

                StringBuilder sb = new StringBuilder()
                        .append("redirect:/opmanager/campaign/regular/list");

                return sb.toString();
            }

        } catch (Exception e) {
            log.error("정기발송 등록 에러 : {}", e.getMessage(), e);
        }

        return "redirect:/opmanager/campaign/regular/list";
    }

    /**
     * 정기발송 취소처리
     *
     * @param id
     * @return
     */
    @PostMapping(value = "regular/cancel/{id}")
    public JsonView regularEdit(@PathVariable("id") Long id) {

        try {
            CampaignRegular campaignRegular = campaignRegularService.getCampaignRegular(id)
                    .orElseThrow(() -> new UserException("정기발송 정보가 없습니다.", "/opmanager/regular/list"));

            campaignRegular.setStatus("2");
            CampaignUserParam campaignUserParam = new CampaignUserParam();

            String status = "cancel";
            campaignRegularService.updateCampaignRegular(id, campaignRegular, campaignUserParam, status);

        } catch (Exception e) {
            log.error("정기발송 취소 에러 : {}", e.getMessage(), e);
            return JsonViewUtils.failure(e.getMessage());
        }

        return JsonViewUtils.success();
    }

    /**
     * 정기발송 삭제 처리
     *
     * @param id
     * @return
     */
    @PostMapping(value = "regular/delete/{id}")
    public JsonView regularDelete(@PathVariable("id") Long id) {

        try {
            campaignRegularService.getCampaignRegular(id)
                    .orElseThrow(() -> new UserException("정기발송 정보가 없습니다.", "/opmanager/regular/list"));

            campaignRegularService.deleteCampaignRegular(id);

        } catch (Exception e) {
            log.error("정기발송 삭제 에러 : {}", e.getMessage(), e);
            return JsonViewUtils.failure(e.getMessage());
        }

        return JsonViewUtils.success();
    }

    /**
     * 자동 발송내역 상세 POPUP
     * @param
     * @return
     */
    @GetMapping("/send-list-auto/{campaignId}/{autoMonth}")
    @RequestProperty(layout = "base")
    public String getAutoSendDetail(Model model, CampaignSendLogDto dto,
                                    @PathVariable("campaignId") Long campaignId,
                                    @PageableDefault(sort = "sendDate", direction = Sort.Direction.DESC) Pageable pageable) throws Exception {

        if (campaignId != null) {
            dto.setCampaignKey(StringUtils.long2string(campaignId));
        }

        model.addAttribute("dto", dto);
        model.addAttribute("pageContent", campaignService.getCampaignSendLogs(dto.getPredicate(), pageable));

        return "view:/campaign/send-list-auto";
    }

    private long smsPushReceiveCount(CampaignBatchDto campaignBatchDto, String receiveSms, String receivePush) {
        campaignBatchDto.setReceiveSms(receiveSms);
        campaignBatchDto.setReceivePush(receivePush);

        long count = campaignService.getCampaignBatchListCount(campaignBatchDto.getPredicate());

        return count;

    }

    private void setGroupLevelName(CampaignBatchDto campaignBatchDto, Model model) {
        GroupSearchParam groupSearchParam = new GroupSearchParam();
        String groupName = "전체 그룹";
        String levelName = "전체 등급";

        if ("0".equals(campaignBatchDto.getGroupCode())) {
            groupName = "그룹 미지정 회원";

        } else if (!StringUtils.isEmpty(campaignBatchDto.getGroupCode())) {
            groupSearchParam.setGroupCode(campaignBatchDto.getGroupCode());
            List<Group> group = groupService.getGroupList(groupSearchParam);
            groupName = group.get(0).getGroupName();
        }

        if (campaignBatchDto.getLevelId() == 0) {
            levelName = "레벨 미지정 회원";

        } else if (campaignBatchDto.getLevelId() > 0) {
            UserLevel userLevel = userLevelMapper.getUserLevelById(campaignBatchDto.getLevelId());
            levelName = userLevel.getLevelName();
        }

        model.addAttribute("groupName", groupName);
        model.addAttribute("levelName", levelName);
    }

}
