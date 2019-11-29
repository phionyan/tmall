package com.phion.tmall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 用HBuilder开发的时候方便前端调试，需要跨域支持
 * @author 15037
 *
 */
@Configuration
public class CORSConfiguration extends WebMvcConfigurerAdapter{
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//所有请求都允许跨域
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*")
				.allowedHeaders("*");
	}
}