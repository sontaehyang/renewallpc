package saleson.shop.featuredbanner;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

import saleson.shop.featuredbanner.domain.FeaturedBanner;

@Mapper("featuredBannerMapper")
public interface FeaturedBannerMapper {
	
	/**
	 * 기획전 배너조회
	 * @param featuredBannerId
	 * @return
	 */
	FeaturedBanner getFeaturedBanner(int featuredBannerId);

	/**
	 * 기획전 배너수정
	 * @param featuredBanner
	 */
	void updateFeaturedBanner(FeaturedBanner featuredBanner);
	
	/**
	 * 배너이미지 삭제수정
	 * @param featuredBanner
	 */
	void updateBannerImage(FeaturedBanner featuredBanner);
}
