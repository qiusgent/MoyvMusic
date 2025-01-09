package com.moyv.music;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* 热更新，热加载
* 在高级设置里搜compiler第一个
* ctrl+f9(执行快捷键 热加载)
*
*
* */

@SpringBootApplication
@MapperScan("com.moyv.music.dao")
public class MusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicApplication.class, args);
    }

}
