package saleson.shop.google.analytics;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.onlinepowers.framework.util.CipherUtils;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.model.google.analytics.ConfigGoogleAnalytics;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.Categories;
import saleson.shop.config.ConfigService;
import saleson.shop.google.analytics.domain.CommonTrackingScript;
import saleson.shop.google.analytics.domain.GtagBuilder;
import saleson.shop.google.analytics.domain.measuring.*;
import saleson.shop.order.OrderService;
import saleson.shop.order.claimapply.domain.OrderReturnApply;
import saleson.shop.order.domain.*;
import saleson.shop.order.refund.domain.OrderRefund;
import saleson.shop.order.refund.domain.OrderRefundDetail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;

@Service("GoogleAnalyticsService")
public class GoogleAnalyticsServiceImpl implements GoogleAnalyticsService {

    private static final Logger log = LoggerFactory.getLogger(GoogleAnalyticsService.class);

    @Autowired
    private ConfigGoogleAnalyticsRepository configGoogleAnalyticsRepository;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    RestTemplate customRestTemplate;

    @Autowired
    ConfigService configService;

    @Autowired
    OrderService orderService;

    private void sendGoogleAnalytics(HttpServletRequest request, MultiValueMap<String, String> map) {


        try {

            String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
            String url = "https://www.google-analytics.com/collect";

            URI uri = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("www.google-analytics.com")
                    .path("/collect")
                    .build()
                    .encode()
                    .toUri();

            map.add("z", DateUtils.getToday(Const.DATETIME_FORMAT));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add(HttpHeaders.USER_AGENT, ShopUtils.unescapeHtml(userAgent));
            RequestEntity requestEntity = new RequestEntity(map, headers, HttpMethod.POST, uri);
            ResponseEntity<String> entity = customRestTemplate.exchange(requestEntity, String.class);

            if (HttpStatus.OK != entity.getStatusCode()) {
                log.error("sendGoogleAnalytics : \n {}", JsonViewUtils.objectToJson(entity));
            }

        } catch (Exception e) {
            log.error("sendGoogleAnalytics Error {}", e.getMessage(), e);
        }
    }



    @Override
    public void saveConfig(ConfigGoogleAnalytics configGoogleAnalytics) {

        ConfigGoogleAnalytics storeConfigGoogleAnalytics = getConfig();

        if (storeConfigGoogleAnalytics == null) {
            storeConfigGoogleAnalytics = new ConfigGoogleAnalytics();
        }

        storeConfigGoogleAnalytics.setUpdateData(configGoogleAnalytics);

        try {
            String authFileString = getAuthFileString(configGoogleAnalytics.getAuthJsonFile());

            if (!StringUtils.isEmpty(authFileString)) {
                storeConfigGoogleAnalytics.setAuthFile(CipherUtils.encrypt(authFileString));
            }
        } catch (Exception ignore) {
            log.error("setAuthFile Error [{}]", ignore.getMessage(), ignore);
        }

        configGoogleAnalyticsRepository.save(storeConfigGoogleAnalytics);
    }

