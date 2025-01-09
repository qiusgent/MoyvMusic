package com.moyv.music.service;

import com.moyv.music.domain.Consumer;

import java.util.List;

public interface ConsumerService {
    /*增加*/
    public boolean insert(Consumer consumer);

    /*修改*/
    public boolean update(Consumer consumer);

    /*验证密码*/
    public boolean verifyPassword(String username,String password);

    /*删除*/
    public boolean delete(Integer id);

    /*查询*/
    public Consumer findById(Integer id);

    /*查询所有用户*/
    public List<Consumer> allConsumer();

    /*根据账号查询*/
    public Consumer getByUsername(String username);

}
