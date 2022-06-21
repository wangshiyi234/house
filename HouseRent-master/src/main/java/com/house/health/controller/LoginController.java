package com.house.health.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.house.health.entity.Users;
import com.house.health.service.IUserService;
import com.house.health.sms.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;


@Controller
public class LoginController {
	
	@Autowired
	private IUserService mapper;
	
	@RequestMapping("/login")
	@ResponseBody
	public String toCustomerPage(String username,String password,String mycode,HttpServletRequest req,HttpSession session) {
		Users user = new Users();
		String code =null;
		user.setuName(username);
		user.setuPassword(password);
		Users loginUser = mapper.login(user);
		 code = (String) session.getAttribute("kaptcha");
		 if(loginUser==null&& Objects.equals(mycode, code)){
			 return "FAIL1";
		 }
		 else if (loginUser!=null&& !Objects.equals(mycode, code)){
			 return "FAIL2";
		 }

		if(loginUser!=null&& Objects.equals(mycode, code)) {
			req.getSession().setAttribute("loginUser", loginUser);
			return "OK";
		}
		return "FAIL";
	}
	
	@RequestMapping("/signout")
	public String signout(HttpSession session) {
		session.invalidate();
		return "redirect:toIndexPage";
	}
	
	@RequestMapping("/regist")
	@ResponseBody
	public String regist(Users user) {
		int regist;
		try {
			regist = mapper.regist(user);
			if(regist>0) {
				return "OK";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "FAIL";
	}
	@RequestMapping("/getcode")
	@ResponseBody
	public void getCode(Users user){
		System.out.println(user);
		Client client = new Client();
		client.setAppId("hw_10902");     //开发者ID，在【设置】-【开发设置】中获取
		client.setSecretKey("bf80b2a52abb94a4fbeb7c61015bf713");    //开发者密钥，在【设置】-【开发设置】中获取
		client.setVersion("1.0");

		/**
		 *   json格式可在 bejson.com 进行校验
		 */
		String singnstr = "闪速码";

		Client.Request request = new Client.Request();
		request.setMethod("sms.message.send");
		request.setBizContent("{\"mobile\":[\""+user.getuPhoneNumber()+"\"],\"type\":0,\"template_id\":\"ST_2020101100000003\",\"sign\":\"" + singnstr +"\",\"send_time\":\"\",\"params\":{\"code\":5637}}");  // 这里是json字符串，send_time 为空时可以为null, params 为空时可以为null,短信签名填写审核后的签名本身，不需要填写签名id
		System.out.println( client.execute(request) );
	}
	
}
