package saleson.shop.popup.domain;

import java.util.ArrayList;
import java.util.List;

public class PopupContent {
	
	private int popup_Id;
	private String member_name;
	private String bureau;
	private String tel;
	private String duty;
	
	public int getPopupId() {
		return popup_Id;
	}
	public void setPopupId(int popup_Id) {
		this.popup_Id = popup_Id;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getBareau() {
		return bureau;
	}
	public void setBareau(String bureau) {
		this.bureau = bureau;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	
	
}