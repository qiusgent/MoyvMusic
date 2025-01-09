package com.moyv.music.service;

import com.moyv.music.domain.Collect;
import com.moyv.music.domain.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectService {
    /*增加*/
    public boolean insert(Collect collect);

    /*删除*/
    public boolean delete(Integer userId,Integer songId);

    /*查询所有收藏*/
    public List<Collect> allCollect();

    /*查询某个用户下的收藏列表*/
    public List<Collect> collectOfuserId(Integer userId);

    /*查询某个用户下的是否已经收藏了某个歌曲**/
    public boolean exsistSongId(@Param("userId") Integer userId, @Param("songId") Integer songId);
}
