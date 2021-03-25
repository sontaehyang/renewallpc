package saleson.shop.google.analytics.domain.measuring;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import saleson.common.utils.CommonUtils;
import saleson.model.google.analytics.ConfigGoogleAnalytics;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MeasuringAction extends CommonMeasurement{

    private String ec;
    private String ea;
    private String el;

    public MeasuringAction(ConfigGoogleAnalytics configGoogleAnalytics,
                           String clientId,
                           String productAction) {
        super(configGoogleAnalytics, clientId, "event", productAction);
    }

    public MultiValueMap<String, String> map() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("v", CommonUtils.dataNvl(getV()));
        map.add("tid", CommonUtils.dataNvl(getTid()));
        map.add("cid", CommonUtils.dataNvl(getCid()));
        map.add("t", CommonUtils.dataNvl(getT()));
        map.add("pa", CommonUtils.dataNvl(getPa()));
        map.add("cu", CommonUtils.dataNvl(getCu()));
        map.add("ec", CommonUtils.dataNvl(getEc()));
        map.add("ea", CommonUtils.dataNvl(getEa()));
        map.add("el", CommonUtils.dataNvl(getEl()));

        List<Product> products = getProducts();

        if (products != null && !products.isEmpty()) {
            int position = 1;

            for (Product i : products) {

                String prefix = "pr"+position;

                map.add(prefix + "id", CommonUtils.dataNvl(i.getId()));
                map.add(prefix + "nm", CommonUtils.dataNvl(i.getNm()));
                map.add(prefix + "ca", CommonUtils.dataNvl(i.getCa()));
                map.add(prefix + "br", CommonUtils.dataNvl(i.getBr()));
                map.add(prefix + "va", CommonUtils.dataNvl(i.getVa()));
                map.add(prefix + "pr", CommonUtils.dataNvl(i.getPr()));
                map.add(prefix + "qt", CommonUtils.dataNvl(i.getQt()));
                map.add(prefix + "ps",String.valueOf(position));

                position++;
            }
        }

        return map;
    }
}
