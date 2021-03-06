package saleson.common.notification.domain;

import com.onlinepowers.framework.notification.message.OpMessage;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.config.SalesonProperty;
import saleson.common.enumeration.UmsType;
import saleson.model.Ums;
import saleson.model.UmsDetail;
import saleson.model.campaign.ApplicationInfo;

import java.time.LocalTime;
import java.util.*;

public class UmsTemplate implements OpMessage {

    private static final Logger logger = LoggerFactory.getLogger(UmsTemplate.class);

    private HashMap<String, String> codeMap;
    private HashMap<String, String> codeViewMap;
    private Ums ums;

    private String phoneNumber;
    private String callbackNumber;

    private String title;
    private String message;
    private String[] receivers = new String[1];
    private String smsType;

    private UmsExpansionInfo umsExpansionInfo;

    public UmsTemplate() {
        initCommonCodeMap();
    }

    public UmsTemplate(Ums ums) {
        init(ums);
    }

    public UmsTemplate(Ums ums, String phoneNumber) {
        init(ums);
        this.receivers[0] = phoneNumber;
    }

    public UmsTemplate(Ums ums, String phoneNumber, String callbackNumber) {
        this(ums, phoneNumber);
        this.callbackNumber = callbackNumber;
    }

    public UmsTemplate(Ums ums, String phoneNumber, UmsExpansionInfo umsExpansionInfo) {
        this(ums, phoneNumber);
        this.umsExpansionInfo = umsExpansionInfo;
    }

