package com.moyv.music.service;

import com.moyv.music.domain.Song;

import java.util.List;

public interface SongService {
    /*增加*/
    public boolean insert(Song song);

    /*修改*/
    public boolean update(Song song);

    /*删除*/
    public boolean delete(Integer id);

    /*查询*/
    public Song findById(Integer id);

    /*查询所有歌曲*/
    public List<Song> allSong();

    /*精确查询*/
    public List<Song> songOfName(String name);

    /*模糊查询*/
    public List<Song> likesongOfName(String name);

    /*根据歌手id查询*/
    public List<Song> songOfSingerId(Integer singerId);
}
