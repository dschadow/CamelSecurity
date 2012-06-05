package com.trivadis.camel.security;

import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.apache.cxf.BusFactory;
import org.apache.cxf.frontend.ClientFactoryBean;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.trivadis.camel.security.services.CategoryService;
import com.trivadis.camel.security.user.UserData;

/**
 * JUnit tests for the second web service for category calculation. This test requires a running web server on port 8080
 * (configured in URL).
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
public class CalculateCategoryEndpointTest extends CamelSpringTestSupport {
    private static final String PORT = "8080";
    private static final String SIMPLE_ENDPOINT_ADDRESS = "http://localhost:" + PORT + "/CamelPayloadSecurity/category";
    private static final String USERDATA_COMPLETE = "Trivadis GmbH, Dominik, Schadow, Industriestraße 4, 70565, Stuttgart, Germany, 1234567890, 49";
    private static final String CALCULATE_REQUEST = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
            + "<soap:Body><ser:calculateCategory xmlns:ser=\"http://services.security.camel.trivadis.com/\">"
            + "<arg0 xmlns=\"http://services.security.camel.trivadis.com/\"><category></category><city>Stuttgart</city>"
            + "<company>Trivadis GmbH</company><country>Germany</country><firstName>Dominik</firstName><lastName>Schadow</lastName>"
            + "<socialSecurityNumber>1234567890</socialSecurityNumber><street>Industriestraße 4</street><zip>70565</zip></arg0>"
            + "</ser:calculateCategory></soap:Body></soap:Envelope>";
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
    public void testCategoryEndpoint() {
        ClientProxyFactoryBean proxyFactory = new ClientProxyFactoryBean();
        ClientFactoryBean clientBean = proxyFactory.getClientFactoryBean();
        clientBean.setAddress(SIMPLE_ENDPOINT_ADDRESS);
        clientBean.setServiceClass(CategoryService.class);
        clientBean.setBus(BusFactory.getDefaultBus());

        CategoryService client = (CategoryService) proxyFactory.create();
        String result = client.calculateCategory(userData);
        assertEquals(USERDATA_COMPLETE, result);
    }

    @Test
    public void testXmlDeclaration() throws Exception {
        String response = template.requestBody(SIMPLE_ENDPOINT_ADDRESS, CALCULATE_REQUEST, String.class);
        assertTrue("Can't find the xml declaration.", response.startsWith("<?xml version='1.0' encoding="));
    }

    @Test
    public void testPublishEndpointUrl() throws Exception {
        String response = template.requestBody(SIMPLE_ENDPOINT_ADDRESS + "?wsdl", null, String.class);
        assertTrue("Can't find the right service location.",
                response.contains("http://localhost:" + PORT + "/CamelPayloadSecurity/category"));
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("spring/camel-context.xml");
    }
}
