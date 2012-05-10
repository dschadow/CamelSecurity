package com.trivadis.camel.security.test;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.component.shiro.security.ShiroSecurityToken;
import org.apache.camel.component.shiro.security.ShiroSecurityTokenInjector;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.trivadis.camel.security.user.UserData;

/**
 * JUnit tests for the Spring-Security routes.
 *
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
public class SpringSecurityTest extends CamelSpringTestSupport {
    private final byte[] passPhrase = "CamelSecureRoute".getBytes();
    private static final String USERDATA_COMPLETE =
            "Trivadis GmbH, Dominik, Schadow, Industriestraße 4, 70565, Stuttgart, Germany, 1234567890, 49";
    private static final String USERDATA_PARTIAL =
            "Trivadis GmbH, Dominik, Schadow, Industriestraße 4, 70565, Stuttgart, Germany, 1234567890, 0";

    @Test
    public void testSpringRouteWithValidUser() throws Exception {
        ShiroSecurityToken shiroSecurityToken = new ShiroSecurityToken("userEditor", "secret2");

        ShiroSecurityTokenInjector shiroSecurityTokenInjector =
                new ShiroSecurityTokenInjector(shiroSecurityToken, passPhrase);
        UserData userData =
                template.requestBodyAndHeader("direct:findUserDataSpring", 1234567890, "SHIRO_SECURITY_TOKEN",
                        shiroSecurityTokenInjector.encrypt(), UserData.class);

        assertEquals(USERDATA_COMPLETE, userData.toString());
    }

    @Test
    public void testSpringRouteWithPartialValidUser() throws Exception {
        ShiroSecurityToken shiroSecurityToken = new ShiroSecurityToken("userAgent", "secret1");

        ShiroSecurityTokenInjector shiroSecurityTokenInjector =
                new ShiroSecurityTokenInjector(shiroSecurityToken, passPhrase);
        UserData userData =
                template.requestBodyAndHeader("direct:findUserDataSpring", 1234567890, "SHIRO_SECURITY_TOKEN",
                        shiroSecurityTokenInjector.encrypt(), UserData.class);

        assertEquals(USERDATA_PARTIAL, userData.toString());
    }

    @Test
    public void testSpringRouteWithInvalidUser() throws Exception {
        try {
            ShiroSecurityToken shiroSecurityToken = new ShiroSecurityToken("userAgent", "secret");

            ShiroSecurityTokenInjector shiroSecurityTokenInjector =
                    new ShiroSecurityTokenInjector(shiroSecurityToken, passPhrase);
            template.sendBodyAndHeader("direct:findUserDataSpring", 1234567890, "SHIRO_SECURITY_TOKEN",
                    shiroSecurityTokenInjector.encrypt());
        } catch (CamelExecutionException ex) {
            if (ex.getCause() instanceof IncorrectCredentialsException) {
                // OK
            } else {
                Assert.fail(ex.getMessage());
            }
        }
    }

    @Test(expected = CamelExecutionException.class)
    public void testSpringRouteWithoutToken() throws Exception {
        template.sendBody("direct:findUserDataSpring", 1234567890);
    }

    @Test
    public void testSpringRouteWithUnknownUser() throws Exception {
        try {
            ShiroSecurityToken shiroSecurityToken = new ShiroSecurityToken("myUser", "mySecret");

            ShiroSecurityTokenInjector shiroSecurityTokenInjector =
                    new ShiroSecurityTokenInjector(shiroSecurityToken, passPhrase);
            template.sendBodyAndHeader("direct:findUserDataSpring", 1234567890, "SHIRO_SECURITY_TOKEN",
                    shiroSecurityTokenInjector.encrypt());
        } catch (CamelExecutionException ex) {
            if (ex.getCause() instanceof UnknownAccountException) {
                // OK
            } else {
                Assert.fail(ex.getMessage());
            }
        }
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("spring/camel-context.xml");
    }
}
