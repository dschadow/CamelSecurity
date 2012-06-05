package com.trivadis.camel.security.category;

import com.trivadis.camel.security.user.UserData;

/**
 * Simple category calculation bean.
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
public class CategoryBean {
	public String processData(UserData userData) {		
		int category = calculateCategory(userData.getSocialSecurityNumber());
		userData.setCategory(category);

		return userData.toString();
	}

	private int calculateCategory(int socialSecurityNumber) {
		return ("" + socialSecurityNumber).charAt(0);
	}
}
