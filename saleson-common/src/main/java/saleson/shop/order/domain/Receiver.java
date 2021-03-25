package saleson.shop.order.domain;

import java.util.ArrayList;
import java.util.List;

import saleson.common.utils.ShopUtils;
import saleson.shop.coupon.domain.OrderCoupon;

import com.onlinepowers.framework.util.StringUtils;

public class Receiver {
	
	private long userId;
	private String sessionId;
	private String orderCode;
	private int shippingIndex;
	private String receiveZipcode;
	private String receiveNewZipcode;
	private String receiveCompanyName;
	private String receiveSido;
	private String receiveSigungu;
	private String receiveEupmyeondong;
	private String receiveAddress;
	private String receiveAddressDetail;
	private String receiveName;
	private String receivePhone;
	private String receiveMobile;
	private String content;
	private String createdDate;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	private List<BuyQuantity> buyQuantitys;
	public List<BuyQuantity> getBuyQuantitys() {
		return buyQuantitys;
	}
	public void setBuyQuantitys(List<BuyQuantity> buyQuantitys) {
		this.buyQuantitys = buyQuantitys;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	// 배송지별 배송될 상품들
	private List<Shipping> itemGroups;
	private List<BuyItem> items;
	public List<BuyItem> getItems() {
		return items;
	}
	
	public void setItems(List<BuyItem> items) {
		this.items = items;
	}
	
	/**
	 * 상품쿠폰 할인금액을 적용
	 * @param isClean - POST로 넘어온 데이터를 적용할때는 쿠폰 사용 정보를 초기화해줘야함!!
	 */
	public void itemCouponUsed(boolean isClean, Buy buy, int shippingIndex) {
		
		if (this.items == null) {
			return;
		}
		
		for(BuyItem buyItem : this.items) {
			
			// 초기화?? -- 중복 상품 쿠폰때문에 초기화 하면 안됨..
			if (isClean) {
				buyItem.setCouponUserId(0);
				buyItem.setAddCouponUserId(0);
			}
			
			// POST로 전송된 쿠폰 사용정보 셋팅
			buyItem.setCouponUserId(buy.getUseCouponKeys(), shippingIndex);
			
			ItemPrice itemPrice = buyItem.getItemPrice();
			
			itemPrice.setCouponDiscountAmount(0);
			
			int couponDiscountPrice = 0;
			int couponDiscountAmount = 0;
			
			List<OrderCoupon> itemCoupons = buyItem.getItemCoupons();
			
			if (itemCoupons != null) {
				for(OrderCoupon itemCoupon : itemCoupons) {
					
					if (buyItem.getCouponUserId() == itemCoupon.getCouponUserId()) {
						
						couponDiscountPrice += itemCoupon.getDiscountPrice();
						couponDiscountAmount += itemCoupon.getDiscountAmount();
	
						String couponKey = "item-coupon-" + itemCoupon.getCouponUserId() + "-" + buyItem.getItemSequence() + "-" + shippingIndex;
						
						// step1에서 사용할 데이터 만들기
						List<String> makeUseCouponKeys = buy.getMakeUseCouponKeys();
						if (makeUseCouponKeys == null) {
							makeUseCouponKeys = new ArrayList<>();
						}
						
						makeUseCouponKeys.add(couponKey);
						buy.setMakeUseCouponKeys(makeUseCouponKeys);
					}
	
				}
			}
			
			/* CJH 2017. 02. 10 중복 할인 쿠폰은 재거
			List<OrderCoupon> addItemCoupons = buyItem.getAddItemCoupons();
			
			if (addItemCoupons != null) {
				for(OrderCoupon itemCoupon : addItemCoupons) {
					
					// CJH 2016. 10. 27 쿠폰 할인금액 정의
					itemCoupon.setDiscountAmount(itemCoupon.getDiscountPrice() * itemPrice.getQuantity());
					if ("3".equals(itemCoupon.getCouponKind()) && buyItem.getAddCouponUserId() == itemCoupon.getCouponUserId()) {
						
						couponDiscountPrice += itemCoupon.getDiscountPrice();
						couponDiscountAmount += itemCoupon.getDiscountAmount();

						String couponKey = "add-item-coupon-" + itemCoupon.getCouponUserId() + "-" + buyItem.getItemSequence() + "-" + shippingIndex;
						
						// step1에서 사용할 데이터 만들기
						List<String> makeUseCouponKeys = buy.getMakeUseCouponKeys();
						if (makeUseCouponKeys == null) {
							makeUseCouponKeys = new ArrayList<>();
						}
						
						makeUseCouponKeys.add(couponKey);
						buy.setMakeUseCouponKeys(makeUseCouponKeys);
					}
				}
			}
			*/
			itemPrice.setCouponDiscountPrice(couponDiscountPrice);
			itemPrice.setCouponDiscountAmount(couponDiscountAmount);
		}
		
	}
	
	public void setShipping(String islandType) {
		Shipping shipping = new Shipping();
		
		if (this.items.isEmpty()) {
			return;
		}

		this.itemGroups = shipping.getShippingGroups(this.items, islandType);
	}
	
	public int getShippingIndex() {
		return shippingIndex;
	}
	public void setShippingIndex(int shippingIndex) {
		this.shippingIndex = shippingIndex;
	}
	public List<Shipping> getItemGroups() {
		return itemGroups;
	}
	public void setItemGroups(List<Shipping> itemGroups) {
		this.itemGroups = itemGroups;
	}

	private String receiveZipcode1;
	private String receiveZipcode2;
	public String getFullReceiveZipcode() {
		
		if (StringUtils.isEmpty(this.receiveZipcode2)) {
			return this.receiveZipcode;
		}
		
		return this.receiveZipcode1 + "-" + this.receiveZipcode2;
	}
	public String getReceiveZipcode1() {
		return receiveZipcode1;
	}
	public void setReceiveZipcode1(String receiveZipcode1) {
		this.receiveZipcode1 = receiveZipcode1;
	}
	public String getReceiveZipcode2() {
		return receiveZipcode2;
	}
	public void setReceiveZipcode2(String receiveZipcode2) {
		this.receiveZipcode2 = receiveZipcode2;
	}

	private String receivePhone1;
	private String receivePhone2;
	private String receivePhone3;
	public String getFullReceivePhone() {
		return this.receivePhone1 + "-" + this.receivePhone2 + "-" + this.receivePhone3;
	}
	public String getReceivePhone1() {
		return receivePhone1;
	}
	public void setReceivePhone1(String receivePhone1) {
		this.receivePhone1 = receivePhone1;
	}
	public String getReceivePhone2() {
		return receivePhone2;
	}
	public void setReceivePhone2(String receivePhone2) {
		this.receivePhone2 = receivePhone2;
	}
	public String getReceivePhone3() {
		return receivePhone3;
	}
	public void setReceivePhone3(String receivePhone3) {
		this.receivePhone3 = receivePhone3;
	}

	private String receiveMobile1;
	private String receiveMobile2;
	private String receiveMobile3;
	public String getFullReceiveMobile() {
		return this.receiveMobile1 + "-" + this.receiveMobile2 + "-" + this.receiveMobile3;
	}
	public String getReceiveMobile1() {
		return receiveMobile1;
	}
	public void setReceiveMobile1(String receiveMobile1) {
		this.receiveMobile1 = receiveMobile1;
	}
	public String getReceiveMobile2() {
		return receiveMobile2;
	}
	public void setReceiveMobile2(String receiveMobile2) {
		this.receiveMobile2 = receiveMobile2;
	}
	public String getReceiveMobile3() {
		return receiveMobile3;
	}
	public void setReceiveMobile3(String receiveMobile3) {
		this.receiveMobile3 = receiveMobile3;
	}
	public String getReceiveNewZipcode() {
		return receiveNewZipcode;
	}
	public void setReceiveNewZipcode(String receiveNewZipcode) {
		this.receiveNewZipcode = receiveNewZipcode;
	}
	public String getReceiveZipcode() {
		return receiveZipcode;
	}
	public void setReceiveZipcode(String receiveZipcode) {
		
		String[] temp = StringUtils.delimitedListToStringArray(receiveZipcode, "-");
		if (temp.length == 2) {
			this.receiveZipcode1 = temp[0];
			this.receiveZipcode2 = temp[1];
		}
		
		this.receiveZipcode = receiveZipcode;
	}
	public String getReceiveCompanyName() {
		return receiveCompanyName;
	}
	public void setReceiveCompanyName(String receiveCompanyName) {
		this.receiveCompanyName = receiveCompanyName;
	}
	public String getReceiveSido() {
		return receiveSido;
	}
	public void setReceiveSido(String receiveSido) {
		this.receiveSido = receiveSido;
	}
	public String getReceiveSigungu() {
		return receiveSigungu;
	}
	public void setReceiveSigungu(String receiveSigungu) {
		this.receiveSigungu = receiveSigungu;
	}
	public String getReceiveEupmyeondong() {
		return receiveEupmyeondong;
	}
	public void setReceiveEupmyeondong(String receiveEupmyeondong) {
		this.receiveEupmyeondong = receiveEupmyeondong;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public String getReceiveAddressDetail() {
		return receiveAddressDetail;
	}
	public void setReceiveAddressDetail(String receiveAddressDetail) {
		this.receiveAddressDetail = receiveAddressDetail;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getReceivePhone() {
		return receivePhone;
	}
	public void setReceivePhone(String receivePhone) {
		
		String[] cutArray = ShopUtils.phoneNumberForDelimitedToStringArray(receivePhone);
		
		this.receivePhone1 = cutArray[0];
		this.receivePhone2 = cutArray[1];
		this.receivePhone3 = cutArray[2];
		
		this.receivePhone = receivePhone;
	}
	public String getReceiveMobile() {
		return receiveMobile;
	}
	public void setReceiveMobile(String receiveMobile) {
		
		String[] cutArray = ShopUtils.phoneNumberForDelimitedToStringArray(receiveMobile);
		
		this.receiveMobile1 = cutArray[0];
		this.receiveMobile2 = cutArray[1];
		this.receiveMobile3 = cutArray[2];
		
		this.receiveMobile = receiveMobile;
	}
	
}
