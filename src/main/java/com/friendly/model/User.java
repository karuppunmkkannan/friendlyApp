/**
 * 
 */
package com.friendly.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Karuppusamy
 * @version 1.0
 *
 */

@Entity // This tells Hibernate to make a table out of this class
public class User implements Serializable {

	

	@Override
	public String toString() {
		return "User [userName=" + userName + ", mobileNumber=" + mobileNumber + ", userImg=" + userImg
				+ ", emailVerified=" + emailVerified + ", userStatus=" + userStatus + ", mobileVerified="
				+ mobileVerified + ", otp=" + otp + ", email=" + email + ", token=" + token + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1425383660285233845L;

	@Column(name="userName")
	@JsonProperty(value = "userName")
	private String userName;

	@Id
	@Column(name="mobileNumber")
	@JsonProperty(value = "mobileNumber")
	private String mobileNumber;
	
	
	@Column(name="userImg")
	@JsonProperty(value = "userImg")
	private String userImg;
	
	@Column(name="emailVerified")
	@JsonProperty(value = "emailVerified")
	private String emailVerified;
	
	@Column(name="userStatus")
	@JsonProperty(value = "userStatus")
	private String userStatus;
	
	@Column(name="mobileVerified")
	@JsonProperty(value = "mobileVerified")
	private String mobileVerified;

	@Column(name="otp")
	@JsonProperty(value = "otp")
	private String otp;
	
	@Column(name="email")
	@JsonProperty(value = "email")
	private String email;

	@Column(name="token")
	@JsonProperty(value = "token")
	private String token;
	
	@Column(name="location")
	@JsonProperty(value = "location")
	private String location;
	
	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	
	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getStatus() {
		return userStatus;
	}

	public void setStatus(String status) {
		this.userStatus = status;
	}

	public String getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(String emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getMobileVerified() {
		return mobileVerified;
	}

	public void setMobileVerified(String mobileVerified) {
		this.mobileVerified = mobileVerified;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}