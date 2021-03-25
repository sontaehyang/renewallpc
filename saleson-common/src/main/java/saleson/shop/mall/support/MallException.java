package saleson.shop.mall.support;


@SuppressWarnings("serial")
public class MallException extends RuntimeException {
	
	public MallException() {
		super("잘못된 접근입니다.");
	}
	
	public MallException(String message) {
		super(message);
	}
	
}
