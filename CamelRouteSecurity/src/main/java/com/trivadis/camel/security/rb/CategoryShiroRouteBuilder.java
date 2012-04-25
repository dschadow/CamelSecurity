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

@Component
public class CategoryShiroRouteBuilder extends SpringRouteBuilder {
    // TODO configuration via XML?

    @Override
    public void configure() throws Exception {
        final byte[] passPhrase =
            {(byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B, (byte) 0x0C, (byte) 0x0D, (byte) 0x0E,
                    (byte) 0x0F, (byte) 0x10, (byte) 0x11, (byte) 0x12, (byte) 0x13, (byte) 0x14, (byte) 0x15,
                    (byte) 0x16, (byte) 0x17};

    List<Permission> permissionsList = new ArrayList<Permission>();
    Permission permission = new WildcardPermission("trivadis:calculateCategory:*");
    permissionsList.add(permission);

    ShiroSecurityPolicy shiroSecurityPolicy =
            new ShiroSecurityPolicy("classpath:shirosecuritypolicy.ini", passPhrase, true, permissionsList);

    onException(UnknownAccountException.class).to("mock:authenticationException");
    onException(IncorrectCredentialsException.class).to("mock:authenticationException");
    onException(LockedAccountException.class).to("mock:authenticationException");
    onException(AuthenticationException.class).to("mock:authenticationException");

    from("direct:calculateCategoryShiro").routeId("calculateCategoryShiro").policy(shiroSecurityPolicy).beanRef(
            "categoryBean", "processData");
    }
}
