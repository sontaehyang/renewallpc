package saleson.common.userauth.utils;

import com.onlinepowers.framework.util.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import saleson.common.Const;
import saleson.common.userauth.domain.UserAuth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class Siren24Utils {
	private static String USER_AUTH_PCC_MID;
	private static String USER_AUTH_IPIN_MID;

	@Value("${user.auth.pcc.mid}")
	public void setUserAuthPccMid(String userAuthPccMid) {
		USER_AUTH_PCC_MID = userAuthPccMid;
	}
	@Value("${user.auth.ipin.mid}")
	public void setUserAuthIpinMid(String userAuthIpinMid) {
		USER_AUTH_IPIN_MID = userAuthIpinMid;
	}

	
	public static final String USER_AUTH_APP_KEY_NAME = "SIREN24-USER-AUTH-KEY";
	
	public static void setUserAuthAppKey(HttpSession session, String appKey) {
		session.setAttribute(USER_AUTH_APP_KEY_NAME, appKey);
	}
	
	public static void removeUserAuthAppKey(HttpSession session) {
		session.removeAttribute(USER_AUTH_APP_KEY_NAME);
	}
	
	public static String getUserAuthAppKey(HttpSession session) {
		try {
			return (String) session.getAttribute(USER_AUTH_APP_KEY_NAME);
		} catch(Exception e) {
			return "";
		}
	}
	
	/**
	 * 사이렌 24 휴대폰 본인확인
	 * @param reqNum
	 * @return
	 */
	public static String getPccEncryptString(String reqNum, String serviceNo) {
		
		String id 			= USER_AUTH_PCC_MID;
		String certDate		= DateUtils.getToday(Const.DATETIME_FORMAT);
		String certGb		= "H";
		String addVar		= "";
		String exVar    	= "0000000000000000";
		
		com.sci.v2.pcc.secu.SciSecuManager seed  = new com.sci.v2.pcc.secu.SciSecuManager();
		
		//02. 1차 암호화
		String encStr = "";
		String reqInfo      = id+"^"+serviceNo+"^"+reqNum+"^"+certDate+"^"+certGb+"^"+addVar+"^"+exVar;  // 데이터 암호화
		encStr              = seed.getEncPublic(reqInfo);

		//03. 위변조 검증 값 생성
		com.sci.v2.pcc.secu.hmac.SciHmac hmac = new com.sci.v2.pcc.secu.hmac.SciHmac();
		String hmacMsg = hmac.HMacEncriptPublic(encStr);

		//03. 2차 암호화
		return seed.getEncPublic(encStr + "^" + hmacMsg + "^" + "0000000000000000");  //2차암호화
	}
	
	/**
	 * 사이렌 24 IPIN 본인확인
	 * @param reqNum
	 * @return
	 */
	public static String getIpinEncryptString(String reqNum, String serviceNo) {
		com.sci.v2.ipin.secu.SciSecuManager seed = new com.sci.v2.ipin.secu.SciSecuManager();
		
		String id 			= USER_AUTH_IPIN_MID;
		String exVar       	= "0000000000000000";
		
		// 1차 암호화
		String reqInfo      = reqNum+"/"+id+"/"+serviceNo+"/"+exVar;  // 데이터 암호화
		String encStr 		= seed.getEncPublic(reqInfo);
		
		// 위변조 검증 값 등록
		com.sci.v2.ipin.secu.hmac.SciHmac hmac = new com.sci.v2.ipin.secu.hmac.SciHmac();
		String hmacMsg = hmac.HMacEncriptPublic(encStr);

		// 2차 암호화
		return seed.getEncPublic(encStr + "/" + hmacMsg + "/" + "00000000");
	}
	
	/**
	 * 사이렌 24 IPIN 결과 수신
	 * @param request
	 * @param userAuth
	 * @return
	 */
	public static void getIpinSuccessData(HttpServletRequest request, UserAuth userAuth) {
		HttpSession session = request.getSession();
		
		String saveAppKey = Siren24Utils.getUserAuthAppKey(session);
		userAuth.setDataStatusCode("2");
		userAuth.setAppKey(saveAppKey);
		if (StringUtils.isEmpty(saveAppKey)) {
			Siren24Utils.removeUserAuthAppKey(session);
			return;
		}
		
		String retInfo  = request.getParameter("retInfo").trim();
		
		if (StringUtils.isEmpty(retInfo)) {
			Siren24Utils.removeUserAuthAppKey(session);
			return;
		}
		
		com.sci.v2.ipin.secu.SciSecuManager sciSecuMg = new com.sci.v2.ipin.secu.SciSecuManager();
		retInfo  = sciSecuMg.getDec(retInfo, saveAppKey);

        // 2.1차 파싱---------------------------------------------------------------
        int inf1 = retInfo.indexOf("/",0);
        int inf2 = retInfo.indexOf("/",inf1+1);

		String encPara  = retInfo.substring(0,inf1);         //암호화된 통합 파라미터
		String encMsg   = retInfo.substring(inf1+1,inf2);    //암호화된 통합 파라미터의 Hash값

        // 3.위/변조 검증 ---------------------------------------------------------------
        if(!sciSecuMg.getMsg(encPara).equals(encMsg)){
        	Siren24Utils.removeUserAuthAppKey(session);
        	return;
        }
		
        // 4.파라미터별 값 가져오기 ---------------------------------------------------------------
        String decPara  = sciSecuMg.getDec(encPara, saveAppKey);
        
        String[] temp = decPara.split("/");
        
        String reqNum     = temp[0];
        String vDiscrNo   = temp[1];
        String name       = temp[2];
        String result     = temp[3];
        String age        = temp[4];
        String sex        = temp[5];
        String ip         = temp[6];
        String authInfo   = temp[7];
        String birth      = temp[8];
        String fgn        = temp[9];
        String discrHash  = temp[10];
        String ciVersion  = temp[11];
        String ciscrHash  = temp.length > 12 ? temp[12] : "";	
		
        discrHash  = sciSecuMg.getDec(discrHash, saveAppKey); //중복가입확인정보는 한번더 복호화
        ciscrHash  = sciSecuMg.getDec(ciscrHash, saveAppKey); //연계정보는 한번더 복호화
        
        userAuth.setAuthBirthDay(birth);
        userAuth.setAuthKey(discrHash);
        userAuth.setAuthName(name);
        userAuth.setAuthSex(sex);
        userAuth.setDataStatusCode("1");
	}
	
	/**
	 * 사이렌 24 휴대폰 인증 결과 수신
	 * @param request
	 * @param userAuth
	 * @return
	 */
	public static void getPccSuccessData(HttpServletRequest request, UserAuth userAuth) {
		HttpSession session = request.getSession();
		
		String saveAppKey = Siren24Utils.getUserAuthAppKey(session);
		userAuth.setDataStatusCode("2");
		userAuth.setAppKey(saveAppKey);
		if (StringUtils.isEmpty(saveAppKey)) {
			Siren24Utils.removeUserAuthAppKey(session);
			return;
		}
		
		String retInfo  = request.getParameter("retInfo").trim();
		if (StringUtils.isEmpty(retInfo)) {
			Siren24Utils.removeUserAuthAppKey(session);
			return;
		}
		
		// 1. 암호화 모듈 (jar) Loading
        com.sci.v2.pcc.secu.SciSecuManager sciSecuMg = new com.sci.v2.pcc.secu.SciSecuManager();
        //쿠키에서 생성한 값을 Key로 생성 한다.
        retInfo  = sciSecuMg.getDec(retInfo, saveAppKey);

        // 2.1차 파싱---------------------------------------------------------------
        String[] aRetInfo1 = retInfo.split("\\^");

		String encPara  = aRetInfo1[0];         //암호화된 통합 파라미터
        String encMsg   = aRetInfo1[1];    //암호화된 통합 파라미터의 Hash값
		
		String  encMsg2   = sciSecuMg.getMsg(encPara);
			// 3.위/변조 검증 ---------------------------------------------------------------
        if(!encMsg2.equals(encMsg)){
        	Siren24Utils.removeUserAuthAppKey(session);
        	return;
        }
        
        retInfo  = sciSecuMg.getDec(encPara, saveAppKey);

        String[] aRetInfo = retInfo.split("\\^");
		
        String name			= aRetInfo[0];
        String birYMD		= aRetInfo[1];
        String sex			= aRetInfo[2];        
        String fgnGbn		= aRetInfo[3];
        String di			= aRetInfo[4];
        String ci1			= aRetInfo[5];
        String ci2			= aRetInfo[6];
        String civersion	= aRetInfo[7];
        String reqNum		= aRetInfo[8];
        String result		= aRetInfo[9];
        String certGb		= aRetInfo[10];
        String cellNo		= aRetInfo[11];
        String cellCorp		= aRetInfo[12];
        String certDate		= aRetInfo[13];
        String addVar		= aRetInfo[14];
        
        userAuth.setAuthBirthDay(birYMD);
        userAuth.setAuthKey(di);
        userAuth.setAuthName(name);
        userAuth.setAuthSex(sex);
        userAuth.setDataStatusCode("1");
	}
}
