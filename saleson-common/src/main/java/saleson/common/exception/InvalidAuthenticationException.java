package saleson.common.exception;

public class InvalidAuthenticationException extends RuntimeException {

    private boolean sleepUserFlag = false;
    private boolean passwordExpiredFlag = false;

    public InvalidAuthenticationException() {
        super("유효한 토큰이 아닙니다.");
    }

    public InvalidAuthenticationException(String message) {
        super(message);
    }

    public InvalidAuthenticationException(String message, boolean sleepUserFlag, boolean passwordExpiredFlag) {
        super(message);

        this.sleepUserFlag = sleepUserFlag;
        this.passwordExpiredFlag = passwordExpiredFlag;
    }

    public boolean isPasswordExpiredFlag() {
        return passwordExpiredFlag;
    }

    public void setPasswordExpiredFlag(boolean passwordExpiredFlag) {
        this.passwordExpiredFlag = passwordExpiredFlag;
    }

    public boolean isSleepUserFlag() {
        return sleepUserFlag;
    }

    public void setSleepUserFlag(boolean sleepUserFlag) {
        this.sleepUserFlag = sleepUserFlag;
    }
}
