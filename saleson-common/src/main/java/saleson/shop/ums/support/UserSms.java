package saleson.shop.ums.support;

import com.onlinepowers.framework.security.userdetails.User;
import saleson.common.enumeration.UmsType;
import saleson.model.Ums;
import saleson.common.notification.domain.UmsTemplate;

import java.util.HashMap;

public class UserSms extends UmsTemplate {

    private User user;

    public UserSms() {
        intiCodeMapView();
    }

    public UserSms(User user, Ums ums, String phoneNumber) {
        super(ums, phoneNumber);

        if (user == null) {
            return;
        }

        this.user = user;
        intiCodeMapView();
        intiCodeMap();

        super.initialize();
    }

    private void intiCodeMapView() {

        HashMap<String, String> map = new HashMap<>();

        map.put("user_name", "이름");

        addCodeViewMap(map);
    }

    private void intiCodeMap() {

        HashMap<String, String> map = new HashMap<>();

        map.put("name", user.getUserName());

        addCodeMap(map);

    }

}
