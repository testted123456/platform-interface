package com.nonobank.inter.component.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nonobank.inter.component.result.Result;
import com.nonobank.inter.component.result.ResultCode;
import com.nonobank.inter.component.result.ResultUtil;

@ControllerAdvice
public class ExceptionHandle {

	public static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Result handle(Exception e){
		if( e instanceof ApiException){
			ApiException tcException = (ApiException)e;
			return ResultUtil.error(tcException.getCode(), tcException.getMessage());
		}else{
			logger.error("发生未知异常");
		    e.printStackTrace();
			return ResultUtil.error(ResultCode.UNKOWN_ERROR.getCode(), e.getClass().getName());
		}
	}
}
