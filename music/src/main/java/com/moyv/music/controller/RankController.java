package com.moyv.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.moyv.music.domain.Rank;
import com.moyv.music.service.RankService;
import com.moyv.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController /*返回json数据*/
@RequestMapping("/rank")
public class RankController {
    @Autowired
    private RankService rankService;

    /*新增评价*/
    @PostMapping("/add")
    public Object add(HttpServletRequest request){
        /*处理json*/
        JSONObject jsonObject = new JSONObject();
        String songListId = request.getParameter("songListId");
        String consumerId = request.getParameter("consumerId");
        String score = request.getParameter("score");
        Rank rank = new Rank();
        rank.setSongListId(Integer.parseInt(songListId));
        rank.setConsumerId(Integer.parseInt(consumerId));
        rank.setScore(Integer.parseInt(score));
        boolean flag = rankService.insert(rank);
        if(flag){
            /*添加*/
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.NSG,"评价成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.NSG,"评价失败");
        return jsonObject;
    }

    /*计算平均分*/
    @GetMapping("/average")
    public Object average(HttpServletRequest request){
        String songListId = request.getParameter("songListId");
//        System.out.println(Integer.parseInt(songListId));
        return rankService.rankOfSongListId(Integer.parseInt(songListId));
    }

}
