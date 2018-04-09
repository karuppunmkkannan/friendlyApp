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

	@JsonProperty(value = "msg")
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Msg [msg=" + msg + "]";
	}

}