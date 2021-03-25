package saleson.common.exception;

public class GiftItemException extends RuntimeException {

    public GiftItemException() {
        super("사은품 처리시 문제가 발생했습니다.");
    }

    public GiftItemException(String message) {
        super(message);
    }
}
