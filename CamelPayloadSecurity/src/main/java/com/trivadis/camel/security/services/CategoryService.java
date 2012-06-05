package com.trivadis.camel.security.services;

import javax.jws.WebService;

import com.trivadis.camel.security.user.UserData;

@WebService
public interface CategoryService {
	String calculateCategory(UserData userData);
}
