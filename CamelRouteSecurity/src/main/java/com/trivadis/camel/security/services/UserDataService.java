package com.trivadis.camel.security.services;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.trivadis.camel.security.user.UserData;

/**
 * UserDataService web service interface.
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
@WebService
public interface UserDataService {
	@WebResult(name = "userData")
	UserData findUserData(@WebParam(name = "socialSecurityNumber", targetNamespace = "") int socialSecurityNumber);
	
	@WebResult(name = "userData")
	UserData findUserDataShiro(@WebParam(name = "socialSecurityNumber", targetNamespace = "") int socialSecurityNumber);

	@WebResult(name = "userData")
	UserData findUserDataSpring(@WebParam(name = "socialSecurityNumber", targetNamespace = "") int socialSecurityNumber);
}
