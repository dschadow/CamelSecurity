package com.trivadis.camel.security.user;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Userdata.
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
@XmlRootElement
public class UserData implements Serializable {
	private static final long serialVersionUID = -1752421601600456937L;
	
	private String company;
	private String firstName;
	private String lastName;
	private String street;
	private String zip;
	private String city;
	private String country;
	private int socialSecurityNumber;
	private int category;

	@XmlElement
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@XmlElement
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlElement
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@XmlElement
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@XmlElement
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@XmlElement
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@XmlElement
	public int getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(int socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	@XmlElement
	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	@Override
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append(company);
		data.append(", ");
		data.append(firstName);
		data.append(", ");
		data.append(lastName);
		data.append(", ");
		data.append(street);
		data.append(", ");
		data.append(zip);
		data.append(", ");
		data.append(city);
		data.append(", ");
		data.append(country);
		data.append(", ");
		data.append(socialSecurityNumber);
		data.append(", ");
		data.append(category);

		return data.toString();
	}
}
