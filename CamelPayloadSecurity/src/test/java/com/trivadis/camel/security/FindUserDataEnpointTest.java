package com.trivadis.camel.security;

import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.apache.cxf.BusFactory;
import org.apache.cxf.frontend.ClientFactoryBean;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.trivadis.camel.security.services.UserDataService;
import com.trivadis.camel.security.user.UserData;

/**
 * JUnit tests for all secured routes.This test requires a running web server on port 8080
 * (configured in URL) for some methods.
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
public class FindUserDataEnpointTest extends CamelSpringTestSupport {
    private static final String PORT = "8080";
    private static final String SIMPLE_ENDPOINT_ADDRESS = "http://localhost:" + PORT + "/CamelPayloadSecurity/userdata";
    private static final String USERDATA_COMPLETE = "Trivadis GmbH, Dominik, Schadow, Industriestraße 4, 70565, Stuttgart, Germany, 1234567890, 49";
    private static final String USERDATA_PARTIAL = "Trivadis GmbH, Dominik, Schadow, Industriestraße 4, 70565, Stuttgart, Germany, 1234567890, 0";
    private UserData userData = new UserData();

    @Before
    public void setup() {
        userData.setCompany("Trivadis GmbH");
        userData.setFirstName("Dominik");
        userData.setLastName("Schadow");
        userData.setStreet("Industriestraße 4");
        userData.setZip("70565");
        userData.setCity("Stuttgart");
        userData.setCountry("Germany");
        userData.setSocialSecurityNumber(1234567890);
    }

    @Test
    public void testUserDataEndpoint() {
        ClientProxyFactoryBean proxyFactory = new ClientProxyFactoryBean();
        ClientFactoryBean clientBean = proxyFactory.getClientFactoryBean();
        clientBean.setAddress(SIMPLE_ENDPOINT_ADDRESS);
        clientBean.setServiceClass(UserDataService.class);
        clientBean.setBus(BusFactory.getDefaultBus());

        UserDataService client = (UserDataService) proxyFactory.create();

        UserData result = client.findUserData(1234567890);
        assertEquals(USERDATA_COMPLETE, result.toString());
    }

    @Test
    public void testSymmetricEncryption() {
        String responseEncrypted = template.requestBody("direct:findUserDataSymEnc", userData, String.class);
        assertNotNull(responseEncrypted);
        assertFalse(responseEncrypted.contains(USERDATA_COMPLETE));
        assertFalse(responseEncrypted.contains(USERDATA_PARTIAL));
        assertTrue(responseEncrypted.endsWith("="));
    }

    @Test
    public void testAsymmetricEncryption() {
        String responseEncrypted = template.requestBody("direct:findUserDataAsymEnc", userData, String.class);
        assertNotNull(responseEncrypted);
        assertFalse(responseEncrypted.contains(USERDATA_COMPLETE));
        assertFalse(responseEncrypted.contains(USERDATA_PARTIAL));
    }

    @Test
    public void testSymmetricXMLEncryption() {
        String responseEncrypted = template.requestBody("direct:findUserDataSymEncXML", userData, String.class);
        assertNotNull(responseEncrypted);
        assertFalse(responseEncrypted.contains(USERDATA_COMPLETE));
        assertFalse(responseEncrypted.contains(USERDATA_PARTIAL));
    }

    @Test
    public void testAsymmetricXMLEncryption() {
        String responseEncrypted = template.requestBody("direct:findUserDataAsymEncXML", userData, String.class);
        assertNotNull(responseEncrypted);
        assertFalse(responseEncrypted.contains(USERDATA_COMPLETE));
        assertFalse(responseEncrypted.contains(USERDATA_PARTIAL));
    }

    @Test
    public void testSignature() {
        String response = template.requestBody("direct:findUserDataSign", userData, String.class);
        assertNotNull(response);
        assertEquals(USERDATA_PARTIAL, response);
    }

    @Test
    public void testPublishEndpointUrl() throws Exception {
        String response = template.requestBody(SIMPLE_ENDPOINT_ADDRESS + "?wsdl", null, String.class);
        assertTrue("Can't find the right service location.",
                response.contains("http://localhost:" + PORT + "/CamelPayloadSecurity/userdata"));
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("spring/camel-context.xml");
    }
}
