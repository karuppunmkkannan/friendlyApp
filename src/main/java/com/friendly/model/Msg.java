/**
 * 
 */
package com.friendly.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Karuppusamy
 * @version 1.0
 *
 */

public class Msg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -356938422929553813L;
	/**
	 * 
	 */

	@JsonProperty(value = "fromUser")
	private User fromUser;

	@JsonProperty(value = "toUser")
	private User toUser;

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	@Override
	public String toString() {
		return "Msg [fromUser=" + fromUser + ", toUser=" + toUser + ", msg=" + msg + "]";
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	@JsonProperty(value = "msg")
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}