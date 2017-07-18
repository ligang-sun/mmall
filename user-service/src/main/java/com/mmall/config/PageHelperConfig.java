package com.mmall.config;

import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;


/**
 * 注册MyBatis分页插件PageHelper  
 * 
 * @author ligang.sun
 *
 */
@Configuration
@MapperScan("com.mmall.mapper")
public class PageHelperConfig {

	 @Bean  
     public PageHelper pageHelper() {  
         PageHelper pageHelper = new PageHelper();  
         Properties p = new Properties();  
         p.setProperty("offsetAsPageNum", "true");  
         p.setProperty("rowBoundsWithCount", "true"); 
         p.setProperty("dialect", "mysql");
         p.setProperty("reasonable", "true");  
         pageHelper.setProperties(p);  
         return pageHelper;  
     }  
}
