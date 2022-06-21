package com.house.health.controller;

import com.house.health.dao.OrderMapper;
import com.house.health.entity.Users;
import com.house.health.service.IOrderService;
import com.house.health.utils.AliPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @create: 2022-03-08 20:30
 * @author: 郑兴源
 * @description:
 **/
@Controller
public class AliPayController {


    @Autowired
    AliPayUtil aliPayUtil;
    @Autowired
    IOrderService service;

    public void setAliPayUtil(AliPayUtil aliPayUtil) {
        this.aliPayUtil = aliPayUtil;
    }
    //进入支付首页
    @GetMapping("/pay")
    public String index() {
        return "alipay";
    }

    /*创建订单*/
    @RequestMapping("/createDan")
    public String createDan(String id,  String title, Model model, HttpSession session) {
        Users loginUser = (Users) session.getAttribute("loginUser");
        int price = service.payOrder(loginUser.getuID());
        String pay = aliPayUtil.pay(loginUser.getuNickName(), String.valueOf(price), "house");
        model.addAttribute("form", pay);
        return "pay";
    }

    //支付成功返回页面
    @GetMapping("/return")
    public String returns(String out_trade_no, Model model) {
        String s = aliPayUtil.selectPaySE(out_trade_no);
        model.addAttribute("paySe", s);
        return "paySE";
    }

}

