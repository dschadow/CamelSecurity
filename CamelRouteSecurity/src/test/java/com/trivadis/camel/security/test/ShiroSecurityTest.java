package com.trivadis.camel.security.test;

import org.apache.camel.component.shiro.security.ShiroSecurityToken;
import org.apache.camel.component.shiro.security.ShiroSecurityTokenInjector;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ShiroSecurityTest extends CamelSpringTestSupport {
        private final byte[] passPhrase = {
            (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
            (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F,
            (byte) 0x10, (byte) 0x11, (byte) 0x12, (byte) 0x13,
            (byte) 0x14, (byte) 0x15, (byte) 0x16, (byte) 0x17};
        
	@Test
    public void testShiroRouteWithValidUser() throws Exception {        
        ShiroSecurityToken shiroSecurityToken = new ShiroSecurityToken("userAgent", "secret1");

        ShiroSecurityTokenInjector shiroSecurityTokenInjector = 
            new ShiroSecurityTokenInjector(shiroSecurityToken, passPhrase);
        template.sendBodyAndHeader("direct:findUserDataShiro", 1234567890, "SHIRO_SECURITY_TOKEN", shiroSecurityTokenInjector.encrypt());
    }
	
	@Test(expected=IncorrectCredentialsException.class)
    public void testShiroRouteWithInvalidUser() throws Exception {        
        ShiroSecurityToken shiroSecurityToken = new ShiroSecurityToken("userAgent", "secret");

        ShiroSecurityTokenInjector shiroSecurityTokenInjector = 
            new ShiroSecurityTokenInjector(shiroSecurityToken, passPhrase);
        template.sendBodyAndHeader("direct:findUserDataShiro", 1234567890, "SHIRO_SECURITY_TOKEN", shiroSecurityTokenInjector.encrypt());
    }

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("spring/camel-context.xml");
	} 

}
