package com.moyv.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.moyv.music.domain.Singer;
import com.moyv.music.service.SingerService;
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
import java.util.Date;

@RestController
@RequestMapping("/singer")
public class SingerController {
    @Autowired
    private SingerService singerService;

    /*添加歌手*/
    @PostMapping("/add")
    public Object addSinger(HttpServletRequest request) throws ParseException {
        /*处理json*/
        JSONObject jsonObject = new JSONObject();
        /*trim(修剪);去掉前后空白字符*/
        String name = request.getParameter("name").trim();
        String sex = request.getParameter("sex").trim();
        String pic = request.getParameter("pic").trim();
        String birth = request.getParameter("birth").trim();
        String location = request.getParameter("location").trim();
        String introduction = request.getParameter("introduction").trim();
        /*DateFormat(日期格式)*/
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        /*parse 方法的异常处理：parse 方法会抛出 ParseException，如果输入的字符串不符合指定的格式，需要处理这个异常*/
        birthDate= dateFormat.parse(birth);
        Singer singer = new Singer();
        singer.setName(name);
        singer.setSex(new Byte(sex)); //强转类型
        singer.setPic(pic);
        singer.setBirth(birthDate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        boolean flag = singerService.insert(singer);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.NSG,"添加成功");
            return  jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.NSG,"添加失败");
        return jsonObject;
    }

    /*修改歌手*/
    @PostMapping("/update")
    public Object updateSinger(HttpServletRequest request) throws ParseException {
        /*处理json*/
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        /*trim(修剪);去掉前后空白字符*/
        String name = request.getParameter("name").trim();
        String sex = request.getParameter("sex").trim();
        String birth = request.getParameter("birth").trim();
        String location = request.getParameter("location").trim();
        String introduction = request.getParameter("introduction").trim();
        /*DateFormat(日期格式)*/
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        /*parse 方法的异常处理：parse 方法会抛出 ParseException，如果输入的字符串不符合指定的格式，需要处理这个异常*/
        birthDate= dateFormat.parse(birth);
        Singer singer = new Singer();
        singer.setId(Integer.parseInt(id)); //Integer.parseInt 转换整数
        singer.setName(name);
        singer.setSex(new Byte(sex)); //强转类型
        singer.setBirth(birthDate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        boolean flag = singerService.update(singer);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.NSG,"修改成功");
            return  jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.NSG,"修改失败");
        return jsonObject;
    }

    /*删除歌手*/
    @GetMapping("/delete")
    public Object deleteSinger(HttpServletRequest request) {
        String id = request.getParameter("id").trim();
        if (id == null || id.isEmpty()) {
            System.out.println("id不存在");
            return false;
        }
        Singer singerlist = singerService.findById(Integer.parseInt(id));
        String filePath = System.getProperty("user.dir") + singerlist.getPic();
        /*默认头像地址*/
        String defaultPath = System.getProperty("user.dir") + "/img/singerPic/touxiang.png";
        if (!filePath.equals(defaultPath)) {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
                boolean flag = singerService.delete(Integer.parseInt(id));
                return flag;
            } else {
                System.out.println("文件不存在");
                return false;
            }
        } else {
            if (filePath.equals(defaultPath)) {
                boolean flag = singerService.delete(Integer.parseInt(id));
                return flag;
            }
            return false;
        }

    }

    /*查询歌手*/
    @PostMapping("/findById")
    public Object findByIdSinger(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return  singerService.findById(Integer.parseInt(id));
    }

    /*根据歌手名精确查找*/
    @GetMapping("/findByName")
    public Object findBySingerName(HttpServletRequest request){
        String singerName = request.getParameter("singerName").trim();
//        System.out.println(singerName);
//        System.out.println(singerService.findByName(singerName));
        return  singerService.findByName(singerName);
    }

    /*查询所有歌手*/
    @PostMapping("/allSinger")
    public Object allSinger(){
        return singerService.allSinger();
    }

    /*模糊查询*/
    @PostMapping("/singerOfName")
    public Object singerOfName(HttpServletRequest request){
        String name = request.getParameter("name").trim();
        return singerService.singerOfName("%"+name+"%");
    }

    /*根据性别查询*/
    @GetMapping("/singerOfSex")
    public Object singerOfSex(HttpServletRequest request){
        String sex = request.getParameter("sex").trim();
        return singerService.singerOfSex(Integer.parseInt(sex));
    }

    /*更新歌手图片*/
    @PostMapping("/updateSingerPic")
    /*MultipartFile简化文件流操作的接口*/
    public Object updateSingerPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id) {
        JSONObject jsonObject = new JSONObject();
        /*isEmpty 空*/
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"文件上传失败");
            return jsonObject;
        }
        /*避免上传一个文件名 覆盖掉*/
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"singerPic";
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir(); //创建目录
        }
        /*实际的文件地址*/
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        /*存储到数据库里的相对文件地址*/
        String storeAvatorPath = "/img/singerPic/"+fileName;
        try {
            avatorFile.transferTo(dest);  //transferTo（转移）上传文件
            Singer singer = new Singer();
            singer.setId(id);
            singer.setPic(storeAvatorPath);
            boolean flag = singerService.update(singer);
            if (flag){
                /*添加到 JSON 对象中*/
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.NSG,"上传头像成功");
                jsonObject.put("pic",storeAvatorPath);
                return  jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"上传头像失败");
            return jsonObject;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
