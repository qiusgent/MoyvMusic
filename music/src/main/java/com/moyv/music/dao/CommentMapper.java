package com.moyv.music.dao;

import com.moyv.music.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    /*增加*/
    public int insert(Comment comment);

    /*修改*/
    public int update(Comment comment);

    /*删除*/
    public int delete(Integer id);

    /*查询*/
    public Comment findById(Integer id);

    /*查询所有评论*/
    public List<Comment> allComment();

    /*查询某个歌曲下的所以评论*/
    public List<Comment> commentOfSongId(Integer songId);

    /*查询某个歌单下的所以评论**/
    public List<Comment> commentOfSongListId(Integer songListId);



}
