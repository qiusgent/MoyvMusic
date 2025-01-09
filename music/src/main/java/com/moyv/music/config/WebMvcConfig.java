package com.moyv.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*解决跨域问题*/
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                       /*来源*/
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                /*访问时验证*/
                .allowCredentials(true);

    }
}
