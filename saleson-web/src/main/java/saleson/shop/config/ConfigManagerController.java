package saleson.shop.config;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import saleson.common.enumeration.mapper.EnumMapper;
import saleson.common.utils.ShopUtils;
import saleson.model.ConfigPg;
import saleson.model.google.analytics.ConfigGoogleAnalytics;
import saleson.shop.config.domain.Config;
import saleson.shop.config.support.PointSaveDayPropertyEditor;
import saleson.shop.config.support.PointUsePropertyEditor;
import saleson.shop.deliveryhope.DeliveryHopeService;
import saleson.shop.deliveryhope.domain.DeliveryHope;
import saleson.shop.google.analytics.GoogleAnalyticsService;
import saleson.shop.order.pg.config.ConfigPgService;
import saleson.shop.point.PointService;
import saleson.shop.point.domain.PointConfig;
import saleson.shop.policy.PolicyService;
import saleson.shop.policy.domain.Policy;
import saleson.shop.policy.support.PolicyParam;
import saleson.shop.rankingconfig.RankingConfigService;
import saleson.shop.rankingconfig.domain.RankingConfig;
import saleson.shop.zipcode.ZipcodeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/opmanager/config/**")
@RequestProperty(title = "쇼핑몰 환경 설정", layout = "default")
public class ConfigManagerController {
    private static final Logger log = LoggerFactory.getLogger(ConfigManagerController.class);

    @Autowired
    ConfigService configService;

    @Autowired
    PointService pointService;

    @Autowired
    DeliveryHopeService deliveryHopeService;

    @Autowired
    private ZipcodeService zipcodeService;

    @Autowired
    private RankingConfigService rankingConfigService;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private ConfigPgService configPgService;

    @Autowired
    EnumMapper enumMapper;

    @Autowired
    private GoogleAnalyticsService googleAnalyticsService;

    @GetMapping("deny/edit")
    public String list(RequestContext requestContext, Model model, Config config,
                       @RequestParam(value = "page", defaultValue = "1") int page) {

        int configCount = configService.getShopConfigCount(config.getShopConfigId());
        String deniedId = config.getDeniedId();
        config = configService.getShopConfig(config.getShopConfigId());
        List<String> list = new ArrayList<>();
        String[] deniedIds = config.getDeniedId().split(",");

        List<String> deniedIdList;

        if (StringUtils.isEmpty(deniedId)) {
            deniedIdList = Arrays.stream(deniedIds)
                    .collect(Collectors.toList());
        } else {
            deniedIdList = Arrays.stream(deniedIds)
                    .filter(id->id.contains(deniedId))
                    .collect(Collectors.toList());
        }

        int perPage = 10;
        int length = deniedIdList.size();
        int lastPage = page * perPage;
        int indexPage = lastPage - 10;
        if (deniedIdList != null && length > 0) {
            for (int i = 0; i < length; i++) {

                String id = deniedIdList.get(i);

                if ((page != 1 && indexPage <= i && lastPage > i)) {
                    // 비어있지 않은 경우 해당 index 확인 후 있으면 add
                    if (deniedId != null) {
                        if (id.indexOf(deniedId) > -1) {
                            list.add(id);
                        }
                    } else {
                        list.add(id);
                    }
                } else if (perPage > i && page == 1) {
                    // 비어있지 않은 경우 해당 index 확인 후 있으면 add
                    if (deniedId != null) {
                        if (id.indexOf(deniedId) > -1) {
                            list.add(id);
                        }
                    } else {
                        list.add(id);
                    }
                }
            }
        }

        Pagination pagination = Pagination.getInstance(length, perPage);
        model.addAttribute("configCount", configCount);
        model.addAttribute("config", config);
        model.addAttribute("list", list);
        model.addAttribute("pagination", pagination);

        return ViewUtils.getView("/config/deny/list");
    }


    /**
     * 회원가입 항목 설정
     *
     * @param requestContext
     * @param config
     * @return
     */
    @PostMapping("deny/edit")
    public String editAction(RequestContext requestContext, Config config) {

        String massage = MessageUtils.getMessage("M00289");
        configService.updateShopConfigDeniedId(config);

        return ViewUtils.redirect("/opmanager/config/deny/edit", massage);
    }


    /**
     * 회원등록 불가능 ID 등록, 수정, 삭제
     *
     * @param requestContext
     * @param config
     * @return
     */
    @PostMapping("deny/editDeniedId")
    public String editDeniedId(RequestContext requestContext, Config config) {
        String message = MessageUtils.getMessage("M00289");

        String deniedKey = config.getDeniedKey();
        String deniedId = config.getDeniedId();
        int configId = config.getShopConfigId();

        config = configService.getShopConfig(config.getShopConfigId());
        StringBuffer deniedIds = new StringBuffer();
        if (config.getDeniedId() != null) {

            // 삭제인 경우
            if (deniedKey.equals("1")) {
                for (int i = 0; i < config.getDeniedId().split(",").length; i++) {
                    // 삭제 대상인것을 제외한 나머지것들을 add 후 update
                    if (!(config.getDeniedId().split(",")[i].indexOf(deniedId) > -1)) {
                        if (i == config.getDeniedId().split(",").length - 1) {
                            deniedIds.append(config.getDeniedId().split(",")[i]);
                        } else {
                            deniedIds.append(config.getDeniedId().split(",")[i] + ",");
                        }
                    }
                }
                // 등록인 경우
            } else {
                message = MessageUtils.getMessage("M00288");
                deniedIds.append(config.getDeniedId());
                deniedIds.append(",");
                deniedIds.append(deniedId);
            }
        }
        Config paramConfig = new Config();
        paramConfig.setDeniedId(deniedIds.toString());
        paramConfig.setShopConfigId(configId);

        configService.updateShopConfigDeniedId(paramConfig);


        return ViewUtils.redirect("/opmanager/config/deny/edit", message);
    }

    @GetMapping("/opmanager/config/deny/form")
    public String createDeniedId(Model model) {
        return ViewUtils.view();
    }

    /**
     * 사이트 정책 설정
     *
     * @param requestContext
     * @param model
     * @param type
     * @return
     */
    @GetMapping("policy/edit/{type}")
    public String policyEdit(RequestContext requestContext, Model model,
                             @PathVariable("type") String type,
                             @RequestParam(value = "id", defaultValue = "0") int id) {


        String policyType = this.getPolicyType(type);

        if (StringUtils.isEmpty(policyType)) {
            throw new PageNotFoundException();
        }

        PolicyParam policyParam = new PolicyParam();
        policyParam.setPolicyType(policyType);

        Policy policy;

        if (id > 0) {
            policy = policyService.getPolicyByParam(id, policyType);
        } else {
            policy = policyService.getCurrentPolicyByType(policyType);
        }

        model.addAttribute("policy", policy);
        model.addAttribute("policyList", policyService.getPolicyListByParam(policyParam));
        model.addAttribute("type", type);

        return ViewUtils.getView("/config/policy/create");
    }

    /**
     * 사이트 정책 설정 수정
     *
     * @param requestContext
     * @param policy
     * @param type
     * @return
     */
    @PostMapping("policy/edit/{type}")
    public String policyEditAction(RequestContext requestContext, Policy policy,
                                   @PathVariable("type") String type) {


        String agreementType = this.getPolicyType(type);

        if (StringUtils.isEmpty(agreementType)) {
            return ViewUtils.redirect("/opmanager/config/policy/edit/" + type, "정책 타입이 맞지 않습니다.");
        }

        policy.setPolicyType(agreementType);

        policyService.insertPolicy(policy);

        return ViewUtils.redirect("/opmanager/config/policy/edit/" + type, MessageUtils.getMessage("M00289"));
    }

    private String getPolicyType(String type) {

        String agreementType = "";

        if (type.equals("agreement")) {
            agreementType = Policy.POLICY_TYPE_AGREEMENT;
        } else if (type.equals("protect-policy")) {
            agreementType = Policy.POLICY_TYPE_PROTECT_POLICY;
        } else if (type.equals("trader-raw")) {
            agreementType = Policy.POLICY_TYPE_TRADER_RAW;
        }

        return agreementType;
    }

    /**
     * 사이트 설정
     *
     * @param requestContext
     * @param model
     * @return
     */
    @GetMapping("site-config")
    public String siteConfig(RequestContext requestContext, Model model) {

        int configCount = configService.getShopConfigCount(1);
        Config config = configService.getShopConfig(Config.SHOP_CONFIG_ID);
        model.addAttribute("config", config);
        model.addAttribute("configCount", configCount);

        return ViewUtils.view();
    }

    /**
     * 사이트 설정 수정
     *
     * @param requestContext
     * @param model
     * @param config
     * @return
     */
    @PostMapping("site-config")
    public String siteConfigAction(RequestContext requestContext, Model model, Config config) {

        configService.updateShopSiteConfig(config);

        return ViewUtils.redirect("/opmanager/config/site-config", MessageUtils.getMessage("M00289"));
    }

    /**
     * 컨버젼 태그 설정
     *
     * @param requestContext
     * @param model
     * @return
     */
    @GetMapping("conversion-tag")
    public String conversionTag(RequestContext requestContext, Model model) {

        int configCount = configService.getShopConfigCount(1);
        Config config = configService.getShopConfig(1);

        model.addAttribute("config", config);
        model.addAttribute("configCount", configCount);

        return ViewUtils.view();
    }

    /**
     * 컨버전 태그 설정 수정
     *
     * @param requestContext
     * @param model
     * @param config
     * @return
     */
    @PostMapping("conversion-tag")
    public String conversionTagAction(RequestContext requestContext, Model model, Config config) {

        configService.updateShopConfigConversionTag(config);

        return ViewUtils.redirect("/opmanager/config/conversion-tag", MessageUtils.getMessage("M00289"));
    }


    /**
     * 컨버젼태그 안내가이드 팝업
     *
     * @return
     */
    @RequestProperty(title = "컨버젼태그 안내가이드", layout = "base")
    @GetMapping("popup/conversion-popup")
    public String conversionTagPopup() {

        return ViewUtils.view();
    }


    /**
     * 상점 기본 설정
     *
     * @param requestContext
     * @param model
     * @return
     */
    @GetMapping("shop-config")
    public String shopConfig(RequestContext requestContext, Model model) {

        int configCount = configService.getShopConfigCount(Config.SHOP_CONFIG_ID);
        Config config = configService.getShopConfig(Config.SHOP_CONFIG_ID);

        //thumbnailSize-> json형식데이터 [2017-06-08]minae.yun
        String jsonSize = config.getThumbnailSize();

        model.addAttribute("jsonSize", jsonSize);
        model.addAttribute("config", config);
        model.addAttribute("configCount", configCount);

        return ViewUtils.view();
    }

    /**
     * 상점 기본 설정 저장
     *
     * @param requestContext
     * @param model
     * @param config
     * @return
     */
    @SuppressWarnings("unchecked")
    @PostMapping("shop-config")
    public String shopConfigAction(RequestContext requestContext, Model model, Config config) {

        //썸네일이미지 설정 정보를 Json데이터로 저장[2017-06-08]minae.yun
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonArrayObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();


        if (config == null || config.getSize() == null || config.getSizeName() == null) {
            return ViewUtils.redirect("/opmanager/config/shop-config", "정보를 정확히 입력해 주세요.");
        }

        String size[] = config.getSize();
        String sizeName[] = config.getSizeName();

        //size와 sizeName이 정상입력된것만 insert
        for (int i = 0; i < size.length; i++) {
            if (!sizeName[i].isEmpty() && !size[i].isEmpty()) {
                jsonObject = new JSONObject();
                jsonObject.put("sizeName", sizeName[i]);
                jsonObject.put("size", size[i]);
            }
            jsonArray.add(jsonObject);
        }
        //jsonArray을 list이름으로 json에 담음.
        jsonArrayObject.put("list", jsonArray);
        String jsonString = jsonArrayObject.toString();
        config.setThumbnailSize(jsonString);

        configService.updateShopConfig(config);

        return ViewUtils.redirect("/opmanager/config/shop-config", MessageUtils.getMessage("M00289"));
    }

    /**
     * 결제 정보 설정
     *
     * @param requestContext
     * @param model
     * @return
     */
    @GetMapping("payment-config")
    public String paymentConfig(RequestContext requestContext, Model model) {

        int configCount = configService.getShopConfigCount(1);
        Config config = configService.getShopConfig(1);

        model.addAttribute("config", config);
        model.addAttribute("configCount", configCount);

        return ViewUtils.view();
    }

    /**
     * 결제 정보 설정 저장
     *
     * @param requestContext
     * @param model
     * @return
     */
    @PostMapping("payment-config")
    public String paymentConfigAction(RequestContext requestContext, Model model, Config config) {

        configService.updateShopConfigPayment(config);

        return ViewUtils.redirect("/opmanager/config/payment-config", MessageUtils.getMessage("M00289"));
    }


    /**
     * 적립금/소비세 설정
     *
     * @param requestContext
     * @param model
     * @return
     */
    @GetMapping("point")
    public String reserveConfig(RequestContext requestContext, Model model) {

        Config config = configService.getShopConfig(Config.SHOP_CONFIG_ID);
        List<PointConfig> pointConfig = pointService.getShopPointConfig();

        model.addAttribute("config", config);
        model.addAttribute("pointConfig", pointConfig);
        model.addAttribute("hours", ShopUtils.getHours());

        return ViewUtils.getView("/config/point");
    }

    /**
     * 적립금/소비세 저장
     *
     * @param requestContext
     * @param model
     * @return
     */
    @PostMapping("point")
    public String reserveConfigAction(Config config) {

        // 오라클에서는 빈값이 자동으로 업데이트가 진행이 안되어서 구문 추가
        // 2017.06.22 youngki.kim
        if (StringUtils.isEmpty(config.getRepeatDayStartTime())) {
            config.setRepeatDayStartTime("00");
        }

        if (StringUtils.isEmpty(config.getRepeatDayEndTime())) {
            config.setRepeatDayEndTime("23");
        }

        configService.updateShopConfigReserve(config);

        return ViewUtils.redirect("/opmanager/config/point", MessageUtils.getMessage("M00289"));
    }


    /**
     * 배송/반품/환불/교환안내 조회
     *
     * @param requestContext
     * @param model
     * @return
     */
    @GetMapping("delivery")
    public String deliveryConfig(RequestContext requestContext, Model model) {

        Config config = configService.getShopConfig(Config.SHOP_CONFIG_ID);

        model.addAttribute("config", config);

        return ViewUtils.getView("/config/delivery");
    }

    /**
     * 배송/반품/환불/교환안내 수정
     *
     * @param config
     * @return
     */
    @PostMapping("delivery")
    public String deliveryConfigAction(Config config) {

        configService.updateShopConfigDeliveryInfo(config);

        return ViewUtils.redirect("/opmanager/config/delivery", MessageUtils.getMessage("M00289"));
    }

    /**
     * 배송희망일 조회
     *
     * @param requestContext
     * @param model
     * @return
     */
    @GetMapping("delivery-hope")
    public String deliveryHopeConfig(RequestContext requestContext, Model model) {

        Config config = configService.getShopConfig(Config.SHOP_CONFIG_ID);

        List<DeliveryHope> deliveryHopeList = deliveryHopeService.getDeliveryHopeList();

        model.addAttribute("config", config);
        model.addAttribute("deliveryHopeList", deliveryHopeList);

        return ViewUtils.getView("/config/delivery-hope");
    }

    /**
     * 배송희망일 수정
     *
     * @param config
     * @return
     */
    @PostMapping("delivery-hope")
    public String deliveryHopeConfigAction(Config config) {

        configService.updateShopConfigDeliveryHope(config);

        return ViewUtils.redirect("/opmanager/config/delivery-hope", MessageUtils.getMessage("M00289"));
    }

    /**
     * 배송희망 시간 추가
     *
     * @param deliveryHope
     * @return
     */
    @PostMapping("delivery-hope/insert")
    public String insertDeliveryHope(DeliveryHope deliveryHope) {

        deliveryHopeService.insertDeliveryHope(deliveryHope);

        return ViewUtils.redirect("/opmanager/config/delivery-hope");
    }

    /**
     * 배송희망 시간 수정
     *
     * @param deliveryHope
     * @return
     */
    @PostMapping("delivery-hope/update")
    public String updateDeliveryHope(DeliveryHope deliveryHope) {

        deliveryHopeService.updateDeliveryHope(deliveryHope);

        return ViewUtils.redirect("/opmanager/config/delivery-hope", MessageUtils.getMessage("M00289"));
    }

    /**
     * 배송희망 시간 삭제
     *
     * @param deliveryHopeId
     * @return
     */
    @PostMapping("delivery-hope/delete/{deliveryHopeId}")
    public String deleteDeliveryHope(@PathVariable("deliveryHopeId") int deliveryHopeId) {

        deliveryHopeService.deleteDeliveryHope(deliveryHopeId);

        return ViewUtils.redirect("/opmanager/config/delivery-hope", MessageUtils.getMessage("M00205"));    // 삭제되었습니다.
    }


    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(int.class, "pointSaveAfterDay", new PointSaveDayPropertyEditor());
        dataBinder.registerCustomEditor(int.class, "pointUseMax", new PointUsePropertyEditor());
        dataBinder.registerCustomEditor(int.class, "pointUseMin", new PointUsePropertyEditor());
        dataBinder.registerCustomEditor(int.class, "pointExpirationMonth", new PointUsePropertyEditor());
        dataBinder.registerCustomEditor(int.class, "shippingCouponExpirationMonth", new PointUsePropertyEditor());
    }

    /**
     * 상품 랭킹 산정 기준 조회
     *
     * @param requestContext
     * @param model
     * @return
     */
    @GetMapping("ranking-config")
    public String RankingConfig(RequestContext requestContext, Model model,
                                @RequestParam(value = "code", required = true, defaultValue = "default") String code) {

        model.addAttribute("rankingConfig", rankingConfigService.getRankingConfigByRankConfigCode(code));

        return ViewUtils.view();
    }

    @PostMapping("ranking-config")
    public String updateRankingConfig(RequestContext requestContext, Model model, RankingConfig rankingConfig) {

        rankingConfigService.updateItemRankingConfig(rankingConfig);

        return ViewUtils.redirect("/opmanager/config/ranking-config?code=" + rankingConfig.getRankConfigCode());
    }

    @GetMapping("policy/list")
    public String policyList(@ModelAttribute("policyParam") PolicyParam policyParam, Model model) {

        if (policyParam.getItemsPerPage() == 0) {
            policyParam.setItemsPerPage(10);
        }

        int totalCount = policyService.getCountPolicyListByParam(policyParam);
        Pagination pagination = Pagination.getInstance(totalCount, policyParam.getItemsPerPage());
        policyParam.setPagination(pagination);

        model.addAttribute("policyList", policyService.getPolicyListByParam(policyParam));
        model.addAttribute("pagination", pagination);
        model.addAttribute("totalCount", totalCount);

        return ViewUtils.getView("/config/policy/list");
    }

    @GetMapping("policy/detail/{policyId}")
    public String updateGetPolicy(@PathVariable int policyId, Model model) {

        model.addAttribute("policy", policyService.getPolicyByPolicyId(policyId));

        return ViewUtils.getView("/config/policy/form");
    }

    @PostMapping("policy/detail/{policyId}")
    public String updatePostPolicy(@ModelAttribute("policy") Policy policy) {

        String message = "";
        if (policy != null) {
            message = "수정되었습니다";
            policyService.updatePolicy(policy);
        } else {
            message = "수정 실패했습니다.";
        }

        return ViewUtils.getView("/config/policy/form", message);
    }

    @GetMapping("policy/create")
    public String createGetPolicy(@ModelAttribute("policy") Policy policy) {
        return ViewUtils.getView("/config/policy/form");
    }

    @PostMapping("policy/create")
    public String createPostPolicy(Policy policy) {
        if (policy != null) {
            policyService.createPolicy(policy);
        }

        return "redirect:/opmanager/config/policy/list";
    }

    @GetMapping("pg")
    @RequestProperty(template = "opmanager")
    public String configPg(Model model) {

        ConfigPg configPg = configPgService.getConfigPg();

        if (configPg == null) {
            configPg = new ConfigPg();
        }


        model.addAttribute("configPg", configPg);
        model.addAttribute("pgTypes", enumMapper.get("PgType"));
        model.addAttribute("cashbillServiceTypes", enumMapper.get("CashbillServiceType"));

        return "view:/opmanager/config/pg/form";
    }


    @PostMapping("pg")
    public String configPgAction(ConfigPg configPg) {

        configPgService.saveConfigPg(configPg);

        return "redirect:/opmanager/config/pg";
    }

    @GetMapping("google-analytics")
    @RequestProperty(template = "opmanager")
    public String configGoogleAnalytics(Model model) {

        ConfigGoogleAnalytics configGoogleAnalytics = googleAnalyticsService.getConfig();

        if (configGoogleAnalytics == null ) {
            configGoogleAnalytics = new ConfigGoogleAnalytics();
        }

        model.addAttribute("configGoogleAnalytics", configGoogleAnalytics);

        return "view:/opmanager/config/google-analytics/form";
    }

    @PostMapping("google-analytics")
    public String configGoogleAnalyticsAction(ConfigGoogleAnalytics configGoogleAnalytics) {

        googleAnalyticsService.saveConfig(configGoogleAnalytics);

        return "redirect:/opmanager/config/google-analytics";
    }

    @GetMapping("order-temp-config")
    @RequestProperty(template = "opmanager")
    public String orderTempConfig(RequestContext requestContext, Model model) {

        int configCount = configService.getShopConfigCount(1);
        Config config = configService.getShopConfig(1);

        model.addAttribute("config", config);
        model.addAttribute("configCount", configCount);

        return ViewUtils.view();
    }

    @PostMapping("order-temp-config")
    public String orderTempConfigAction(RequestContext requestContext, Model model, Config config) {

        configService.updateShopConfig(config);

        return ViewUtils.redirect("/opmanager/config/order-temp-config", MessageUtils.getMessage("M00289"));
    }

    /**
     * 상품 설정 (상/하단 내용)
     * @param model
     * @return
     */
    @GetMapping("item-config")
    public String itemConfig(Model model) {

        Config config = configService.getShopConfig(Config.SHOP_CONFIG_ID);
        model.addAttribute("config", config);

        return ViewUtils.view();
    }

    /**
     * 상품 설정 수정 (상/하단 내용)
     * @param config
     * @return
     */
    @PostMapping("item-config")
    public String itemConfigAction(Config config) {

        configService.updateShopConfigItem(config);

        return ViewUtils.redirect("/opmanager/config/item-config", MessageUtils.getMessage("M00289"));
    }
}
