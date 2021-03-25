package saleson.common.exception;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

public class ProcessApiException  extends RuntimeException{

    private HttpStatus status;

    public ProcessApiException() {
        super("API 실행시 문제가 발생했습니다.");
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ProcessApiException(String message) {
        super(message);
        this.status =HttpStatus.BAD_REQUEST;
    }

    public ProcessApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
