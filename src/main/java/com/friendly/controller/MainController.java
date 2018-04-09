package com.friendly.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.friendly.model.Msg;
import com.friendly.model.ResponseObject;
import com.friendly.model.User;
import com.friendly.service.UserService;
import com.friendly.utility.Utility;
import com.google.gson.Gson;

/**
 * 
 */

/**
 * @author karuppusamy
 * @version 1.0
 *
 */

@RestController
@CrossOrigin(origins = "*")
@SessionAttributes("user")
@RequestMapping("/")
public class MainController {

	private static final Logger logger = Logger.getLogger(MainController.class);

	@Autowired
	private UserService userservice;

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	private static Gson gson = new Gson();

	@RequestMapping("/")
	String home() {
		logger.info("Friendly application started...");
		messagingTemplate.convertAndSend("/chat/msg", "Welcome...");
		return "Friendly application started...";
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ModelMap login(@RequestBody User user, HttpSession httpSession) {
		ModelMap modelMap = new ModelMap();
		try {
			logger.info("user " + user.toString());
			User ouser = userservice.findUserOne(user);
			if (ouser != null && ouser.getMobileNumber().equalsIgnoreCase(user.getMobileNumber())) {
				if (ouser.getMobileVerified().equalsIgnoreCase("success")) {
					modelMap.addAttribute("status", "success");
					modelMap.addAttribute("message", "User already exists");
					modelMap.addAttribute("data", ouser);
					Utility.sendFcmMessage(ouser);
				} else {
					modelMap.addAttribute("status", "OK");
					modelMap.addAttribute("message", "User Mobile verification in pending...");
					modelMap.addAttribute("data", userservice.insertUserService(Utility.sendOtp(user)));
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
			logger.info("Values : " + user.toString());
			User ouser = userservice.findUserOne(user);
			if (ouser != null && ouser.getMobileNumber().equalsIgnoreCase(user.getMobileNumber())
					&& (ouser.getMobileVerified().equalsIgnoreCase("success")
							|| ouser.getOtp().equalsIgnoreCase(user.getOtp()))) {
				modelMap.addAttribute("status", "success");
				modelMap.addAttribute("message", "Suceesfully user logged..");
				ouser.setMobileVerified("success");
				ouser.setToken(user.getToken());
				modelMap.addAttribute("data", userservice.insertUserService(ouser));
				Utility.sendFcmMessage(ouser);
			} else {
				modelMap.addAttribute("status", "OK");
				modelMap.addAttribute("message", "Wrong OTP, please enter correct otp.we send otp now..");
				modelMap.addAttribute("data", userservice.insertUserService(Utility.sendOtp(user)));
			}
		} catch (Exception e) {
			logger.error("Exception in login", e);
		}
		return modelMap;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getAllUsersList", method = RequestMethod.POST)
	public @ResponseBody ModelMap getAllUsersList(@RequestBody ResponseObject responseObject, HttpSession httpSession) {
		ModelMap modelMap = new ModelMap();
		try {
			modelMap.addAttribute("status", "OK");
			modelMap.addAttribute("message", "Recived All Users");
			modelMap.addAttribute("data", userservice.getAllUsersList(responseObject.getUserLists()));
		} catch (Exception e) {
			logger.error("Exception in getAllUsersList", e);
		}
		return modelMap;
	}

	@MessageMapping("/sendMsg")
	public String processMessageFromClient(Msg message) {
		logger.info("Socket connected");
		messagingTemplate.convertAndSend("/chat/msg", message.getMsg());
		return message.getMsg();
	}

	@MessageExceptionHandler
	@SendToUser("/errors")
	public String handleException(Throwable exception) {
		return exception.getMessage();
	}

}
