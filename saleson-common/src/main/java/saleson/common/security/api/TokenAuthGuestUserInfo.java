package saleson.common.security.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saleson.shop.user.domain.GuestUser;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenAuthGuestUserInfo {

    public TokenAuthGuestUserInfo(GuestUser guestUser, String jti) {

        if (guestUser != null) {
            this.userName = guestUser.getUserName();
            this.phoneNumber = guestUser.getPhoneNumber();
        }

        this.jti = jti;
    }

    private String userName;
    private String phoneNumber;
    private String jti;
}
