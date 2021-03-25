package saleson.shop.google.analytics;

import saleson.model.google.analytics.ConfigGoogleAnalytics;
import saleson.shop.google.analytics.domain.CommonTrackingScript;
import saleson.shop.google.analytics.domain.measuring.Product;
import saleson.shop.order.domain.Buy;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.refund.domain.OrderRefund;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface GoogleAnalyticsService {

    /**
     *  GoogleAnalytics 저장
     * @param configGoogleAnalytics
     */
    void saveConfig(ConfigGoogleAnalytics configGoogleAnalytics);

    /**
     * 적용중인 GoogleAnalytics 정보 조회
     * @return
     */
    ConfigGoogleAnalytics getConfig();

    /**
     * GoogleAnalytics 범용 스크립트
     * @return
     */
    CommonTrackingScript getCommonTrackingScript();

    /**
     * GoogleAnalytics Purchase Event
     * @param cid
     * @param order
     * @param page
     * @param request
     */
    void purchase(HttpServletRequest request, String cid, Order order, String page);

    /**
     * GoogleAnalytics Add to Cart Event
     * @param request
     * @param cid
     * @param products
     */
    void addToCart(HttpServletRequest request, String cid, List<Product> products);

    /**
     * GoogleAnalytics Remove From Cart Event
     * @param request
     * @param cid
     * @param products
     */
    void removeFromCart(HttpServletRequest request, String cid, List<Product> products);

    /**
     * GoogleAnalytics Item Detail Event
     * @param request
     * @param cid
     * @param products
     */
    void detail(HttpServletRequest request, String cid, List<Product> products);

    /**
     * GoogleAnalytics Checkout Event
     * @param request
     * @param cid
     * @param buy
     * @param page
     */
    void checkout(HttpServletRequest request, String cid, Buy buy, String page);

    void allRefund(HttpServletRequest request, String orderCode, int orderSequence);

    void partRefund(HttpServletRequest request, Order order, OrderRefund orderRefund);

    void adminRefund(HttpServletRequest request, OrderRefund orderRefund, List<OrderItem> orderItems);

    /**
     * GoogleAnalytics 통계용 인증토큰
     * @return
     */
    String getAccessToken(HttpSession session);

    String getSessionName();
}
