package saleson.shop.ums.support;

import saleson.common.notification.domain.UmsTemplate;
import saleson.common.utils.ShopUtils;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.shop.config.domain.Config;
import saleson.shop.coupon.domain.CouponUser;

import java.util.HashMap;

public class ExpirationCoupon extends UmsTemplate {

    private String siteName;

    private String userName;

    private String couponName;

    private String couponDate;

    public ExpirationCoupon() { intiCodeMapView(); }

    public ExpirationCoupon(Ums ums, CouponUser couponUser, long userId, String phoneNumber, Config config, ApplicationInfo applicationInfo) {
        super(ums, phoneNumber);

        this.siteName = config.getShopName();
        this.userName = couponUser.getUserName();
        this.couponName = ShopUtils.unescapeHtml(couponUser.getCouponName());
        this.couponDate = couponUser.getCouponApplyEndDate();

        intiCodeMapView();
        intiCodeMap();

        super.initialize(userId, applicationInfo);
    }

    private void intiCodeMapView() {

        HashMap<String, String> map = new HashMap<>();

        map.put("site_name", "상점명");
        map.put("user_name", "이름");
        map.put("coupon_name", "쿠폰명");
        map.put("coupon_date", "사용기간");

        addCodeViewMap(map);
    }

    private void intiCodeMap() {

        HashMap<String, String> map = new HashMap<>();

        map.put("site_name", this.siteName);
        map.put("user_name", this.userName);
        map.put("coupon_name", this.couponName);
        map.put("coupon_date", this.couponDate);

        addCodeMap(map);
    }
}
