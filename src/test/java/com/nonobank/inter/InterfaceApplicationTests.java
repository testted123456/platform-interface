package com.nonobank.inter;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nonobank.inter.component.sync.IfromAComponent;
import com.nonobank.inter.repository.InterfaceDefinitionRepository;
import com.nonobank.inter.service.GitService;
import com.nonobank.inter.util.FileUtil;

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
	
	@Autowired
	IfromAComponent ifromAComponent;

	@Test
	public void contextLoads() throws IOException, GitAPIException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		/*gitService.syncBranch("acc","账户","http://git.nonobank.com/trd-pay-acc/acc.git","develop","00");

		File path = new File(ResourceUtils.getURL("classpath:").getPath());
		System.out.println(path.getAbsolutePath());
		ApplicationHome home = new ApplicationHome(this.getClass());
		File jarFile = home.getSource();
		File jarDir = home.getDir();
		File uploadDir = new File(jarDir, "upload-dir/aaa");
		System.out.println(uploadDir.getPath());

		System.out.println(codePath);*/
		
		/*String apidataJsonPath = 
				"F:\\my_project\\java\\platform-testcase\\src\\main\\java\\com\\nonobank\\testcase\\component\\dataProvider\\common\\apidoc\\api_data.json";
		String projectJsonPath = 
				"F:\\my_project\\java\\platform-testcase\\src\\main\\java\\com\\nonobank\\testcase\\component\\dataProvider\\common\\apidoc\\api_project.json";
		String apiDataContent = FileUtil.readFile(apidataJsonPath);
        String projectContent = FileUtil.readFile(projectJsonPath);
        ifromAComponent.syncApidoc("api", "dev", projectContent, apiDataContent);*/

	} 

//	@Test
//	public void insertTest(){
//		InterfaceDefinition interfaceDefinition = new InterfaceDefinition();
//		interfaceDefinition.setName("aaa");
//		interfaceDefinition.setApiType('1');
//		interfaceDefinition.setPostWay('1');
//		interfaceDefinition.setOptstatus((short)1);
//
////		interfaceDefinition.setCreatedTime(LocalDateTime.now());
//
//		interfaceDefinitionRepository.save(interfaceDefinition);
//
//
//	}

}
