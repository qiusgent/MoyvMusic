package com.moyv.music.dao;

import com.moyv.music.domain.SongList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongListMapper {
    /*增加*/
    public int insert(SongList songList);

    /*修改*/
    public int update(SongList songList);

    /*删除*/
    public int delete(Integer id);

    /*查询*/
    public SongList findById(Integer id);

    /*查询所有歌单*/
    public List<SongList> allSongList();

    /*精确查询(根据标题)*/
    public List<SongList> songListOfTitle(String title);

    /*模糊查询(根据标题)*/
    public List<SongList> likeTitle(String title);

    /*模糊查询(根据风格)*/
    public List<SongList> likeStyle(String style);


}
