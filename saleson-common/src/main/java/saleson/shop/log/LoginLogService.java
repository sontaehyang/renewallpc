package saleson.shop.log;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import saleson.shop.log.domain.LoginLog;
import saleson.shop.log.support.LoginLogParam;

public interface LoginLogService {

	/**
	 * 관리자 로그인 로그 등록
	 * @param request
	 * @param isSuccess
	 * @return
	 */
	public int insertLoginLogByManager(HttpServletRequest request, boolean isSuccess);
	
	/**
	 * 판매자 로그인 로그 등록
	 * @param request
	 * @param isSuccess
	 * @return
	 */
	public int insertLoginLogBySeller(HttpServletRequest request, boolean isSuccess);

	/**
	 * 로그인 로그 목록 조회
	 * @param param
	 * @return
	 */
	public List<LoginLog> getLoginLogListByParam(LoginLogParam param);
	
	/**
	 * 로그인 로그 목록 조회
	 * @param param
	 * @return
	 */
	public int getLoginLogListCountByParam(LoginLogParam param);


	/**
	 * 사용자 로그인 로그 등록
	 * @param request
	 * @param isSuccess
	 * @return
	 */
	int insertLoginLogByUser(HttpServletRequest request, boolean isSuccess);
	
}
