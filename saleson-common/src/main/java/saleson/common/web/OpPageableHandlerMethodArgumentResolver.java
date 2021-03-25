package saleson.common.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;

public class OpPageableHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolver {
	public OpPageableHandlerMethodArgumentResolver() {
		super();
		setOneIndexedParameters(true);
		setFallbackPageable(PageRequest.of(1, 10));
	}
}
