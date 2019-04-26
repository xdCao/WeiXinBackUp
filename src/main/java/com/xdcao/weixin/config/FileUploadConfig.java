package com.xdcao.weixin.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;

/**
 * @Author: buku.ch
 * @Date: 2019-03-27 11:19
 */

@Configuration
@ConditionalOnClass({Servlet.class, StandardServletMultipartResolver.class, MultipartConfigElement.class})
@ConditionalOnProperty(prefix = "spring.servlet.multipart",name = "enabled",matchIfMissing = true)
@EnableConfigurationProperties(MultipartProperties.class)
public class FileUploadConfig {

    private final MultipartProperties multipartProperties;

    public FileUploadConfig(MultipartProperties multipartProperties) {
        this.multipartProperties = multipartProperties;
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /*上传配置*/
    @Bean
    @ConditionalOnMissingBean
    public MultipartConfigElement multipartConfigElement() {
        return this.multipartProperties.createMultipartConfig();
    }

    /*注册解析器*/
    @Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
    @ConditionalOnMissingBean
    public StandardServletMultipartResolver multipartResolver() {
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        multipartResolver.setResolveLazily(this.multipartProperties.isResolveLazily());
        return multipartResolver;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    }


}
