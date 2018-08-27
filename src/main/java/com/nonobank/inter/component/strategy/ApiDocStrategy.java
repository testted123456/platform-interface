package com.nonobank.inter.component.strategy;

public interface ApiDocStrategy {
	
	public Character getApiType();
	
	public Character getResponseBodyType();

	public String getRequestHead();
	
	public String getResponseHead();
	
	public String getRequest(String request);
	
	public String getResponse(String response);
}
