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
import org.springframework.web.client.RestTemplate;
import saleson.common.exception.KakaoAlimTalkException;
import saleson.common.notification.UmsApiService;
import saleson.common.notification.domain.UmsExpansionInfo;
import saleson.common.notification.micesoft.domain.kakao.ButtonJson;
import saleson.common.notification.micesoft.domain.kakao.KkoMsg;
import saleson.common.notification.sender.AlimtalkSender;
import saleson.common.utils.CommonUtils;
import saleson.common.utils.ShopUtils;
import saleson.shop.ums.kakao.domain.AlimTalkButton;

import java.util.*;


public class MiceAlimtalkSender implements AlimtalkSender {

	private static final Logger logger = LoggerFactory.getLogger(MiceAlimtalkSender.class);

	@Autowired
	Environment environment;

	@Autowired
	RestTemplate customRestTemplate;

	@Autowired
	UmsApiService umsApiService;

	@Override
	public void sendMessage(OpMessage opMessage) {

		try {
			List<KkoMsg> kkoMsg = new ArrayList<>();
			kkoMsg.add(getKkoMsg(opMessage));

			ObjectMapper objectMapper = new ObjectMapper();
			String kakaoAsString = objectMapper.writeValueAsString(kkoMsg);

			String url = environment.getProperty("ums.api.url") + "/api/kakao";

			umsApiService.umsApiRestTemplate(kakaoAsString, url);

		} catch (Exception e) {
			logger.error("MiceAlimtalkSender Error", e);
		}
	}


	private KkoMsg getKkoMsg(OpMessage opMessage) throws Exception{

		// 알림톡
		String[] receivers = opMessage.getReceivers();


		String callback = opMessage.getFrom();
		String id = environment.getProperty("ums.sub.id");
		String profileKey = environment.getProperty("kakao.alimetalk.senderkey");
		String telNumber = receivers[0];
		String message = opMessage.getMessage();
		String title = opMessage.getTitle();
		String applyCode = "";
		String buttonString = "";
		String buttonJson = "";
		String failedType = null;
		UmsExpansionInfo expansionInfo = null;
		boolean failProcessFlag = false;

		try {
			String[] bcc = opMessage.getBcc();
			applyCode = bcc[1];
			buttonString = bcc[2];
			String jsonUmsExpansionInfo = bcc[3];

			if (!StringUtils.isEmpty(jsonUmsExpansionInfo)) {
				expansionInfo = (UmsExpansionInfo) JsonViewUtils.jsonToObject(jsonUmsExpansionInfo, new TypeReference<UmsExpansionInfo>() {});
			}

		} catch (Exception ignore) {}

		telNumber = telNumber.replaceAll("-","");

		if (StringUtils.isEmpty(callback)) {
			callback = environment.getProperty("ums.callback.number");
		}

		callback = callback.replaceAll("-","");
		message = ShopUtils.unescapeHtml(message);

		KkoMsg kkoMsg = new KkoMsg();

		kkoMsg.setId(id);
		kkoMsg.setPhone(telNumber);
		kkoMsg.setCallback(callback);
		kkoMsg.setMsg(message);
		kkoMsg.setTemplateCode(applyCode);

		failProcessFlag = expansionInfo.isAlimTalkFailProcessFlag();
		if (failProcessFlag) {
			failedType = "LMS";
			kkoMsg.setFailedSubject(title);
			kkoMsg.setFailedMsg(message);
		}

		kkoMsg.setFailedType(failedType);

		kkoMsg.setProfileKey(profileKey);

		if (!StringUtils.isEmpty(buttonString)) {

			try {
				List<AlimTalkButton> buttons
						= (List<AlimTalkButton>)JsonViewUtils.jsonToObject(buttonString, new TypeReference<List<AlimTalkButton>>(){});

				buttonJson = getAlimTalkButtonJson(applyCode, buttons);
			} catch (Exception e) {
				logger.error("KAKAO 알림톡 버튼 JSON 생성 에러 [" + applyCode + "]", e.getMessage(), e);
				throw new KakaoAlimTalkException(e.getMessage());
			}
		}
		kkoMsg.setButtonJson(buttonJson);

		String etc3 = CommonUtils.dataNvl(expansionInfo.getLoginId());
		kkoMsg.setEtc3(etc3);

		return kkoMsg;
	}

	private String getAlimTalkButtonJson(String applyCode, List<AlimTalkButton> buttons) {

		String json = "";

		try {

			Map<String, Object> map = null;

			if (buttons != null && buttons.size() > 0) {
				List<ButtonJson> buttonJsons = new ArrayList<>();


				for (AlimTalkButton button : buttons) {

					ButtonJson buttonJson = new ButtonJson();

					buttonJson.setName(button.getName());
					buttonJson.setType(button.getType());
					buttonJson.setUrl_pc(button.getLinkPc());
					buttonJson.setUrl_mobile(button.getLinkMobile());
					buttonJson.setScheme_ios(button.getSchemeIos());
					buttonJson.setScheme_android(button.getSchemeAndroid());

					buttonJsons.add(buttonJson);
				}

				if (buttonJsons.size() > 0) {
					map = new LinkedHashMap<>();
					map.put("button",buttonJsons);
				}
			}

			if (map != null) {

				json = JsonViewUtils.objectToJson(map);

			}

		} catch (Exception e) {
			logger.error("KAKAO 알림톡 버튼 JSON 생성 에러 ["+applyCode+"]", e);
		}

		return json;
	}
}
