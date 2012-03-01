package com.trivadis.camel.security.services;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface CategoryService {
	@WebResult(name = "userData")
	String calculateCategory(@WebParam(name = "userData", targetNamespace = "") String userData);
}
