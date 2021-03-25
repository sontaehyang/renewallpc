package saleson.shop.smsconfig.support;

import java.util.HashMap;
import java.util.List;

import saleson.shop.config.domain.Config;
import saleson.shop.smsconfig.domain.SmsConfig;
import saleson.shop.user.domain.UserDetail;

import com.onlinepowers.framework.security.userdetails.User;

public class UserSms extends SmsTemplate {

	private String userName;
	private long userId;
	private String title;
	private String content;
	private String smsType;
	private List<Integer> userIds;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	public HashMap<String, String> getMap() {
		HashMap<String, String> map = new HashMap<>();

		map.put("userName", "이름");
		return map;
	}
	
	public UserSms() {
		this.setMap(this.getMap());
	}
	
	public UserSms(User user, SmsConfig smsConfig, Config config) {
		super(smsConfig);
	
		if (user == null) {
			return;
		}
		
		UserDetail userDetail = (UserDetail) user.getUserDetail();
		
		this.userName = user.getUserName();
		HashMap<String, String> map = this.setMailInfo();
		this.setMap(map);
	}
	
	private HashMap<String, String> setMailInfo(){
		
		HashMap<String, String> map = new HashMap<>();
		
		map.put("user_name", this.userName);
		return map;
	
	}
	
}
