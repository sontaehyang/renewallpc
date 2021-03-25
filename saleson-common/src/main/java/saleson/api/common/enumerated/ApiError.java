package saleson.api.common.enumerated;

import org.springframework.http.HttpStatus;
import saleson.common.google.analytics.GoogleAnalytics;

public enum ApiError {

    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 API 입니다.", "API 대상에 오타가 없는지 확인해 보세요."),
    UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED, "인증이 필요합니다.", "토큰 인증정보가 올바르지 않습니다."),
    UNAUTHORIZED_SMS(HttpStatus.UNAUTHORIZED, "인증이 필요합니다.", "SMS 인증이 필요합니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다.", "인증 API를 통해 AccessToken을 발급받은 후 다시 요청해 보세요."),
    NOT_VALID_USER(HttpStatus.UNAUTHORIZED, "회원이 존재하지 않습니다.", "회원 정보가 존재 하지 않습니다."),
    NOT_VALID_LOGIN(HttpStatus.UNAUTHORIZED, "로그인 정보 불일치", "아이디/비밀번호가 일치하지 않습니다."),
    DUPLICATION_LOGIN_ID(HttpStatus.CONFLICT, "로그인 아이디 중복 Error", "이미 존재하는 아이디입니다."),
    DUPLICATION_SMS_INFO(HttpStatus.CONFLICT, "SMS 가입정보 중복 Error", "이미 가입되어있습니다."),
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "System Error (시스템 에러)", "서버 내부 에러가 발생하였습니다. 신속히 조치하겠습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용하지 않는 Method 입니다.", "API에 맞는 Method를 확인 바랍니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", "API 요청에 오류가 있습니다. 요청 URL, 필수 요청 변수가 정확한지 확인 바랍니다."),
    BAD_REQUEST_DISPLAY(HttpStatus.BAD_REQUEST, "부적절한 display 값입니다.", "display 요청 변수값이 허용 범위(1~100)인지 확인해 보세요."),
    BAD_REQUEST_START_VALUE(HttpStatus.BAD_REQUEST, "부적절한 start 값입니다.", "start 요청 변수값이 허용 범위(1~1000)인지 확인해 보세요."),
    BAD_REQUEST_SORT_VALUE(HttpStatus.BAD_REQUEST, "부적절한 sort 값입니다.", "sort 요청 변수 값에 오타가 없는지 확인해 보세요."),
    BAD_REQUEST_NO_ITEM(HttpStatus.BAD_REQUEST, "상품정보가 없습니다.", "상품ID를 확인해주세요."),
    BAD_REQUEST_NO_ITEM_OPTION(HttpStatus.BAD_REQUEST, "상품의 옵션정보가 변경되었습니다.", "옵션정보를 확인해주세요."),
    BAD_REQUEST_FAIL_DOWNLOAD_COUPON(HttpStatus.BAD_REQUEST, "쿠폰이 다운되지 않았습니다.", "쿠폰ID를 확인해주세요."),
    BAD_REQUEST_NOT_CONFIRM_STATUS(HttpStatus.BAD_REQUEST, "상품 구매확정을 할 수 있는 상태가 아닙니다.", "주문상태를 확인해주세요"),
    BAD_REQUEST_INQUIRY_DELETE_FAIL(HttpStatus.BAD_REQUEST, "답변완료 된 문의는 삭제하실 수 없습니다.", "문의를 삭제 할 수 없습니다."),
    BAD_REQUEST_CART_BUY_MAX_FAIL(HttpStatus.BAD_REQUEST, "장바구니에 담긴 수량이 최대 구매 가능 수량을 초과하였습니다.", "수량을 확인해주세요."),
    BAD_REQUEST_CART_BUY_LIMIT_FAIL(HttpStatus.BAD_REQUEST, "장바구니에 담긴 수량은 999개를 초과할 수 없습니다.", "수량을 확인해주세요."),
    BAD_REQUEST_CART_BUY_MIN_FAIL(HttpStatus.BAD_REQUEST, "장바구니에 담긴 수량이 최소 구매 가능 수량을 미달하였습니다.", "수량을 확인해주세요."),
    BAD_REQUEST_NO_FEATURED(HttpStatus.BAD_REQUEST, "기획전이 존재하지 않습니다.", "기획전 ID/공개 여부를 확인해주세요."),
    BAD_REQUEST_FEATURED_FINISH(HttpStatus.BAD_REQUEST, "해당 기획전은 종료되었습니다.", "기획전 기간/공개 여부를 확인해주세요."),
    BAD_REQUEST_FAIL_PASSWD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.", "비밀번호를 확인해주세요."),
    BAD_REQUEST_NOT_ALLOWED_WORD(HttpStatus.BAD_REQUEST, "금지어를 확인해주세요.", "내용에 금지어가 포함되어 있습니다."),
    BAD_REQUEST_NO_PAYMENT(HttpStatus.BAD_REQUEST, "설정된 결제 방법이 없습니다.", "고객센터로 문의 부탁 드립니다."),
    BAD_REQUEST_NO_SHIPPING(HttpStatus.BAD_REQUEST, "배송지 필수정보 미입력", "배송지 정보를 입력해주세요."),
    BAD_REQUEST_PAY_MIN_FAIL(HttpStatus.BAD_REQUEST, "실시간 계좌이체 결제 요청 실패 : 금액오류(200원 이하 이체불가)", "최소 결제 금액을 확인해주세요."),
    BAD_REQUEST_BUY_MIN_FAIL(HttpStatus.BAD_REQUEST, "최소 결제가능 금액보다 작습니다.", "최소 결제가능 금액을 확인해주세요."),
    BAD_BUY_NO_ITEM(HttpStatus.BAD_REQUEST, "결제가능 상품이 없습니다.", "결제가능 상품이 없습니다."),
    FAIL_CALL_NPAY(HttpStatus.BAD_REQUEST, "네이버 페이 실행에 실패 했습니다.", "네이버 페이 실행에 실패 했습니다."),
    FAIL_GOOGLE_ANALYTICS(HttpStatus.BAD_REQUEST, "Google Analytics 처리에 실패 했습니다.", "Google Analytics 처리에 실패 했습니다."),
    FAIL_GOOGLE_ANALYTICS_COMMON_TRACKING(HttpStatus.BAD_REQUEST, "Google Analytics 공통 처리에 실패 했습니다.", "Google Analytics 공통 처리에 실패 했습니다.");

    private HttpStatus httpStatus;
    private String message;
    private String description;

    ApiError(HttpStatus httpStatus, String message, String description) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.description = description;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
