package com.nonobank.inter.component.remoteEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.nonobank.inter.component.config.HttpServerProperties;

public class RemoteSysCfg {
	
	public static Logger logger = LoggerFactory.getLogger(RemoteSysCfg.class);

	@Autowired
	HttpServerProperties httpServerProperties;

}
