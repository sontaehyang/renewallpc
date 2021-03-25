package saleson.common.security.matcher;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CsrfSecurityRequestMatcher implements RequestMatcher{

    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

    private List<String> matcherList;

    public CsrfSecurityRequestMatcher(String... matcher) {

        matcherList = new ArrayList<>();

        if (matcher != null && matcher.length > 0) {
            for (String m : matcher) {
                matcherList.add(m);
            }
        }

    }

    @Override
    public boolean matches(HttpServletRequest request) {

        if (allowedMethods.matcher(request.getMethod()).matches()) {
            return false;
        }

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String uri = request.getRequestURI();
        for (String pattern : matcherList) {

            if (antPathMatcher.match(pattern, uri)) {
                return false;
            }
        }

        return true;
    }
}
