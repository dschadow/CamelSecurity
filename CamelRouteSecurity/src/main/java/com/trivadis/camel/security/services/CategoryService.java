package com.trivadis.camel.security.services;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * CategoryService web service interface.
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
@WebService
public interface CategoryService {
	@WebResult(name = "userData")
	String calculateCategory(@WebParam(name = "userData", targetNamespace = "") String userData);
}
