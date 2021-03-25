package saleson.shop.ums.support;

import antlr.StringUtils;
import com.onlinepowers.framework.security.userdetails.User;
import saleson.common.config.SalesonProperty;
import saleson.common.notification.domain.UmsTemplate;
import saleson.common.utils.ShopUtils;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.shop.config.domain.Config;
import saleson.shop.item.domain.Item;
import saleson.shop.restocknotice.domain.RestockNotice;

import java.util.HashMap;

public class ItemRestock extends UmsTemplate {

    private RestockNotice restockNotice;

    private Item item;

    private User user;

    public ItemRestock() { intiCodeMapView(); }

    public ItemRestock(Ums ums, RestockNotice restockNotice, String phoneNumber, ApplicationInfo applicationInfo, Item item, User user) {
        super(ums, phoneNumber);
        this.restockNotice = restockNotice;
        this.item = item;
        this.user = user;

        intiCodeMapView();
        intiCodeMap();

        super.initialize(restockNotice.getUserId(), applicationInfo);
    }

    private void intiCodeMapView() {

        HashMap<String, String> map = new HashMap<>();

        map.put("item_name", "상품명");
        map.put("user_name", "이름");

        map.put("site_url", "사이트URL");
        map.put("site_name", "상점명");

        addCodeViewMap(map);
    }

    private void intiCodeMap() {

        HashMap<String, String> map = new HashMap<>();

        Config config = ShopUtils.getConfig();

        map.put("item_name", ShopUtils.unescapeHtml(restockNotice.getItemName()));
        map.put("user_name", user.getUserName());

        map.put("site_name", config.getShopName());

        if ("api".equals(SalesonProperty.getSalesonViewType())) {
            map.put("item_link", SalesonProperty.getSalesonUrlFrontend() + "/items/details.html?code=" + item.getItemUserCode());
            map.put("site_url", SalesonProperty.getSalesonUrlFrontend());
        } else {
            map.put("item_link", SalesonProperty.getSalesonUrlShoppingmall() + "/products/view/" + item.getItemUserCode());
            map.put("site_url", SalesonProperty.getSalesonUrlShoppingmall());
        }

        addCodeMap(map);

    }
}
