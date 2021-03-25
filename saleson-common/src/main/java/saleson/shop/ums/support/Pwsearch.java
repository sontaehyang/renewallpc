package saleson.shop.ums.support;

import com.onlinepowers.framework.security.userdetails.User;
import saleson.common.config.SalesonProperty;
import saleson.common.enumeration.UmsType;
import saleson.common.utils.ShopUtils;
import saleson.model.Ums;
import saleson.shop.config.domain.Config;
import saleson.common.notification.domain.UmsTemplate;
import saleson.shop.user.domain.UserDetail;

import java.util.HashMap;

public class Pwsearch extends UmsTemplate {

    private User user;

    public Pwsearch() {
        intiCodeMapView();
    }

    public Pwsearch(Ums ums, String phoneNumber, User user) {
        super(ums, phoneNumber);
        this.user = user;

        intiCodeMapView();
        intiCodeMap();

        super.initialize();
    }

    private void intiCodeMapView() {

        HashMap<String, String> map = new HashMap<>();

        map.put("user_name", "이름");
        map.put("find_id", "아이디");
        map.put("find_pw", "비밀번호");

        map.put("site_name", "사이트명");
        map.put("site_url", "사이트URL");

        map.put("counsel_tel_number", "사이트 전화번호");

        addCodeViewMap(map);
    }

    private void intiCodeMap() {

        HashMap<String, String> map = new HashMap<>();
        UserDetail userDetail = (UserDetail) user.getUserDetail();

        map.put("user_name", user.getUserName());
        map.put("findId", user.getLoginId());
        map.put("findPw", user.getPassword());

        Config config = ShopUtils.getConfig();

        map.put("siteName", config.getShopName());
        map.put("siteUrl", SalesonProperty.getSalesonUrlShoppingmall());

        map.put("counsel_tel_number", config.getCounselTelNumber());

        addCodeMap(map);

    }
}
