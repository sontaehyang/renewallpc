package saleson.shop.ums.support;

import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.DateUtils;
import saleson.common.enumeration.UmsType;
import saleson.model.Ums;
import saleson.shop.config.domain.Config;
import saleson.common.notification.domain.UmsTemplate;
import saleson.shop.user.domain.UserDetail;

import java.util.HashMap;

public class MemberJoin extends UmsTemplate {

    private User user;
    private Config config;

    public MemberJoin() {
        intiCodeMapView();
    }


    public MemberJoin(Ums ums, User user, Config config, String phoneNumber) {
        super(ums, phoneNumber);
        this.user = user;
        this.config = config;

        intiCodeMapView();
        intiCodeMap();

        super.initialize();
    }

    private void intiCodeMapView() {

        HashMap<String, String> map = new HashMap<>();

        map.put("zipcode", "우편번호");
        map.put("address", "주소");
        map.put("addr_detail", "상세주소");
        map.put("email", "이메일");
        map.put("id", "아이디");
        map.put("user_name", "이름");
        map.put("phone", "전화번호");
        map.put("mobile", "핸드폰번호");
        map.put("reg_date", "등록일자");
        map.put("site_name", "상점명");
        map.put("counsel_tel_number", "고객센터");

        addCodeViewMap(map);
    }

    private void intiCodeMap() {

        UserDetail userDetail = (UserDetail) user.getUserDetail();

        HashMap<String, String> map = new HashMap<>();

        map.put("zipcode", userDetail.getPost());
        map.put("address", userDetail.getAddress());
        map.put("addr_detail", userDetail.getAddressDetail());

        map.put("email", user.getEmail());
        map.put("id", user.getLoginId());
        map.put("user_name", user.getUserName());
        map.put("phone", userDetail.getTelNumber());
        map.put("mobile", userDetail.getPhoneNumber());
        map.put("reg_date", DateUtils.date(user.getCreatedDate()));

        map.put("site_name", config.getShopName());

        map.put("counsel_tel_number", config.getCounselTelNumber());

        addCodeMap(map);

    }


}
