package com.trivadis.camel.security.crypto;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

/**
 * Sample symmetric key generation class.
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
public class KeyGeneration {
	private static Key desKey = null;
	private static Key aesKey = null;

	static {
		KeyGenerator generator;
		try {
			generator = KeyGenerator.getInstance("DES");
			desKey = generator.generateKey();
			
			generator = KeyGenerator.getInstance("AES");
			aesKey = generator.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static Key getDesKey() {
		return desKey;
	}
	
	public static Key getAesKey() {
		return aesKey ;
	}
}
