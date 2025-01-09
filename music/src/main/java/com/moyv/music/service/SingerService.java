package com.moyv.music.service;

import com.moyv.music.domain.Singer;

import java.util.List;

public interface SingerService {
    /*增加*/
    public boolean insert(Singer singer);

    /*修改*/
    public boolean update(Singer singer);

    /*删除*/
    public boolean delete(Integer id);

    /*查询*/
    public Singer findById(Integer id);

    /*根据歌手名精确查找*/
    public List<Singer> findByName(String name);

    /*查询所有歌手*/
    public List<Singer> allSinger();

    /*模糊查询*/
    public List<Singer> singerOfName(String name);

    /*根据性别查询*/
    public List<Singer> singerOfSex(Integer sex);
}
