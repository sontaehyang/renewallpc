package saleson.shop.google.analytics.domain.measuring;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import saleson.common.utils.CommonUtils;
import saleson.model.google.analytics.ConfigGoogleAnalytics;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class Purchase extends CommonMeasurement {

    private static final Logger log = LoggerFactory.getLogger(Purchase.class);

    private String dh; // Document hostname.
    private String dp; // Page.
    private String dt; // Title.

    private String ti; // Transaction ID. Required.
    private String ta; // Affiliation.
    private String tr; // Revenue.
    private String tt; // Tax.
    private String ts; // Shipping.
    private String tcc; // Transaction coupon.

    private String cu; // Currency Code

    public Purchase(ConfigGoogleAnalytics configGoogleAnalytics,
                    String clientId,
                    String orderCode,
                    int orderSequence,
                    String page) {

        super(configGoogleAnalytics, clientId, "pageview", "purchase");

        setTi(orderCode + "-" + orderSequence);
        setDp(page);
    }

    public MultiValueMap<String, String> map() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("v", CommonUtils.dataNvl(getV()));
        map.add("tid", CommonUtils.dataNvl(getTid()));
        map.add("cid", CommonUtils.dataNvl(getCid()));
        map.add("t", CommonUtils.dataNvl(getT()));
        map.add("dh", CommonUtils.dataNvl(getDh()));
        map.add("dp", CommonUtils.dataNvl(getDp()));
        map.add("dt", CommonUtils.dataNvl(getDt()));

        map.add("ti", CommonUtils.dataNvl(getTi()));
        map.add("ta", CommonUtils.dataNvl(getTa()));
        map.add("tr", CommonUtils.dataNvl(getTr()));
        map.add("tt", CommonUtils.dataNvl(getTt()));
        map.add("ts", CommonUtils.dataNvl(getTs()));
        map.add("tcc", CommonUtils.dataNvl(getTcc()));

        map.add("pa", CommonUtils.dataNvl(getPa()));
        map.add("cu", CommonUtils.dataNvl(getCu()));

        List<Product> products = getProducts();
        if (products != null && !products.isEmpty()) {
            int position = 1;

            for (Product i : products) {

                String prefix = "pr" + position;

                map.add(prefix + "id", CommonUtils.dataNvl(i.getId()));
                map.add(prefix + "nm", CommonUtils.dataNvl(i.getNm()));
                map.add(prefix + "ca", CommonUtils.dataNvl(i.getCa()));
                map.add(prefix + "br", CommonUtils.dataNvl(i.getBr()));
                map.add(prefix + "va", CommonUtils.dataNvl(i.getVa()));
                map.add(prefix + "pr", CommonUtils.dataNvl(i.getPr()));
                map.add(prefix + "qt", CommonUtils.dataNvl(i.getQt()));
                map.add(prefix + "ps", String.valueOf(position));

                position++;
            }
        }

        return map;
    }

}
