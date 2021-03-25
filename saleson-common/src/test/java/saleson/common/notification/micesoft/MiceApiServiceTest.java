package saleson.common.notification.micesoft;

import com.onlinepowers.framework.util.JsonViewUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import saleson.common.SalesonTest;
import saleson.common.notification.micesoft.domain.api.ResponseImage;

import java.io.FileInputStream;

class MiceApiServiceTest extends SalesonTest {

    @Autowired
    MiceApiService miceApiService;

    @Test
    void getMmsImageUrl() {

        try {
            FileInputStream fis = new FileInputStream("src/test/resources/stringtoolong.txt");
            String base64 = IOUtils.toString(fis, "UTF-8");
            ResponseImage responseImage = miceApiService.getMmsImageUrl(base64);

            System.out.println(JsonViewUtils.objectToJson(responseImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void jsonTest() {
        JSONObject jo1 = new JSONObject();

        jo1.put("title", "제목1");

        jo1.put("content", "내용1");

        String jsonStr = jo1.toString();
        System.out.println(jsonStr);

    }
}