    private String getAuthFileString(MultipartFile authFile) {
        StringBuffer sb = new StringBuffer();

        if (authFile != null) {

            try {

                InputStreamReader reader = new InputStreamReader(authFile.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                reader.close();
            } catch (Exception e) {
                log.error("getAuthFileString error [{}]", e.getMessage(), e);
            }
        }

        return sb.toString();
    }

    @Override
    public ConfigGoogleAnalytics getConfig() {

        List<ConfigGoogleAnalytics> list = configGoogleAnalyticsRepository.findAll();

        if (list != null && !list.isEmpty()) {
           return list.get(0);
        }

        return null;
    }

    @Override
    public CommonTrackingScript getCommonTrackingScript() {

        StringBuffer sb = new StringBuffer();

        if(UserUtils.isUserLogin()) {

            Map<String, Object> map = new LinkedHashMap<>();
            map.put("user_id", UserUtils.getLoginId());

            sb.append(GtagBuilder.build().set(map).toString());
        }

        return getCommonScript(getConfig(), sb.toString());
    }

    private CommonTrackingScript getCommonScript(ConfigGoogleAnalytics configGoogleAnalytics, String gtag) {

        if (configGoogleAnalytics != null) {

            String measurementId = configGoogleAnalytics.getMeasurementId();
            if (configGoogleAnalytics.isCommonTrackingFlag() && !StringUtils.isEmpty(measurementId)) {

                String src = "https://www.googletagmanager.com/gtag/js?id=\""+measurementId+"\"";

                StringBuffer sb = new StringBuffer();

                sb.append("window.dataLayer = window.dataLayer || [];");
                sb.append("function gtag(){dataLayer.push(arguments);}");
                sb.append(" gtag('js', new Date());");
                sb.append(" gtag('config', '"+measurementId+"');");

                if(!StringUtils.isEmpty(gtag)) {
                    sb.append(gtag);
                }

                return new CommonTrackingScript(src, sb.toString());
            }
        }

        return null;
    }

    @Override
    public void purchase(HttpServletRequest request, String cid, Order order, String page) {

        ConfigGoogleAnalytics config = getConfig();

        if (validEcommerce(config) && !StringUtils.isEmpty(cid) && order != null) {

            Purchase purchase = new Purchase(config, cid, order.getOrderCode(), order.getOrderSequence(), page);

            int totalOrderAmount = order.getTotalOrderAmount();
            int tax = (int)(totalOrderAmount / 1.1 * 0.1);

            purchase.setTr(String.valueOf(totalOrderAmount));
            purchase.setTt(String.valueOf(tax));
            purchase.setTs(String.valueOf(order.getTotalShippingAmount()));

            List<Product> products = new ArrayList<>();

            for(OrderShippingInfo info : order.getOrderShippingInfos()) {
                for(OrderItem orderItem : info.getOrderItems()) {
                    Categories categories = categoriesService.getCategoriesById(orderItem.getCategoryId());
                    products.add(new Product(orderItem, categories.getCategoryName()));
                }
            }

            purchase.setProducts(products);

            sendGoogleAnalytics(request, purchase.map());
        }
    }

    @Override
    public void addToCart(HttpServletRequest request, String cid, List<Product> products) {

        ConfigGoogleAnalytics config = getConfig();

        if (validEcommerce(config) && !StringUtils.isEmpty(cid) && products != null && !products.isEmpty()) {

            MeasuringAction action = new MeasuringAction(config, cid, "add");

            action.setEc("ecommerce");
            action.setEa("add_to_cart");
            action.setEl("장바구니 추가");
            action.setProducts(products);

            sendGoogleAnalytics(request, action.map());
        }
    }

    @Override
    public void removeFromCart(HttpServletRequest request, String cid, List<Product> products) {

        ConfigGoogleAnalytics config = getConfig();

        if (validEcommerce(config) && !StringUtils.isEmpty(cid) && products != null && !products.isEmpty()) {

            MeasuringAction action = new MeasuringAction(config, cid, "remove");

            action.setEc("ecommerce");
            action.setEa("remove_from_cart");
            action.setEl("장바구니 제거");

            action.setProducts(products);

            sendGoogleAnalytics(request, action.map());
        }

    }

    @Override
    public void detail(HttpServletRequest request, String cid, List<Product> products) {

        ConfigGoogleAnalytics config = getConfig();

        if (validEcommerce(config) && !StringUtils.isEmpty(cid) && products != null && !products.isEmpty()) {
            MeasuringAction action = new MeasuringAction(config, cid, "detail");

            action.setEc("ecommerce");
            action.setEa("detail");
            action.setEl("상품 상세");

            action.setProducts(products);

            sendGoogleAnalytics(request, action.map());
        }

    }

    @Override
    public void checkout(HttpServletRequest request, String cid, Buy buy, String page) {

        ConfigGoogleAnalytics config = getConfig();

        if (validEcommerce(config) && !StringUtils.isEmpty(cid) && buy != null) {

            Checkout checkout = new Checkout(config, cid, page, 1);
            List<Product> products = new ArrayList<>();

            List<Receiver> receivers = buy.getReceivers();

            for (Receiver receiver : receivers) {
                List<Shipping> shippings = receiver.getItemGroups();

                for (Shipping shipping : shippings) {

                    if (shipping.isSingleShipping()) {
                        products.add(new Product(shipping.getBuyItem()));
                    } else {
                        List<BuyItem> buyItems = shipping.getBuyItems();
                        for (BuyItem buyItem : buyItems) {
                            products.add(new Product(buyItem));
                        }
                    }

                }
            }

            checkout.setProducts(products);
            sendGoogleAnalytics(request, checkout.map());
        }
    }

    @Override
    public void allRefund(HttpServletRequest request, String orderCode, int orderSequence) {

        String cid = UUID.randomUUID().toString();

        ConfigGoogleAnalytics config = getConfig();

        if (validEcommerce(config) && !StringUtils.isEmpty(cid) && !StringUtils.isEmpty(orderCode)) {

            Refund refund = new Refund(config, cid, orderCode, orderSequence);

            refund.setEc("ecommerce");
            refund.setEa("refund");
            refund.setEl("상품 반품");

            sendGoogleAnalytics(request, refund.map());

        }
    }

    @Override
    public void partRefund(HttpServletRequest request, Order order, OrderRefund orderRefund) {

        String cid = UUID.randomUUID().toString();

        ConfigGoogleAnalytics config = getConfig();

        if (validEcommerce(config) && !StringUtils.isEmpty(cid) && order != null && orderRefund != null) {

            Refund refund = new Refund(config, cid, order.getOrderCode(), order.getOrderSequence());

            refund.setEc("ecommerce");
            refund.setEa("refund");
            refund.setEl("상품 반품");

            List<OrderItem> orderItems = new ArrayList<>();
            for(OrderShippingInfo info : order.getOrderShippingInfos()) {
                for(OrderItem orderItem : info.getOrderItems()) {
                    orderItems.add(orderItem);
                }
            }

            setRefundByOrderRefund(refund, orderItems, orderRefund);

            if (!refund.getProducts().isEmpty()) {
                sendGoogleAnalytics(request, refund.map());
            }
        }

    }

    @Override
    public void adminRefund(HttpServletRequest request, OrderRefund orderRefund, List<OrderItem> orderItems) {

        String cid = UUID.randomUUID().toString();

        ConfigGoogleAnalytics config = getConfig();

        if (validEcommerce(config) && !StringUtils.isEmpty(cid)
                && orderRefund != null && orderItems != null && !orderItems.isEmpty()) {

            String orderCode = orderRefund.getOrderCode();
            int orderSequence = orderRefund.getOrderSequence();

            Refund refund = new Refund(config, cid, orderCode, orderSequence);

            refund.setEc("ecommerce");
            refund.setEa("refund_admin");
            refund.setEl("상품 반품(관리자)");

            setRefundByOrderRefund(refund, orderItems, orderRefund);

            if (!refund.getProducts().isEmpty()) {
                sendGoogleAnalytics(request, refund.map());
            }
        }
    }

    private void setRefundByOrderRefund(Refund refund, List<OrderItem> orderItems, OrderRefund orderRefund) {

        List<Product> products = new ArrayList<>();
        List<OrderRefundDetail> refundDetails = orderRefund.getGroups();

        for (OrderRefundDetail refundDetail : refundDetails) {
            List<saleson.shop.order.claimapply.domain.OrderCancelApply> orderCancelApplys
                    = refundDetail.getOrderCancelApplys();

            if (orderCancelApplys != null) {
                orderCancelApplys.forEach(apply -> {

                    OrderItem oi = getOrderItemByList(orderItems, apply.getItemSequence());
                    if (oi != null) {

                        Product p = new Product();
                        p.setId(oi.getItemUserCode());
                        p.setQt(String.valueOf(apply.getClaimApplyQuantity()));
                        p.setPr(String.valueOf(oi.getSalePrice()));

                        products.add(p);
                    }
                });
            }

            List<OrderReturnApply> orderReturnApplys = refundDetail.getOrderReturnApplys();

            if (orderReturnApplys != null) {
                orderReturnApplys.forEach(apply -> {
                    OrderItem oi = getOrderItemByList(orderItems, apply.getItemSequence());
                    if (oi != null) {

                        Product p = new Product();
                        p.setId(oi.getItemUserCode());
                        p.setQt(String.valueOf(apply.getClaimApplyQuantity()));
                        p.setPr(String.valueOf(oi.getSalePrice()));

                        products.add(p);
                    }
                });
            }

        }

        refund.setProducts(products);
        refund.setTr(String.valueOf(orderRefund.getTotalReturnAmount()));
    }

    private OrderItem getOrderItemByList(List<OrderItem> orderItems, int itemSequence) {

        if (orderItems != null && !orderItems.isEmpty()) {

            for (OrderItem orderItem : orderItems) {
                if (itemSequence == orderItem.getItemSequence()) {
                    return orderItem;
                }
            }
        }

        return null;
    }

    private boolean validEcommerce(ConfigGoogleAnalytics config) {
        if (config == null) {
            return false;
        }

        String measurementId = config.getMeasurementId();
        if (StringUtils.isEmpty(measurementId) || !config.isEcommerceTrackingFlag()) {
            return false;
        }

        return true;
    }

    @Override
    public String getAccessToken(HttpSession session) {

        String sessionName = getSessionName();
        ConfigGoogleAnalytics config = getConfig();

        if (config != null && config.isStatisticsFlag() && !StringUtils.isEmpty(config.getAuthFile())) {

            try {

                AccessToken sessionAccessToken = (AccessToken)session.getAttribute(sessionName);
                Date now = new Date();
                if (sessionAccessToken != null && now.compareTo(sessionAccessToken.getExpirationTime()) == -1) {
                    return sessionAccessToken.getTokenValue();
                }

                String jsonString = CipherUtils.decrypt(config.getAuthFile());

                GoogleCredentials credentials = ServiceAccountCredentials.fromStream(new ByteArrayInputStream(jsonString.getBytes()))
                        .createScoped(Collections.singleton(
                                "https://www.googleapis.com/auth/analytics.readonly"
                        ));

                credentials.refreshIfExpired();

                AccessToken token = credentials.getAccessToken();

                session.setAttribute(sessionName, token);

                return token.getTokenValue();
            } catch (Exception ignore) {
                log.error("getAuthToken error [{}]", ignore.getMessage(), ignore);
            }

        }

        return "";
    }

    @Override
    public String getSessionName() {
        return "SESSION_GOOGLE_ANALYTICS_ACCESS_TOKEN";
    }
}
