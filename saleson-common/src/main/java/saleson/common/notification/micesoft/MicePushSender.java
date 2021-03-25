package saleson.common.notification.micesoft;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinepowers.framework.notification.message.OpMessage;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import saleson.common.enumeration.DeviceType;
import saleson.common.notification.UmsApiService;
import saleson.common.notification.domain.UmsExpansionInfo;
import saleson.common.notification.micesoft.domain.push.Pushmsg;
import saleson.common.notification.sender.PushSender;
import saleson.common.utils.CommonUtils;

import java.util.*;


public class MicePushSender implements PushSender {

    private static final Logger log = LoggerFactory.getLogger(MicePushSender.class);

    @Autowired
    private Environment environment;

    @Autowired
    RestTemplate customRestTemplate;

    @Autowired
    UmsApiService umsApiService;

    @Override
    public void sendMessage(OpMessage opMessage) {

        try {
            List<Pushmsg> pushmsg = new ArrayList<>();
            pushmsg.add(getPushmsg(opMessage));

            ObjectMapper objectMapper = new ObjectMapper();
            String pushAsString = objectMapper.writeValueAsString(pushmsg);

            String url = environment.getProperty("ums.api.url") + "/api/push?listFlag=N";
            umsApiService.umsApiRestTemplate(pushAsString, url);

        } catch (Exception ignore) {
            log.error("MicePushSender sendMessage Error", ignore);
        }
    }

    @Override
    public void sendMessages(List<OpMessage> opMessages) {

        try {
            List<Pushmsg> pushmsgs = new ArrayList<>();

            if (opMessages != null && !opMessages.isEmpty()) {
                opMessages.stream().filter(Objects::nonNull).forEach(message -> {
                    try {

                        pushmsgs.add(getPushmsg(message));

                    } catch (Exception ignore) {
                        log.error("MicePushSender opMessage Error {}", JsonViewUtils.objectToJson(message), ignore);
                    }
                });

                if (pushmsgs != null && !"".equals(pushmsgs)) {

                    ObjectMapper objectMapper = new ObjectMapper();
                    String pushAsString = objectMapper.writeValueAsString(pushmsgs);

                    String url = environment.getProperty("ums.api.url") + "/api/push?listFlag=Y";
                    umsApiService.umsApiRestTemplate(pushAsString, url);
                }
            }

        } catch (Exception ignore) {
            log.error("MicePushSender sendMessages Error", ignore);
        }
    }

