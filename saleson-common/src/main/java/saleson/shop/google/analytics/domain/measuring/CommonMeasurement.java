package saleson.shop.google.analytics.domain.measuring;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.model.google.analytics.ConfigGoogleAnalytics;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Data
public class CommonMeasurement {

    private static final Logger log = LoggerFactory.getLogger(CommonMeasurement.class);

    private String v; // Version.
    private String tid; // Tracking ID / Property ID. ex) UA-XXXXX-Y
    private String cid; // Anonymous Client ID.
    private String t; // hit type.
    private String cu; // Currency code.
    private String pa; // Product action

    private List<Product> products;

    public CommonMeasurement() {
        setV("1");
    }

    public CommonMeasurement(ConfigGoogleAnalytics configGoogleAnalytics, String clientId, String t, String pa) {

        this();

        if (configGoogleAnalytics != null) {
            setTid(configGoogleAnalytics.getMeasurementId());
            setCu(configGoogleAnalytics.getCurrency());
        }
        setCid(clientId);
        setT(t);
        setPa(pa);
    }

    protected String getURLEncode(String value) {

        try {

            return URLEncoder.encode(value,"UTF-8");

        } catch (UnsupportedEncodingException e) {
            log.error("CommonMeasurement URLEncode: {}", e.getMessage(), e);
        }

        return value;
    }
}
