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
 * JUnit tests for the second web service for category calculation. This test requires a running web server on port 8080 (configured in URL).
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
public class CalculateCategoryEndpointTest extends CamelSpringTestSupport {
    private static final String SIMPLE_ENDPOINT_ADDRESS = "http://localhost:" + 8080 + "/CamelPayloadSecurity/category";
    private static final String USERDATA_COMPLETE = "Trivadis GmbH, Dominik, Schadow, Industriestraße 4, 70565, Stuttgart, Germany, 1234567890, 49";
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

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("spring/camel-context.xml");
    }
}
