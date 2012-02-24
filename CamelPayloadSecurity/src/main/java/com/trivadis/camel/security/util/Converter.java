package com.trivadis.camel.security.util;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.bouncycastle.util.encoders.Base64;

import com.trivadis.camel.security.user.UserData;

/**
 * Utility with different converter methods.
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
public class Converter {
	public String convertToBase64(byte[] data) {
		return new String(Base64.encode(data));
	}

	public byte[] convertToByte(String data) {
		return Base64.decode(data);
	}

	public String convertToXml(Object source) {
		StringWriter sw = new StringWriter();

		try {
			JAXBContext context = JAXBContext.newInstance(source.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty("com.sun.xml.bind.xmlDeclaration",
					Boolean.FALSE);
			marshaller.marshal(source, sw);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}

		return sw.toString();
	}
	
	public UserData convertToUserData(byte[] data) {
		UserData userData = new UserData();
		String dataString = new String(data);
		String[] dataArray = dataString.split(", ");
		userData.setCompany(dataArray[0]);
		userData.setFirstName(dataArray[1]);
		userData.setLastName(dataArray[2]);
		userData.setStreet(dataArray[3]);
		userData.setZip(dataArray[4]);
		userData.setCity(dataArray[5]);
		userData.setCountry(dataArray[6]);
		userData.setSocialSecurityNumber(Integer.valueOf(dataArray[7]));
		userData.setCategory(Integer.valueOf(dataArray[8]));
		
		return userData;
	}
}
