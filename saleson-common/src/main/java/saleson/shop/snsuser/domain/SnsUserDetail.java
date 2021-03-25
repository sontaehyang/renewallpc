package saleson.shop.snsuser.domain;

/**
 * @author	seungil.lee
 * @since	2017-05-24
 */

public class SnsUserDetail {
	// SNS_DETAIL_ID - 사용자 아이디
	private int snsDetailId;
	
	// SNS_USER_ID - OP_SNS_USER.SNS_USER_ID(FK)
	private int snsUserId;
	
	// USER_ID - 사용자 아이디
	private long userId;
	
	// SNS_TYPE - SNS 종류 (naver, facebook, kakao)
	private String snsType;
	
	// SNS_ID - SNS 제공 사용자 고유 ID
	private String snsId;
	
	// SNS_NAME - SNS 제공 사용자명
	private String snsName;
	
	// EMAIL - SNS 제공 사용자 EMAIL
	private String email;
	
	// CREATED_DATE - 생성일
	private String createdDate;
	
	// OP_USER.LOGIN_ID - 로그인시 사용되는 아이디
	private String loginId;

	// mypage인지 아닌지 확인
	private Boolean isMypage = false;

	// CERTIFIED_DATE - OP_USER 및 OP_USER_DETAIL 정보 입력 완료 시간
	private String certifiedDate;

	public int getSnsDetailId() {
		return snsDetailId;
	}

	public void setSnsDetailId(int snsDetailId) {
		this.snsDetailId = snsDetailId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getSnsUserId() {
		return snsUserId;
	}

	public void setSnsUserId(int snsUserId) {
		this.snsUserId = snsUserId;
	}

	public String getSnsType() {
		return snsType;
	}

	public void setSnsType(String snsType) {
		this.snsType = snsType;
	}

	public String getSnsId() {
		return snsId;
	}

	public void setSnsId(String snsId) {
		this.snsId = snsId;
	}

	public String getSnsName() {
		return snsName;
	}

	public void setSnsName(String snsName) {
		this.snsName = snsName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Boolean getIsMypage() {
		return isMypage;
	}

	public void setIsMypage(Boolean isMypage) {
		this.isMypage = isMypage;
	}

	public String getCertifiedDate() {
		return certifiedDate;
	}

	public void setCertifiedDate(String certifiedDate) {
		this.certifiedDate = certifiedDate;
	}
}
