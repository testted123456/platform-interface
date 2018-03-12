package com.nonobank.inter.component.sync;

import com.alibaba.fastjson.JSONObject;
import com.nonobank.inter.entity.InterfaceDefinition;
import com.nonobank.inter.entity.apidomain.Apidoc;
import com.nonobank.inter.repository.InterfaceDefinitionRepository;
import com.nonobank.inter.util.ApidocUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by tangrubei on 2018/3/9.
 */
@Component
public class IfromAComponent {

    @Autowired
    private InterfaceDefinitionRepository interfaceDefinitionRepository;

    private static final String TYPE_POST = "post";

    private static final String PROTOCAL_HTTPS = "https";

    private static final char CHAR_HTTP_TYPE = '0' ;

    private static final char CHAR_HTTPS_TYPE = '1' ;

    private char protocol = '3' ;


    private static Function<String, Character> func_get_portocol_type = content -> content.startsWith(PROTOCAL_HTTPS) ? CHAR_HTTPS_TYPE : CHAR_HTTP_TYPE;

    /**
     * 检查接口是否存在
     *
     * @param apidoc apidoc数据
     * @param system 系统名称
     * @param branch 分支
     * @return
     */
    private InterfaceDefinition checkIsExist(Apidoc apidoc, String system, String branch) {
        InterfaceDefinition interfaceDefinitions = interfaceDefinitionRepository.findByNameAndSystemAndBranchEquals(apidoc.getName(), system, branch);
        return interfaceDefinitions;
    }

    /**
     * 依据项目内容初始化协议
     *
     * @param projectContet 项目内容
     */
    private void initProtocolFromProject(String projectContet) {
        JSONObject projectJson = JSONObject.parseObject(projectContet);
        if (projectJson.containsKey("url")) {
            String projecturl = String.valueOf(projectJson.get("url"));
            this.protocol = func_get_portocol_type.apply(projecturl);
        }
    }


    /**
     * 给interfaceDefinition对象设置值
     *
     * @param interfaceDefinition 传入的interface对象
     * @param apidoc              传入的apidoc对象
     * @param system              系统名称
     * @param branch              分支名称
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private InterfaceDefinition setValueFromApidoc(InterfaceDefinition interfaceDefinition, Apidoc apidoc, String system, String branch) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (null == interfaceDefinition) {
            interfaceDefinition = new InterfaceDefinition();
        }
        interfaceDefinition.setName(apidoc.getTitle());
        interfaceDefinition.setDescription(apidoc.getDescription());
        interfaceDefinition.setPostWay((TYPE_POST.equals(apidoc.getType().toLowerCase()) ? '1' : '0'));
        interfaceDefinition.setUrlAddress(apidoc.getUrl());
        if (interfaceDefinition.getId() == null) {
            interfaceDefinition.setCreatedBy("System");
            interfaceDefinition.setCreatedTime(LocalDateTime.now());
            interfaceDefinition.setRequestHead("{\"Content-Type\": \"application/json\"}");
        } else {
            interfaceDefinition.setUpdatedBy("System");
            interfaceDefinition.setUpdatedTime(LocalDateTime.now());
        }
        interfaceDefinition.setSystem(system);
        interfaceDefinition.setBranch(branch);
        interfaceDefinition.setModule(apidoc.getGroupTitle());
        interfaceDefinition.setOptstatus((short) 0);

        Character apiType = this.protocol != '3' ? this.protocol : func_get_portocol_type.apply(apidoc.getUrl());
        interfaceDefinition.setApiType(apiType);

        interfaceDefinition.setRequestBody(ApidocUtil.parameter2json(apidoc.getParameter()));
        interfaceDefinition.setResponseBody(ApidocUtil.response2json(apidoc.getSuccess()));
        return interfaceDefinition;
    }

    /**
     * 同步接口信息
     *
     * @param system         系统名称
     * @param branch         分支名称
     * @param projectContent 项目内容
     * @param apiDataContent apidata内容
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public void syncApidoc(String system, String branch, String projectContent, String apiDataContent) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        this.initProtocolFromProject(projectContent);
        List<Apidoc> apidocs = ApidocUtil.loadApiStr(apiDataContent);
        List<InterfaceDefinition> interfaceDefinitions = new ArrayList<>();
        for (Apidoc apidoc : apidocs) {
            InterfaceDefinition interfaceDefinition = this.checkIsExist(apidoc, system, branch);
            interfaceDefinition = this.setValueFromApidoc(interfaceDefinition,apidoc,system,branch);
            interfaceDefinitions.add(interfaceDefinition);
        }
        interfaceDefinitionRepository.save(interfaceDefinitions);
    }


}
