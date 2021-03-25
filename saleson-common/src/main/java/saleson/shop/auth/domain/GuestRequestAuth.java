package saleson.shop.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuestRequestAuth {

    private String userName;
    private String phoneNumber;
    private String requestToken;
    private String authNumber;

}
