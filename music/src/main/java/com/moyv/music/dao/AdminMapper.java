package com.moyv.music.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {

    /*验证密码*/
    public int verifyPassword(String username,String password);
}
