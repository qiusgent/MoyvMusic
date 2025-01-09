package com.moyv.music.service;

import com.moyv.music.domain.Comment;

import java.util.List;

public interface CommentService {
    /*增加*/
    public boolean insert(Comment comment);

    /*修改*/
    public boolean update(Comment comment);

    /*删除*/
    public boolean delete(Integer id);

    /*查询*/
    public Comment findById(Integer id);

    /*查询所有评论*/
    public List<Comment> allComment();

    /*查询某个歌曲下的所以评论*/
    public List<Comment> commentOfSongId(Integer songId);

    /*查询某个歌单下的所以评论**/
    public List<Comment> commentOfSongListId(Integer songListId);
}
