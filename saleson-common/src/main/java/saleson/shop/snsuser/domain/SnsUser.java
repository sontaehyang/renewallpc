package saleson.shop.snsuser.domain;

import java.util.List;

/**
 * @author	seungil.lee
 * @since	2017-05-24
 */

public class SnsUser {
	public SnsUser() {}	
	public SnsUser(long userId) {
		this.userId = userId;
	}
	
	// SNS_USER_ID
	private int snsUserId;
	
	// USER_ID - 사용자 아이디
	private long userId;
	
	// CERTIFIED_DATE - OP_USER 및 OP_USER_DETAIL 정보 입력 완료 시간
	private String certifiedDate;

	// 인증 된 SNS종류
	private List<SnsUserDetail> snsDetails;

	
	public int getSnsUserId() {
		return snsUserId;
	}
	
	public void setSnsUserId(int snsUserId) {
		this.snsUserId = snsUserId;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCertifiedDate() {
		return certifiedDate;
	}

	public void setCertifiedDate(String certifiedDate) {
		this.certifiedDate = certifiedDate;
	}

	public List<SnsUserDetail> getSnsDetails() {
		return snsDetails;
	}

	public void setSnsDetails(List<SnsUserDetail> snsDetails) {
		this.snsDetails = snsDetails;
	}
}
