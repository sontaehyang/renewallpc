package saleson.common.asp28.analytics;

import java.util.List;

import saleson.shop.categories.domain.Breadcrumb;
import saleson.shop.categories.domain.BreadcrumbCategory;
import saleson.shop.item.domain.Item;
import saleson.shop.order.domain.Buy;
import saleson.shop.order.domain.BuyItem;
import saleson.shop.order.domain.ItemPrice;

import com.onlinepowers.framework.util.ValidationUtils;

public class Asp28Analytics {
	
	private String itemUserCode = "";
	private String categoryName = "";
	private String itemPrice = "";
	private String products = "";
	private String cartItems = "";
	private String query = "";
	private String orderCode = "";
	private String searchYn = "";
	private String deviceType = "Web";
	private String orderPayAmount = "";
	
	public Asp28Analytics() {
		
	}
	
	public Asp28Analytics(String query, String searchYn, String deviceType) {
		this.query = query;
		this.searchYn = searchYn;
	}
	
	/**
	 * 상품 상세
	 * @param item
	 * @param deviceType
	 */
	public Asp28Analytics(Item item, String deviceType) {
		if (item == null) {
			return;
		}
		
		this.itemUserCode = item.getItemUserCode();
		
		if (ValidationUtils.isNotNull(item.getBreadcrumbs())) {
			
			String categoryName = "";
			if (item.getBreadcrumbs().size() > 0) {
				Breadcrumb breadcrumb = item.getBreadcrumbs().get(0);
				int i = 0;
				for(BreadcrumbCategory category : breadcrumb.getBreadcrumbCategories()) {
					categoryName += (i++ > 0 ? ";" : "") + category.getCategoryName();
				}
			}
			this.categoryName = categoryName;
		}
		
		this.itemPrice = Integer.toString(item.getSalePrice());
	}
	
	// 장바구니, 주문서 작성에서 사용
	public Asp28Analytics(List<BuyItem> list, String deviceType, String type) {
		if (list == null) {
			return;
		}
		
		int i = 0;
		for(BuyItem buyItem : list) {
			
			
			Item item = buyItem.getItem();
			ItemPrice itemPrice = buyItem.getItemPrice();
			
			if (i > 0) {
				if ("cart".equals(type.toLowerCase())) {
					this.cartItems += ";";
				} else {
					this.products += ";";
				}
			}
			
			if ("cart".equals(type.toLowerCase())) {
				this.cartItems += item.getItemUserCode() + "_" + itemPrice.getQuantity();
			} else {
				this.products += item.getItemUserCode() + "_" + itemPrice.getSaleAmount() + "_" + itemPrice.getQuantity();
			}
			
			i++;
		}
	}
	
	//주문 완료에서만 사용
	public Asp28Analytics(Buy buy, String deviceType, String orderCode) {
		//List<OrderItem> list = order.getOrderItems();
		/*
		if (list == null) {
			return;
		}
		
		int i = 0;
		for(OrderItem item : list) {
			
			if (i > 0) {
				//this.products += ";";
				//this.cartItems += ";";
			}
			
			//this.cartItems += item.getItemName() + "_" + item.getQuantity();
			//this.products += item.getItemUserCode() + "_" + ((item.getItemPrice() + item.getTotalRequiredOptionsPrice() ))+ "_" + item.getQuantity();
			i++;
		}
		
		//this.orderPayAmount = Integer.toString(order.getOrderPayment().getOrderPayAmount());
		this.orderCode = orderCode;
		*/
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getItemUserCode() {
		return itemUserCode;
	}
	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	public String getCartItems() {
		return cartItems;
	}
	public void setCartItems(String cartItems) {
		this.cartItems = cartItems;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getSearchYn() {
		return searchYn;
	}

	public void setSearchYn(String searchYn) {
		this.searchYn = searchYn;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getOrderPayAmount() { 
		return orderPayAmount;
	}

	public void setOrderPayAmount(String orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

}
