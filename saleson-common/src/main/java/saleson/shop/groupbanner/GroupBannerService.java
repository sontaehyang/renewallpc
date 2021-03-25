package saleson.shop.groupbanner;

import java.util.List;

import saleson.shop.groupbanner.domain.GroupBanner;

public interface GroupBannerService {

	/**
	 * 그룹별 베너를 등록하거나 수정한다.
	 * @param groupBanner
	 */
	public void editGroupBanner(GroupBanner groupBanner);
	
	/**
	 * GROUP별 등록 베너 목록
	 * @param categoryGroupId
	 * @return
	 */
	public List<GroupBanner> getGroupBannerByCategoryGroupId(int categoryGroupId);
	
	/**
	 * 그룹별 베너 등록
	 * @param groupBanner
	 */
	public void insertGroupBanner(GroupBanner groupBanner);
	
	/**
	 * 그룹별 베너 수정
	 * @param groupBanner
	 */
	public void updateGroupBanner(GroupBanner groupBanner);
	
	/**
	 * 그룹별 베너 삭제
	 * @param categoryGroupBannerId
	 */
	public void deleteGroupBannerById(int categoryGroupBannerId);
	
}
