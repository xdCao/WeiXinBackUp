package com.xdcao.weixin;

import com.google.gson.Gson;
import com.xdcao.weixin.utils.MessageUtil;
import com.xdcao.weixin.utils.SignUtil;
import org.modelmapper.ModelMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.xdcao.weixin.dao")
public class WeixinApplication {

    @Bean
    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }
    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(WeixinApplication.class, args);
    }

}
