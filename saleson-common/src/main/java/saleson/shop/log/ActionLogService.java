package saleson.shop.log;

import javax.servlet.http.HttpServletRequest;

public interface ActionLogService {

    void insertManagerActionLog(HttpServletRequest request);
}
