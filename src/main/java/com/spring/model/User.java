package com.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private long userId;
	
	@Column(name = "username")
    private String username;  
	
	
	@Column(name = "password")
    private String password;   

	@Column(name = "email",unique=true)
    private String email;
    
	
	@Column(name ="enabled")
	private int enabled;
	
	@Column(name ="usertype")
	private String usertype;
	
	@Column(name ="profilepicture")
	private String profilepicture;
	
	@Column(name ="designation")
	private String designation;
	
	@Column(name ="company")
	private String company;
	
	@Column(name ="phone")
	private String phone;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	@JsonProperty(value = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getProfilepicture() {
		return profilepicture;
	}

	public void setProfilepicture(String profilepicture) {
		this.profilepicture = profilepicture;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", enabled=" + enabled + ", usertype=" + usertype + ", profilepicture=" + profilepicture
				+ ", designation=" + designation + ", company=" + company + ", phone=" + phone + "]";
	}
	
	
	

}
