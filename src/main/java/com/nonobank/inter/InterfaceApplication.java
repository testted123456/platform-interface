package com.nonobank.inter;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableAsync
@EnableRedisHttpSession
public class InterfaceApplication {
	
//	private CorsConfiguration buildConfig() {  
//        CorsConfiguration corsConfiguration = new CorsConfiguration();  
//        corsConfiguration.addAllowedOrigin("*");  
//        corsConfiguration.addAllowedHeader("*");  
//        corsConfiguration.addAllowedMethod("*");  
//        return corsConfiguration;  
//    }  
//      
//    /** 
//     * 跨域过滤器 
//     * @return 
//     */  
//    @Bean  
//    public CorsFilter corsFilter() {  
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
//        source.registerCorsConfiguration("/**", buildConfig()); // 4  
//        return new CorsFilter(source);  
//    }  

	public static void main(String[] args) {

		SpringApplication app =
				new SpringApplication(InterfaceApplication.class);

		Properties properties = new Properties();
		properties.setProperty("spring.resources.static-locations",
				"classpath:/newLocation1/, classpath:/newLocation2/");
		app.setDefaultProperties(properties);

		app.setDefaultProperties(properties);

		app.run(args);
//		SpringApplication.run(InterfaceApplication.class, args);
	}
}
