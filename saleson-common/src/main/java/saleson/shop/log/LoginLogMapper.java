package saleson.shop.log;

import java.util.List;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

import saleson.shop.log.domain.LoginLog;
import saleson.shop.log.support.LoginLogParam;

@Mapper("loginLogMapper")
public interface LoginLogMapper {

	/**
	 * 로그인 로그 등록
	 * @param loginLog
	 * @return
	 */
	public int insertLoginLog(LoginLog loginLog);
	
	/**
	 * 로그인 로그 목록 조회
	 * @param param
	 * @return
	 */
	public int getLoginLogListCountByParam(LoginLogParam param);
	
	/**
	 * 로그인 로그 목록 조회
	 * @param param
	 * @return
	 */
	public List<LoginLog> getLoginLogListByParam(LoginLogParam param);

	/**
	 * 사용자 로그인 로그 등록
	 * @param loginLog
	 * @return
	 */
	int insertLoginLogByUser(LoginLog loginLog);
}
