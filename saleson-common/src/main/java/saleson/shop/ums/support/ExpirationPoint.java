package saleson.shop.ums.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import saleson.common.notification.domain.UmsTemplate;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.shop.config.domain.Config;
import saleson.shop.point.domain.Point;

import java.util.HashMap;

public class ExpirationPoint extends UmsTemplate {

    private String siteName;

    private String userName;

    private int totalPoint;

    private int expirationPoint;

    private String expirationDate;

    public ExpirationPoint() { intiCodeMapView(); }

    public ExpirationPoint(Ums ums, Point point, long userId, String phoneNumber, Config config, ApplicationInfo applicationInfo) {
        super(ums, phoneNumber);

        this.siteName = config.getShopName();
        this.userName = point.getUserName();
        this.totalPoint = point.getTotalPoint();
        this.expirationPoint = point.getExpirationPoint();
        this.expirationDate = DateUtils.formatDate(point.getExpirationDate(), "-");

        intiCodeMapView();
        intiCodeMap();

        super.initialize(userId, applicationInfo);
    }

    private void intiCodeMapView() {

        HashMap<String, String> map = new HashMap<>();

        map.put("site_name", "상점명");
        map.put("user_name", "이름");
        map.put("total_point", "전체포인트");
        map.put("expiration_point", "소멸예정 포인트");
        map.put("expiration_date", "소멸예정일");

        addCodeViewMap(map);
    }

    private void intiCodeMap() {

        HashMap<String, String> map = new HashMap<>();

        map.put("site_name", this.siteName);
        map.put("user_name", this.userName);
        map.put("totalPoint", StringUtils.numberFormat(Integer.toString(this.totalPoint)));
        map.put("expirationPoint", StringUtils.numberFormat(Integer.toString(this.expirationPoint)));
        map.put("expirationDate", this.expirationDate);

        addCodeMap(map);
    }
}
