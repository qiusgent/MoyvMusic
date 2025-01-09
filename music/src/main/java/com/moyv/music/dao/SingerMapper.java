package com.moyv.music.dao;

import com.moyv.music.domain.Singer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingerMapper {
    /*增加*/
    public int insert(Singer singer);

    /*修改*/
    public int update(Singer singer);

    /*删除*/
    public int delete(Integer id);

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
