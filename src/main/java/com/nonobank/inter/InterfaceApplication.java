package com.nonobank.inter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@SpringBootApplication
@EnableAsync
@EnableRedisHttpSession
@EnableEurekaClient
@RefreshScope
@RestController
@Configuration
@EnableDiscoveryClient
@EnableFeignClients
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
    
    @Value("${server.port}")
	String port;
	
	@RequestMapping("/hello")
	public String home() {
		return "hello world from port ";
	}
}
