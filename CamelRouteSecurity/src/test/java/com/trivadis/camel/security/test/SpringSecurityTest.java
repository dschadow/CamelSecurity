package com.trivadis.camel.security.test;

import javax.security.auth.Subject;

import org.apache.camel.CamelAuthorizationException;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.trivadis.camel.security.user.UserData;

/**
 * JUnit tests for the routes protected by Spring Security.
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
public class SpringSecurityTest extends CamelSpringTestSupport {
    private static final String USERDATA_COMPLETE = "Trivadis GmbH, Dominik, Schadow, Industriestraße 4, 70565, Stuttgart, Germany, 1234567890, 49";
    private static final String USERDATA_PARTIAL = "Trivadis GmbH, Dominik, Schadow, Industriestraße 4, 70565, Stuttgart, Germany, 1234567890, 0";

    @Test
    public void testRouteWithValidAgentUser() throws Exception {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("userAgent", "secret1");

        Subject subject = new Subject();
        subject.getPrincipals().add(token);

        UserData userData = template.requestBodyAndHeader("direct:findUserDataSpring", 1234567890,
                Exchange.AUTHENTICATION, subject, UserData.class);

        assertEquals(USERDATA_PARTIAL, userData.toString());
    }
    
    @Test
    public void testRouteWithValidEditorUser() throws Exception {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("userEditor", "secret2");

        Subject subject = new Subject();
        subject.getPrincipals().add(token);

        UserData userData = template.requestBodyAndHeader("direct:findUserDataSpring", 1234567890,
                Exchange.AUTHENTICATION, subject, UserData.class);

        assertEquals(USERDATA_COMPLETE, userData.toString());
    }
    
    @Test
    public void testRouteWithValidAdminUser() throws Exception {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("userAdmin", "secret3");

        Subject subject = new Subject();
        subject.getPrincipals().add(token);

        UserData userData = template.requestBodyAndHeader("direct:findUserDataSpring", 1234567890,
                Exchange.AUTHENTICATION, subject, UserData.class);

        assertEquals(USERDATA_COMPLETE, userData.toString());
    }

    @Test
    public void testFirstRouteWithValidUser() throws Exception {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("userAgent", "secret1");

        Subject subject = new Subject();
        subject.getPrincipals().add(token);

        UserData userData = template.requestBodyAndHeader("direct:findUserDataSpring", 1234567890,
                Exchange.AUTHENTICATION, subject, UserData.class);

        assertEquals(USERDATA_PARTIAL, userData.toString());
    }

    @Test
    public void testRouteWithInvalidPassword() throws Exception {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("userAgent", "secret");

            Subject subject = new Subject();
            subject.getPrincipals().add(token);

            template.sendBodyAndHeader("direct:findUserDataSpring", 1234567890, Exchange.AUTHENTICATION, subject);
        } catch (CamelExecutionException ex) {
            if (ex.getCause() instanceof CamelAuthorizationException) {
                // OK
            } else {
                Assert.fail(ex.getMessage());
            }
        }
    }

    @Test
    public void testRouteWithUnknownUser() throws Exception {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("fake", "fake");

            Subject subject = new Subject();
            subject.getPrincipals().add(token);

            template.sendBodyAndHeader("direct:findUserDataSpring", 1234567890, Exchange.AUTHENTICATION, subject);
        } catch (CamelExecutionException ex) {
            if (ex.getCause() instanceof CamelAuthorizationException) {
                // OK
            } else {
                Assert.fail(ex.getMessage());
            }
        }
    }

    @Test
    public void testRouteWithoutToken() throws Exception {
        try {
            template.requestBody("direct:findUserDataSpring", 1234567890);
        } catch (CamelExecutionException ex) {
            if (ex.getCause() instanceof CamelAuthorizationException) {
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
