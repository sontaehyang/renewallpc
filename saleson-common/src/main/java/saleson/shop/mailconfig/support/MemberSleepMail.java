package saleson.shop.mailconfig.support;

import com.onlinepowers.framework.security.userdetails.User;
import saleson.common.config.SalesonProperty;
import saleson.shop.config.domain.Config;
import saleson.shop.mailconfig.domain.MailConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MemberSleepMail extends MailTemplate {
	public HashMap<String, String> getMap() {
		HashMap<String, String> map = new HashMap<>();

		map.put("after30", "30일 후 날짜");
		map.put("after31", "31일 후 날짜");
		
		map.put("siteName", "상점명");
		map.put("siteUrl", "상점URL");
		map.put("loginId", "사용자 아이디");
		
		return map;
	}
	
	public MemberSleepMail() {
		this.setMap(this.getMap());
	}
	
	public MemberSleepMail(User user, MailConfig mailConfig, Config config) {
		super(mailConfig);
		
		HashMap<String, String> map = new HashMap<>();
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
		cal.add(Calendar.DATE, 30);
		String after30 = format.format(cal.getTime());
		cal.add(Calendar.DATE, 1);
		String after31 = format.format(cal.getTime());
		
		map.put("after30", after30);
		map.put("after31", after31);
		map.put("loginId", user.getLoginId());
		
		map.put("siteName", config.getShopName());
		map.put("siteUrl", SalesonProperty.getSalesonUrlShoppingmall());
		
		this.setMap(map);
	}
	
}
