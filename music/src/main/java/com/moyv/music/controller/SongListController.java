package com.moyv.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.moyv.music.domain.SongList;
import com.moyv.music.service.SongListService;

import com.moyv.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


/*歌单控制类*/
@RestController
@RequestMapping("/songList")
public class SongListController {
    @Autowired
    private SongListService songListService;

    /*添加歌单*/
    @PostMapping("/add")
    public Object addSongList(HttpServletRequest request)  {
        /*处理json*/
        JSONObject jsonObject = new JSONObject();
        /*trim(修剪);去掉前后空白字符*/
        String title = request.getParameter("title").trim();   //标题
        String pic = request.getParameter("pic").trim();
        String introduction = request.getParameter("introduction").trim();  //简介
        String style = request.getParameter("style").trim();
        /*保存到歌单的对象中*/
        SongList songList = new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        boolean flag = songListService.insert(songList);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.NSG,"添加成功");
            return  jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.NSG,"添加失败");
        return jsonObject;
    }

    /*修改歌单*/
    @PostMapping("/update")
    public Object updateSongList(HttpServletRequest request) {
        /*处理json*/
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        /*trim(修剪);去掉前后空白字符*/
        String title = request.getParameter("title").trim();   //标题
        String introduction = request.getParameter("introduction").trim();  //简介
        String style = request.getParameter("style").trim();

        SongList songList = new SongList();
        songList.setId(Integer.parseInt(id)); //Integer.parseInt 转换整数
        songList.setTitle(title);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        songList.setIntroduction(introduction);
        boolean flag = songListService.update(songList);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.NSG,"修改成功");
            return  jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.NSG,"修改失败");
        return jsonObject;
    }

    /*删除歌单*/
    @GetMapping("/delete")
    public Object deleteSongList(HttpServletRequest request) {
        String id = request.getParameter("id").trim();
        if (id == null || id.isEmpty()) {
            System.out.println("id不存在");
            return false;
        }
        SongList songList = songListService.findById(Integer.parseInt(id));
        String filePath = System.getProperty("user.dir") + songList.getPic();
        /*默认头像地址*/
        String defaultPath = System.getProperty("user.dir") + "/img/songListPic/gedan.png";
        if (!filePath.equals(defaultPath)) {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
                boolean flag = songListService.delete(Integer.parseInt(id));
                return flag;
            } else {
                System.out.println("文件不存在");
                return false;
            }
        } else {
            if (filePath.equals(defaultPath)) {
                boolean flag = songListService.delete(Integer.parseInt(id));
                return flag;
            }
            return false;
        }
    }

    /*查询歌单*/
    @PostMapping("/findById")
    public Object findByIdSongList(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return  songListService.findById(Integer.parseInt(id));
    }

    /*查询所有歌单*/
    @PostMapping("/allSongList")
    public Object allSongList(){
        return songListService.allSongList();
    }

    /*精确查询(根据标题)*/
    @PostMapping("/songListOfTitle")
    public Object songListOfTitle(HttpServletRequest request){
        String title = request.getParameter("title").trim();
        return songListService.songListOfTitle(title);
    }

    /*模糊查询(根据标题)*/
    @GetMapping("/likeTitle")
    public Object likeTitle(HttpServletRequest request){
        String title = request.getParameter("title").trim();
        return songListService.likeTitle("%"+title+"%");
    }

    /*模糊查询(根据风格)*/
    @PostMapping("/likeStyle")
    public Object likeStyle(HttpServletRequest request){
        String style = request.getParameter("style").trim();
        return songListService.likeStyle("%"+style+"%");
    }

    /*更新歌单图片*/
    @PostMapping("/updateSongListPic")
    /*MultipartFile简化文件流操作的接口*/
    public Object updateSongListPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id) {
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
                +System.getProperty("file.separator")+"songListPic";
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir(); //创建目录
        }
        /*实际的文件地址*/
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        /*存储到数据库里的相对文件地址*/
        String storeAvatorPath = "/img/songListPic/"+fileName;
        try {
            avatorFile.transferTo(dest);  //transferTo（转移）上传文件
            SongList songList = new SongList();
            songList.setId(id);
            songList.setPic(storeAvatorPath);
            boolean flag = songListService.update(songList);
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
