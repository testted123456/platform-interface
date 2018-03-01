package com.nonobank.inter.component.exception;

import com.nonobank.inter.component.result.ResultCode;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 2495559366495945814L;
	private int code;

	public ApiException(ResultCode resultCode) {
		super(resultCode.getMsg());
		this.code = resultCode.getCode();
	}
	
	public ApiException(int code, String msg){
		super(msg);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
