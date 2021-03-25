package saleson.shop.log;

import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.shop.log.domain.LoginLog;
import saleson.shop.log.support.LoginLogParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("loginLogService")
public class LoginLogServiceImpl implements LoginLogService{

	@Autowired
	private LoginLogMapper loginLogMapper;

	@Override
	public List<LoginLog> getLoginLogListByParam(LoginLogParam param) {
		return loginLogMapper.getLoginLogListByParam(param);
	}

	@Override
	public int getLoginLogListCountByParam(LoginLogParam param) {
	return loginLogMapper.getLoginLogListCountByParam(param);
	}
	
	@Override
	public int insertLoginLogByManager(HttpServletRequest request, boolean isSuccess) {
		return insertLoginLog(request, LoginLog.LOGIN_TYPE_MANAGER, isSuccess, "");
	}

	@Override
	public int insertLoginLogBySeller(HttpServletRequest request, boolean isSuccess) {
		return insertLoginLog(request, LoginLog.LOGIN_TYPE_SELLER, isSuccess, "");
	}

	private int insertLoginLog(HttpServletRequest request, String loginType, boolean isSuccess, String memo) {
		LoginLog loginLog = new LoginLog();
		
		loginLog.setLoginType(loginType);
		
		String loginId = request.getParameter("op_username");
		String sellerMemo;
		if (LoginLog.LOGIN_TYPE_SELLER.equals(loginType)) {

			sellerMemo = "Seller User";

			if (StringUtils.isEmpty(loginId)) {
				loginId = request.getParameter("loginId");
				sellerMemo = "Seller";
			}

			memo += " / "+sellerMemo;
		}
		
		loginLog.setLoginId(loginId);
		
		String successFlag = isSuccess ? "Y" : "N" ;
		loginLog.setSuccessFlag(successFlag);
		
		loginLog.setRemoteAddr(saleson.common.utils.CommonUtils.getClientIp(request));
		loginLog.setMemo(memo);
		
		return loginLogMapper.insertLoginLog(loginLog);
	}

	@Override
	public int insertLoginLogByUser(HttpServletRequest request, boolean isSuccess) {
		String loginId = request.getParameter("op_username");
		LoginLog loginLog = getBaseUserLoginLog(request, loginId, "", isSuccess);
		return loginLogMapper.insertLoginLogByUser(loginLog);
	}

	private LoginLog getBaseUserLoginLog(HttpServletRequest request, String loginId, String memo, boolean isSuccess) {
		if (StringUtils.isEmpty(loginId)) {
			loginId = "-";
		}

		LoginLog loginLog = new LoginLog();

		loginLog.setLoginType(LoginLog.LOGIN_TYPE_USER);

		loginLog.setLoginId(loginId);

		String successFlag = isSuccess ? "Y" : "N" ;
		loginLog.setSuccessFlag(successFlag);

		loginLog.setRemoteAddr(saleson.common.utils.CommonUtils.getClientIp(request));
		loginLog.setMemo(memo);
		return loginLog;
	}
}
