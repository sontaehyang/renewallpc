package saleson.shop.eventview;

import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.Const;
import saleson.common.utils.CommonUtils;
import saleson.common.utils.LocalDateUtils;
import saleson.common.utils.ShopUtils;
import saleson.shop.eventcode.EventCodeService;
import saleson.shop.eventcode.domain.EventItemStatistics;
import saleson.shop.eventcode.domain.EventStatistics;
import saleson.shop.eventcode.support.EventStatisticsParam;
import saleson.shop.item.support.ItemParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/opmanager/evnet-statistics")
@RequestProperty(template = "opmanager", layout="default")
public class EventStatisticsManagerController {

    @Autowired
    private EventCodeService eventCodeService;

    @GetMapping("/list")
    public String getEventStatisticsList(Model model, EventStatisticsParam param, Pageable pageable) {

        String today = LocalDateUtils.localDateTimeToString(LocalDateTime.now(), Const.DATE_FORMAT);

        if (StringUtils.isEmpty(param.getBeginDate()) || StringUtils.isEmpty(param.getEndDate())) {
            param.setBeginDate(today);
            param.setEndDate(today);
        }

        Page<Map<String, Object>> pageContent = eventCodeService.getEventStatisticsList(param, pageable);
        List<EventStatistics> list = getEventStatisticsList(pageContent);

        model.addAttribute("param", param);
        model.addAttribute("list", list);
        model.addAttribute("totalStatistics", eventCodeService.getTotalEventStatistics(param));
        model.addAttribute("pageContent", pageContent);

        return "view";

    }

    @GetMapping("/item-list")
    public String getEventItemStatisticsList(Model model, EventStatisticsParam param, Pageable pageable) {

        String today = LocalDateUtils.localDateTimeToString(LocalDateTime.now(), Const.DATE_FORMAT);

        if (StringUtils.isEmpty(param.getBeginDate()) || StringUtils.isEmpty(param.getEndDate())) {
            param.setBeginDate(today);
            param.setEndDate(today);
        }

        Page<Map<String, Object>> pageContent = eventCodeService.getEventItemStatisticsList(param, pageable);

        List<EventItemStatistics> list = new ArrayList<>();

        if (pageContent != null) {
            List<Map<String, Object>> content = pageContent.getContent();

            if (content != null && !content.isEmpty()) {

                content.forEach(c-> {
                    EventItemStatistics s = new EventItemStatistics(c);
                    list.add(s);
                });

            }
        }

        model.addAttribute("param", param);
        model.addAttribute("list", list);
        model.addAttribute("totalStatistics", eventCodeService.getTotalEventItemStatistics(param));
        model.addAttribute("pageContent", pageContent);

        return "view";

    }

    private List<EventStatistics> getEventStatisticsList(Page<Map<String, Object>> pageContent) {

        List<EventStatistics> list  = new ArrayList<>();
        List<String> eventCodes = new ArrayList<>();

        if (pageContent != null) {
            List<Map<String, Object>> content = pageContent.getContent();

            if (content != null && !content.isEmpty()) {

                content.forEach(c-> {
                    EventStatistics s = new EventStatistics(c);
                    list.add(s);
                    if (!StringUtils.isEmpty(s.getEventCode())) {
                        eventCodes.add(s.getEventCode());
                    }
                });

            }
        }

        List<Map<String, Object>> contents = eventCodeService.getEventCodeLogContents(eventCodes);

        if (contents != null && !contents.isEmpty()) {

            for (EventStatistics statistics : list) {

                if (StringUtils.isEmpty(statistics.getEventCode())) {
                    continue;
                }

                for (Map<String, Object> map : contents) {
                    String eventCode = CommonUtils.dataNvl(map.get("EVENT_CODE"));
                    String content = CommonUtils.dataNvl(map.get("CONTENTS"));

                    if (eventCode.equals(statistics.getEventCode())) {
                        statistics.setContents(ShopUtils.unescapeHtml(content));
                        break;
                    }
                }
            }
        }

        return list;
    }
}
