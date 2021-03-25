package saleson.shop.featured;

import saleson.shop.featured.domain.Featured;

import com.onlinepowers.framework.orm.mybatis.annotation.MapperBatch;

@MapperBatch("featuredMapperBatch")
public interface FeaturedMapperBatch {
	
	/**
	 * 기획페이지 노출 순서를 일괄 변경한다..
	 * @param featuredListParam
	 */
	void updateFeaturedOrdering(Featured featured);

}
