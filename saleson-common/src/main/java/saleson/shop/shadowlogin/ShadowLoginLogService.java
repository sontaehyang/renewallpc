package saleson.shop.shadowlogin;

public interface ShadowLoginLogService {

	/**
	 * 로그인 로그 등록
	 * @param loginTarget
	 * @param loginTargetId
	 * @param managerId
	 * @return
	 */
	public int insertShadowLoginLog(String loginTarget, long loginTargetId, long managerId);
	
	/**
	 * 로그아웃
	 * @param shadowLoginId
	 */
	public void updateShadowLogoutLog(int shadowLoginId);
}
