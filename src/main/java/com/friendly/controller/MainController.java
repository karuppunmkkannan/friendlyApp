package com.friendly.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

@Controller
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

	@RequestMapping("/index")
	String index() {
		logger.info("index called");
		return "index";
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/postParamsjsonObj", method = { RequestMethod.POST }, produces = "application/json")
	public @ResponseBody ModelMap postParamsjsonObj(@RequestBody User user, HttpServletResponse response,
			HttpServletRequest servletRequest) {
		ModelMap modelMap = new ModelMap();
		try {
			logger.info("test postParamsjsonObj ");
			modelMap.addAttribute("user", user);
		} catch (Exception e) {
			logger.error("Exception in postParamsjsonObj", e);
		}
		return modelMap;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/postParamsjson", method = { RequestMethod.POST }, produces = "application/json")
	public @ResponseBody ModelMap postParamsjson(@RequestParam(value = "param1integerid", required = true) String id,
			@RequestParam(value = "param2stringname", required = true) String name, HttpServletResponse response,
			HttpServletRequest servletRequest) {
		ModelMap modelMap = new ModelMap();
		try {
			logger.info("test postParamsjson ");
			modelMap.addAttribute("param1integerid", id);
			modelMap.addAttribute("param2stringname", name);
		} catch (Exception e) {
			logger.error("Exception in postParamsjson", e);
		}
		return modelMap;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/postEmptyjson", method = { RequestMethod.POST }, produces = "application/json")
	public @ResponseBody ModelMap postEmptyjson(HttpServletResponse response, HttpServletRequest servletRequest) {
		ModelMap modelMap = new ModelMap();
		try {
			logger.info("test getEmptyjson ");
			modelMap.addAttribute("data", "this service is empty post with content type is application/json ");
		} catch (Exception e) {
			logger.error("Exception in getEmptyjson", e);
		}
		return modelMap;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getParamsjson", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ModelMap getParamsjson(@RequestParam(value = "param1integerid", required = true) Long id,
			@RequestParam(value = "param2stringname", required = true) String name, HttpServletResponse response,
			HttpServletRequest servletRequest) {
		ModelMap modelMap = new ModelMap();
		try {
			logger.info("test getParamsjson ");
			modelMap.addAttribute("param1integerid", id);
			modelMap.addAttribute("param2stringname", name);
		} catch (Exception e) {
			logger.error("Exception in getParamsjson", e);
		}
		return modelMap;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getEmptyjson", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ModelMap getEmptyjson(HttpServletResponse response, HttpServletRequest servletRequest) {
		ModelMap modelMap = new ModelMap();
		try {
			logger.info("test getEmptyjson ");
			modelMap.addAttribute("data", "this service is empty get with content type is application/json ");
		} catch (Exception e) {
			logger.error("Exception in getEmptyjson", e);
		}
		return modelMap;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getParams", method = RequestMethod.GET, produces = "text/plain")
	public @ResponseBody String getParams(@RequestParam(value = "param1integerid", required = true) Long id,
			@RequestParam(value = "param2stringname", required = true) String name, HttpServletResponse response,
			HttpServletRequest servletRequest) {
		try {
			logger.info("test getParams ");
		} catch (Exception e) {
			logger.error("Exception in getEmpty", e);
		}
		return "this service is getParams get with content type is text/plain params : param1integerid = " + id
				+ " param2stringname = " + name;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getEmpty", method = RequestMethod.GET, produces = "text/plain")
	public @ResponseBody String getEmpty(HttpServletResponse response, HttpServletRequest servletRequest) {
		try {
			logger.info("test getEmpty ");
		} catch (Exception e) {
			logger.error("Exception in getEmpty", e);
		}
		return "this service is empty get with content type is text/plain ";
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

	@CrossOrigin(origins = "*")
	@MessageMapping("/sendMsg")
	public void processMessageFromClient(Msg message) {
		logger.info("Socket connected" + message);
	}

	@CrossOrigin(origins = "*")
	@MessageMapping("/sendFcmMsg")
	public void sendFcmMsg(Msg msg) {
		logger.info("Socket connected" + msg.toString());
		Utility.sendFcmMessage(msg);
	}

	@MessageExceptionHandler
	@SendToUser("/errors")
	public String handleException(Throwable exception) {
		return exception.getMessage();
	}

}
