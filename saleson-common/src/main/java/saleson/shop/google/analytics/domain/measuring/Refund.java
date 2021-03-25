package saleson.shop.google.analytics.domain.measuring;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import saleson.common.utils.CommonUtils;
import saleson.model.google.analytics.ConfigGoogleAnalytics;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class Refund extends CommonMeasurement {

    private String ec;
    private String ea;
    private String el;
    private String ni; // Non-interaction parameter.

    private String tr; // Transaction Revenue
    private String ti; // Transaction ID. Required.

    public Refund(ConfigGoogleAnalytics configGoogleAnalytics,
                  String clientId,
                  String orderCode, int orderSequence) {
        super(configGoogleAnalytics, clientId, "event", "refund");
        setNi("1");
        setTi(orderCode + "-" + orderSequence);
    }

    public MultiValueMap<String, String> map() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("v", CommonUtils.dataNvl(getV()));
        map.add("tid", CommonUtils.dataNvl(getTid()));
        map.add("cid", CommonUtils.dataNvl(getCid()));
        map.add("t", CommonUtils.dataNvl(getT()));
        map.add("ec", CommonUtils.dataNvl(getEc()));
        map.add("ea", CommonUtils.dataNvl(getEa()));
        map.add("el", CommonUtils.dataNvl(getEl()));

        map.add("ni", CommonUtils.dataNvl(getNi()));

        map.add("ti", CommonUtils.dataNvl(getTi()));

        if (!StringUtils.isEmpty(getTr())) {
            map.add("tr", CommonUtils.dataNvl(getTr()));
        }

        map.add("pa", CommonUtils.dataNvl(getPa()));

        List<Product> products = getProducts();
        if (products != null && !products.isEmpty()) {
            int position = 1;

            for (Product i : products) {

                String prefix = "pr"+position;

                map.add(prefix + "id", CommonUtils.dataNvl(i.getId()));
                map.add(prefix + "qt", CommonUtils.dataNvl(i.getQt()));

                if (!StringUtils.isEmpty(i.getPr())) {
                    map.add(prefix + "pr", CommonUtils.dataNvl(i.getPr()));
                }

                map.add(prefix + "ps",String.valueOf(position));

                position++;
            }
        }

        return map;
    }
}
