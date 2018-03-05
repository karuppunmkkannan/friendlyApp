/**
 * 
 */
package com.friendly.utility;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import com.friendly.model.User;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;

/**
 * @author Karuppusamy
 * @version 1.0
 *
 */

public abstract class Utility {

	private static Random random = new Random();

	public static final String MSG_API_KEY = "200682AZpe5sPa5a996fe2";

	public static final String MSG_CONTENT = "FriendlyApp ";

	public static String mainUrl = "http://control.msg91.com/api/sendotp.php?";

	public static long getOtp() {
		return random.nextInt(9999)+1000;
	}

	public static User sendOtp(User user) throws UnirestException {
		user.setOtp(getOtp());
		StringBuilder sbPostData = new StringBuilder(mainUrl);
		sbPostData.append("authkey=" + Utility.MSG_API_KEY);
		sbPostData.append("&mobile=" + user.getMobileNumber());

		/*
		 * if (user.getStatus().equalsIgnoreCase("direct")) {
		 * sbPostData.append("&email=" + user.getEmail()); }
		 */

		sbPostData.append("&otp=" + user.getOtp());

		JSONObject jsonObject = Unirest.post(sbPostData.toString()).asJson().getBody().getArray().getJSONObject(0);

		System.out.println(jsonObject.toString());

		if (jsonObject.getString("type").equalsIgnoreCase("success")) {
			user.setMobileVerified("pending");

		} else {

			user.setMobileVerified("failure");
		}

		if (user.getStatus().equalsIgnoreCase("direct")) {
			user.setEmailVerified("pending");
		} else {
			user.setEmailVerified("success");
		}

		return user;
	}

}
