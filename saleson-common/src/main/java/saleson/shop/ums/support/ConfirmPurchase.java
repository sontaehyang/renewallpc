package saleson.shop.ums.support;

import saleson.common.config.SalesonProperty;
import saleson.common.notification.domain.UmsTemplate;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.shop.config.domain.Config;

import java.util.HashMap;

public class ConfirmPurchase extends UmsTemplate {

    private Config config;

    public ConfirmPurchase() { intiCodeMapView(); }

    public ConfirmPurchase(Ums ums, long userId, String phoneNumber, Config config, ApplicationInfo applicationInfo) {
        super(ums, phoneNumber);

        this.config = config;

        intiCodeMapView();
        intiCodeMap();

        super.initialize(userId, applicationInfo);
    }

    private void intiCodeMapView() {

        HashMap<String, String> map = new HashMap<>();

        map.put("site_url", "사이트URL");
        map.put("site_name", "상점명");

        addCodeViewMap(map);
    }

    private void intiCodeMap() {

        HashMap<String, String> map = new HashMap<>();

        if ("api".equals(SalesonProperty.getSalesonViewType())) {
            map.put("site_url", SalesonProperty.getSalesonUrlFrontend());
        } else {
            map.put("site_url", SalesonProperty.getSalesonUrlShoppingmall());
        }

        map.put("site_name", config.getShopName());

        addCodeMap(map);
    }
}
