package com.moyv.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.moyv.music.domain.Singer;
import com.moyv.music.domain.Song;
import com.moyv.music.service.SongService;
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
@RequestMapping("/song")
public class SongController {
    @Autowired
    private SongService songService;

    @PostMapping("/add")
    public Object addSong(HttpServletRequest request, @RequestParam("file") MultipartFile mpFile){
        JSONObject jsonObject = new JSONObject();
        /*获取前端的参数*/
        String singerId = request.getParameter("singerId").trim();
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim();
        String pic = "/img/songPic/songicon.png";
        String lyric = request.getParameter("lyric").trim(); //歌词

        /*上传歌曲文件*/
        if(mpFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"歌曲上传失败");
            return jsonObject;
        }
        /*避免上传一个文件名 覆盖掉*/
        String fileName = System.currentTimeMillis()+mpFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
        /*如果文件路径不存在，新增该路径*/
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir(); //创建目录
        }
        /*实际的文件地址*/
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        /*存储到数据库里的相对文件地址*/
        String storeUrlPath = "/song/"+fileName;
        try {
            mpFile.transferTo(dest);  //transferTo（转移）上传文件
            Song song = new Song();
            song.setSingerId(Integer.parseInt(singerId));
            song.setName(name);
            song.setIntroduction(introduction);
            song.setPic(pic);
//            System.out.println(song.getPic());
            song.setLyric(lyric);
            song.setUrl(storeUrlPath);
            boolean flag = songService.insert(song);
            if (flag){
                /*添加到 JSON 对象中*/
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.NSG,"保存成功");
                jsonObject.put("avator",storeUrlPath);
                return  jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"保存失败");
            return jsonObject;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*根据歌手id查询歌曲*/
    @GetMapping("/detail")
    public Object singerOfSingerId(HttpServletRequest request){
        String singerId = request.getParameter("singerId");
        return songService.songOfSingerId(Integer.parseInt(singerId));
    }

    /*更新歌曲信息*/
    @PostMapping("/update")
    public Object updateSong(HttpServletRequest request) throws ParseException {
        /*处理json*/
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        /*trim(修剪);去掉前后空白字符*/
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim(); //专辑
        String lyric = request.getParameter("lyric").trim();              //歌词

        Song song = new Song();
        song.setId(Integer.parseInt(id)); //Integer.parseInt 转换整数
        song.setName(name);
        song.setIntroduction(introduction);
        song.setLyric(lyric);
        boolean flag = songService.update(song);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.NSG,"修改成功");
            return  jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.NSG,"修改失败");
        return jsonObject;
    }

    /*删除歌曲*/
    @GetMapping("/delete")
    public Object deleteSinger(HttpServletRequest request) {
        /*查询数据库中对应的 本地文件地址，删除掉，再进形下面的代码*/
        String id = request.getParameter("id").trim();
        if (id == null || id.isEmpty()) {
            System.out.println("id不存在");
            return false;
        }
        Song songlist = songService.findById(Integer.parseInt(id));
        /*System.getProperty("user.dir") 本地路径*/
        String filePath = System.getProperty("user.dir") + songlist.getUrl();
        String imgPath = System.getProperty("user.dir") + songlist.getPic();
        String defaultPicPath = System.getProperty("user.dir") + "/img/songPic/songicon.png";
        File fileSong = new File(filePath);
        File fileImg = new File(imgPath);
        if (filePath != null && !imgPath.equals(defaultPicPath)) {
            if (fileSong.exists() && fileImg.exists()) {
                fileSong.delete();
                fileImg.delete();
                boolean flag = songService.delete(Integer.parseInt(id));
                return flag;
            } else {
                System.out.println("文件不存在");
                return false;
            }
        } else {
            if (filePath == null) {
                System.out.println("歌曲地址不存在");
            }
            if (imgPath.equals(defaultPicPath)) {
                fileSong.delete();
                boolean flag = songService.delete(Integer.parseInt(id));
                return flag;
            }

            return false;
        }
    }

    /*更新歌曲图片*/
    @PostMapping("/updateSongPic")
    /*MultipartFile简化文件流操作的接口*/
    public Object updateSongPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id) {

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
                +System.getProperty("file.separator")+"songPic";
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir(); //创建目录
        }
        /*实际的文件地址*/
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        /*存储到数据库里的相对文件地址*/
        String storeAvatorPath = "/img/songPic/"+fileName;
        try {
            avatorFile.transferTo(dest);  //transferTo（转移）上传文件
            Song song = new Song();
            song.setId(id);
            song.setPic(storeAvatorPath);
            boolean flag = songService.update(song);
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

    /*更新歌曲*/
    @PostMapping("/updateSongUrl")
    /*MultipartFile简化文件流操作的接口*/
    public Object updateSongUrl(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id) {
        /*上传之前先删除*/
        Song songlist = songService.findById(id);
        String oldFilePath = System.getProperty("user.dir") + songlist.getUrl();
        File file = new File(oldFilePath);
        if(oldFilePath != null){
            file.delete();
        }
        /*------上传-----*/
        JSONObject jsonObject = new JSONObject();
        /*isEmpty 空*/
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"文件上传失败");
            return jsonObject;
        }
        /*避免上传一个文件名 覆盖掉*/
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir(); //创建目录
        }
        /*实际的文件地址*/
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        /*存储到数据库里的相对文件地址*/
        String storeAvatorPath = "/song/"+fileName;
        try {
            avatorFile.transferTo(dest);  //transferTo（转移）上传文件
            Song song = new Song();
            song.setId(id);

            song.setUrl(storeAvatorPath);
            boolean flag = songService.update(song);
            if (flag){
                /*添加到 JSON 对象中*/
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.NSG,"上传歌曲成功");
                jsonObject.put("avator",storeAvatorPath);
                return  jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"上传歌曲失败");
            return jsonObject;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*根据歌曲id查询歌曲对象*/
    @GetMapping("/songDetail")
    public Object songOfSingerId(HttpServletRequest request){
        String songId = request.getParameter("songId");
        return songService.findById(Integer.parseInt(songId));
    }

    /*根据歌曲名字 查询歌曲对象*/
    @GetMapping("/songOfSongName")
    public Object songOfSongName(HttpServletRequest request){
        String songName = request.getParameter("songName");
        return songService.songOfName(songName);
    }

    /*根据歌曲名字 模糊查询*/
    @GetMapping("/likesongOfSongName")
    public Object likesongOfSongName(HttpServletRequest request){
        String songName = request.getParameter("songName");
        return songService.likesongOfName(songName);
    }

    /*查询歌曲总数*/
    @PostMapping("/allSong")
    public Object allSong(HttpServletRequest request){
        return songService.allSong();
    }
}
