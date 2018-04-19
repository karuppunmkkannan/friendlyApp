
package com.friendly.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.friendly.model.ResponseObject;
import com.friendly.model.User;
import com.friendly.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;

/**
 * @author Altrocks
 * @version 1.0
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@Component
public class DummyTest {

	public static Random random = new Random();

	public static Gson gson = new Gson();

	private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

	private static Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

	class MySort implements Comparator<Integer> {
		public int compare(Integer x, Integer y) {
			return y.compareTo(x);
		}
	}

	@Test
	public void testWebSockets() {

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	@Ignore
	public void testsockets() {

		try {
			System.out.println("nmk");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	@Ignore
	public void testEmail() {
		try {

			String mail = "nmk@gmail.com";

			String mobile = "954349445";

			Matcher matcher = pattern.matcher(mail);

			if (matcher.matches()) {
				System.out.println("Email");
			} else {
				System.out.println("Not an enail");
			}

			if (mobile.matches("[0-9]+") && mobile.length() == 10) {
				System.out.println("mobile");
			} else {
				System.out.println("Not an mobile");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	@Ignore
	public void testsnumberString() {

		ArrayList<String> m = new ArrayList<>();
		m.add("9543494451");

		ResponseObject object = new ResponseObject();

		object.setUserLists(m);

		System.out.println(gson.toJson(object));

		System.out.println(gson.toJson(m, new TypeToken<ArrayList<String>>() {
		}.getType()));

		String num = "+91 954 3494 451";

		num = num.replaceAll("\\s+", "");

		if (num.startsWith("+")) {
			num = num.substring(3);
		}

		System.out.println(num);

		if (num.matches("[0-9]+") && num.length() == 10) {
			System.out.println(num);
		}
	}

	@Test
	@Ignore
	public void tests() {

	}

	@Test
	@Ignore
	public void testJob() {
		try {

			User user = new User();

			user.setEmail("nmk@gmail.com");
			user.setMobileNumber("9543494451");
			user.setOtp(String.valueOf(random.nextInt(9999)));
			user.setUserName("Nmkkannan");

			String mainUrl = "http://control.msg91.com/api/sendotp.php?";

			StringBuilder sbPostData = new StringBuilder(mainUrl);
			sbPostData.append("authkey=" + Utility.MSG_API_KEY);
			sbPostData.append("&mobile=" + user.getMobileNumber());
			// sbPostData.append("&message=" + "FriendlyApp Your otp is " +
			// user.getOtp());
			// sbPostData.append("&email=" + user.getEmail());
			sbPostData.append("&otp=" + user.getOtp());

			System.out.println(sbPostData.toString());

			HttpRequestWithBody response = Unirest.post(sbPostData.toString());

			JSONObject jsonObject = response.asJson().getBody().getArray().getJSONObject(0);

			if (jsonObject.getString("type").equalsIgnoreCase("success")) {
				System.out.println("success");
			} else {
				System.out.println("false");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
