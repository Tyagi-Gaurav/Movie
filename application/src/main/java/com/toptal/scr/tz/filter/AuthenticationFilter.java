package com.toptal.scr.tz.filter;

import com.toptal.scr.tz.resource.domain.ImmutableUserProfile;
import com.toptal.scr.tz.resource.domain.UserProfile;
import com.toptal.scr.tz.service.UserService;
import com.toptal.scr.tz.service.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("signingKey")
	private Key signingKey;

	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String userId = null;
		String jwtToken = null;
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			JwtTokenUtil jwtTokenUtil = getJwtTokenUtil(jwtToken);

			try {
				userId = jwtTokenUtil.getUserIdFromToken();
				LOG.info("Fetched UserId from token: " + userId);
				validateToken(jwtTokenUtil, userId, chain, request, response);
			} catch (IllegalArgumentException e) {
				LOG.error("Unable to get JWT Token", e);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	protected JwtTokenUtil getJwtTokenUtil(String jwtToken) {
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(jwtToken, signingKey);
		return jwtTokenUtil;
	}

	private void validateToken(JwtTokenUtil jwtTokenUtil, String userId, FilterChain chain, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			LOG.info("Fetch user from repository: {}", userId);
			User userDetails = userService.findUserBy(UUID.fromString(userId));

			if (jwtTokenUtil.validateToken(userDetails)) {
				LOG.info("Token validated for user {}", userId);
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

				List<Object> authoritiesObjects = (List<Object>) jwtTokenUtil.getClaimFromToken(claims -> claims.get("Authorities"));

				LinkedHashMap authorities = (LinkedHashMap) authoritiesObjects.get(0);
				String authority = authorities.get("authority").toString();
				UserProfile userprofile = ImmutableUserProfile.builder()
						.id(userDetails.id())
						.authority(authority)
						.build();
				request.setAttribute("userProfile", userprofile);

				LOG.info("User authenticated with role: {}", authority);
			}
		}
		
		chain.doFilter(request, response);
	}
}