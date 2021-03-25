package saleson.common.notification.micesoft;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

public class JsonTest {
    @Test
    void jsonTest() {
        JSONObject jo1 = new JSONObject();

        jo1.put("title", "제목1");

        jo1.put("content", "내용1");


        String jsonStr = jo1.toString();
        System.out.println(jsonStr);


        JSONArray ja = new JSONArray();

        ja.add("1111asadfasdfasfda");

        ja.add("222222");

        String jsonStr2 = ja.toString();
        System.out.println(jsonStr2);

    }

}
