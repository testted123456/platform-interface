package com.nonobank.inter.remotecontroller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.nonobank.inter.component.result.Result;

@FeignClient(value="PLATFORM-TESTCASE")
public interface RemoteTestCase {

	@PostMapping(value="sysBranch/updateBySystemAndBranch", consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	Result updateBySystemAndBranch(@RequestBody JSONObject jsonObj);
	
	@GetMapping(value="sysBranch/noticeSyncResult")
	@ResponseBody
	Result noticeSyncResult(@RequestParam(value="result") String result, @RequestParam(value="system") String system, @RequestParam(value="branch") String branch, @RequestParam(value="version") String version);

	@GetMapping(value="sysBranch/noticeSyncResult")
	@ResponseBody
	Result setCodeChecked(@RequestParam(value="system") String system, @RequestParam(value="branch") String branch, @RequestParam(value="codeChecked") String codeChecked);

	@GetMapping(value="sysCfg/getBySystem")
	@ResponseBody
	Result getSysCfg(@RequestParam(value="system") String system);
}
