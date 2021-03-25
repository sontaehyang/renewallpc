package saleson.shop.banword.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.onlinepowers.framework.annotation.Title;

@SuppressWarnings("serial")
public class BanWord implements Serializable {
	private int banWordId;
	
	@NotNull
	@Title("금칙어")
	private String banWord;
	private long userId;
	private String userName;
	private String creationDate;
	
	public int getBanWordId() {
		return banWordId;
	}
	public void setBanWordId(int banWordId) {
		this.banWordId = banWordId;
	}
	public String getBanWord() {
		return banWord;
	}
	public void setBanWord(String banWord) {
		this.banWord = banWord;
	}
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
	
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
}
