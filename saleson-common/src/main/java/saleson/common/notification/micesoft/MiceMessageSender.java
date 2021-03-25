package saleson.common.notification.micesoft;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinepowers.framework.notification.message.OpMessage;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import saleson.common.notification.UmsApiService;
import saleson.common.notification.domain.UmsExpansionInfo;
import saleson.common.notification.micesoft.domain.message.MmsMsg;
import saleson.common.notification.micesoft.domain.message.SmsMsg;
import saleson.common.notification.sender.MessageSender;
import saleson.common.utils.CommonUtils;
import saleson.common.utils.ShopUtils;

import java.util.*;

public class MiceMessageSender implements MessageSender {

    private static final Logger log = LoggerFactory.getLogger(MiceMessageSender.class);
    private final int SMS_BYTE_LENGTH = 80;

    @Autowired
    private Environment environment;

    @Autowired
    RestTemplate customRestTemplate;

    @Autowired
    UmsApiService umsApiService;

    @SneakyThrows
    @Override
    public void sendSms(OpMessage opMessage) {
        SmsMsg smsMsg = getSmsMsg(opMessage);
        MmsMsg mmsMsg = getMmsMsg(opMessage);

        List<String> phoneNumbers = new ArrayList<>();
        List<String> etc3List = new ArrayList<>();

        String url = environment.getProperty("ums.api.url");

        if (isSms(opMessage)) {
            List<String> smsMsgList = new ArrayList<>();
            smsMsgList.add(String.valueOf(getSmsMsg(opMessage).getMsg()));

            phoneNumbers.add(getSmsMsg(opMessage).getPhone());
            etc3List.add(getSmsMsg(opMessage).getEtc3());

            smsMsg.setPhoneNumbers(phoneNumbers);
            smsMsg.setEtc3List(etc3List);
            smsMsg.setMsgs(smsMsgList);

            ObjectMapper objectMapper = new ObjectMapper();

            String userAsString = objectMapper.writeValueAsString(smsMsg);

            umsApiService.umsApiRestTemplate(userAsString, url + "/api/sms");

        } else {
            List<String> mmsMsgList = new ArrayList<>();
            mmsMsgList.add(String.valueOf(getMmsMsg(opMessage).getMsg()));

            phoneNumbers.add(getMmsMsg(opMessage).getPhone());
            etc3List.add(getMmsMsg(opMessage).getEtc3());

            mmsMsg.setPhoneNumbers(phoneNumbers);
            mmsMsg.setEtc3List(etc3List);
            mmsMsg.setMsgs(mmsMsgList);

            ObjectMapper objectMapper = new ObjectMapper();

            String userAsString = objectMapper.writeValueAsString(mmsMsg);

            umsApiService.umsApiRestTemplate(userAsString, url + "/api/mms");

        }

    }

    @SneakyThrows
    @Override
    public void sendSmsList(List<OpMessage> opMessages) {
        SmsMsg smsMsgs = getSmsMsg(opMessages.get(0));
        MmsMsg mmsMsgs = getMmsMsg(opMessages.get(0));

        List<String> smsMsgList = new ArrayList<>();
        List<String> mmsMsgList = new ArrayList<>();

        List<String> phoneNumbers = new ArrayList<>();
        List<String> etc3List = new ArrayList<>();

        if (opMessages != null && !opMessages.isEmpty()) {

            opMessages.stream().filter(Objects::nonNull).forEach(message -> {
                try {

                    if (isSms(message)) {
                        smsMsgList.add(getSmsMsg(message).getMsg());
                        phoneNumbers.add(getSmsMsg(message).getPhone());
                        etc3List.add(getSmsMsg(message).getEtc3());
                    } else {
                        mmsMsgList.add(getMmsMsg(message).getMsg());
                        phoneNumbers.add(getMmsMsg(message).getPhone());
                        etc3List.add(getMmsMsg(message).getEtc3());
                    }

                } catch (Exception ignore) {
                    log.error("MicePushSender opMessage Error", ignore);
                }
            });

        }

        if (!phoneNumbers.isEmpty()) {
            int fileCnt = mmsMsgs.getFileCnt();
            int byteLength = mmsMsgs.getMsg().getBytes().length;

            if (fileCnt >= 2 || byteLength > SMS_BYTE_LENGTH) {
                mmsMsgs.setPhoneNumbers(phoneNumbers);
                mmsMsgs.setMsgs(mmsMsgList);
                mmsMsgs.setEtc3List(etc3List);

                ObjectMapper objectMapper = new ObjectMapper();

                String userAsString = objectMapper.writeValueAsString(mmsMsgs);  // @SneakyThrows

                String url = environment.getProperty("ums.api.url") + "/api/mms";
                umsApiService.umsApiRestTemplate(userAsString, url);

            } else {
                smsMsgs.setPhoneNumbers(phoneNumbers);
                smsMsgs.setMsgs(smsMsgList);
                smsMsgs.setEtc3List(etc3List);

                ObjectMapper objectMapper = new ObjectMapper();

                String userAsString = objectMapper.writeValueAsString(smsMsgs);  // @SneakyThrows

                String url = environment.getProperty("ums.api.url") + "/api/sms";
                umsApiService.umsApiRestTemplate(userAsString, url);
            }
        }
    }

