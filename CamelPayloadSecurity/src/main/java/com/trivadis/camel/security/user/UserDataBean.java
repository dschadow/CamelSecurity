package com.trivadis.camel.security.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * Simple userdata retrieval bean.
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
public class UserDataBean {
	private SimpleJdbcTemplate simpleJdbcTemplate;

	public UserData loadUserData(final int socialSecurityNumber) {
		String sql = "select company, first_name, last_name, street, zip, city, country "
				+ "from userdata where social_security_number = ?";

		RowMapper<UserData> mapper = new RowMapper<UserData>() {
			public UserData mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UserData userData = new UserData();
				userData.setCompany(rs.getString("company"));
				userData.setFirstName(rs.getString("first_name"));
				userData.setLastName(rs.getString("last_name"));
				userData.setStreet(rs.getString("street"));
				userData.setZip(rs.getString("zip"));
				userData.setCity(rs.getString("city"));
				userData.setCountry(rs.getString("country"));
				userData.setSocialSecurityNumber(socialSecurityNumber);

				return userData;
			}
		};

		return simpleJdbcTemplate.queryForObject(sql, mapper,
				socialSecurityNumber);
	}

	public void setDataSource(DataSource dataSource) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}
}
