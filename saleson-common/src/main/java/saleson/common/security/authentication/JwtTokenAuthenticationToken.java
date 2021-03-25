package saleson.common.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;

public class JwtTokenAuthenticationToken extends AbstractAuthenticationToken {

    private Object principal;

    public JwtTokenAuthenticationToken(UserDetails userDetails) {
        super(userDetails.getAuthorities());
        super.setAuthenticated(true);
        this.principal = userDetails;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
