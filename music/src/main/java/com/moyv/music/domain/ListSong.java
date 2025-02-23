package com.moyv.music.domain;

import java.io.Serializable;

/*歌单歌曲管理*/
/*实现对象序列化的一个标识接口(序列化与反序列化)*/
public class ListSong implements Serializable {
    private Integer id;
    private Integer songId;      /*歌曲id*/
    private Integer songListId;  /*歌单id*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public Integer getSongListId() {
        return songListId;
    }

    public void setSongListId(Integer songListId) {
        this.songListId = songListId;
    }
}
