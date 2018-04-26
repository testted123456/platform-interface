package com.nonobank.inter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.net.URISyntaxException;

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


    public static void main(String[] args) throws URISyntaxException {

        SpringApplication.run(InterfaceApplication.class, args);
    }
}
