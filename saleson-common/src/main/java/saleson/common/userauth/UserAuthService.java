package saleson.common.userauth;

import javax.servlet.http.HttpServletRequest;

import saleson.common.userauth.domain.UserAuth;

public interface UserAuthService {
	
	/**
	 * DB에 저장된 인증요청 임시 데이터 조회
	 * @return
	 */
	public UserAuth getUserAuth(HttpServletRequest request, String dataStatusCode);
	
	/**
	 * 아이핀 본인확인 임호화 데이터
	 * @param request
	 * @return
	 */
	public String getIpinEncryptString(HttpServletRequest request, String target);
	
	/**
	 * 아이핀 본인확인 결과
	 * @param request
	 */
	public void setIpinAuthSuccess(HttpServletRequest request);
	
	/**
	 * 휴대폰 본인확인 암호화 데이터
	 * @param request
	 * @return
	 */
	public String getPccEncryptString(HttpServletRequest request, String target);
	
	/**
	 * 휴대폰 본인확인 결과
	 * @param request
	 */
	public void setPccAuthSuccess(HttpServletRequest request);
}
