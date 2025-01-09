package com.moyv.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.moyv.music.domain.Collect;
import com.moyv.music.service.CollectService;
import com.moyv.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private CollectService collectService;

    /*添加收藏*/
    @PostMapping("/add")
    public Object addCollect(HttpServletRequest request) throws ParseException {
        /*处理json*/
        JSONObject jsonObject = new JSONObject();
        /*trim(修剪);去掉前后空白字符*/
        String userId = request.getParameter("userId");
        String type = request.getParameter("type");
        String songId = request.getParameter("songId");
        if(songId == null || songId.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.NSG,"收藏的歌曲为空");
            return jsonObject;
        }
        if (collectService.exsistSongId(Integer.parseInt(userId),Integer.parseInt(songId))){
            jsonObject.put(Consts.CODE,2);
            jsonObject.put(Consts.NSG,"已收藏");
            return jsonObject;
        }

        Collect collect = new Collect();
        collect.setUserId(Integer.parseInt(userId));

        collect.setType(type);
        collect.setSongId(Integer.parseInt(songId));

        boolean flag = collectService.insert(collect);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.NSG,"收藏成功");
            return  jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.NSG,"收藏失败");
        return jsonObject;
    }

    /*删除收藏*/
    @GetMapping("/delete")
    public Object deleteCollect(HttpServletRequest request) {
        String userId = request.getParameter("userId").trim();
        String songId = request.getParameter("songId").trim();
        boolean flag = collectService.delete(Integer.parseInt(userId),Integer.parseInt(songId));
        return flag;
    }

    /*查询所有收藏*/
    @PostMapping("/allCollect")
    public Object allCollect(){
        return collectService.allCollect();
    }

    /*查询某个用户下的收藏列表*/
    @GetMapping("/collectOfuserId")
    public Object collectOfuserId(HttpServletRequest request){
        String userId = request.getParameter("userId").trim();
        return collectService.collectOfuserId(Integer.parseInt(userId));
    }

}