    public UmsTemplate(Ums ums, String phoneNumber, String callbackNumber, UmsExpansionInfo umsExpansionInfo) {
        this(ums, phoneNumber, callbackNumber);
        this.umsExpansionInfo = umsExpansionInfo;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Collection<String> getMessageReceivers() {
        return null;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String[] getReceivers() {
        return this.receivers;
    }

    @Override
    public String getFrom() {
        return this.callbackNumber;
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public boolean isHTML() {
        return false;
    }

    @Override
    public String[] getCc() { return new String[0]; }

    @Override
    public String[] getBcc() {

        /*
        UMS ????????? ?????????
        ?????? ?????? ????????? ??????

        [0] UMS ????????? ??????
        [1] UMS ????????? ?????? ??????
        [2] UMS ????????? ?????? Json
        [3] UMS ?????? ?????? Json
         */

        String[] infos = {
                getTemplateCode(),
                getApplyCode(),
                getAlimTalkButtons(),
                getUmsExpansionInfoJson()
        };

        return infos;
    }

    @Override
    public String getSmsType() {
        return this.smsType;
    }

    /**
     * code <templateId, messageCode>
     *
     * @return
     */
    public HashMap<String, String> getTemplateCodes() {
        if (codeMap == null) {
            codeMap = new LinkedHashMap<>();
        }

        codeMap.put("order_deposit_wait", "????????????");
        codeMap.put("order_cready_payment", "????????????");
        codeMap.put("order_delivering", "?????????");

        codeMap.put("pwsearch", "PW??????");

        codeMap.put("member_join", "????????????");
        codeMap.put("qna_complete", "????????????");
        codeMap.put("item_restock", "???????????????");
        codeMap.put("confirm_purchase", "????????????");
        codeMap.put("confirm_purchase_request", "???????????? ??????");
        codeMap.put("expiration_coupon", "?????????????????? ??????");
        codeMap.put("birthday_coupon", "???????????? ??????");
        codeMap.put("order_refund", "?????? ??? ???????????? ??????");
        codeMap.put("expiration_point", "????????? ?????? ??????");

        codeMap.put("user_sms", "??????SMS??????");

        return codeMap;
    }

    public String getTemplateCodeTitle(String key) {
        HashMap<String, String> code = this.getTemplateCodes();
        if (ValidationUtils.isNotNull(code.get(key))) {
            return code.get(key);
        }

        return "";
    }

    /**
     * Class ????????? ????????? ??????
     *
     * @param ums
     */
    private void init(Ums ums) {

        this.ums = ums;
        this.codeMap = new HashMap<>();

        initCommonCodeMap();

    }

    /**
     * ?????? ??????
     */
    private void initCommonCodeMap() {

        addCodeMap("site_url", SalesonProperty.getSalesonUrlShoppingmall());
        addCodeViewMap("site_url", "?????? URL");
    }

    public String getReplacementMessage(UmsType umsType) {
        return getReplacementContent(umsType, false);
    }

    public String getReplacementTitle(UmsType umsType) {
        return getReplacementContent(umsType, true);
    }

    private String getReplacementContent(UmsType umsType, boolean isTitle) {
        String message = "";
        List<UmsDetail> details = ums.getDetailList();
        boolean isReplacementMessage = false;


        for (UmsDetail detail : details) {

            if (!isReplacementMessage && umsType == detail.getUmsType()) {

                if (detail.isUsedFlag()) {

                    if (isTitle) {
                        message = detail.getTitle();
                    } else {
                        message = detail.getMessage();
                    }

                    if (!StringUtils.isEmpty(message)) {
                        for (String key : codeMap.keySet()) {
                            String fieldValue = codeMap.get(key);

                            if (StringUtils.isEmpty(fieldValue) || "null".equals(fieldValue)) {
                                continue;
                            }

                            String pattern = this.getTemplatePattern(detail.getUmsType(), key);

                            message = message.replace(pattern, fieldValue);

                        }

                        isReplacementMessage = true;
                    }
                }
            }
        }

        return message;
    }

    /**
     * ???????????? ????????? ????????? ?????????
     *
     * @param codeName
     * @return
     */
    private String getTemplatePattern(UmsType umsType, String codeName) {

        if (UmsType.ALIM_TALK == umsType) {
            return getAlimTalkTemplatePattern(codeName);
        }

        String str = "";
        if (StringUtils.isNotEmpty(codeName)) {
            str = "{" + StringUtils.convertToUnderScore(codeName) + "}";
        }

        return str;
    }

    /**
     * ???????????? ????????? ????????? ?????????
     *
     * @param codeName
     * @return
     */
    private String getAlimTalkTemplatePattern(String codeName) {
        String str = "";
        if (StringUtils.isNotEmpty(codeName)) {
            str = "#{" + StringUtils.convertToUnderScore(codeName) + "}";
        }

        return str;
    }

    public void addCodeMap(String key, String value) {
        if (codeMap == null) {
            codeMap = new HashMap<>();
        }

        codeMap.put(key, value);
    }

    public void addCodeMap(Map<String, String> map) {

        for (String key : map.keySet()) {
            addCodeMap(key, map.get(key));
        }
    }

    public HashMap<String, String> getCodeMap() {
        return codeMap;
    }

    public void addCodeViewMap(String key, String value) {
        if (codeViewMap == null) {
            codeViewMap = new HashMap<>();
        }

        codeViewMap.put(key, value);
    }

    public void addCodeViewMap(Map<String, String> map) {

        for (String key : map.keySet()) {
            addCodeViewMap(key, map.get(key));
        }
    }

    public HashMap<String, String> getCodeViewMap() {
        return codeViewMap;
    }

    public UmsDetail getUmsDetail(UmsType umsType) {
        List<UmsDetail> details = ums.getDetailList();

        for (UmsDetail detail : details) {
            if (umsType.getCode().equals(detail.getUmsType().getCode())) {
                return detail;
            }
        }

        return new UmsDetail();
    }

    public void initialize() {
        this.initialize(0L, null);
    }

    public void initialize(long userId, ApplicationInfo applicationInfo) {

        try {

            Ums ums = getUms();

            String title = "";
            String message = "";
            String smsType = UmsType.MESSAGE.getCode();
            UmsExpansionInfo expansionInfo = new UmsExpansionInfo();

            if (applicationInfo != null) {
                expansionInfo.setLoginId(userId + "");
                expansionInfo.setPushToken(applicationInfo.getPushToken());
                expansionInfo.setApplicationNo(applicationInfo.getApplicationNo());
                expansionInfo.setApplicationVersion(applicationInfo.getApplicationVersion());
                expansionInfo.setUuid(applicationInfo.getUuid());
                expansionInfo.setDeviceType(applicationInfo.getDeviceType());
            }

            if (isValidUms(ums)) {
                String templateCode = ums.getTemplateCode();
                boolean isMessageUsedFlag = false;
                boolean isAlimTalkUsedFlag = false;
                boolean isPushUsedFlag = false;
                boolean isMakeContent = false;
                boolean isAlimTalkFailProcessFlag = false;
                boolean isPushFailProcessFlag = false;
                int messageIndex = 0;

                List<UmsDetail> umsDetails = ums.getDetailList();

                if (umsDetails != null && !umsDetails.isEmpty()) {

                    for (UmsDetail detail : umsDetails) {

                        if (UmsType.ALIM_TALK == detail.getUmsType()) {
                            isAlimTalkUsedFlag = detail.isUsedFlag();
                            isAlimTalkFailProcessFlag = detail.isFailProcessFlag();

                        } else if (UmsType.MESSAGE == detail.getUmsType()) {
                            isMessageUsedFlag = detail.isUsedFlag();
                            detail.setTitle(ums.getTemplateName());
                            messageIndex = umsDetails.indexOf(detail);

                        } else if (UmsType.PUSH == detail.getUmsType()) {
                            isPushUsedFlag = detail.isUsedFlag();
                            detail.setTitle(ums.getTemplateName());
                            isPushFailProcessFlag = detail.isFailProcessFlag();
                        }
                    }

                    // ????????? ????????? ??????????????? ??????????????? ?????? ?????? ???????????? ??????
                    if (applicationInfo == null && (isPushUsedFlag && !isAlimTalkUsedFlag && !isMessageUsedFlag)) {
                        isMessageUsedFlag = true;
                        umsDetails.get(messageIndex).setUsedFlag(true);
                    }
                }

                if (!isMakeContent && isAlimTalkUsedFlag) {
                    title = getReplacementTitle(UmsType.ALIM_TALK);
                    message = getReplacementMessage(UmsType.ALIM_TALK);
                    smsType = UmsType.ALIM_TALK.getCode();
                    isMakeContent = true;
                    expansionInfo.setAlimTalkFailProcessFlag(isAlimTalkFailProcessFlag);
                }

                // ???????????? ???????????? ??????????????????
                if (!isMakeContent && !isAlimTalkUsedFlag && isMessageUsedFlag) {
                    title = getReplacementTitle(UmsType.MESSAGE);
                    message = getReplacementMessage(UmsType.MESSAGE);

                    UmsDetail messageDetail = getUmsDetail(UmsType.MESSAGE);
                    smsType = messageDetail.getSendType();
                    isMakeContent = true;
                }

                if (!isMakeContent && isPushUsedFlag) {
                    title = getReplacementTitle(UmsType.PUSH);
                    message = getReplacementMessage(UmsType.PUSH);
                    smsType = UmsType.PUSH.getCode();
                    isMakeContent = true;
                    expansionInfo.setPushFailProcessFlag(isPushFailProcessFlag);
                }

                Map<String, Object> otherData = new HashMap<>();
                otherData.put("ETC3", expansionInfo.getLoginId());
                expansionInfo.setOtherData(otherData);
            }

            this.title = title;
            this.message = message;
            this.smsType = smsType;
            this.umsExpansionInfo = expansionInfo;

        } catch (Exception e) {
            logger.error("Set Ums Content Error", e);
        }

    }

    public Ums getUms() {
        return ums;
    }

    public UmsType getUmsType() {

        if (getSmsType().equals(UmsType.ALIM_TALK.getCode())) {
            return UmsType.ALIM_TALK;
        }

        if (getSmsType().equals(UmsType.PUSH.getCode())) {
            return UmsType.PUSH;
        }

        if ("sms".equals(getSmsType()) || "lms".equals(getSmsType()) || "mms".equals(getSmsType())) {
            return UmsType.MESSAGE;
        }

        return null;
    }

    public String getTemplateCode() {
        return getUms().getTemplateCode();
    }

    public String getApplyCode() {

        if (UmsType.ALIM_TALK == getUmsType()) {
            UmsDetail umsDetail = getUmsDetail(UmsType.ALIM_TALK);

            return umsDetail.getApplyCode();
        }

        return "";
    }

    public String getAlimTalkButtons() {

        if (UmsType.ALIM_TALK == getUmsType()) {
            UmsDetail umsDetail = getUmsDetail(UmsType.ALIM_TALK);

            return umsDetail.getAlimTalkButtons();
        }

        return "";
    }

    public String getUmsExpansionInfoJson() {

        try {
            UmsExpansionInfo info = this.umsExpansionInfo;

            if (info == null) {
                return "";
            }

            return JsonViewUtils.objectToJson(info);

        } catch (Exception ignore) {
            logger.error("make UmsExpansionInfo error", ignore);
            return "";
        }
    }

    public boolean isValidUms(Ums ums) {

        if (ums == null) {
            return false;
        }

        // ?????? ?????? ??? ?????? ?????? ?????? (21??? ~ 6??? ??????)
        LocalTime startTime = LocalTime.of(20, 59, 59);
        LocalTime endTime = LocalTime.of(6, 0, 1);

        boolean isNightSendFlag = (ums.isNightSendFlag() && !(LocalTime.now().isAfter(startTime) || LocalTime.now().isBefore(endTime))) || !ums.isNightSendFlag();

        return ums.isUsedFlag() && isNightSendFlag;
    }


}
