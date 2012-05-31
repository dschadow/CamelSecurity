package com.trivadis.camel.security;

import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FindUserDataEnpointTest extends CamelSpringTestSupport {
    @Test
    public void testSymmetricEncryption() {
        String responseAsBase64 = template.requestBody("direct:findUserDataSymEnc", 1234567890, String.class);
        assertNotNull(responseAsBase64);
        assertTrue(responseAsBase64.endsWith("="));
    }
    
    @Test
    public void testAsymmetricEncryption() {
        String responseAsBase64 = template.requestBody("direct:findUserDataAsymEnc", 1234567890, String.class);
        assertNotNull(responseAsBase64);
    }
    
    @Test
    public void testSignature() {
        int response = template.requestBody("direct:findUserDataSign", 1234567890, Integer.class);
        assertEquals(1234567890, response);
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("spring/camel-context.xml");
    }
}
