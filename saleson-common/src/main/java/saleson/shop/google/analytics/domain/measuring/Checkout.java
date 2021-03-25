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
public class Checkout extends CommonMeasurement {

    private String dh; // Document hostname.
    private String dp; // Page.
    private String dt; // Title.
    private String pa; // Product action (checkout).
    private String cos; // Checkout step #1.
    private String col; // Checkout step option.

    public Checkout(ConfigGoogleAnalytics configGoogleAnalytics, String clientId, String dp, int step) {
        super(configGoogleAnalytics, clientId, "pageview", "checkout");
        setDp(dp);
        setCos(String.valueOf(step));
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
        map.add("pa", CommonUtils.dataNvl(getPa()));

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

        map.add("cos", CommonUtils.dataNvl(getCos()));
        map.add("co1", CommonUtils.dataNvl(getCol()));
        return map;
    }
}
