package saleson.common.utils;

import com.onlinepowers.framework.util.JsonViewUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ShopUtilsTest {

    @Test
    void getOptions() {

        String options = "S||옵션선택||1||0";

        System.out.println(JsonViewUtils.objectToJson(ShopUtils.getOptions(options)));
        System.out.println(JsonViewUtils.objectToJson(ShopUtils.viewOptionTextNoUl(options)));

        options = "S||공병 선택||500ml||0^^^S||기본 구성||[에뜨벨라] 캐리어오일 1L 호호바||0";

        System.out.println(JsonViewUtils.objectToJson(ShopUtils.getOptions(options)));
        System.out.println(JsonViewUtils.objectToJson(ShopUtils.viewOptionTextNoUl(options)));

        options = "S2||상품구분||스냅백||색상||파랑||20";

        System.out.println(JsonViewUtils.objectToJson(ShopUtils.getOptions(options)));
        System.out.println(JsonViewUtils.objectToJson(ShopUtils.viewOptionTextNoUl(options)));

        options = "S3||브랜드||나이키||색상||빨강||사이즈||L||1000";

        System.out.println(JsonViewUtils.objectToJson(ShopUtils.getOptions(options)));
        System.out.println(JsonViewUtils.objectToJson(ShopUtils.viewOptionTextNoUl(options)));

        options = "T||텍스트 옵션1||2||0^^^T||텍스트 옵션2||1||0^^^T||텍스트 옵션3||3||0";

        System.out.println(JsonViewUtils.objectToJson(ShopUtils.getOptions(options)));
        System.out.println(JsonViewUtils.objectToJson(ShopUtils.viewOptionTextNoUl(options)));
    }

}