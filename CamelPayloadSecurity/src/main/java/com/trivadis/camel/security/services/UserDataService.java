package com.trivadis.camel.security.services;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.trivadis.camel.security.user.UserData;

@WebService
public interface UserDataService {
	@WebResult(name = "userData")
	UserData findUserData(@WebParam(name = "socialSecurityNumber") int socialSecurityNumber);
	
	@WebResult(name = "userData")
	String findUserDataSymEncXML(@WebParam(name = "socialSecurityNumber") int socialSecurityNumber);

	@WebResult(name = "userData")
	String findUserDataAsymEncXML(@WebParam(name = "socialSecurityNumber") int socialSecurityNumber);

	@WebResult(name = "userData")
	String findUserDataSymEnc(@WebParam(name = "socialSecurityNumber") int socialSecurityNumber);

	@WebResult(name = "userData")
	String findUserDataAsymEnc(@WebParam(name = "socialSecurityNumber") int socialSecurityNumber);

	@WebResult(name = "userData")
	String findUserDataSign(@WebParam(name = "socialSecurityNumber") int socialSecurityNumber);
}
