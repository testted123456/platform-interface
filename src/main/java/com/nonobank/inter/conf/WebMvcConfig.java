package com.nonobank.inter.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;


/**
 * Created by tangrubei on 2018/3/9.
 */

/**
 * 配置静态资源映射
 *
 * @author sam
 * @since 2017/7/16
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value("${apidocPath}")
    private String apidocPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        ApplicationHome home = new ApplicationHome(this.getClass());
        String apidochDirPath = home.getDir().getPath() + "/" + apidocPath + "/";
        registry.addResourceHandler("/apidocs/**").addResourceLocations("file:" + apidochDirPath);
//        registry.addResourceHandler("/apidoc/**").addResourceLocations("file:/Users/tangrubei/projects/nonoPro/interface/apidocs/acc/develop/");
        registry.addResourceHandler("/image/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);


    }
}
