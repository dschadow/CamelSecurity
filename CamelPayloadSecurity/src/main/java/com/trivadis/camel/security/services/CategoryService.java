package com.trivadis.camel.security.services;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.trivadis.camel.security.user.UserData;

@WebService
public interface CategoryService {
	@WebResult(name = "userData")
	UserData calculateCategory(@WebParam(name = "socialSecurityNumber", targetNamespace = "") int socialSecurityNumber);
	
	@WebResult(name = "userData")
	String calculateCategoryXML(@WebParam(name = "socialSecurityNumber", targetNamespace = "") int socialSecurityNumber);

	@WebResult(name = "userData")
	String calculateCategorySymEncXML(@WebParam(name = "socialSecurityNumber", targetNamespace = "") int socialSecurityNumber);

	@WebResult(name = "userData")
	String calculateCategoryAsymEncXML(@WebParam(name = "socialSecurityNumber", targetNamespace = "") int socialSecurityNumber);

	@WebResult(name = "userData")
	String calculateCategorySymEnc(@WebParam(name = "socialSecurityNumber", targetNamespace = "") int socialSecurityNumber);

	@WebResult(name = "userData")
	String calculateCategoryAsymEnc(@WebParam(name = "socialSecurityNumber", targetNamespace = "") int socialSecurityNumber);

	@WebResult(name = "userData")
	String calculateCategorySign(@WebParam(name = "socialSecurityNumber", targetNamespace = "") int socialSecurityNumber);
}
