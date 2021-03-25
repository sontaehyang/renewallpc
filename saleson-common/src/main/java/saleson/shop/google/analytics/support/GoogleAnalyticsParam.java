package saleson.shop.google.analytics.support;

import lombok.Data;
import saleson.shop.google.analytics.domain.measuring.Product;

import java.util.List;
import java.util.Map;

@Data
public class GoogleAnalyticsParam {

    private String itemUserCode;
    private String orderCode;
    private int orderSequence;
    private int itemSequence;

    private String cid;
    private String page;
    private List<Product> products;
    private List<String> cartArrayRequiredItems;

    private boolean addQuantityFlag;
    private boolean allCancelFlag;

    private Map<String, RefundParam> refundParamMap;

}
