package com.friendly.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6689728237986855901L;

	private ArrayList<String> userLists = new ArrayList<>();

	public ArrayList<String> getUserLists() {
		return userLists;
	}

	public void setUserLists(ArrayList<String> userLists) {
		this.userLists = userLists;
	}

	@Override
	public String toString() {
		return "ResponseObject [userLists=" + userLists + "]";
	}

}