    private Pushmsg getPushmsg(OpMessage opMessage) throws Exception {

        int SMS_BYTE_LENGTH = 80;

        String[] receivers = opMessage.getReceivers();

        String callback = opMessage.getFrom();
        String id = environment.getProperty("ums.sub.id");
        String telNumber = receivers[0];
        String message = opMessage.getMessage();
        String title = opMessage.getTitle();
        UmsExpansionInfo expansionInfo = null;

        Pushmsg pushmsg = new Pushmsg();

        try {
            String[] bcc = opMessage.getBcc();
            String jsonUmsExpansionInfo = bcc[3];

            if (!StringUtils.isEmpty(jsonUmsExpansionInfo)) {
                expansionInfo = (UmsExpansionInfo) JsonViewUtils.jsonToObject(jsonUmsExpansionInfo, new TypeReference<UmsExpansionInfo>() {});
            }

        } catch (Exception ignore) {}

        telNumber = telNumber.replaceAll("-", "");

        if (StringUtils.isEmpty(callback)) {
            callback = environment.getProperty("ums.callback.number");
        }

        callback = callback.replaceAll("-", "");

        String messageKey = "";
        String messageType = "";
        String loginId = "";

        String reservationDate = "";

        String pushToken = "";
        String applicationNo = "";
        String applicationVersion = "";
        String uuid = "";
        String deviceType = "";

        String deepLink = "";
        String pushType = "";

        Map<String, Object> otherData = null;

        String webUrl = "";
        String imgUrl = "";
        String stsUrl = "";
        String popupYn = "";
        String stsYn = "";

        boolean failProcessFlag = false;
        String rtType = null;
        String failedSubject = null;
        String failedMsg = null;
        String failedType = null;
        String failedImg = "";

        String etc1 = "";
        String etc2 = "";

        // PUSH 설정
        if (expansionInfo != null) {

            messageKey = expansionInfo.getMessageKey();
            messageType = expansionInfo.getMessageType();

            if (StringUtils.isEmpty(messageType)) {
                messageType = "01";
            } else {
                // 메시지유형 (0:일반, 1:광고)
                switch (messageType) {
                    case"0":
                        messageType = "01"; break;
                    case"1":
                        messageType = "02"; break;
                    default:
                        messageType = "";
                }
            }

            loginId = expansionInfo.getLoginId();

            reservationDate = expansionInfo.getReservationDate();

            pushToken = expansionInfo.getPushToken();
            applicationNo = expansionInfo.getApplicationNo();
            applicationVersion = expansionInfo.getApplicationVersion();
            uuid = expansionInfo.getUuid();
            deepLink = expansionInfo.getDeepLink();


            if (DeviceType.IOS.equals(expansionInfo.getDeviceType())) {
                deviceType = "1";
                pushType = "A";
            } else if (DeviceType.ANDROID.equals(expansionInfo.getDeviceType())) {
                deviceType = "2";
                pushType = "F";
            } else {
                deviceType = "";
                pushType = "";
            }

            otherData = expansionInfo.getOtherData();

            if (otherData != null) {

                webUrl = CommonUtils.dataNvl(otherData.get("WEB_URL"));
                imgUrl = CommonUtils.dataNvl(otherData.get("IMG_URL"));
                stsUrl = CommonUtils.dataNvl(otherData.get("STS_URL"));
                popupYn = CommonUtils.dataNvl(otherData.get("POPUP_YN"));
                stsYn = CommonUtils.dataNvl(otherData.get("STS_YN"));

                failedImg = CommonUtils.dataNvl(otherData.get("FAILED_IMG"));
                etc1 = CommonUtils.dataNvl(otherData.get("ETC1"));
                etc2 = CommonUtils.dataNvl(otherData.get("ETC2"));

            }

            //failedType = expansionInfo.getResendType();

            if (!StringUtils.isEmpty(failedType) && "SMS".equals(failedType)) {
                int byteLength = message.getBytes().length;

                if (byteLength > SMS_BYTE_LENGTH) {
                    failedType = "LMS";
                }

                if (!StringUtils.isEmpty(failedImg)) {
                    failedType = "MMS";
                }
            }

            failProcessFlag = expansionInfo.isPushFailProcessFlag();
            if (failProcessFlag) {
                rtType = "LMS";
                failedSubject = title;
                failedMsg = message;
            }
        }

        // PUSH MESSAGE 설정

        String testYn = environment.getProperty("push.test");
        String kind = "CCS";
        String customData = "";


        if (StringUtils.isEmpty(testYn)) {
            testYn = "Y";
        }

        pushmsg.setMsgNo(messageKey);
        pushmsg.setPushType(pushType);
        pushmsg.setPushToken(pushToken);
        pushmsg.setTitle(title);
        pushmsg.setCnts(message);
        pushmsg.setMsgType(messageType);
        pushmsg.setMenuId(deepLink);
        pushmsg.setAppNo(applicationNo);
        pushmsg.setAppVer(applicationVersion);
        pushmsg.setMbrNo(loginId);
        pushmsg.setUuid(uuid);
        pushmsg.setOs(deviceType);
        pushmsg.setResvDt(reservationDate);
        pushmsg.setTestYn(testYn);
        pushmsg.setWebUrl(webUrl);
        pushmsg.setImgUrl(imgUrl);
        pushmsg.setStsUrl(stsUrl);
        pushmsg.setPopupYn(popupYn);
        pushmsg.setStsYn(stsYn);
        pushmsg.setKind(kind);
        pushmsg.setCustomData(customData);
        pushmsg.setRtType(rtType);
        pushmsg.setFailedType(failedType);
        pushmsg.setId(id);
        pushmsg.setPhone(telNumber);
        pushmsg.setCallback(callback);
        pushmsg.setFailedSubject(failedSubject);
        pushmsg.setFailedMsg(failedMsg);
        pushmsg.setFailedImg(failedImg);
        pushmsg.setEtc1(etc1);
        pushmsg.setEtc2(etc2);

        return pushmsg;
    }
}
