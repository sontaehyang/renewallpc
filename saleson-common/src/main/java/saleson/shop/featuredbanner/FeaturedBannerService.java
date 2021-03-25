package saleson.shop.featuredbanner;

import saleson.shop.featuredbanner.domain.FeaturedBanner;

public interface FeaturedBannerService {

	/**
	 * 기획전 배너조회
	 * @param featuredBannerId
	 * @return
	 */
	public FeaturedBanner getFeaturedBanner(int featuredBannerId);
	
	/**
	 * 기획전 배너수정
	 * @param featuredBanner
	 */
	public void updateFeaturedBanner(FeaturedBanner featuredBanner);
	
	/**
	 * 기획전 배너삭제
	 * @param imageName
	 */
	public void deleteBannerImage(String imageName);
}
