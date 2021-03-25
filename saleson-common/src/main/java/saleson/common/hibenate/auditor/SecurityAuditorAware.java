package saleson.common.hibenate.auditor;

import com.onlinepowers.framework.security.userdetails.OpUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityAuditorAware implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null
				|| authentication instanceof AnonymousAuthenticationToken                // SKC : anonymous 인 경우에 빈값으로 리턴해야 함.
				|| !authentication.isAuthenticated()) {
			return Optional.empty();
		}

		return Optional.of(((OpUserDetails) authentication.getPrincipal()).getId());
	}
}
