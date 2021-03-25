package saleson.common.web.security.xss;

import com.onlinepowers.framework.util.PropertiesUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

public class XSSRequestWrapper extends HttpServletRequestWrapper {
    public XSSRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        } else {
            int count = values.length;
            String[] encodedValues = new String[count];

            for(int i = 0; i < count; ++i) {
                if (this.isExcludeParameter(parameter)) {
                    encodedValues[i] = this.stripXSS(values[i]);
                } else {
                    encodedValues[i] = this.stripXSSExtend(this.stripXSS(values[i]));
                }
            }

            return encodedValues;
        }
    }

    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        value = this.stripXSS(value);
        if (!this.isExcludeParameter(parameter)) {
            value = this.stripXSSExtend(value);
        }

        return value;
    }

    public String getHeader(String name) {
        String value = super.getHeader(name);
        return this.stripXSS(value);
    }

    private String stripXSS(String value) {
        if (value != null) {
            value = value.replaceAll("", "");
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("</script>", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("<script(.*?)>", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("javascript:", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("vbscript:", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("onload(.*?)=", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("onmouseover(.*?)=", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("onerror(.*?)=", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            /*value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");*/
            value = value.replaceAll("\\'", "&#39;");
        }

        return value;
    }

    private String stripXSSExtend(String value) {
        if (value != null) {
            Pattern scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\'(.*?)\\'", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            value = this.stripTags(value);
        }

        return value;
    }

    private Boolean isExcludeParameter(String parameter) {
        String excludeParameters = PropertiesUtils.getProperty("xss.filter.exclude.parameters");
        String[] params = StringUtils.tokenizeToStringArray(excludeParameters, ",");
        String[] var4 = params;
        int var5 = params.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String param = var4[var6];
            if (param.equals(parameter)) {
                return true;
            }
        }

        return false;
    }

    private String stripTags(String value) {
        value = value.replaceAll("<(/)?([a-zA-Z1-9]*)(\\s[a-zA-Z1-9]*=[^>]*)?(\\s)*(/)?>", "");
        value = value.replaceAll("<", "&lt;");
        value = value.replaceAll(">", "&gt;");
        value = value.replaceAll("\"", "&quot;");
        value = value.replaceAll("'", "&#39;");
        return value;
    }
}

