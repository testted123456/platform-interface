package com.nonobank.inter;



import com.nonobank.inter.entity.InterfaceDefinition;
import com.nonobank.inter.repository.InterfaceDefinitionRepository;
import com.nonobank.inter.service.GitService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationHome;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InterfaceApplicationTests {

	@Value("${codePath}")
	private String codePath;

	@Value("${apidocPath}")
	private String apidocPath;

	@Autowired
	private GitService gitService;

	@Autowired
	private InterfaceDefinitionRepository interfaceDefinitionRepository;

	@Test
	public void contextLoads() throws IOException, GitAPIException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

//		gitService.syncBranch("acc","http://git.nonobank.com/trd-pay-acc/acc.git","develop","00");

//		File path = new File(ResourceUtils.getURL("classpath:").getPath());
//		System.out.println(path.getAbsolutePath());
//		ApplicationHome home = new ApplicationHome(this.getClass());
//		File jarFile = home.getSource();
//		File jarDir = home.getDir();
//		File uploadDir = new File(jarDir, "upload-dir/aaa");
//		System.out.println(uploadDir.getPath());
//
//		System.out.println(codePath);

	}

	@Test
	public void insertTest(){
		InterfaceDefinition interfaceDefinition = new InterfaceDefinition();
		interfaceDefinition.setName("aaa");
		interfaceDefinition.setApiType('1');
		interfaceDefinition.setPostWay('1');
		interfaceDefinition.setOptstatus((short)1);

//		interfaceDefinition.setCreatedTime(LocalDateTime.now());

		interfaceDefinitionRepository.save(interfaceDefinition);


	}



}
