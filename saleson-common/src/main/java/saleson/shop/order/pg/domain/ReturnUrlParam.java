package saleson.shop.order.pg.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnUrlParam {

    private String salesonId;
    private String token;
    private String tokenType;
    private String failUrl;
    private String successUrl;
    private String deviceType;

}
