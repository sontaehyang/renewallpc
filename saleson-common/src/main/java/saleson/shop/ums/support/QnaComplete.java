package saleson.shop.ums.support;

import saleson.common.config.SalesonProperty;
import saleson.common.utils.ShopUtils;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.shop.config.domain.Config;
import saleson.shop.qna.domain.Qna;
import saleson.common.notification.domain.UmsTemplate;

import java.util.HashMap;

public class QnaComplete extends UmsTemplate {

    private Qna qna;

    private String siteUrl;

    public QnaComplete() {
        intiCodeMapView();
    }

    public QnaComplete(Ums ums, String phoneNumber, Qna qna, ApplicationInfo applicationInfo) {
        super(ums, phoneNumber);
        this.qna = qna;

        this.siteUrl = SalesonProperty.getSalesonUrlShoppingmall();

        intiCodeMapView();
        intiCodeMap();

        super.initialize(qna.getUserId(), applicationInfo);
    }

    private void intiCodeMapView() {

        HashMap<String, String> map = new HashMap<>();

        map.put("site_url", "상점URL");
        map.put("site_name", "상점명");

        map.put("user_name", "이름");
        map.put("email", "이메일");
        map.put("order_code", "주문번호");
        map.put("item_name", "상품명");
        map.put("qna_group", "QNA-GROUP");
        map.put("qna_type", "QNA-TYPE");
        map.put("question", "문의내용");
        map.put("counsel_tel_number", "고객센터");

        addCodeViewMap(map);
    }

    private void intiCodeMap() {

        HashMap<String, String> map = new HashMap<>();

        Config config = ShopUtils.getConfig();

        map.put("site_url", this.siteUrl);
        map.put("site_name", config.getShopName());

        map.put("user_name", qna.getUserName());
        map.put("email", qna.getEmail());
        map.put("order_code", qna.getOrderCode());
        map.put("item_name", qna.getItemName());
        map.put("qna_group", qna.getQnaGroup());
        map.put("qna_type", qna.getQnaType());
        map.put("question", qna.getQuestion());
        map.put("counsel_tel_number", config.getCounselTelNumber());

        addCodeMap(map);

    }
}
