package saleson.shop.shadowlogin;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

import saleson.shop.shadowlogin.domain.ShadowLoginLog;

@Mapper("shadowLoginLogMapper")
public interface ShadowLoginLogMapper {
	
	/**
	 * Shadow Login Log
	 * @param shadowLoginLog
	 */
	void insertShadowLoginLog(ShadowLoginLog shadowLoginLog);
	
	/**
	 * 로그아웃 기록
	 * @param shadowLoginLogId
	 */
	void updateShadowLogoutLogById(int shadowLoginLogId);
	
}
