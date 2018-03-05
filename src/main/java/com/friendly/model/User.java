/**
 * 
 */
package com.friendly.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Karuppusamy
 * @version 1.0
 *
 */

@Entity // This tells Hibernate to make a table out of this class
public class User implements Serializable {

	@Override
	public String toString() {
		return "User [userName=" + userName + ", mobileNumber=" + mobileNumber + ", emailVerified=" + emailVerified
				+ ", mobileVerified=" + mobileVerified + ", otp=" + otp + ", email=" + email + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1425383660285233845L;

	private String userName;

	private String mobileNumber;

	private String emailVerified;
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	private String mobileVerified;

	private long otp;

	public long getOtp() {
		return otp;
	}

	public void setOtp(long otp) {
		this.otp = otp;
	}

	@Id
	private String email;

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