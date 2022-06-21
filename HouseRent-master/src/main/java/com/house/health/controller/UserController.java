package com.house.health.controller;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.kaptcha.Producer;
import com.house.health.entity.House;
import com.house.health.entity.Users;
import com.house.health.service.IHouserService;
import com.house.health.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.awt.image.BufferedImage;
import java.io.IOException;


@Controller
public class UserController {
	
	@Autowired
	private IUserService service;
	@Autowired
	private IHouserService dao;
	
	@RequestMapping("/toUserSystem")
	public String toUserSystemPage() {
		return "customer";
	}
	
	@RequestMapping("/toUserRentalPage")
	public String toUserRentalPage() {
		return "myrental";
	}
	
	@RequestMapping("/welcome")
	public String toWelcomePage() {
		return "welcome";
	}
	
	@RequestMapping("/toUpdateHousePage")
	public String toUpdatePage(int hID,HttpServletRequest request) {
		House house = dao.findHouseDetailsById(hID);
		request.getSession().setAttribute("House", house);
		return "updatehouse";
	}
	
	@RequestMapping("/updateUserPwd")
	@ResponseBody
	public String updateUserPwd(String id,String newPwd,String oldPwd) {
		Users oldUser = new Users();
		oldUser.setuID(Integer.parseInt(id));
		oldUser.setuPassword(oldPwd);
		Users checkUser = service.checkOldPwd(oldUser);
		if(checkUser!=null) {
			Users newUser = new Users();
			newUser.setuID(Integer.parseInt(id));
			newUser.setuPassword(newPwd);
			int n = service.updateUserPwd(newUser);
			if(n>0) {
				return "OK";
			}
		}
		return "FAIL";
	}
	@Autowired
	private Producer kaptchaProducer;

	@RequestMapping( "/kaptcha")
	@ResponseBody
	public String getKaptcha(HttpServletResponse response, HttpSession session){
		//    生成验证码
		String text= kaptchaProducer.createText();
		//    生成验证码图片
		BufferedImage image = kaptchaProducer.createImage(text);
		//    将图片传入session
		session.setAttribute("kaptcha", text);
		System.out.println(text);
		//    将图片输出到前端(图片+格式)
		response.setContentType("image/png");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			ImageIO.write(image, "png", outputStream);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("响应验证码失败");
		}
		return text;
	}
}