    private boolean isSms(OpMessage opMessage) {

        String smsType  = opMessage.getSmsType();
        String message  = opMessage.getMessage();
        UmsExpansionInfo expansionInfo = getUmsExpansionInfo(opMessage);

        if ("sms".equals(smsType) || "lms".equals(smsType) || "mms".equals(smsType)) {
            smsType = "0";
        }

        boolean smsFlag = "0".equals(smsType);

        if (smsFlag) {
            int byteLength = message.getBytes().length;

            if (byteLength > SMS_BYTE_LENGTH) {
                return false;
            }

            if (expansionInfo != null) {

                String imagePath = expansionInfo.getImagePath();
                if (!StringUtils.isEmpty(imagePath)) {
                    return false;
                }
            }
        }

        return smsFlag;
    }

    private SmsMsg getSmsMsg(OpMessage opMessage) {

        String id = environment.getProperty("ums.sub.id");
        String[] receivers = opMessage.getReceivers();
        String callback = opMessage.getFrom();
        String telNumber = receivers[0];    //  phoneNumbers
        String message = opMessage.getMessage();
        telNumber = telNumber.replaceAll("-","");

        if (StringUtils.isEmpty(callback)) {
            callback = environment.getProperty("ums.callback.number");
        }

        callback = callback.replaceAll("-","");

        String reqdate = null;
        String etc1 = null;
        String etc3 = null;

        Map<String, Object> otherData = null;
        UmsExpansionInfo expansionInfo = getUmsExpansionInfo(opMessage);

        if (expansionInfo != null) {

            reqdate = expansionInfo.getReservationDate();

            otherData = expansionInfo.getOtherData();

            if (otherData != null) {

                etc1 = CommonUtils.dataNvl(otherData.get("ETC1"));
                etc3 = CommonUtils.dataNvl(otherData.get("ETC3"));

            }
        }

        SmsMsg smsMsg = new SmsMsg();
        smsMsg.setId(id);
        smsMsg.setPhone(telNumber); // phoneNumbers List
        smsMsg.setCallback(callback);
        smsMsg.setReqdate(reqdate);
        smsMsg.setMsg(message);
        smsMsg.setEtc1(etc1);
        smsMsg.setEtc3(etc3);

        return smsMsg;
    }

    private MmsMsg getMmsMsg(OpMessage opMessage) {

        String id = environment.getProperty("ums.sub.id");
        String[] receivers = opMessage.getReceivers();
        String callback = opMessage.getFrom();
        String telNumber = receivers[0];
        String message = opMessage.getMessage();
        String title = opMessage.getTitle();

        telNumber = telNumber.replaceAll("-","");

        if (StringUtils.isEmpty(callback)) {
            callback = environment.getProperty("ums.callback.number");
        }

        callback = callback.replaceAll("-","");
        message = ShopUtils.unescapeHtml(message);

        String reqdate = null;

        int fileCnt = 1;
        String filePath1 = null;
        int filePath1Siz = 0;

        String etc1 = null;
        String etc3 = null;

        Map<String, Object> otherData = null;
        UmsExpansionInfo expansionInfo = getUmsExpansionInfo(opMessage);

        if (expansionInfo != null) {

            reqdate = expansionInfo.getReservationDate();


            filePath1 = expansionInfo.getImagePath();
            filePath1Siz = expansionInfo.getImageSize();

            if (!StringUtils.isEmpty(filePath1)) {
                fileCnt = fileCnt + 1;
            }

            otherData = expansionInfo.getOtherData();

            if (otherData != null) {

                etc1 = CommonUtils.dataNvl(otherData.get("ETC1"));
                etc3 = CommonUtils.dataNvl(otherData.get("ETC3"));

            }
        }

        MmsMsg mmsMsg = new MmsMsg();

        mmsMsg.setId(id);
        mmsMsg.setSubject(title);
        mmsMsg.setPhone(telNumber);
        mmsMsg.setCallback(callback);
        mmsMsg.setReqdate(reqdate);
        mmsMsg.setMsg(message);
        mmsMsg.setFileCnt(fileCnt);
        mmsMsg.setFilePath1(filePath1);
        mmsMsg.setFilePath1Siz(filePath1Siz);
        mmsMsg.setEtc1(etc1);
        mmsMsg.setEtc3(etc3);

        return mmsMsg;
    }

    private UmsExpansionInfo getUmsExpansionInfo(OpMessage opMessage) {

        UmsExpansionInfo expansionInfo = null;

        try {
            String[] bcc = opMessage.getBcc();
            String jsonUmsExpansionInfo = bcc[3];

            if (!StringUtils.isEmpty(jsonUmsExpansionInfo)) {
                expansionInfo = (UmsExpansionInfo) JsonViewUtils.jsonToObject(jsonUmsExpansionInfo, new TypeReference<UmsExpansionInfo>() {});
            }

        } catch (Exception ignore) {}

        return expansionInfo;
    }

}
