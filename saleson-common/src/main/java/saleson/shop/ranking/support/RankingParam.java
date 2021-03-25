package saleson.shop.ranking.support;

import java.util.List;

import saleson.common.utils.CommonUtils;
import saleson.shop.categories.domain.Group;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class RankingParam extends SearchParam {
	private String categoryTeamCode = "";
	private String categoryGroupCode = "";
	private String categoryUrl = "";

	private int limit = 100;

	// 순위 저장시 사용
	private int rankingId;
	private int[] itemIds;
	private String categoryCode;
	private int ordering;
	private int itemId;

	private String categoryClass;
	private String groupCategoryClassCodes;

	private String rankingType;
	private String rankingCode;
	private String viewTarget;

	// 그룹별 랭킹 상품 한방 조회 시 사용 .
	private List<Group> groups;

	// 전용상품 구분
	private List<String> privateTypes;
	private String privateType; 		// 전용상품 (OP_COMMON_CODE > ITEM_PRIVATE_TYPE)

	private String isAdult;
	
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public String getViewTarget() {
		return viewTarget;
	}
	public void setViewTarget(String viewTarget) {
		this.viewTarget = viewTarget;
	}
	public String getGroupCategoryClassCodes() {
		return groupCategoryClassCodes;
	}
	public void setGroupCategoryClassCodes(String groupCategoryClassCodes) {
		this.groupCategoryClassCodes = groupCategoryClassCodes;
	}
	public String getCategoryClass() {
		return categoryClass;
	}
	public void setCategoryClass(String categoryClass) {
		this.categoryClass = categoryClass;
	}
	public String getCategoryTeamCode() {
		return categoryTeamCode;
	}
	public void setCategoryTeamCode(String categoryTeamCode) {
		this.categoryTeamCode = categoryTeamCode;
	}
	public String getCategoryGroupCode() {
		return categoryGroupCode;
	}
	public void setCategoryGroupCode(String categoryGroupCode) {
		this.categoryGroupCode = categoryGroupCode;
	}
	public String getCategoryUrl() {
		return categoryUrl;
	}
	public void setCategoryUrl(String categoryUrl) {
		this.categoryUrl = categoryUrl;
	}
	
	public boolean hasParamValue() {
		return !(this.categoryTeamCode.equals("") && this.categoryGroupCode.equals("")
				&& this.categoryUrl.equals(""));
	}
	public int[] getItemIds() {
		return CommonUtils.copy(itemIds);
	}
	public void setItemIds(int[] itemIds) {
		this.itemIds = CommonUtils.copy(itemIds);
	}
	public int getRankingId() {
		return rankingId;
	}
	public void setRankingId(int rankingId) {
		this.rankingId = rankingId;
	}
	
	
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getCategoryCode() {
		if (!this.categoryUrl.equals("")) {
			return this.categoryUrl;
			
		} else if (!this.categoryGroupCode.equals("")) {
			return this.categoryGroupCode;
			
		} else if (!this.categoryTeamCode.equals("")) {
			return this.categoryTeamCode;
		}
		
		return "";
	}
	public String getRankingType() {
		return rankingType;
	}
	public void setRankingType(String rankingType) {
		this.rankingType = rankingType;
	}
	public String getRankingCode() {
		return rankingCode;
	}
	public void setRankingCode(String rankingCode) {
		this.rankingCode = rankingCode;
	}
	public List<String> getPrivateTypes() {
		return privateTypes;
	}
	public void setPrivateTypes(List<String> privateTypes) {
		this.privateTypes = privateTypes;
	}
	public String getPrivateType() {
		return privateType;
	}
	public void setPrivateType(String privateType) {
		this.privateType = privateType;
	}
	public String getIsAdult() {
		return isAdult;
	}
	public void setIsAdult(String isAdult) {
		this.isAdult = isAdult;
	}
	
	
	
}
