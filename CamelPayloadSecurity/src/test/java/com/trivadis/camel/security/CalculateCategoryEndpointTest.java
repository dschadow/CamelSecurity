package com.trivadis.camel.security;

import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.trivadis.camel.security.user.UserData;

public class CalculateCategoryEndpointTest extends CamelSpringTestSupport {
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
		String response = template.requestBody("cxf:bean:categoryEndpoint",
				userData, String.class);
		assertNotNull(response);
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("spring/camel-context.xml");
	}
}
