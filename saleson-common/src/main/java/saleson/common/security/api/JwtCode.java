package saleson.common.security.api;

import saleson.common.enumeration.mapper.CodeMapperType;

public enum JwtCode implements CodeMapperType {
    JWT_CHARSET("UTF-8","jwtCharset","jwtCharset"),
    JWT_TOKEN_HEADER_PARAM("Authorization","jwtTokenHeaderParam","jwtTokenHeaderParam"),
    JWT_TOKEN_PREFIX("Bearer ","jwtTokenHeaderParam","jwtTokenHeaderParam"),
    TOKEN_SECURE_KEY("UTTpX3QrWPZKFqxjW/+aFXOGxK+i9EofiGRxsG5ad0g=","tokenSecureKey","토큰 인증키(HS256)"),

    JWT_CLAIM_ID("id ","user logid","로그인 id"),
    JWT_CLAIM_LOGIN_TYPE("op_login_type ","user login type","로그인 타입"),
    JWT_CLAIM_SIGN("sign","sign","Token 인증체크용 데이터"),
    JWT_CLAIM_GUEST_USER("guest_user","guest_user","Guest User 정보"),

    REFRESH_TOKEN_SECURE_KEY("b18n9591c9y220uw5o82c8vb", "refreshTokenSecureKey", "토큰 발행 HMAC Hashing key"),
    TOKEN_SIGN_SECURE_KEY("UTTpX3QrWPZKFqxjW", "tokenSignSecureKey", "토큰 Sing HMAC Hashing key");
    ;

    JwtCode(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

    private String code;
    private String title;
    private String description;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

	@Override
	public Boolean isEnabled() {
		return true;
	}


}
