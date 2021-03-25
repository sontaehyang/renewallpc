package saleson.common.exception;

public class KakaoAlimTalkException extends RuntimeException {

    public KakaoAlimTalkException() {
        super("카카오 알림톡 처리시 문제가 발생했습니다.");
    }

    public KakaoAlimTalkException(String message) {
        super(message);
    }

    public KakaoAlimTalkException(String message, Throwable cause) {
        super(message, cause);
    }
}
