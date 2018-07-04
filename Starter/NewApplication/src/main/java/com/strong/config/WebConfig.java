package com.strong.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	private static Logger logger = LoggerFactory.getLogger(WebConfig.class);
	
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
        
        configurer.setUseSuffixPatternMatch(false);//URL不会识别和匹配.*后缀
    }
    
//    @Bean(name = "multipartResolver")
//    public CommonsMultipartResolver multipartResolver() {
//        logger.info("Loading the multipart resolver");
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        return multipartResolver;
//    }   
}
