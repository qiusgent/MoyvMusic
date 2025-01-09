package com.moyv.music.dao;

import com.moyv.music.domain.ListSong;
import org.springframework.stereotype.Repository;

import java.util.List;

/*歌单歌曲*/
@Repository
public interface ListSongMapper {
    /*增加*/
    public int insert(ListSong listSong);

    /*修改*/
    public int update(ListSong listSong);

    /*删除*/
    public int delete(Integer songId,Integer songListId);

    /*用歌曲和歌单id 精确查询*/
    public ListSong findBySongAndSongList(Integer songId,Integer songListId);

    /*查询*/
    public ListSong findById(Integer id);

    /*查询所有歌单里的歌曲*/
    public List<ListSong> allListSong();

    /*根据歌单id查询所有的歌曲*/
    public List<ListSong> listSongOfSongList(Integer songListId);
}
