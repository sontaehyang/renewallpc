package saleson.shop.smsconfig.support;

import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.DateUtils;
import saleson.common.config.SalesonProperty;
import saleson.shop.config.domain.Config;
import saleson.shop.smsconfig.domain.SmsConfig;
import saleson.shop.user.domain.UserDetail;

import java.util.HashMap;

public class MemberJoinSms extends SmsTemplate {
	
	private String zipcode;
	private String address;
	private String addressDetail;
	private String compName;
	private String email;
	private String id;
	private String name;
	private String phone;
	private String mobile;
	private String siteName;
	private String siteUrl;
	private String regDate;
	
	public HashMap<String, String> getMap() {
		HashMap<String, String> map = new HashMap<>();

		map.put("zipcode", "우편번호");
		map.put("address", "주소");
		map.put("addr_detail", "상세주소");
		//map.put("comp_name", "회사명");
		map.put("email", "이메일");
		map.put("id", "아이디");
		map.put("name", "이름");
		map.put("phone", "전화번호");
		map.put("mobile", "핸드폰번호");
		map.put("reg_date", "등록일자");
		
		map.put("site_name", "상점명");
		map.put("site_url", "상점URL");
		
		
		return map;
	}
	
	public MemberJoinSms() {
		this.setMap(this.getMap());
	}
	
	public MemberJoinSms(User user, SmsConfig smsConfig, Config config) {
		super(smsConfig);
	
		if (user == null) {
			return;
		}
		
		UserDetail userDetail = (UserDetail) user.getUserDetail();
		
		this.zipcode = userDetail.getPost();
		this.address = userDetail.getAddress();
		this.addressDetail = userDetail.getAddressDetail();
		//this.compName = userDetail.getCompanyName();
		this.email = user.getEmail();
		this.id = user.getLoginId();
		this.mobile = userDetail.getPhoneNumber();
		this.phone = userDetail.getTelNumber();
		this.regDate = DateUtils.date(user.getCreatedDate());
		
		this.siteName = config.getShopName();
		this.siteUrl =  SalesonProperty.getSalesonUrlShoppingmall();
	
		HashMap<String, String> map = this.setMailInfo();
		this.setMap(map);
	}
	
	private HashMap<String, String> setMailInfo(){
		
		HashMap<String, String> map = new HashMap<>();
		
		map.put("zipcode", this.zipcode);
		map.put("address", this.address);
		map.put("addr_detail", this.addressDetail);
		map.put("comp_name", this.compName);
		map.put("email", this.email);
		map.put("id", this.id);
		map.put("name", this.name);
		map.put("phone", this.phone);
		map.put("mobile", this.mobile);
		map.put("reg_date", this.regDate);
		
		map.put("site_name", this.siteName);
		map.put("site_url", this.siteUrl);
		return map;
	
	}
}
