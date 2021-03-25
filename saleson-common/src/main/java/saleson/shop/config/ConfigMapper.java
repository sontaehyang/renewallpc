package saleson.shop.config;

import org.springframework.cache.annotation.CacheEvict;

import saleson.shop.config.domain.Config;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("configMapper")
public interface ConfigMapper {
	int getShopConfigCount(int shopId);

	Config getShopConfig(int shopId);
	
	@CacheEvict(value="shopConfig", allEntries=true)
	void updateShopConfigDeniedId(Config config);
	
	@CacheEvict(value="shopConfig", allEntries=true)
	void updateShopConfig(Config config);
	
	@CacheEvict(value="shopConfig", allEntries=true)
	void updateShopConfigConversionTag(Config config);
	
	@CacheEvict(value="shopConfig", allEntries=true)
	void updateShopSiteConfig(Config config);
	
	@CacheEvict(value="shopConfig", allEntries=true)
	void updateShopConfigPayment(Config config);
	
	@CacheEvict(value="shopConfig", allEntries=true)
	void updateShopConfigTopBanner(Config config);
	
	@CacheEvict(value="shopConfig", allEntries=true)
	void deleteShopConfigTopBanner(Config config);
	
	@CacheEvict(value="shopConfig", allEntries=true)
	void deleteShopConfigBannerImage(Config config);
	
	@CacheEvict(value="shopConfig", allEntries=true)
	void updateShopConfigReserve(Config config);
	
	@CacheEvict(value="shopConfig", allEntries=true)
	void updateShopConfigDeliveryInfo(Config config);
	
	@CacheEvict(value="shopConfig", allEntries=true)
	void updateShopConfigDeliveryHope(Config config);

	@CacheEvict(value="shopConfig", allEntries=true)
	void updateCategoryUpdatedDate();

	@CacheEvict(value="shopConfig", allEntries=true)
	void updateShopConfigItem(Config config);
}
