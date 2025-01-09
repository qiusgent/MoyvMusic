package com.moyv.music.dao;

import com.moyv.music.domain.Consumer;
import org.springframework.stereotype.Repository;

import java.util.List;

/*前端用户*/
@Repository
public interface ConsumerMapper {
    /*增加*/
    public int insert(Consumer consumer);

    /*修改*/
    public int update(Consumer consumer);

    /*验证密码*/
    public int verifyPassword(String username,String password);

    /*删除*/
    public int delete(Integer id);

    /*查询*/
    public Consumer findById(Integer id);

    /*根据用户名精确查找*/
//    public List<Consumer> findByName(String name);

    /*查询所有用户*/
    public List<Consumer> allConsumer();

    /*根据账号查询*/
    public Consumer getByUsername(String username);


    /*模糊查询*/
//    public List<Consumer> consumerOfName(String name);

    /*根据类别查询*/
//    public List<Consumer> consumerOfSex(Integer sex);



}
