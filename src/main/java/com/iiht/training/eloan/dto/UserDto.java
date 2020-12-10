package com.iiht.training.eloan.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.Length;

public class UserDto {
	
	private Long id;
	@NotNull(message="FirstName is a required field")
	@Length(min=3, max=100, message="FirstName should be min 3 and max 100 characters")
	private String firstName;
	@NotNull(message="LastName is a required field")
	@Length(min=3, max=100, message="LastName should be min 3 and max 100 characters")
	private String lastName;
	@Email
	@NotNull(message="Email is a required field")
	@Length(min=3, max=100, message="Email should be min 3 and max 100 characters")
	private String email;
	@NotNull(message="Mobile is a required field")
	@Length(min=3, max=100, message="Mobile should be min 10 and max 10 characters")
	private String mobile;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
