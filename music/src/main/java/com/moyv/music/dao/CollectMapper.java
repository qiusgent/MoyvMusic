package com.moyv.music.dao;

import com.moyv.music.domain.Collect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectMapper {
    /*增加*/
    public int insert(Collect collect);

    /*删除*/
    public int delete(Integer userId,Integer songId);

    /*查询所有收藏*/
    public List<Collect> allCollect();

    /*查询某个用户下的收藏列表*/
    public List<Collect> collectOfuserId(Integer userId);

    /*查询某个用户下的是否已经收藏了某个歌曲**/
    public int exsistSongId(@Param("userId") Integer userId,@Param("songId") Integer songId);

}
