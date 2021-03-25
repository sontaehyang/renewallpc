package saleson.common.utils;

import org.springframework.ui.Model;
import saleson.common.opengraph.OpenGraph;
import saleson.shop.featured.domain.Featured;
import saleson.shop.item.domain.Item;

public class OpenGraphUtils {

    public static void setOpenGraphModel(Model model, OpenGraph openGraph) {
        model.addAttribute("openGraphInfo", openGraph);
    }

    public static void setOpenGraphModelByItem(Model model, Item item) {
        OpenGraphUtils.setOpenGraphModel(model, new OpenGraph(item));
    }

    public static void setOpenGraphModelByFeatured(Model model, Featured featured) {
        OpenGraphUtils.setOpenGraphModel(model, new OpenGraph(featured));
    }

    public static String makeOpenGraphTag(String property, String content) {

        return "<meta property=\""+property+"\" content=\""+content+"\"/>\n";
    }
}
