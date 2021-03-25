package saleson.shop.categoriesfilter;

import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seller/categories-filter")
@RequestProperty(title="카테고리 필터 관리", layout="default")
public class CategoriesFilterSellerController extends CategoriesFilterManagerController {
}