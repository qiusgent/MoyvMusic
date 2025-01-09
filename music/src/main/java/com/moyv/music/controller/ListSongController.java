package com.moyv.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.moyv.music.domain.ListSong;
import com.moyv.music.service.ListSongService;
import com.moyv.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/listSong")
public class ListSongController {
    @Autowired
    private ListSongService listSongService;

    /*给歌单添加歌曲*/
    @PostMapping("/add")
    public Object addListSong(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        /*获取前端的参数*/
        String songId = request.getParameter("songId").trim();  //歌曲id
        String songListId = request.getParameter("songListId").trim();  //歌单id
        ListSong listSong = new ListSong();
        listSong.setSongId(Integer.parseInt(songId));
        listSong.setSongListId(Integer.parseInt(songListId));
        boolean flag = listSongService.insert(listSong);
        if (flag) {
            /*添加到 JSON 对象中*/
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.NSG, "保存成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.NSG, "保存失败");
        return jsonObject;
    }

    /*根据歌单id查询歌曲*/
    @GetMapping("detail")
    public Object listSongOfSongList(HttpServletRequest request){
        String songListId = request.getParameter("songListId");
        return listSongService.listSongOfSongList(Integer.parseInt(songListId));
    }

    /*用歌曲和歌单id 精确查询*/
    @GetMapping("findBySongAndSongList")
    public Object findBySongAndSongList(HttpServletRequest request){
        String songId = request.getParameter("songId").trim();
        String songListId = request.getParameter("songListId").trim();
        return listSongService.findBySongAndSongList(Integer.parseInt(songId),Integer.parseInt(songListId));
    }

    /*删除歌单里的歌曲*/
    @GetMapping("/delete")
    public Object delete(HttpServletRequest request) {
        /*查询数据库中对应的 本地文件地址，删除掉，再进形下面的代码*/
        String songId = request.getParameter("songId").trim();
        String songListId = request.getParameter("songListId").trim();
        boolean flag = listSongService.delete(Integer.parseInt(songId),Integer.parseInt(songListId));
        return  flag;
    }

}
