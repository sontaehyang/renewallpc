package saleson.common.exception;

public class SecureServletException extends RuntimeException{

    public SecureServletException() {
        super("Https 요청이 아닙니다.");
    }

    public SecureServletException(String message) {
        super(message);
    }

}
