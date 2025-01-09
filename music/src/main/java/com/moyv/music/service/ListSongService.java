package com.moyv.music.service;
import com.moyv.music.domain.ListSong;
import java.util.List;

/*歌单里的歌曲*/
public interface ListSongService {
    /*增加*/
    public boolean insert(com.moyv.music.domain.ListSong listSong);

    /*修改*/
    public boolean update(com.moyv.music.domain.ListSong listSong);

    /*删除*/
    public boolean delete(Integer songId,Integer songListId);

    /*用歌曲和歌单id 精确查询*/
    public ListSong findBySongAndSongList(Integer songId,Integer songListId);

    /*查询*/
    public ListSong findById(Integer id);

    /*查询所有歌单里的歌曲*/
    public List<ListSong> allListSong();

    /*根据歌单id查询所有的歌曲*/
    public List<ListSong> listSongOfSongList(Integer songListId);
}
