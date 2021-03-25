package saleson.common.notification.domain;

import com.onlinepowers.framework.notification.message.OpMessage;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.CommonUtils;
import saleson.model.campaign.Campaign;
import saleson.model.campaign.CampaignUser;
import saleson.model.eventcode.EventCode;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampaignTemplate implements OpMessage {

    private static final Logger log = LoggerFactory.getLogger(CampaignTemplate.class);

    private HashMap<String, String> codeMap;
    private String callbackNumber;

    private String title;
    private String message;
    private String[] receivers = new String[1];
    private String smsType;

    private UmsExpansionInfo umsExpansionInfo;
    private List<EventCode> campaignUrls;

    private String prefixRedirectEventUrl;

    public CampaignTemplate(Campaign campaign, CampaignUser campaignUser, String callbackNumber, String prefixRedirectEventUrl ) {
        this.prefixRedirectEventUrl = prefixRedirectEventUrl;
        initialize(campaign, campaignUser);
        this.callbackNumber = callbackNumber;
    }

    public CampaignTemplate(Campaign campaign, CampaignUser campaignUser, String callbackNumber ) {
        initialize(campaign, campaignUser);
        this.callbackNumber = callbackNumber;
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
        UMS 서비스 에서는
        필요 정보 확장에 사용

        [0] UMS 템플릿 코드
        [1] UMS 알림톡 승인 코드
        [2] UMS 알림톡 버튼 Json
        [3] UMS 확장 정보 Json
         */

        String[] infos = {
                "",
                "",
                "",
                getUmsExpansionInfoJson()
        };

        return infos;
    }

    @Override
    public String getSmsType() {
        return this.smsType;
    }


    private void initialize(Campaign campaign, CampaignUser campaignUser) {

        initCommonCodeMap();

        int SMS_BYTE_LENGTH = 80;

        if (campaign != null) {
            String phoneNumber = "";
            String title = "";
            String message = "";
            String smsType = "";

            phoneNumber = campaignUser.getPhoneNumber();
            title = campaign.getTitle();
            message = campaign.getContent();

            smsType = campaign.getSendType();

            UmsExpansionInfo expansionInfo = new UmsExpansionInfo();

            Long campaignId = campaign.getId();

            expansionInfo.setImagePath(campaign.getImageUrl());
            expansionInfo.setMessageKey("campaign-"+campaignId);
            expansionInfo.setMessageType(campaign.getMessageType());

            expansionInfo.setLoginId(campaignUser.getPk().getUserId()+"");

            expansionInfo.setPushToken(campaignUser.getPushToken());
            expansionInfo.setApplicationNo(campaignUser.getApplicationNo());
            expansionInfo.setApplicationVersion(campaignUser.getApplicationVersion());
            expansionInfo.setUuid(campaignUser.getUuid());
            expansionInfo.setDeviceType(campaignUser.getDeviceType());

            expansionInfo.setResendType(campaign.getResendType());

            expansionInfo.setReservationDate(campaign.getSendDate());

            Map<String, Object> otherData = new HashMap<>();

            otherData.put("IMG_URL", campaign.getImageUrl());
            otherData.put("ETC1", campaign.getId()+"");
            otherData.put("ETC2", "");
            otherData.put("ETC3", expansionInfo.getLoginId());

            expansionInfo.setOtherData(otherData);

            this.campaignUrls = campaign.getUrlList();
            this.umsExpansionInfo = expansionInfo;
            this.receivers[0] = phoneNumber;
            this.title = getReplacementTitle(campaign, campaignUser);
            this.message = getReplacementMessage(campaign, campaignUser);
            this.smsType = smsType;
        }
    }

    public String getReplacementMessage(Campaign campaign, CampaignUser campaignUser) {
        return getReplacementContent(campaign, false, campaignUser);
    }

    public String getReplacementTitle(Campaign campaign, CampaignUser campaignUser) {
        return getReplacementContent(campaign, true, campaignUser);
    }

    private String getReplacementContent(Campaign campaign, boolean isTitle, CampaignUser campaignUser) {
        String message = "";

        boolean isReplacementMessage = false;

        if (isTitle) {
            message = campaign.getTitle();
        } else {
            message = campaign.getContent();
        }

        if (!StringUtils.isEmpty(message)) {

            List<EventCode> urls = this.campaignUrls;

            if (urls != null && !urls.isEmpty()) {

                String prefix = this.prefixRedirectEventUrl;

                for (EventCode url : urls) {
                    String fieldValue = url.getContents();

                    if (!StringUtils.isEmpty(prefix)) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(prefix);
                        sb.append("/");
                        sb.append(url.getEventCode());
                        sb.append("/");
                        sb.append(campaignUser.getPk().getUserId());
                        fieldValue = sb.toString();
                    }

                    if (StringUtils.isEmpty(fieldValue) || "null".equals(fieldValue)) {
                        continue;
                    }

                    String pattern = this.getTemplatePattern(url.getChangeCode());

                    message = message.replace(pattern, fieldValue);
                }

            }

            String pattern = this.getTemplatePattern("user_name");
            message = message.replace(pattern, campaignUser.getUserName());

            pattern = this.getTemplatePattern("point");
            message = message.replace(pattern, StringUtils.numberFormat(CommonUtils.longNvl(campaignUser.getPoint())));

            if (campaignUser.getBatchDate() == null) {
                pattern = this.getTemplatePattern("batch_date");
                message = message.replace(pattern, DateUtils.getToday("yyyy-MM-dd"));
            } else {
                pattern = this.getTemplatePattern("batch_date");
                message = message.replace(pattern, campaignUser.getBatchDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }

            for (String key : codeMap.keySet()) {
                String fieldValue = codeMap.get(key);

                if (StringUtils.isEmpty(fieldValue) || "null".equals(fieldValue)) {
                    continue;
                }

                pattern = this.getTemplatePattern(key);

                message = message.replace(pattern, fieldValue);

            }

        }

        return message;
    }

    public String getUmsExpansionInfoJson() {

        try {
            UmsExpansionInfo info = this.umsExpansionInfo;

            if (info == null) {
                return "";
            }

            return JsonViewUtils.objectToJson(info);

        } catch (Exception ignore) {
            log.error("make UmsExpansionInfo error", ignore);
            return "";
        }
    }

    public void addCodeMap(String key, String value) {
        if (codeMap == null) {
            codeMap = new HashMap<>();
        }

        codeMap.put(key, value);
    }

    /**
     * 템플릿에 사용된 패턴을 리턴함
     *
     * @param codeName
     * @return
     */
    private String getTemplatePattern(String codeName) {

        String str = "";
        if (StringUtils.isNotEmpty(codeName)) {
            str = "{" + StringUtils.convertToUnderScore(codeName) + "}";
        }

        return str;
    }

    /**
     * 공통 코드
     */
    private void initCommonCodeMap() {

        addCodeMap("site_url", SalesonProperty.getSalesonUrlShoppingmall());
    }
}
