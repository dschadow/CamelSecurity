package com.trivadis.camel.security.util;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.bouncycastle.util.encoders.Base64;

/**
 * XML utility class.
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
public class XMLUtil {
	public String convertToXml(Object source) {
		StringWriter sw = new StringWriter();
		
		try {
			JAXBContext context = JAXBContext.newInstance(source.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
			marshaller.marshal(source, sw);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}

		return sw.toString();
	}
	
	public String convertToBase64(byte[] data) {
		return new String(Base64.encode(data));
	}
	
	public byte[] convertToByte(String data) {
		return Base64.decode(data);
	}

	public String convertToHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);

        for (int i = 0; i < data.length; i++) {
            if (((int) data[i] & 0xff) < 0x10)
                sb.append("0");

            sb.append(Long.toString((int) data[i] & 0xff, 16));
        }

        return sb.toString();
    }
}
