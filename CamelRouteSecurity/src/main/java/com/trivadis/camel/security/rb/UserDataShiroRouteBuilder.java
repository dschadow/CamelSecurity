package com.trivadis.camel.security.rb;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.component.shiro.security.ShiroSecurityPolicy;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.stereotype.Component;

/**
 * Java RouteBuilder implementation for the findUserDataShiro route. This route requires the
 * <b>trivadis:findUserDataShiro:*</b> permission.
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
@Component
public class UserDataShiroRouteBuilder extends SpringRouteBuilder {
    // TODO configuration via XML?

    @Override
    public void configure() throws Exception {
        final byte[] passPhrase = "CamelSecureRoute".getBytes();

        List<Permission> permissionsList = new ArrayList<Permission>();
        Permission permission = new WildcardPermission("trivadis:findUserDataShiro:*");
        permissionsList.add(permission);

        ShiroSecurityPolicy shiroSecurityPolicy =
                new ShiroSecurityPolicy("classpath:shirosecuritypolicy.ini", passPhrase, true, permissionsList);

        onException(UnknownAccountException.class).to("mock:authenticationException");
        onException(IncorrectCredentialsException.class).to("mock:authenticationException");
        onException(LockedAccountException.class).to("mock:authenticationException");
        onException(AuthenticationException.class).to("mock:authenticationException");

        from("direct:findUserDataShiro").routeId("findUserDataShiro").policy(shiroSecurityPolicy).beanRef(
                "userDataBean", "loadUserData").to("direct:calculateCategoryShiro");
    }
}
