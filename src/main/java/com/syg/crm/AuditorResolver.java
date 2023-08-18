package com.syg.crm;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.syg.crm.util.Util;

import jakarta.servlet.http.HttpServletRequest;

public class AuditorResolver implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		// get the request
		HttpServletRequest request = requestAttributes.getRequest();
		// get headers
		String auth = request.getHeader("auth");

		String userName = Util.getLoggedInUserName(auth);

		return Optional.of(userName);
	}

}
