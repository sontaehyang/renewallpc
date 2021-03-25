package saleson.shop.smsconfig.support;

import com.onlinepowers.framework.security.userdetails.User;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.ShopUtils;
import saleson.shop.config.domain.Config;
import saleson.shop.smsconfig.domain.SmsConfig;
import saleson.shop.user.domain.UserDetail;

import java.util.HashMap;

public class PwsearchSms extends SmsTemplate {
	
	public HashMap<String, String> getMap() {
		HashMap<String, String> map = new HashMap<>();

		map.put("name", "이름");
		//map.put("compName", "회사명");
		map.put("findId", "아이디");
		map.put("findPw", "비밀번호");
		map.put("siteName", "사이트명");
		map.put("siteUrl", "사이트URL");
		
		return map;
	}
	
	public PwsearchSms() {
		this.setMap(this.getMap());
	}
	
	public PwsearchSms(User user, SmsConfig smsConfig) {
		super(smsConfig);
		
		HashMap<String, String> map = new HashMap<>();
		UserDetail userDetail = (UserDetail) user.getUserDetail();
		
		map.put("name", user.getUserName());
		//map.put("compName", userDetail.getCompanyName());
		map.put("findId", user.getLoginId());
		map.put("findPw", user.getPassword());
		
		Config config = ShopUtils.getConfig();
		map.put("siteName", config.getShopName());
		map.put("siteUrl", SalesonProperty.getSalesonUrlShoppingmall());
		
		this.setMap(map);
	}
}
