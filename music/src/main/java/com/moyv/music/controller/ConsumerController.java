package com.moyv.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.moyv.music.domain.Consumer;
import com.moyv.music.service.ConsumerService;
import com.moyv.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    /*添加用户*/
    @PostMapping("/add")
    public Object addConsumer(HttpServletRequest request) throws ParseException {
        /*处理json*/
        JSONObject jsonObject = new JSONObject();
        /*trim(修剪);去掉前后空白字符*/
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String sex = request.getParameter("sex").trim();
        String phoneNum = request.getParameter("phoneNum").trim();
        String email = request.getParameter("email").trim();
        String birth = request.getParameter("birth").trim();
        String introduction = request.getParameter("introduction").trim();  //签名
        String location = request.getParameter("location").trim();
        String avator = request.getParameter("avator").trim();              //头像

        /*equals(相等)*/
        if(username == null || username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"用户名不能为空");
            return jsonObject;
        }
        if(password == null || password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"用户名不能为空");
            return jsonObject;
        }

        /*DateFormat(日期格式)*/
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        /*parse 方法的异常处理：parse 方法会抛出 ParseException，如果输入的字符串不符合指定的格式，需要处理这个异常*/
        birthDate= dateFormat.parse(birth);

        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(new Byte(sex)); //强转类型
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birthDate);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        consumer.setAvator(avator);


        boolean flag = consumerService.insert(consumer);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.NSG,"添加成功");
            return  jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.NSG,"添加失败");
        return jsonObject;
    }

    /*修改用户*/
    @PostMapping("/update")
    public Object updateConsumer(HttpServletRequest request) throws ParseException {
        /*处理json*/
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        /*trim(修剪);去掉前后空白字符*/
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String sex = request.getParameter("sex").trim();
        String phoneNum = request.getParameter("phoneNum").trim();
        String email = request.getParameter("email").trim();
        String birth = request.getParameter("birth").trim();
        String introduction = request.getParameter("introduction").trim();  //签名
        String location = request.getParameter("location").trim();

        if(username == null || username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"用户名不能为空");
            return jsonObject;
        }
        if(password == null || password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"用户名不能为空");
            return jsonObject;
        }

        /*DateFormat(日期格式)*/
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        /*parse 方法的异常处理：parse 方法会抛出 ParseException，如果输入的字符串不符合指定的格式，需要处理这个异常*/
        birthDate= dateFormat.parse(birth);
        Consumer consumer = new Consumer();
        consumer.setId(Integer.parseInt(id)); //Integer.parseInt 转换整数
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(new Byte(sex)); //强转类型
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birthDate);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
//        System.out.println(consumer);
        boolean flag = consumerService.update(consumer);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.NSG,"修改成功");
            return  jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.NSG,"修改失败");
        return jsonObject;

    }

    /*删除用户*/
    @GetMapping("/delete")
    public Object deleteConsumer(HttpServletRequest request) {
        String id = request.getParameter("id").trim();
        if (id == null || id.isEmpty()) {
            System.out.println("id不存在");
            return false;
        }
        Consumer consumerlist = consumerService.findById(Integer.parseInt(id));
        String filePath = System.getProperty("user.dir") + consumerlist.getAvator();
        /*默认头像地址*/
        String defaultPath = System.getProperty("user.dir") + "/avatorImages/user.png";
        if (!filePath.equals(defaultPath)) {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
                boolean flag = consumerService.delete(Integer.parseInt(id));
                return flag;
            } else {
                System.out.println("文件不存在");
                return false;
            }
        } else {
            if (filePath.equals(defaultPath)) {
                boolean flag = consumerService.delete(Integer.parseInt(id));
                return flag;
            }
            return false;
        }

    }

    /*查询用户*/
    @GetMapping("/findById")
    public Object findByIdConsumer(HttpServletRequest request){
        String id = request.getParameter("id");
        Object result = consumerService.findById(Integer.parseInt(id));
        return result;
    }

    /*根据账号查询*/
    @GetMapping("/getByUsername")
    public Object getByUsername(HttpServletRequest request){
        String username = request.getParameter("username").trim();
        return  consumerService.getByUsername(username);
    }

    /*查询所有用户*/
    @PostMapping("/allConsumer")
    public Object allConsumer(){
        return consumerService.allConsumer();
    }

    /*更新用户图片*/
    @PostMapping("/updateConsumerPic")
    /*MultipartFile简化文件流操作的接口*/
    public Object updateConsumerPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id) {
        JSONObject jsonObject = new JSONObject();
        /*isEmpty 空*/
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"文件上传失败");
            return jsonObject;
        }
        /*避免上传一个文件名 覆盖掉*/
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"avatorImages";
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir(); //创建目录
        }
        /*实际的文件地址*/
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        /*存储到数据库里的相对文件地址*/
        String storeAvatorPath = "/avatorImages/"+fileName;
        try {
            avatorFile.transferTo(dest);  //transferTo（转移）上传文件
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvator(storeAvatorPath);
            boolean flag = consumerService.update(consumer);
            if (flag){
                /*添加到 JSON 对象中*/
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.NSG,"上传头像成功");
                jsonObject.put("avator",storeAvatorPath);
                return  jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"上传头像失败");
            return jsonObject;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*登录*/
    @PostMapping("/login")
    public Object loginConsumer(HttpServletRequest request) throws ParseException {
        /*处理json*/
        JSONObject jsonObject = new JSONObject();
        /*trim(修剪);去掉前后空白字符*/
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        /*equals(相等)*/
        if(username == null || username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"用户名不能为空");
            return jsonObject;
        }
        if(password == null || password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"用户名不能为空");
            return jsonObject;
        }

        boolean flag = consumerService.verifyPassword(username,password);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.NSG,"登录成功");
            jsonObject.put("userMsg",consumerService.getByUsername(username));
            return  jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.NSG,"用户名或密码错误");
        return jsonObject;
    }

}
