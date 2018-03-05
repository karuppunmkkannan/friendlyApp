package com.friendly.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.friendly.model.User;
import com.friendly.service.UserService;
import com.friendly.utility.Utility;

/**
 * 
 */

/**
 * @author karuppusamy
 * @version 1.0
 *
 */

@RestController
@SessionAttributes("user")
public class MainController {

	private static final Logger logger = Logger.getLogger(MainController.class);

	@Autowired
	private UserService userservice;

	@RequestMapping("/")
	String home() {
		logger.info("Friendly application started...");
		return "Friendly application started...";
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ModelMap login(@RequestBody User user, HttpSession httpSession) {
		ModelMap modelMap = new ModelMap();
		try {
			User ouser = userservice.findUserOne(user);
			if (ouser != null && ouser.getEmail().equalsIgnoreCase(user.getEmail())) {
				if (ouser.getMobileVerified().equalsIgnoreCase("success")) {
					modelMap.addAttribute("status", "success");
					modelMap.addAttribute("message", "User already exists");
					modelMap.addAttribute("data", ouser);
				} else {
					modelMap.addAttribute("status", "OK");
					modelMap.addAttribute("message", "User Mobile verification in pending...");
					modelMap.addAttribute("data", ouser);
				}
			} else {
				modelMap.addAttribute("status", "OK");
				modelMap.addAttribute("message", "Found New user! OTP send to your number...");
				modelMap.addAttribute("data", userservice.insertUserService(Utility.sendOtp(user)));
			}
			logger.info("Values : " + user.toString());
		} catch (Exception e) {
			logger.error("Exception in login", e);
		}
		return modelMap;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/loginVerified", method = RequestMethod.POST)
	public @ResponseBody ModelMap loginVerified(@RequestBody User user, HttpSession httpSession) {
		ModelMap modelMap = new ModelMap();
		try {
			User ouser = userservice.findUserOne(user);
			if (ouser != null && ouser.getEmail().equalsIgnoreCase(user.getEmail())
					&& (ouser.getMobileVerified().equalsIgnoreCase("success") || ouser.getOtp() == user.getOtp())) {
				modelMap.addAttribute("status", "success");
				modelMap.addAttribute("message", "Suceesfully user logged..");
				ouser.setMobileVerified("success");
				modelMap.addAttribute("data", userservice.insertUserService(ouser));
			} else {
				modelMap.addAttribute("status", "OK");
				modelMap.addAttribute("message", "Wrong OTP, please enter correct otp.we send otp now..");
				modelMap.addAttribute("data", userservice.insertUserService(Utility.sendOtp(user)));
			}
			logger.info("Values : " + user.toString());
		} catch (Exception e) {
			logger.error("Exception in login", e);
		}
		return modelMap;
	}

}
