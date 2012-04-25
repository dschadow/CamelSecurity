package com.trivadis.camel.security.rb;

import org.apache.camel.component.shiro.security.ShiroSecurityPolicy;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Component;

@Component
public class ShiroSecurityRouteBuilder extends SpringRouteBuilder {
	// TODO configuration via XML?

	// TODO policy options (different rights not working)

	@Override
	public void configure() throws Exception {
		ShiroSecurityPolicy shiroSecurityPolicy = new ShiroSecurityPolicy(
				"classpath:shirosecuritypolicy.ini");

		onException(UnknownAccountException.class).to(
				"mock:authenticationException");
		onException(IncorrectCredentialsException.class).to(
				"mock:authenticationException");
		onException(LockedAccountException.class).to(
				"mock:authenticationException");
		onException(AuthenticationException.class).to(
				"mock:authenticationException");

		from("direct:findUserDataShiro").routeId("findUserDataShiro")
				.policy(shiroSecurityPolicy)
				.beanRef("userDataBean", "loadUserData")
				.to("direct:calculateCategory");
	}
}
