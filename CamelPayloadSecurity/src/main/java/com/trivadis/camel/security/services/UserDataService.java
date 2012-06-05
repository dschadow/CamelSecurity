package com.trivadis.camel.security.services;

import javax.jws.WebService;

import com.trivadis.camel.security.user.UserData;

@WebService
public interface UserDataService {
	UserData findUserData(int socialSecurityNumber);
	
	String findUserDataSymEncXML(int socialSecurityNumber);

	String findUserDataAsymEncXML(int socialSecurityNumber);

	String findUserDataSymEnc(int socialSecurityNumber);

	String findUserDataAsymEnc(int socialSecurityNumber);

	String findUserDataSign(int socialSecurityNumber);
}
