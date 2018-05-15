package com.nonobank.inter.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.data.ExecutionDataWriter;
import org.jacoco.core.runtime.RemoteControlReader;
import org.jacoco.core.runtime.RemoteControlWriter;
import org.jacoco.core.tools.ExecFileLoader;
import org.jacoco.report.DirectorySourceFileLocator;
import org.jacoco.report.FileMultiReportOutput;
import org.jacoco.report.IReportVisitor;
import org.jacoco.report.html.HTMLFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationHome;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import com.nonobank.inter.service.CodeCoverService;

@Service
@EnableAsync
public class CodeCoverServiceImpl implements CodeCoverService {

	public static Logger logger = LoggerFactory.getLogger(CodeCoverServiceImpl.class);

	@Value("${codePath}")
	private String codePath;

	@Value("${mvnPath}")
	private String mvnPath;

	/**
	 * 编译原码文件
	 */
	@Override
	public void compileCode(String codePath) {
		logger.info("开始比编译代码...");
		String[] cmds = new String[] { "/bin/sh", "-c", "cd " + codePath + ";" + mvnPath + "mvn clean compile" };
		Process p;
		try {
			p = Runtime.getRuntime().exec(cmds);

			BufferedReader rd = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				logger.info(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * dump jacoco文件
	 * 
	 * @throws IOException
	 */
	@Override
	public void getJacocoDump(String ip, int port, String dumpFile, boolean reset) throws IOException {
		logger.info("开始dump jacoco文件...");

		File f = new File(dumpFile);

		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}

		FileOutputStream localFile = new FileOutputStream(dumpFile);
		ExecutionDataWriter localWriter = new ExecutionDataWriter(localFile);
		Socket socket = new Socket(InetAddress.getByName(ip), port);
		RemoteControlWriter writer = new RemoteControlWriter(socket.getOutputStream());
		RemoteControlReader reader = new RemoteControlReader(socket.getInputStream());
		reader.setSessionInfoVisitor(localWriter);
		reader.setExecutionDataVisitor(localWriter);
		writer.visitDumpCommand(true, reset);
		reader.read();
		socket.close();
		localFile.close();
	}

	/**
	 * 生成jacoco文件 jacocoDumpPath jacoco文件路径 sourceCodePath 源码文件路径 reportPath
	 * 报告生成路径 title 报告标题
	 * 
	 * @throws IOException
	 */
	@Override
	public void generateReport(String jacocoDumpFile, String sourceCodePath, String classCodePath, String reportPath,
			String title) throws IOException {

		ExecFileLoader execFileLoader = new ExecFileLoader();
		execFileLoader.load(new File(jacocoDumpFile));

		CoverageBuilder coverageBuilder = new CoverageBuilder();
		Analyzer analyzer = new Analyzer(execFileLoader.getExecutionDataStore(), coverageBuilder);
		analyzer.analyzeAll(new File(classCodePath));
		IBundleCoverage bundleCoverage = coverageBuilder.getBundle(title);

		final HTMLFormatter htmlFormatter = new HTMLFormatter();
		final IReportVisitor visitor = htmlFormatter.createVisitor(new FileMultiReportOutput(new File(reportPath)));

		visitor.visitInfo(execFileLoader.getSessionInfoStore().getInfos(),
				execFileLoader.getExecutionDataStore().getContents());

		visitor.visitBundle(bundleCoverage, new DirectorySourceFileLocator(new File(sourceCodePath), "utf-8", 4));

		visitor.visitEnd();
	}

	@Async
	@Override
	public void generateReport(String jacocoDumpath, String system, String branch, String reportPath, String ip,
			Integer port, String title) {
		
		try {
			ApplicationHome home = new ApplicationHome(this.getClass());
			File branchCodeDir = new File(home.getDir(), String.format("%s/%s/%s", codePath, system, branch));
			jacocoDumpath = home.getDir() + File.separator + jacocoDumpath;
			reportPath = home.getDir() + File.separator + reportPath;
			String sourceCodePath = branchCodeDir.getAbsolutePath();
			String classCodePath = sourceCodePath + File.separator + "target" + File.separator + "classes";
			compileCode(sourceCodePath);
			getJacocoDump(ip, port, jacocoDumpath + File.separator + system + "-" + branch + ".exec", false);
			generateReport(jacocoDumpath + File.separator + system + "-" + branch + ".exec", sourceCodePath,
					classCodePath, reportPath, title);
		} catch (IOException e) {
			logger.error("生成代码覆盖率失败，" + e.getLocalizedMessage());
		}
	}

}
