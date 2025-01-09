package com.moyv.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.moyv.music.domain.Comment;
import com.moyv.music.service.CommentService;
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
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /*添加评论*/
    @PostMapping("/add")
    public Object addComment(HttpServletRequest request) throws ParseException {
        /*处理json*/
        JSONObject jsonObject = new JSONObject();
        /*trim(修剪);去掉前后空白字符*/
        String userId = request.getParameter("userId");
        String type = request.getParameter("type");
        String songId = request.getParameter("songId");
        String songListId = request.getParameter("songListId");
        String content = request.getParameter("content");


        Comment comment = new Comment();
        comment.setUserId(Integer.parseInt(userId));
        comment.setType(type);
        if(Integer.parseInt(type) == 0){
            comment.setSongId(Integer.parseInt(songId));
        }else {
            comment.setSongListId(Integer.parseInt(songListId));
        }
        comment.setContent(content);

        boolean flag = commentService.insert(comment);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.NSG,"评论成功");
            return  jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.NSG,"评论失败");
        return jsonObject;
    }

    /*修改评论*/
    @PostMapping("/update")
    public Object updateComment(HttpServletRequest request) throws ParseException {
        /*处理json*/
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String userId = request.getParameter("userId").trim();
        String type = request.getParameter("type").trim();
        String songId = request.getParameter("songId").trim();
        String songListId = request.getParameter("songListId").trim();
        String content = request.getParameter("content").trim();


        Comment comment = new Comment();
        comment.setId(Integer.parseInt(id));
        comment.setUserId(Integer.parseInt(userId));
        comment.setType(type);
        if(songId !=null && songId.equals("")){
           songId = null;
        }else {
            comment.setSongId(Integer.parseInt(songId));
        }
        if(songListId!=null && songListId.equals("")){
            songListId = null;
        }else {
            comment.setSongListId(Integer.parseInt(songListId));
        }
        comment.setContent(content);
        boolean flag = commentService.update(comment);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.NSG,"修改成功");
            return  jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.NSG,"修改失败");
        return jsonObject;
    }

    /*删除评论*/
    @GetMapping("/delete")
    public Object deleteComment(HttpServletRequest request) {
        String id = request.getParameter("id").trim();
        boolean flag = commentService.delete(Integer.parseInt(id));
        return flag;
    }

    /*查询评论*/
    @PostMapping("/findById")
    public Object findByIdComment(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return  commentService.findById(Integer.parseInt(id));
    }


    /*查询所有歌手*/
    @PostMapping("/allComment")
    public Object allComment(){
        return commentService.allComment();
    }

    /*查询某个歌曲下的所有评论*/
    @GetMapping("/commentOfSongId")
    public Object commentOfSongId(HttpServletRequest request){
        String songId = request.getParameter("songId").trim();
        return commentService.commentOfSongId(Integer.parseInt(songId));
    }

    /*查询某个歌单下的所以评论**/
    @GetMapping("/commentOfSongListId")
    public Object commentOfSongListId(HttpServletRequest request){
        String songListId = request.getParameter("songListId").trim();
        return commentService.commentOfSongListId(Integer.parseInt(songListId));
    }

    /*给某个评论点赞*/
    @PostMapping("/like")
    public Object like(HttpServletRequest request){
        /*处理json*/
        JSONObject jsonObject = new JSONObject();
        String userId = request.getParameter("userId").trim();
        String up = request.getParameter("up").trim();
        Comment comment = new Comment();
        comment.setId(Integer.parseInt(userId));
        comment.setUp(Integer.parseInt(up));
        boolean flag = commentService.update(comment);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.NSG,"点赞成功");
            return  jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.NSG,"点赞失败");
        return jsonObject;
    }
}
