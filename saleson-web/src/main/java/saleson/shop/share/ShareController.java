package saleson.shop.share;

import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saleson.shop.share.domain.ShareParam;
import saleson.common.opengraph.OpenGraph;
import saleson.shop.item.ItemService;

@RestController
@RequestMapping("/share")
public class ShareController {

    private Logger log = LoggerFactory.getLogger(ShareController.class);

    @Autowired
    ItemService itemService;

    @GetMapping(value = "", produces="text/html; charset=UTF-8")
    public String share(ShareParam param) {

        OpenGraph openGraph = new OpenGraph();

        openGraph.setTitle(param.getT());
        openGraph.setUrl(param.getU());
        openGraph.setImage(param.getI());
        openGraph.setDescription(param.getD());

        return makeHtml(openGraph);
    }

    private String makeHtml(OpenGraph openGraph) {
        StringBuffer sb = new StringBuffer();

        try {

            sb.append("<html lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n");
            sb.append("<head>\n");

            sb.append("<meta charset=\"utf-8\">\n");

            if (!StringUtils.isEmpty(openGraph.getType())) {
                sb.append(makeMetaTag("og:type", openGraph.getType()));
            }

            if (!StringUtils.isEmpty(openGraph.getUrl())) {
                sb.append(makeMetaTag("og:url", openGraph.getUrl()));
            }

            if (!StringUtils.isEmpty(openGraph.getTitle())) {
                sb.append(makeMetaTag("og:title", openGraph.getTitle()));
            }

            if (!StringUtils.isEmpty(openGraph.getImage())) {
                sb.append(makeMetaTag("og:image", openGraph.getImage()));
            }

            if (!StringUtils.isEmpty(openGraph.getDescription())) {
                sb.append(makeMetaTag("og:description", openGraph.getDescription()));
            }

            sb.append("<script type=\"text/javascript\">");
            sb.append("location.replace('"+openGraph.getUrl()+"')");
            sb.append("</script>");

            sb.append("</head>\n");
            sb.append("</html>\n");

        } catch (Exception e) {
            log.error("make Html tag Error [{}]", JsonViewUtils.objectToJson(openGraph), e);
        }
        return sb.toString();
    }

    private String makeMetaTag(String name, String content) {

        return "<meta name=\""+name+"\" content=\""+content+"\"/>\n";
    }

}
