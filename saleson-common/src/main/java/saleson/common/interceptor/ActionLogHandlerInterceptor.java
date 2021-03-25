package saleson.common.interceptor;

import com.onlinepowers.framework.util.ValidationUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import saleson.common.utils.SellerUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.seller.main.domain.Seller;
import saleson.shop.log.ActionLogService;

public class ActionLogHandlerInterceptor extends HandlerInterceptorAdapter {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private String MANAGER_URI = "/opmanager/";
    private String SELLER_URI = "/seller/";

    @Autowired
    private ActionLogService actionLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestUri = request.getRequestURI();

        boolean isManager = UserUtils.isManagerLogin();

        Seller seller = SellerUtils.getSeller();

        if ((isManager && isManagePage(requestUri)) || (ValidationUtils.isNotNull(seller) && ShopUtils.isSellerPage())) {

            actionLogService.insertManagerActionLog(request);

        }

        return true;
    }

    /**
     * OP_MANAGER_ACTION_LOG 에 로그를 남겨야하는 페이지인가?
     */
    private boolean isManagePage(String requestUri) {
        String[] uriPatterns = new String[]{
                MANAGER_URI, SELLER_URI
        };

        for (String uri : uriPatterns) {

            if (requestUri.indexOf(uri + "/login") > -1 || requestUri.indexOf(uri + "/accessdenied") > -1) {
                return false;
            }

            if (requestUri.startsWith(uri)) {
                return true;
            }

        }

        return false;
    }
}
