package com.moyv.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.moyv.music.service.AdminService;
import com.moyv.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController /*返回json数据*/
public class AdminController {
    @Autowired
    private AdminService adminService;

    /*判断登录是否成功*/
    @RequestMapping(value = "/admin/login",method = RequestMethod.POST)
    public Object loginStatus(HttpServletRequest request, HttpSession session){
        /*处理json*/
        JSONObject jsonObject = new JSONObject();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        boolean flag = adminService.verifyPassword(name,password);
        if(flag){
            /*添加*/
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.NSG,"登录成功");
            /*会将指定的对象与给定名称关联，并存储在会话中*/
            session.setAttribute(Consts.NAME,name);
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.NSG,"用户名或密码错误");
        return jsonObject;

    }


}
