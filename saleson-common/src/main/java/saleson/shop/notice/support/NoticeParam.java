package saleson.shop.notice.support;

import com.onlinepowers.framework.web.domain.SearchParam;
import saleson.common.utils.CommonUtils;

@SuppressWarnings("serial")
public class NoticeParam extends SearchParam {
	private int noticeId;
	private String subCategory;
	private String categoryTeam;
	private String subject;
	private String noticeFlag;
	private int visibleType;
	private long sellerId;
	
	// 조회용 팀코드
	private String[] teamCodes;
	
	
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}
	public String getNoticeFlag() {
		return noticeFlag;
	}
	public void setNoticeFlag(String noticeFlag) {
		this.noticeFlag = noticeFlag;
	}
	public int getVisibleType() {
		return visibleType;
	}
	public void setVisibleType(int visibleType) {
		this.visibleType = visibleType;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public String getCategoryTeam() {
		return categoryTeam;
	}
	public void setCategoryTeam(String categoryTeam) {
		this.categoryTeam = categoryTeam;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String[] getTeamCodes() {
		return CommonUtils.copy(teamCodes);
	}
	public void setTeamCodes(String[] teamCodes) {
		this.teamCodes = CommonUtils.copy(teamCodes);
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	
}
