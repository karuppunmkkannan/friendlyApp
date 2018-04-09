/**
 * 
 */
package com.friendly.utility;

import java.util.Random;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.friendly.model.User;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author Karuppusamy
 * @version 1.0
 *
 */

public abstract class Utility {

	private static final Logger logger = Logger.getLogger(Utility.class);

	private static Random random = new Random();

	public static final String MSG_API_KEY = "201519AI2NNrrUPjMf5aa0c3f8";

	public static final String deviceTokenForTest = "dt11jkPxtU0:APA91bHVtfGNpvLkhu_H5Jq2TzdozLdmzDpDs_8TEZ8-m4Cj_9Os0qFYE4hNV9U-vpYHij2uWG0rpgOvRjCF-DLDUEvfkZziMrUNighlaYzOTDXJIp9QT-7hRJDW7Piv-gAhQjOl-7l_";

	public static String mainUrl = "http://control.msg91.com/api/sendotp.php?";

	public static final String fcmApiKey = "AAAAEMi5s9g:APA91bEftQPIannOEujFvlL42jI-qK9OBALs0EL28m8v0Jp4boa_ZunjcTkAoTAwlChcd9mh5vqFNXqBIVMha7U3UXuXyOPydQK0yzN5GaJpfK1VnLpKxSt3j9IMM5Xbfi2MLJ0O6mB5";

	public static final String fcmServerUrl = "https://fcm.googleapis.com/fcm/send";

	public static RestTemplate restTemplate = new RestTemplate();

	public static HttpHeaders httpHeaders = new HttpHeaders();

	public static long getOtp() {
		return random.nextInt(9999) + 1000;
	}

	public static User sendOtp(User user) throws UnirestException {
		user.setOtp(String.valueOf(getOtp()));
		StringBuilder sbPostData = new StringBuilder(mainUrl);
		sbPostData.append("authkey=" + Utility.MSG_API_KEY);
		sbPostData.append("&mobile=" + user.getMobileNumber());

		/*
		 * if (user.getStatus().equalsIgnoreCase("direct")) {
		 * sbPostData.append("&email=" + user.getEmail()); }
		 */

		sbPostData.append("&otp=" + user.getOtp());

		JSONObject jsonObject = Unirest.post(sbPostData.toString()).asJson().getBody().getArray().getJSONObject(0);

		logger.info("Normal Message : " + jsonObject.toString());

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

	public static boolean sendFcmMessage(User user) {
		try {

			httpHeaders.set("Authorization", "key=" + fcmApiKey);
			httpHeaders.set("Content-Type", "application/json");
			JSONObject body = new JSONObject();

			JSONObject notification = new JSONObject();
			notification.put("title", "FriendlyApp");
			notification.put("body", "Hi " + user.getUserName() + " Welcome to FriendlyApp");

			JSONObject data = new JSONObject();
			data.put("msg", "Welcome");

			body.put("priority", "high");
			body.put("notification", notification);
			body.put("data", data);
			body.put("to", user.getToken());

			HttpEntity<String> httpEntity = new HttpEntity<String>(body.toString(), httpHeaders);
			String response = restTemplate.postForObject(fcmServerUrl, httpEntity, String.class);
			
			logger.info("Fcm Message : " + response);

		} catch (Exception e) {
			logger.error("Exception in sendFcmMessage", e);
		}

		return true;
	}

}
