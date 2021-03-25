package saleson.shop.google.analytics.domain;


import com.onlinepowers.framework.util.JsonViewUtils;

import java.util.Map;

public class GtagBuilder {

    public static Builder build() {
        return new Builder();
    }

    public static class Builder {

        private StringBuffer sb = new StringBuffer();

        public Builder config (String measurementId , Map<String, Object> field) {

            if (field != null && !field.isEmpty()) {
                sb.append(" gtag('config', '"+measurementId+"', "+field+");");
            }

            return this;
        }

        public Builder set (Map<String, Object> field) {

            if (field != null && !field.isEmpty()) {
                sb.append(" gtag('set', "+ JsonViewUtils.objectToJson(field) +");");
            }

            return this;
        }

        public Builder event (String name, Map<String, Object> field) {

            if (field != null && !field.isEmpty()) {
                sb.append(" gtag('event','"+name+"', "+ JsonViewUtils.objectToJson(field) +");");
            }

            return this;
        }

        public String toString() {
            return sb.toString();
        }

    }
}
