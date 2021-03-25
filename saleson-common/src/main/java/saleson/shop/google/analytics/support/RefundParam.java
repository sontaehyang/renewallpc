package saleson.shop.google.analytics.support;

import lombok.Data;

@Data
public class RefundParam {
    private String orderCode;
    private String orderSequence;
    private String itemSequence;
    private String applyQuantity;
}
