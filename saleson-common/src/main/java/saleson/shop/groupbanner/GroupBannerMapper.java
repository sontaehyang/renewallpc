package saleson.shop.groupbanner;

import java.util.List;

import saleson.shop.groupbanner.domain.GroupBanner;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("groupBannerMapper")
public interface GroupBannerMapper {

	/**
	 * GROUP별 등록 베너 목록
	 * @param categoryGroupId
	 * @return
	 */
	List<GroupBanner> getGroupBannerByCategoryGroupId(int categoryGroupId);
	
	/**
	 * 그룹별 베너 등록
	 * @param groupBanner
	 */
	void insertGroupBanner(GroupBanner groupBanner);
	
	/**
	 * 그룹별 베너 수정
	 * @param groupBanner
	 */
	void updateGroupBanner(GroupBanner groupBanner);
	
	/**
	 * 그룹별 베너 삭제
	 * @param categoryGroupBannerId
	 */
	void deleteGroupBannerById(int categoryGroupBannerId);
	
	
	
}
