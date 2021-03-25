package saleson.shop.eventview;

import com.onlinepowers.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.enumeration.eventcode.EventCodeLogType;
import saleson.common.enumeration.eventcode.EventCodeType;
import saleson.common.utils.EventViewUtils;
import saleson.model.eventcode.EventCodeLog;
import saleson.shop.eventcode.EventCodeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping({"/ev","/m/ev"})
public class EventViewController {

    private static final Logger log = LoggerFactory.getLogger(EventViewController.class);

    @Autowired
    EventCodeService eventCodeService;

    @Autowired
    Environment environment;

    @GetMapping("/{code}/{id}")
    public String redirect(HttpServletRequest request,
                           HttpServletResponse response,
                           @PathVariable("code") String code,
                           @PathVariable("id") long userId) {
        
        String redirect = eventCodeService.getRedirect(code, userId);
        insertLog(redirect, EventCodeType.NONE, request, response);

        return "redirect:" + redirect;
    }

    @GetMapping("/{code}")
    public String redirect(HttpServletRequest request,
                           HttpServletResponse response,
                           @PathVariable("code") String code) {

        String redirect = eventCodeService.getRedirect(code, 0);
        insertLog(redirect, EventCodeType.NONE, request, response);

        return "redirect:" + redirect;
    }

    @GetMapping("/c/{code}/{id}")
    public String campaign(HttpServletRequest request,
                           HttpServletResponse response,
                           @PathVariable("code") String code,
                           @PathVariable("id") long userId) {

        String redirect = eventCodeService.getCampaignRedirect(code, userId);
        insertLog(redirect, EventCodeType.CAMPAIGN, request, response);

        return "redirect:" + redirect;
    }

    @GetMapping("/c/{code}")
    public String campaign(HttpServletRequest request,
                           HttpServletResponse response,
                           @PathVariable("code") String code) {

        String redirect = eventCodeService.getCampaignRedirect(code, 0);
        insertLog(redirect, EventCodeType.CAMPAIGN, request, response);

        return "redirect:" + redirect;
    }

    @GetMapping("/s/{code}/{id}")
    public String share(HttpServletRequest request,
                        HttpServletResponse response,
                        @PathVariable("code") String itemUserCode,
                        @PathVariable("id") long userId) {

        String redirect = eventCodeService.getItemRedirect("share", itemUserCode, userId);
        insertLog(redirect, EventCodeType.SHARE, request, response);

        return "redirect:" + redirect;
    }

    @GetMapping("/s/{code}")
    public String share(HttpServletRequest request,
                        HttpServletResponse response,
                        @PathVariable("code") String itemUserCode) {

        String redirect = eventCodeService.getItemRedirect("share", itemUserCode, 0);
        insertLog(redirect, EventCodeType.SHARE, request, response);

        return "redirect:" + redirect;
    }

    @GetMapping("/ep/{channel}/{code}")
    public String epItem(HttpServletRequest request,
                         HttpServletResponse response,
                         @PathVariable("code") String itemUserCode,
                         @PathVariable("channel") String channel) {

        String redirect = eventCodeService.getItemRedirect(channel, itemUserCode, 0);
        insertLog(redirect, EventCodeType.EP_ITEM, request, response);

        return "redirect:" + redirect;
    }

    @GetMapping("/e/{code}")
    public String event(HttpServletRequest request,
                        HttpServletResponse response,
                        @PathVariable("code") String code) {

        String redirect = eventCodeService.getRedirect(code, 0);
        insertLog(redirect, EventCodeType.FEATURED, request, response);

        return "redirect:" + redirect;
    }

    @GetMapping("/e/{code}/{id}")
    public String event(HttpServletRequest request,
                        HttpServletResponse response,
                        @PathVariable("code") String code,
                        @PathVariable("id") long userId) {

        String redirect = eventCodeService.getRedirect(code, userId);
        insertLog(redirect, EventCodeType.FEATURED, request, response);

        return "redirect:" + redirect;
    }

    private void insertLog(String url,
                           EventCodeType codeType,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        EventCodeLog eventCodeLog = EventViewUtils.getEventCodeLog(url, codeType, EventCodeLogType.VIEW);

        String uid = EventViewUtils.getUidCookieValue(request);

        if (!StringUtils.isEmpty(uid)) {
            eventCodeLog.setUid(uid);
        }

        eventCodeService.insertLog(eventCodeLog);

        if (isApiView()) {
            url += url.contains("?") ? "&uid=" + uid : "";
        } else {
            EventViewUtils.setUidCookie(response, eventCodeLog.getUid());
            EventViewUtils.setQueryStringCookie(response, eventCodeLog.getQueryString());
        }
    }

    private boolean isApiView() {
        return "api".equals(environment.getProperty("saleson.view.type"));
    }
}
