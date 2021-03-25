package saleson.common.context;

import java.io.Serializable;
import java.util.List;

import saleson.common.utils.SellerUtils;
import saleson.shop.categories.domain.Group;
import saleson.shop.categories.domain.Team;
import saleson.shop.config.domain.Config;
import saleson.shop.item.domain.Item;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.seo.domain.Seo;

import com.onlinepowers.framework.repository.Code;

@SuppressWarnings("serial")
public class ShopContext implements Serializable {
	public static final String REQUEST_NAME = "OP_CONTEXT";
	
	private Config config;
	private List<Team> gnbCategories;
	private List<Group> shopCategoryGroups;
	
	private List<Code> searchCategories;
	private Seo seo;
	private List<OrderItem> cartList;		// 장바구니 정보
	private List<Item> todayItems;		// 오늘 본 상품정
	private String alternateBaseUri = ""; 		// 모바일, PC 대응 URL (head -> link 기술)
	private boolean isMobileDevice;
	private boolean isNaverPay;
	private String naverWcslogKey;
	private boolean isMobileLayer;
	
	private List<saleson.shop.group.domain.Group> userGroups;		// 쇼핑몰 회원 그룹 정보.

	private int managerTimeout;
	private int userTimeout;

	public boolean isMobileLayer() {
		return isMobileLayer;
	}
	public void setMobileLayer(boolean isMobileLayer) {
		this.isMobileLayer = isMobileLayer;
	}
	public String getNaverWcslogKey() {
		return naverWcslogKey;
	}
	public void setNaverWcslogKey(String naverWcslogKey) {
		this.naverWcslogKey = naverWcslogKey;
	}
	public boolean isNaverPay() {
		return isNaverPay;
	}
	public void setNaverPay(boolean isNaverPay) {
		this.isNaverPay = isNaverPay;
	}
	public boolean isMobileDevice() {
		return isMobileDevice;
	}
	public void setMobileDevice(boolean isMobileDevice) {
		this.isMobileDevice = isMobileDevice;
	}
	private int point;
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}

	public Config getConfig() {
		return config;
	}
	public void setConfig(Config config) {
		this.config = config;
	}
	public Seo getSeo() {
		return seo;
	}
	public void setSeo(Seo seo) {
		this.seo = seo;
	}
	
	public List<Team> getGnbCategories() {
		return gnbCategories;
	}
	public void setGnbCategories(List<Team> gnbCategories) {
		this.gnbCategories = gnbCategories;
	}
	
	public List<OrderItem> getCartList() {
		return cartList;
	}
	public void setCartList(List<OrderItem> cartList) {
		this.cartList = cartList;
	}
	public List<Code> getSearchCategories() {
		return searchCategories;
	}
	public void setSearchCategories(List<Code> searchCategories) {
		this.searchCategories = searchCategories;
	}
	public List<Item> getTodayItems() {
		return todayItems;
	}
	public void setTodayItems(List<Item> todayItems) {
		this.todayItems = todayItems;
	}
	public String getAlternateBaseUri() {
		return alternateBaseUri;
	}
	public void setAlternateBaseUri(String alternateBaseUri) {
		this.alternateBaseUri = alternateBaseUri;
	}
	
	public List<saleson.shop.group.domain.Group> getUserGroups() {
		return userGroups;
	}
	public void setUserGroups(List<saleson.shop.group.domain.Group> userGroups) {
		this.userGroups = userGroups;
	}
	public long getHqSellerId() {
		return SellerUtils.DEFAULT_OPMANAGER_SELLER_ID;
		
	}
	public List<Group> getShopCategoryGroups() {
		return shopCategoryGroups;
	}
	public void setShopCategoryGroups(List<Group> shopCategoryGroups) {
		this.shopCategoryGroups = shopCategoryGroups;
	}

	public int getManagerTimeout() {
		return managerTimeout;
	}

	public void setManagerTimeout(int managerTimeout) {
		this.managerTimeout = managerTimeout;
	}

	public int getUserTimeout() {
		return userTimeout;
	}

	public void setUserTimeout(int userTimeout) {
		this.userTimeout = userTimeout;
	}
}
