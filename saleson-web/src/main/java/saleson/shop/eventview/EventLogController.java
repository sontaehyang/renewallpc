package saleson.shop.eventview;

import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.enumeration.eventcode.EventCodeLogType;
import saleson.common.enumeration.eventcode.EventCodeType;
import saleson.common.utils.UserUtils;
import saleson.shop.eventcode.EventCodeService;
import saleson.shop.eventcode.support.EventLogParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping({"/event-log","/m/event-log"})
public class EventLogController {

    private static final Logger log = LoggerFactory.getLogger(EventLogController.class);

    @Autowired
    private EventCodeService eventCodeService;

    @PostMapping("/item")
    public JsonView item(HttpServletRequest request, @RequestBody EventLogParam param) {

        try {
            eventCodeService.insertLog(request, UserUtils.getUserId(), param.getId());
        } catch (Exception ignore) {
            log.error("event item view log error [{}] - {}", param.getId(), ignore.getMessage(),ignore);
        }

        return JsonViewUtils.success();
    }

    @PostMapping("/join-user")
    public JsonView joinUser(HttpServletRequest request, @RequestBody EventLogParam param) {

        try {

            long userId = Long.parseLong(param.getId());

            eventCodeService.insertLog(request, userId, EventCodeType.NONE, EventCodeLogType.USER);
        } catch (Exception ignore) {
            log.error("event join user log error [{}] - {}", param.getId(), ignore.getMessage(),ignore);
        }

        return JsonViewUtils.success();
    }

    @PostMapping("/featured")
    public JsonView featured(HttpServletRequest request, @RequestBody EventLogParam param) {

        try {
            eventCodeService.insertLog(request, EventCodeLogType.FEATURED, UserUtils.getUserId(), param.getItems());
        } catch (Exception ignore) {
            log.error("event featured log error [{}] - {}", param.getId(), ignore.getMessage(),ignore);
        }

        return JsonViewUtils.success();
    }

    @PostMapping("/order")
    public JsonView order(HttpServletRequest request, @RequestBody EventLogParam param) {

        try {
            eventCodeService.insertLog(request, UserUtils.getUserId(), param.getId(), param.getItems());
        } catch (Exception ignore) {
            log.error("event order error [{}] - {}", param.getId(), ignore.getMessage(),ignore);
        }

        return JsonViewUtils.success();
    }
}
