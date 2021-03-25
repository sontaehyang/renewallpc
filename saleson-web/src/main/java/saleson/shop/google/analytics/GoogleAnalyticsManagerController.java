package saleson.shop.google.analytics;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FlashMapUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.Const;
import saleson.model.google.analytics.ConfigGoogleAnalytics;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
@RequestMapping("/opmanager/google-analytics")
@RequestProperty(template = "opmanager", layout = "default")
public class GoogleAnalyticsManagerController {

    @Autowired
    private GoogleAnalyticsService googleAnalyticsService;

    private String[] PAGE_TYPES = {"user","session","traffic","page-view","promotion"};

    @GetMapping("/{page}")
    public String index(@PathVariable("page") String page,
                        @RequestParam(name="reload", defaultValue = "") String reload,
                        HttpSession session, Model model) {

        if (!Arrays.stream(PAGE_TYPES).anyMatch(page::equals)) {
            FlashMapUtils.setMessage("해당 페이지가 없습니다.");

            return "redirect:/opmanager";
        }

        if ("Y".equals(reload)) {
            session.removeAttribute(googleAnalyticsService.getSessionName());
        }

        ConfigGoogleAnalytics config = googleAnalyticsService.getConfig();
        String token = googleAnalyticsService.getAccessToken(session);

        boolean validFlag = config != null
                && config.isStatisticsFlag()
                && !StringUtils.isEmpty(config.getProfile())
                && !StringUtils.isEmpty(config.getAuthFile())
                && !StringUtils.isEmpty(token);

        if (!validFlag) {

            if (config == null) {
                config = new ConfigGoogleAnalytics();
            }

            model.addAttribute("config", config);
            return "view:/google-analytics/config";
        } else {

            String endDate = DateUtils.getToday(Const.DATE_FORMAT);

            model.addAttribute("endDate", endDate);
            model.addAttribute("beginDate", DateUtils.addDay(endDate, -7));
            model.addAttribute("accessToken", token);
            model.addAttribute("config", config);
            return "view:/google-analytics/"+page;
        }
    }

    @PostMapping("/{page}")
    public String configAction(@PathVariable("page") String page,
                               ConfigGoogleAnalytics config) {

        String redirect = "/opmanager/google-analytics/"+page;

        if (!Arrays.stream(PAGE_TYPES).anyMatch(page::equals)) {
            FlashMapUtils.setMessage("해당 페이지가 없습니다.");
            return "redirect:"+redirect;
        }

        ConfigGoogleAnalytics storeConfig = googleAnalyticsService.getConfig();

        if (storeConfig == null) {
            storeConfig = new ConfigGoogleAnalytics();
        }

        storeConfig.setStatisticsFlag(true);
        storeConfig.setAuthJsonFile(config.getAuthJsonFile());
        storeConfig.setProfile(config.getProfile());

        googleAnalyticsService.saveConfig(storeConfig);

        return "redirect:"+redirect;
    }
}
