package saleson.shop.naver;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.config.SalesonProperty;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.Category;
import saleson.shop.categories.domain.Group;
import saleson.shop.categories.domain.Team;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.item.support.ItemParam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/naver")
@RequestProperty(layout="base")
public class NaverController {

    @Autowired
    ItemService itemService;

    @Autowired
    CategoriesService categoriesService;

    @RequestMapping({"/shopping/ep", "/pay/ep"})
    public String ep(RequestContext requestContext) {
        ItemParam itemParam = new ItemParam();

        itemParam.setConditionType("EP_ITEM_LIST");
        itemParam.setDisplayFlag("Y");					// 공개 상품만
        itemParam.setDataStatusCode("1");

        if (requestContext.getRequestUri().indexOf("shopping") > -1) {
            itemParam.setNaverShoppingFlag("Y");
        }

        OutputStreamWriter osw = null;
        StringBuffer sb = new StringBuffer();

        String path = "/ep/";
        String fileName = "ep.txt";

        // 상품 정보
        List<Item> list = itemService.getItemList(itemParam);

        // 카테고리 정보
        List<Team> categories = categoriesService.getCategoriesForApi();

        sb.append("id\ttitle\tprice_pc\tlink\timage_link\tcategory_name1\tshipping\treview_count\n");

        for (Item item : list) {
            String itemName = item.getItemName();
            if (requestContext.getRequestUri().indexOf("shopping") > -1) {
                itemName = item.getNaverShoppingItemName();
            }

            sb.append(item.getItemUserCode()+"\t");             // id
            sb.append(itemName+"\t");                           // title
            sb.append(item.getSalePrice()+"\t");                // price_pc

            sb.append(SalesonProperty.getSalesonUrlShoppingmall() + "/products/view/" + item.getItemUserCode()+"\t");  // link
            sb.append(SalesonProperty.getSalesonUrlShoppingmall() + item.getImageSrc()+"\t");  // image_link

            String categoryName1 = "";
            if (item.getItemCategories().size() > 0) {
                Map<String, String> infos = getCategoryNameInfo(categories, Integer.toString(item.getItemCategories().get(0).getCategoryId()));
                if (infos.size() > 0) {
                    categoryName1 = infos.get("category_name1");
                }
            }

            sb.append(categoryName1+"\t");                      // category_name1
            sb.append(item.getShipping()+"\t");                 // shipping
            sb.append(item.getReviewCount()+"\n");              // review_count
        }

        try {
            String uploadPath = FileUtils.getDefaultUploadPath() + path;

            File savePath = new File(uploadPath);
            if (!savePath.exists()) {
                savePath.mkdirs();
            }

            osw = new OutputStreamWriter(new FileOutputStream(uploadPath + fileName), "UTF-8");
            osw.write(sb.toString());

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (osw != null) try { osw.close(); } catch (Exception e) {}
        }

        return ViewUtils.redirect("/upload" + path + fileName);
    }

    private Map<String, String> getCategoryNameInfo(List<Team> categories, String categoryId) {
        Map<String, String> infos = new HashMap<>();

        for (Team team : categories) {
            for (Group group : team.getGroups()) {
                for (Category category1 : group.getCategories()) {
                    if (categoryId.equals(category1.getCategoryId())) {
                        infos.put("category_name1", group.getName());
                        infos.put("category_name2", category1.getName());
                        infos.put("category_name3", "");
                        infos.put("category_name4", "");
                        return infos;
                    }
                    for (Category category2 : category1.getChildCategories()) {
                        if (categoryId.equals(category2.getCategoryId())) {
                            infos.put("category_name1", group.getName());
                            infos.put("category_name2", category1.getName());
                            infos.put("category_name3", category2.getName());
                            infos.put("category_name4", "");
                            return infos;
                        }
                        for (Category category3 : category2.getChildCategories()) {
                            if (categoryId.equals(category3.getCategoryId())) {
                                infos.put("category_name1", group.getName());
                                infos.put("category_name2", category1.getName());
                                infos.put("category_name3", category2.getName());
                                infos.put("category_name4", category3.getName());
                                return infos;
                            }
                            for (Category category4 : category3.getChildCategories()) {
                                if (categoryId.equals(category4.getCategoryId())) {
                                    infos.put("category_name1", group.getName());
                                    infos.put("category_name2", category1.getName());
                                    infos.put("category_name3", category2.getName());
                                    infos.put("category_name4", category3.getName());
                                    infos.put("category_name5", category4.getName());
                                    return infos;
                                }
                            }
                        }
                    }
                }
            }
        }

        return infos;
    }
}