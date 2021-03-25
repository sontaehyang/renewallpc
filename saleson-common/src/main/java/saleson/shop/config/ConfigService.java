package saleson.shop.config;

import saleson.shop.config.domain.Config;

public interface ConfigService {
	/**
	 * 상점 설정 조회
	 * @param shopId
	 * @return
	 */
	public int getShopConfigCount(int shopId);
	
	/**
	 * 상점 설정 조회 데이터
	 * @param shopId
	 * @return
	 */
	public Config getShopConfig(int shopId);
	
	
	/**
	 * 상점 설정 조회 데이터 (캐시)
	 * @param shopId
	 * @return
	 */
	public Config getShopConfigCache(int shopId);
	
	/**
	 * 상점 설정 - 블랙리스트 
	 * @param config
	 */
	public void updateShopConfigDeniedId(Config config);
	
	/**
	 * 상점설정 - 기본 설정
	 * @param config
	 */
	public void updateShopConfig(Config config);
	
	/**
	 * 컨버젼 태그 
	 * @param config
	 */
	public void updateShopConfigConversionTag(Config config);
	
	
	/**
	 * 사이트 환경 설정
	 * @param config
	 */
	public void updateShopSiteConfig(Config config);
	
	/**
	 * 결제 정보 설정
	 * @param config
	 */
	public void updateShopConfigPayment(Config config);
	
	/**
	 * TOP 배너 설정
	 * @param config
	 */
	public void updateShopConfigTopBanner(Config config);
	
	/**
	 * TOP 배너 삭제
	 */
	public void deleteShopConfigTopBanner(Config config);
	
	/**
	 * 배너 이미지삭제
	 */
	public void deleteShopConfigBannerImage(Config config, String bannerType);
	
	/**
	 * 적립금/소비세 설정
	 */
	public void updateShopConfigReserve(Config config);
	
	/**
	 * 배송/반품/환불/교환안내 설정
	 * @param config
	 */
	public void updateShopConfigDeliveryInfo(Config config);
	
	/**
	 * 배송희망일 설정
	 * @param config
	 */
	public void updateShopConfigDeliveryHope(Config config);

	void updateCategoryUpdatedDate();

	/**
	 * 상품 설정 수정 (상/하단 내용)
	 * @param config
	 */
	void updateShopConfigItem(Config config);
}
