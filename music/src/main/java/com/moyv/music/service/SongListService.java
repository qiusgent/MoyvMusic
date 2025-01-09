package com.moyv.music.service;

import com.moyv.music.domain.Song;
import com.moyv.music.domain.SongList;

import java.util.List;

public interface SongListService {
    /*增加*/
    public boolean insert(SongList songList);

    /*修改*/
    public boolean update(SongList songList);

    /*删除*/
    public boolean delete(Integer id);

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
