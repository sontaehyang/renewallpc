package saleson.common.opengraph;

import com.onlinepowers.framework.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.ShopUtils;
import saleson.shop.categories.domain.Categories;
import saleson.shop.featured.domain.Featured;
import saleson.shop.item.domain.Item;

import javax.servlet.http.HttpServletRequest;

@Data
@AllArgsConstructor
public class OpenGraph {

    public OpenGraph() {
        setType("website");
    }

    public OpenGraph(HttpServletRequest request) {
        this();
        setUrlByRequest(request);
    }

    public OpenGraph(Item item, HttpServletRequest request) {
        this(item);
        setUrlByRequest(request);
    }

    public OpenGraph(Categories category, HttpServletRequest request) {
        this(category);
        setUrlByRequest(request);
    }

    public OpenGraph(Item item) {

        this();

        if (item != null) {

            String url = SalesonProperty.getSalesonUrlShoppingmall() + item.getLink();
            String image = SalesonProperty.getSalesonUrlShoppingmall() + ShopUtils.loadImage(item.getItemUserCode(), item.getItemImage(), "S");

            if (image.equals(SalesonProperty.getSalesonUrlShoppingmall())) {
                image = "";
            }

            setUrl(url);
            setLink(url);
            setDescription(item.getItemSummary());
            setImage(image);
            setTitle(item.getItemName());
        }
    }

    public OpenGraph(Featured featured) {

        this();

        if (featured != null) {
            String url = SalesonProperty.getSalesonUrlShoppingmall() + featured.getPageLink();
            String image = SalesonProperty.getSalesonUrlShoppingmall() + featured.getThumbnailImageSrc();

            if (image.equals(SalesonProperty.getSalesonUrlShoppingmall())) {
                image = "";
            }

            setTitle(featured.getFeaturedName());
            setImage(image);
            setDescription(featured.getFeaturedSimpleContent());
            setUrl(url);
            setLink(url);
        }
    }

    public OpenGraph(Categories category) {
        this();

        if (category != null) {

            String url = SalesonProperty.getSalesonUrlShoppingmall()
                    + "/categories/index/"
                    + category.getCategoryUrl();

            setUrl(url);
            setLink(url);
            setTitle(category.getCategoryName());
        }
    }

    private String link;
    private String title;
    private String type;
    private String image;
    private String description;
    private String url;

    private void setUrlByRequest(HttpServletRequest request) {

        if (request != null) {

            StringBuffer sb = new StringBuffer(SalesonProperty.getSalesonUrlShoppingmall() + request.getRequestURI());
            String queryString = request.getQueryString();

            if (!StringUtils.isEmpty(queryString)) {
                sb.append("?").append(queryString);
            }

            setUrl(sb.toString());
        }

    }
}
