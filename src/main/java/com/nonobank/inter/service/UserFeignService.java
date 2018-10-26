package com.nonobank.inter.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nonobank.inter.component.result.Result;

@FeignClient(value="PLATFORM-USER")
public interface UserFeignService {

	@RequestMapping(value = "/user/getAllRoles",method = RequestMethod.GET)
	Result getAllRoles();
}
