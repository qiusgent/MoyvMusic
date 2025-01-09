package com.moyv.music.dao;

import com.moyv.music.domain.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongMapper {
    /*增加*/
    public int insert(Song song);

    /*修改*/
    public int update(Song song);

    /*删除*/
    public int delete(Integer id);

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
