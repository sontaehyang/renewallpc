package saleson.shop.seo;

import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import saleson.common.config.SalesonProperty;
import saleson.common.opengraph.OpenGraph;
import saleson.common.utils.OpenGraphUtils;
import saleson.common.utils.ShopUtils;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.Categories;
import saleson.shop.config.domain.Config;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.seo.domain.Seo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/seo")
public class SeoController {


    private Logger log = LoggerFactory.getLogger(SeoController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private SeoService seoService;

    @Autowired
    Environment environment;

    @GetMapping(value = "/item/{itemUserCode}", produces="text/html; charset=UTF-8")
    @ResponseBody
    public String item(HttpServletRequest request,
                       @PathVariable("itemUserCode") String itemUserCode) {

        Seo seo = null;
        OpenGraph openGraph = new OpenGraph();

        try {

            Item item = itemService.getItemByItemUserCode(itemUserCode);

            if (item != null) {

                seo = item.getSeo();
                openGraph = new OpenGraph(item, request);

                if (isApiView()) {
                    String link = SalesonProperty.getSalesonUrlFrontend()
                            + "/items/details.html?code="
                            + item.getItemUserCode();

                    openGraph.setLink(link);
                }
            }

        } catch (Exception e) {
            log.error("seo item [{}] [{}]", JsonViewUtils.objectToJson(openGraph), JsonViewUtils.objectToJson(seo), e);
        }
        return makeHtml(seo, openGraph, isBot(request));
    }

    @GetMapping(value = "/categories/{categoryCode}", produces="text/html; charset=UTF-8")
    @ResponseBody
    public String categories(HttpServletRequest request,
                             @PathVariable("categoryCode") String categoryCode) {

        Seo seo = null;
        OpenGraph openGraph = new OpenGraph();

        try {

            Categories category = categoriesService.getCategoryByCategoryUrl(categoryCode);

            if (category != null) {


                seo = category.getCategoriesSeo();
                openGraph = new OpenGraph(category, request);

                if (isApiView()) {

                    String link = SalesonProperty.getSalesonUrlFrontend()
                            + "/category/?code="
                            + category.getCategoryUrl();

                    openGraph.setLink(link);
                }
            }

        } catch (Exception e) {
            log.error("seo categories [{}] [{}]", JsonViewUtils.objectToJson(openGraph), JsonViewUtils.objectToJson(seo), e);
        }
        return makeHtml(seo, openGraph, isBot(request));
    }

    private Seo getShopConfigSeo() {
        Config config = ShopUtils.getConfig();

        Seo seo = new Seo();

        seo.setTitle(config.getSeoTitle());
        seo.setKeywords(config.getSeoKeywords());
        seo.setDescription(config.getSeoDescription());
        seo.setHeaderContents1(config.getSeoHeaderContents1());
        seo.setThemawordTitle(config.getSeoThemawordTitle());
        seo.setThemawordDescription(config.getSeoThemawordDescription());

        return seo;
    }

    private String makeHtml(Seo seo, OpenGraph openGraph, boolean isBot) {

        StringBuffer sb = new StringBuffer();

        try {

            if (seo == null || seo.isSeoNull()) {
                seo = getShopConfigSeo();
            }

            sb.append("<!doctype html>");
            sb.append("<html lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n");
            sb.append("<head>\n");
            sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\">\n");
            sb.append("<meta charset=\"utf-8\">\n");
            sb.append("<link rel=\"icon\" href=\"/content/images/common/favicon.ico\" />\n");

            //seo
            if (!StringUtils.isEmpty(seo.getTitle())) {
                sb.append("<title>"+seo.getTitle()+"</title>\n");
            }

            if (!"Y".equals(seo.getIndexFlag())) {
                sb.append(makeMetaTag("robots", "noindex,noarchive"));
            }

            if (!StringUtils.isEmpty(seo.getKeywords())) {
                sb.append(makeMetaTag("keywords", seo.getKeywords()));
            }

            if (!StringUtils.isEmpty(seo.getDescription())) {
                sb.append(makeMetaTag("description", seo.getDescription()));
            }

            // open graph
            if (!StringUtils.isEmpty(openGraph.getType())) {
                sb.append(makeOpenGraphTag("og:type", openGraph.getType()));
            }

            if (!StringUtils.isEmpty(openGraph.getUrl())) {
                sb.append(makeOpenGraphTag("og:url", openGraph.getUrl()));
            }

            if (!StringUtils.isEmpty(openGraph.getTitle())) {
                sb.append(makeOpenGraphTag("og:title", openGraph.getTitle()));
            }

            if (!StringUtils.isEmpty(openGraph.getImage())) {
                sb.append(makeOpenGraphTag("og:image", openGraph.getImage()));
            }

            if (!StringUtils.isEmpty(openGraph.getDescription())) {
                sb.append(makeOpenGraphTag("og:description", openGraph.getDescription()));
            }

            // script
            if (!isBot) {
                sb.append("<script type=\"text/javascript\">");
                sb.append("location.replace('"+openGraph.getLink()+"')");
                sb.append("</script>");
            }

            sb.append("</head>\n");
            sb.append("<body>");

            if (!StringUtils.isEmpty(seo.getHeaderContents1())) {
                sb.append("<h1>"+seo.getHeaderContents1()+"</h1>");
            }

            sb.append("</body>");
            sb.append("</html>\n");

        } catch (Exception e) {
            log.error("seo make Html tag Error [{}] [{}]", JsonViewUtils.objectToJson(openGraph), JsonViewUtils.objectToJson(seo), e);
        }
        return sb.toString();
    }

    private String makeOpenGraphTag(String property, String content) {
        return OpenGraphUtils.makeOpenGraphTag(property, content);
    }

    private String makeMetaTag(String name, String content) {

        return "<meta name=\""+name+"\" content=\""+content+"\"/>\n";
    }

    private boolean isBot(HttpServletRequest request) {

        String userAgent = request.getHeader("User-Agent").toUpperCase();

        List<String> bots = getBots();

        for (String bot : bots) {
            if (userAgent.indexOf(bot) > -1) {
                return true;
            }
        }

        return false;
    }

    private boolean isApiView() {
        return "api".equals(environment.getProperty("saleson.view.type"));
    }

    private List<String> getBots() {
        List<String> list = new ArrayList<>();

        list.add("GOOGLEBOT");
        list.add("COWBOT");
        list.add("YETI");
        list.add("EMPAS");
        list.add("MSNBOT");
        list.add("DAUMOA");
        list.add("BAIDUSPIDER");
        list.add("TWITTERBOT");
        list.add("FACEBOOKEXTERNALHIT");
        list.add("ROGERBOT");
        list.add("LINKEDINBOT");
        list.add("EMBEDLY");
        list.add("QUORA LINK PREVIEW");
        list.add("SHOWYOUBOT");
        list.add("OUTBRAIN");
        list.add("PINTEREST");
        list.add("SLACKBOT");
        list.add("VKSHARE");
        list.add("W3C_VALIDATOR");

        return list;
    }

}
