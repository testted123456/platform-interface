package com.nonobank.inter.service;

import java.io.IOException;

public interface CodeCoverService {
	
	public void compileCode(String codePath);
	
	public void getJacocoDump(String ip, int port, String dumpFile, boolean reset) throws IOException;
	
	public void generateReport(String jacocoDumpPath, String sourceCodePath, String classCodePath, String reportPath, String title) throws IOException;
	
	public void generateReport(String jacocoDumpath, String system, String branch, String reportPath, String ip, Integer port, String title) throws IOException;
}
