package saleson.shop.openmarket.domain;

import com.onlinepowers.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.ShopUtils;
import saleson.model.ConfigPg;
import saleson.shop.config.domain.Config;

import java.util.HashMap;

public class NaverPay {
	private static final Logger log = LoggerFactory.getLogger(NaverPay.class);

	private String buttonScriptUrl;
	private String buttonKey;
	private boolean use;
	private String orderApiUrl;
	private String payPopupUrl;
	private String certiKey;
	private String merchantId;
	private String backUrl;
	private String deviceType;
	private String wishlistApiUrl;
	private String wishlistPopupUrl;
	
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	private HashMap<String, String> interfaceInfo;
	
	public HashMap<String, String> getInterfaceInfo() {
		return interfaceInfo;
	}
	
	/**
	 * 
	 * @param salesCode - 경로별매출코드.매출코드가필요한경우에입력한다.최대300자
	 * @param cpaInflowCode - 지식쇼핑 CPA 코드. 지식쇼핑 가맹점 중 파라미터 방식을 이용한 CPA 과금을 원하는 가맹점은 이 값을 입력한다.
	 * @param mileageInflowCode - 네이버 마일리지 유입 경로 코드.
	 * @param naverInflowCode - 네이버 서비스 유입 경로 코드. 네이버 서비스 유입 경로 코드(네이버 서비스를 통해서 유입된 경우에, 서비스를 구분하기 위해 사용되는 코드)를 입력한다.
	 * @param saClickId - SA CLICK ID. 네이버 검색광고 이용 가맹점 중 광고주 센터의 광고 효 과 보고서를 통해 체크아웃 전환 데이터를 확인하길 원하는 가맹점은
							SA 로부터 받은 추적 URL 파라미터 중 NVADID 를 입력한다.
	 */
	public void setInterfaceInfo(String salesCode, String cpaInflowCode, String mileageInflowCode,
			String naverInflowCode, String saClickId) {
		
		HashMap<String, String> t = new HashMap<>();
		t.put("salesCode", "");
		t.put("cpaInflowCode", "");
		t.put("mileageInflowCode", "");
		t.put("naverInflowCode", "");
		t.put("saClickId", "");
		
		int addCount = 0;
		if (StringUtils.isNotEmpty(salesCode)) {
			t.put("salesCode", salesCode);
			addCount++;
		}
		
		if (StringUtils.isNotEmpty(cpaInflowCode)) {
			t.put("cpaInflowCode", cpaInflowCode);
			addCount++;
		}
		
		if (StringUtils.isNotEmpty(mileageInflowCode)) {
			t.put("mileageInflowCode", mileageInflowCode);
			addCount++;
		}
		
		if (StringUtils.isNotEmpty(naverInflowCode)) {
			t.put("naverInflowCode", naverInflowCode);
			addCount++;
		}
		
		if (StringUtils.isNotEmpty(saClickId)) {
			t.put("saClickId", saClickId);
			addCount++;
		}
		
		if (addCount == 0) {
			t = null;
		}
		
		this.interfaceInfo = t;
	}

	public NaverPay(String deviceType, String backUrl, ConfigPg configPg) {

		try {

			if (configPg == null) {
				throw new NullPointerException("PG 설정 정보가 없습니다");
			}

			this.use = configPg.isUseNpayOrder();
			
			Config config = ShopUtils.getConfig();
			if (!"Y".equals(config.getNaverPayFlag())) {
				this.use = false;
			}
			
			if (this.use) {
				
				deviceType = deviceType.toLowerCase();
				
				this.buttonKey = configPg.getNpayButtonKey();


				if ("mobile".equals(deviceType.toLowerCase())) {
					this.payPopupUrl = SalesonProperty.getNaverCheckoutMobilePayurl();
					this.wishlistPopupUrl = SalesonProperty.getNaverCheckoutMobileWishlist();
					this.buttonScriptUrl = SalesonProperty.getNaverCheckoutMobileButtonScript();

				} else {
					this.payPopupUrl = SalesonProperty.getNaverCheckoutPcPayurl();
					this.wishlistPopupUrl = SalesonProperty.getNaverCheckoutPcWishlist();
					this.buttonScriptUrl = SalesonProperty.getNaverCheckoutPcButtonScript();

				}

				this.wishlistApiUrl = SalesonProperty.getNaverCheckoutWishlistApiUrl();
				this.orderApiUrl = SalesonProperty.getNaverCheckoutOrderApiUrl();
				this.merchantId = configPg.getNpayMid();
				this.certiKey = configPg.getNpayKey();
				
				String redirectUrl = SalesonProperty.getSalesonUrlShoppingmall();
				
				if ("mobile".equals(deviceType.toLowerCase())) {
					if (!backUrl.startsWith("/m")) {
						redirectUrl += "/m";
					}
				}
				
				redirectUrl += backUrl;
				this.backUrl = redirectUrl;
				this.deviceType = deviceType;
			}
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			this.use = false;
		}
	}

	public String getButtonKey() {
		return buttonKey;
	}

	public void setButtonKey(String buttonKey) {
		this.buttonKey = buttonKey;
	}

	public boolean isUse() {
		return use;
	}

	public void setUse(boolean use) {
		this.use = use;
	}

	public String getOrderApiUrl() {
		return orderApiUrl;
	}

	public void setOrderApiUrl(String orderApiUrl) {
		this.orderApiUrl = orderApiUrl;
	}

	public String getPayPopupUrl() {
		return payPopupUrl;
	}

	public void setPayPopupUrl(String payPopupUrl) {
		this.payPopupUrl = payPopupUrl;
	}

	public String getWishlistApiUrl() {
		return wishlistApiUrl;
	}

	public void setWishlistApiUrl(String wishlistApiUrl) {
		this.wishlistApiUrl = wishlistApiUrl;
	}

	public String getWishlistPopupUrl() {
		return wishlistPopupUrl;
	}

	public void setWishlistPopupUrl(String wishlistPopupUrl) {
		this.wishlistPopupUrl = wishlistPopupUrl;
	}

	public String getCertiKey() {
		return certiKey;
	}

	public void setCertiKey(String certiKey) {
		this.certiKey = certiKey;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public String getButtonScriptUrl() {
		return buttonScriptUrl;
	}

	public void setButtonScriptUrl(String buttonScriptUrl) {
		this.buttonScriptUrl = buttonScriptUrl;
	}
	
	
}
