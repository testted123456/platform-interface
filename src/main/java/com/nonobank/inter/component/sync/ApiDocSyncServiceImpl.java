package com.nonobank.inter.component.sync;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tangrubei on 2017/3/28.
 */
public class ApiDocSyncServiceImpl {




    /*
    private String httpProtocol = null;


    @Autowired
    private InterfaceDefinitionService interfaceDefinitionService;


    private InterfaceDefinitionWithBLOBs crateInsertInterfaceDefinition(InterfaceDefinitionWithBLOBs interfaceDefinitionWithBLOBs, Apidoc apidoc, String sysName, String branch) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        InterfaceDefinitionWithBLOBs interfaceDefinition = interfaceDefinitionWithBLOBs != null ? interfaceDefinitionWithBLOBs : new InterfaceDefinitionWithBLOBs();
        interfaceDefinition.setName(apidoc.getTitle());
        interfaceDefinition.setDescription(apidoc.getDescription());
        interfaceDefinition.setMethodType((short) ("post".equals(apidoc.getType().toLowerCase()) ? 1 : 0));
        interfaceDefinition.setUrlAddress(apidoc.getUrl());
        if (interfaceDefinition.getId() == null) {
            interfaceDefinition.setCreatedBy("System");
            interfaceDefinition.setCreatedTime(new Date());
            log.info("接口" + interfaceDefinition.getName() + "不存在，执行插入操作");
            System.out.println("接口" + interfaceDefinition.getName() + "不存在，执行插入操作");
        } else {
            log.info("接口" + interfaceDefinition.getName() + "存在，执行更新操作");
            System.out.println("接口" + interfaceDefinition.getName() + "存在，执行更新操操作");
            interfaceDefinition.setUpdatedBy("System");
            interfaceDefinition.setUpdatedTime(new Date());
        }
        interfaceDefinition.setSystem(sysName);
        interfaceDefinition.setBranch(branch);
        interfaceDefinition.setModuleName(apidoc.getGroupTitle());
        interfaceDefinition.setBodyType((short) 2);
        interfaceDefinition.setOptstatus((short) 0);
        interfaceDefinition.setHead("{\"Content-Type\": \"application/json\"}");
        String http = "";
        if (!apidoc.getUrl().startsWith("http")) {
            http = this.httpProtocol != null ? this.httpProtocol.toUpperCase() : "HTTP";
        } else {
            http = (apidoc.getUrl().startsWith("https") ? "https" : "http").toUpperCase();
        }

        interfaceDefinition.setHttps(http);
        interfaceDefinition.setRequestBody(ApiDocSyncServiceUtil.parameter2json(apidoc.getParameter()));
        interfaceDefinition.setResponseBody(ApiDocSyncServiceUtil.response2json(apidoc.getSuccess()));
        this.interfaceDefinitionService.insert(interfaceDefinition);

        return interfaceDefinition;
//        return null;
    }

    //    判断是否存在
    public InterfaceDefinitionWithBLOBs checkIsExist(Apidoc apidoc, String system, String branch) {
        log.info("判断接口" + apidoc.getTitle() + "是否存在");
        return interfaceDefinitionService.checkInterfaceIsExist(apidoc.getTitle(), system, branch, apidoc.getGroupTitle());
    }


    public InterfaceDefinitionWithBLOBs compareApi(Apidoc apidoc, String system, String branch) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        InterfaceDefinitionWithBLOBs interfaceDefinitionWithBLOBs = this.checkIsExist(apidoc, system, branch);
        return this.crateInsertInterfaceDefinition(interfaceDefinitionWithBLOBs, apidoc, system, branch);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void syncAllApidoc(String systemName, String branch) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String url = (String) ServiceProperty.
                getProperty(systemName);

        log.info("--------" + systemName + "--" + branch + " 开始执行---------");
        List<Apidoc> apidocs = ApiDocSyncServiceUtil.loadApi(url);
        List<InterfaceDefinitionWithBLOBs> insertList = new ArrayList<>();
        List<InterfaceDefinitionWithBLOBs> updateList = new ArrayList<>();
        for (Apidoc apidoc : apidocs) {
            if (apidoc.getUrl() != null && apidoc.getUrl().length() > 0) {
                InterfaceDefinitionWithBLOBs interfaceDefinitionWithBLOBs = this.compareApi(apidoc, systemName, branch);
                if (interfaceDefinitionWithBLOBs.getId() != null) {
                    updateList.add(interfaceDefinitionWithBLOBs);
                } else {
                    insertList.add(interfaceDefinitionWithBLOBs);
                }
            }
        }
        interfaceDefinitionService.interfaceOperator(insertList, updateList);
        log.info("--------" + systemName + "--" + branch + " 执行结束---------");
    }

    @Override
    public void syncAllApidoc(String path, String projectPath, String systemName, String branch) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String jsonstr = new String(Files.readAllBytes(Paths.get(path)),"utf-8");
        String projectJsonstr = new String(Files.readAllBytes(Paths.get(projectPath)),"utf-8");

        JSONObject projectJson = JSONObject.parseObject(projectJsonstr);
        if (projectJson.containsKey("url")) {
            String projecturl = String.valueOf(projectJson.get("url"));
            this.httpProtocol = projecturl.startsWith("https") ? "https" : "http";
        } else {
            this.httpProtocol = null;
        }

        log.info("--------" + systemName + "--" + branch + " 开始执行---------");
        List<Apidoc> apidocs = ApiDocSyncServiceUtil.loadApiStr(jsonstr);
        List<InterfaceDefinitionWithBLOBs> insertList = new ArrayList<>();
        List<InterfaceDefinitionWithBLOBs> updateList = new ArrayList<>();
        for (Apidoc apidoc : apidocs) {
            if (apidoc.getUrl() != null && apidoc.getUrl().length() > 0) {
                InterfaceDefinitionWithBLOBs interfaceDefinitionWithBLOBs = this.compareApi(apidoc, systemName, branch);
                if (interfaceDefinitionWithBLOBs.getId() != null) {
                    updateList.add(interfaceDefinitionWithBLOBs);
                } else {
                    insertList.add(interfaceDefinitionWithBLOBs);
                }
            }
        }
        interfaceDefinitionService.interfaceOperator(insertList, updateList);
        log.info("--------" + systemName + "--" + branch + " 执行结束---------");
    }

    @Override
    public void createApiDocs(String systemName, String branch) throws IOException {
        String gitUrl = ApiDocEnum.getApiEnumByName(systemName).getUrl();
//        URL path = this.getClass().getResource("/");
//        String rootPath = path.getPath().substring(0, path.getPath().indexOf("/WEB-INF/"));
        String rootPath = System.getProperty("user.dir");
//        rootPath = rootPath.substring(0,rootPath.indexOf("/WEB-INF/"));
        System.out.println("root path is ");
        System.out.println(rootPath);
        String codePath = rootPath + "/codes/" + systemName + "/" + branch;
        System.out.println(codePath);
        String apiPath = rootPath + "/apidocs/" + systemName + "/" + branch;
        System.out.println(apiPath);
        deleteFile(new File(codePath));
        cloneCode(gitUrl, new File(codePath), branch);
        System.out.println("conde is ok");
        deleteFile(new File(apiPath));
        createApisHtml(codePath, apiPath);
        System.out.println("api create is ok");

    }


    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {


//        String[] s = {
//                "http://usr.stb.com/usr-doc/api_data.json",
//                "http://acc.stb.com/acc-doc/api_data.json",
//                "http://pay.stb.com/pay-doc/api_data.json",
//                "http://loan.stb.com/loan-doc/api_data.json",
//                "http://invt.stb.com/invt-doc/api_data.json",
////                "http://mkt.stb.com/mkt-doc/api_data.json",
//                "http://192.168.1.128:8080/trd-doc/api_data.json",
//                "http://infra.stb.com/infra-doc/api_data.json",
//                "http://prod.stb.com/prod-doc/api_data.json",
//                "http://192.168.3.44:8080/apidoc/api_data.json"};
//        Set<String> set = new HashSet<>();
//        for (String str : s) {
//            List<Apidoc> apidocs = ApiDocSyncServiceUtil.loadApi(str);
//            apidocs.forEach(apidoc -> {
//                if (apidoc != null && apidoc.getParameter() != null && apidoc.getParameter().getFields() != null) {
//                    apidoc.getParameter().getFields().getParameter().forEach(parameter_ -> {
//                        set.add(parameter_.getType());
//                    });
//                }
//                if (apidoc != null && apidoc.getSuccess() != null && apidoc.getSuccess().getFields() != null) {
//                    apidoc.getSuccess().getFields().getSuccess200().forEach(success200 -> {
//                        set.add(success200.getType());
//                    });
//                }
//            });
//        }
//
//        set.forEach(s1 -> {
//            System.out.println(s1);
//        });

//        List<Apidoc> apidocs = ApiDocSyncServiceUtil.loadApi("http://acc.stb.com/acc-doc/api_data.json");
//        apidocs.forEach(apidoc -> {
//            apidoc.getParameter().getFields().getParameter().forEach(parameter_ -> {
//                System.out.println(parameter_.getType());
//            });
//            Success success = apidoc.getSuccess();
//            if (success!=null){
//                success.getFields().getSuccess200().forEach(success200 -> {
//                    System.out.println(success200.getType());
//                });
//            }
//        });


//        List<Apidoc> apifilters = apidocs.stream().filter(api -> api.getUrl().equals("/acc-app/fundquery/queryAccountBalance")).collect(Collectors.toList());
//        String str  = ApiDocSyncServiceUtil.parameter2json(apifilters.get(0).getParameter());
//        String str = ApiDocSyncServiceUtil.response2json(apifilters.get(0).getSuccess());

//        System.out.println(str);

    }
    */
}

