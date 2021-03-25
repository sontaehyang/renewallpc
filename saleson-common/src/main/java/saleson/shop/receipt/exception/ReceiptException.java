package saleson.shop.receipt.exception;

import saleson.shop.order.support.OrderException;

@SuppressWarnings("serial")
public class ReceiptException extends OrderException {
	private static String BASE_REDIRECT_URL = "/cart";

	public ReceiptException() {
		super("잘못된 접근입니다.");
		setRedirectUrl(BASE_REDIRECT_URL);
	}

	public ReceiptException(String message) {
		super(message);
		setRedirectUrl(BASE_REDIRECT_URL);
	}

	public ReceiptException(String message, String redirectUrl) {
		super(message);
		setRedirectUrl(redirectUrl);
	}

	public ReceiptException(String message, String redirectUrl, String javascript) {
		super(message);
		setRedirectUrl(redirectUrl);
		setJavascript(javascript);
	}

}
