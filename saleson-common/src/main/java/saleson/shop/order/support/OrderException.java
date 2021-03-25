package saleson.shop.order.support;

import saleson.model.OrderCancelFail;

@SuppressWarnings("serial")
public class OrderException extends RuntimeException {
	private static String BASE_REDIRECT_URL = "/cart";
	private String redirectUrl;
	private String javascript;

	private String orderCode;
	private int orderSequence;
	private int itemSequence;

    private OrderCancelFail orderCancelFail;

	public OrderException(Throwable cause) {
		super("잘못된 접근입니다.", cause);
		setRedirectUrl(BASE_REDIRECT_URL);
	}
	public OrderException() {
		super("잘못된 접근입니다.");
		setRedirectUrl(BASE_REDIRECT_URL);
	}
	
	public OrderException(String message) {
		super(message);
		setRedirectUrl(BASE_REDIRECT_URL);
	}

	public OrderException(String message, Throwable cause) {
		super(message, cause);
		setRedirectUrl(BASE_REDIRECT_URL);
	}
	
	public OrderException(String message, String redirectUrl) {
		super(message);
		setRedirectUrl(redirectUrl);
	}
	public OrderException(String message, String redirectUrl, Throwable cause) {
		super(message, cause);
		setRedirectUrl(redirectUrl);
	}
	
	public OrderException(String message, String redirectUrl, String javascript) {
		super(message);
		setRedirectUrl(redirectUrl);
		setJavascript(javascript);
	}

	public OrderException(String message, String redirectUrl, String javascript, Throwable cause) {
		super(message, cause);
		setRedirectUrl(redirectUrl);
		setJavascript(javascript);
	}

	public OrderException(String message, String orderCode, int orderSequence) {
		super(message);
		this.orderCode = orderCode;
		this.orderSequence = orderSequence;
	}

	public OrderException(String message, String orderCode, int orderSequence, int itemSequence) {
		super(message);
		this.orderCode = orderCode;
		this.orderSequence = orderSequence;
		this.itemSequence = itemSequence;
	}

    public OrderException(String message, String redirectUrl, OrderCancelFail orderCancelFail, Throwable cause) {
        super(message, cause);
        setRedirectUrl(redirectUrl);
        setOrderCancelFail(orderCancelFail);
    }

	public String getJavascript() {
		return javascript;
	}

	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}

	public String getRedirectUrl() {
		return this.redirectUrl;
	}
	
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getOrderSequence() {
		return orderSequence;
	}

	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}

	public int getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}

    public OrderCancelFail getOrderCancelFail() {
        return orderCancelFail;
    }

    public void setOrderCancelFail(OrderCancelFail orderCancelFail) {
        this.orderCancelFail = orderCancelFail;
    }
}
