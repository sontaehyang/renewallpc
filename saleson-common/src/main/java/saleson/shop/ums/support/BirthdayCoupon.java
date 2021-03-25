package saleson.shop.ums.support;

import saleson.common.config.SalesonProperty;
import saleson.common.notification.domain.UmsTemplate;
import saleson.common.utils.ShopUtils;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.shop.config.domain.Config;
import saleson.shop.coupon.domain.CouponUser;

import java.util.HashMap;

public class BirthdayCoupon extends UmsTemplate {

    private String siteName;
    private String userName;
    private String couponName;

    public BirthdayCoupon() { intiCodeMapView(); }

    public BirthdayCoupon(Ums ums, CouponUser couponUser, long userId, String phoneNumber, Config config, ApplicationInfo applicationInfo) {
        super(ums, phoneNumber);

        this.siteName = config.getShopName();
        this.userName = couponUser.getUserName();
        this.couponName = ShopUtils.unescapeHtml(couponUser.getCouponName());

        intiCodeMapView();
        intiCodeMap();

        super.initialize(userId, applicationInfo);
    }

    private void intiCodeMapView() {

        HashMap<String, String> map = new HashMap<>();

        map.put("site_name", "상점명");
        map.put("user_name", "이름");
        map.put("coupon_name", "쿠폰명");
        map.put("inquiry_link", "1:1문의 링크");
        map.put("counsel_tel_number", "고객센터");

        addCodeViewMap(map);
    }

    private void intiCodeMap() {

        HashMap<String, String> map = new HashMap<>();
        Config config = ShopUtils.getConfig();

        map.put("site_name", this.siteName);
        map.put("user_name", this.userName);
        map.put("coupon_name", this.couponName);
        map.put("counsel_tel_number", config.getCounselTelNumber());

        if ("api".equals(SalesonProperty.getSalesonViewType())) {
            map.put("inquiry_link", SalesonProperty.getSalesonUrlFrontend() + "/mypage/inquiry.html");
        } else {
            map.put("inquiry_link", SalesonProperty.getSalesonUrlShoppingmall() + "/mypage/inquiry");
        }

        addCodeMap(map);
    }
}
