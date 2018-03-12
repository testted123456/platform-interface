package com.nonobank.inter.controller;

<<<<<<< HEAD
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
=======
import java.time.LocalDateTime;
import java.util.ArrayList;
>>>>>>> 8df8ce158e08f8a03033838c57e7b1c744b1a8f3
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import com.nonobank.inter.entity.GitRequestEntity;
import com.nonobank.inter.service.GitService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
<<<<<<< HEAD
import org.springframework.validation.annotation.Validated;
=======
import org.springframework.web.bind.annotation.CookieValue;
>>>>>>> 8df8ce158e08f8a03033838c57e7b1c744b1a8f3
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nonobank.inter.component.convert.ApiConvert;
import com.nonobank.inter.component.result.Result;
import com.nonobank.inter.component.result.ResultCode;
import com.nonobank.inter.component.result.ResultUtil;
import com.nonobank.inter.entity.InterfaceDefinition;
import com.nonobank.inter.entity.InterfaceDefinitionFront;
import com.nonobank.inter.service.InterfaceDefinitionService;

@Controller
@RequestMapping(value = "api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InterfaceController {
<<<<<<< HEAD

    public static Logger logger = LoggerFactory.getLogger(InterfaceController.class);

    @Autowired
    InterfaceDefinitionService interfaceDefinitionService;

    @Autowired
    private GitService gitService;

    /**
     * 新增api目录节点
     *
     * @param
     * @return
     */
    @PostMapping(value = "addApiDir")
    @ResponseBody
    public Result addDir(@RequestBody InterfaceDefinitionFront interfaceDefinitionFront) {
        logger.info("开始新增接口目录");

        InterfaceDefinition interfaceDefinition = ApiConvert.convertFront2Api(interfaceDefinitionFront);
        interfaceDefinition.setType(false);
        interfaceDefinition.setOptstatus((short) 0);
        InterfaceDefinition api = interfaceDefinitionService.add(interfaceDefinition);

        logger.info("开始新增接口目录成功，接口ID：", api.getId());
        return ResultUtil.success(api);
    }

    /**
     * 新增api
     *
     * @param
     * @return
     */
    @PostMapping(value = "addApi")
    @ResponseBody
    public Result addApi(@RequestBody @Valid InterfaceDefinitionFront interfaceDefinitionFront, BindingResult bindingResult) {
        logger.info("开始新增接口");

        InterfaceDefinition interfaceDefinition = ApiConvert.convertFront2Api(interfaceDefinitionFront);

        if (bindingResult.hasErrors()) {
            String erroMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            logger.error("接口校验失败：{}", erroMsg);
            return ResultUtil.error(ResultCode.VALIDATION_ERROR.getCode(), erroMsg);
        } else {
            interfaceDefinition.setOptstatus((short) 0);
            interfaceDefinition.setType(true);
            InterfaceDefinition api = interfaceDefinitionService.add(interfaceDefinition);
            logger.info("开始新增接口成功，接口ID：", api.getId());
            return ResultUtil.success(api);
        }
    }

    @PostMapping(value = "updateApi")
    @ResponseBody
    public Result updateApi(@RequestBody @Valid InterfaceDefinitionFront interfaceDefinitionFront, BindingResult bindingResult) {
        logger.info("开始修改api信息");

        InterfaceDefinition interfaceDefinition = ApiConvert.convertFront2Api(interfaceDefinitionFront);

        if (bindingResult.hasErrors()) {
            String erroMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            logger.error("接口校验失败：{}", erroMsg);
            return ResultUtil.error(ResultCode.VALIDATION_ERROR.getCode(), erroMsg);
        } else {
            Optional<InterfaceDefinition> optApi = Optional.ofNullable(interfaceDefinitionService.findById(interfaceDefinition.getId()));
            Optional<InterfaceDefinition> updatedOptApi = optApi.map(a -> interfaceDefinitionService.update(a));

            if (updatedOptApi.isPresent()) {
                logger.info("修改api信息成功");
                return ResultUtil.success(updatedOptApi.get());
            } else {
                logger.error("更新api失败");
                return ResultUtil.error(ResultCode.VALIDATION_ERROR.getCode(), "要更新的记录不存在");
            }
        }
    }

    /**
     * 获取api目录节点
     *
     * @param id
     * @return
     */
    @GetMapping(value = "getApi")
    @ResponseBody
    public Result getApi(@RequestParam(required = true) Integer id) {

        logger.info("开始查询api信息");

        Optional<InterfaceDefinition> optApi = Optional.ofNullable(interfaceDefinitionService.findById(id));

        if (optApi.isPresent()) {
            logger.info("查询api信息成功， id：{}", id);
            InterfaceDefinition api = optApi.get();
            InterfaceDefinitionFront apiFront = ApiConvert.convertApi2Front(api);
            return ResultUtil.success(apiFront);
        } else {
            logger.error("查询api信息失败， id：{}", id);
            return ResultUtil.error(ResultCode.EMPTY_ERROR.getCode(), "查询记录不存在");
        }
    }

    /**
     * 根据父节点Id查询子节点树
     *
     * @param pId
     * @return
     */
    @GetMapping(value = "getApiTreeByPId")
    @ResponseBody
    public Result getApiTreeByPId(@RequestParam Integer pId) {
        logger.info("开始查询api数信息");

        List<InterfaceDefinition> interfaceDefinitions = interfaceDefinitionService.findByPId(pId);
        return ResultUtil.success(interfaceDefinitions);
    }


    /**
     * 根据父节点Id查询子节点树
     *
     *
     * @param
     * @param
     * @return
     */
    @PostMapping(value = "getBranchs")
    @ResponseBody
    public Result getBranchs(@RequestBody @Validated(GitRequestEntity.ValidateGetBranchs.class) GitRequestEntity gitSyncEntity, BindingResult bindingResult) throws GitAPIException {
        logger.info("开始获取git远程分支");
        if (bindingResult.hasErrors()) {
            String erroMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            logger.error("获取远程分支失败：{}", erroMsg);
            return ResultUtil.error(ResultCode.VALIDATION_ERROR.getCode(), erroMsg);
        } else {
            List<Object> list = gitService.getRemoteRepositories(gitSyncEntity.getSystem(), gitSyncEntity.getGitAddress());
            return ResultUtil.success(list);
        }
    }


    /**
     * 根据父节点Id查询子节点树
     *
     *
     * @param
     * @param
     * @return
     */
    @PostMapping(value = "syncApidoc")
    @ResponseBody
    public Result syncApidoc(@RequestBody @Validated(GitRequestEntity.ValidateSyncApi.class) GitRequestEntity gitSyncEntity, BindingResult bindingResult) throws GitAPIException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        logger.info("开始获取git远程分支");
        if (bindingResult.hasErrors()) {
            String erroMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            logger.error("获取远程分支失败：{}", erroMsg);
            return ResultUtil.error(ResultCode.VALIDATION_ERROR.getCode(), erroMsg);
        } else {
            gitService.syncBranch(gitSyncEntity.getSystem(),gitSyncEntity.getGitAddress(),gitSyncEntity.getBranch(),gitSyncEntity.getVersionCode());
            return ResultUtil.success();
        }
    }
=======
	
	public static Logger logger = LoggerFactory.getLogger(InterfaceController.class);
	
	@Autowired
	InterfaceDefinitionService interfaceDefinitionService;
	
	/**
	 * 新增api目录节点
	 * @param interfaceDefinition
	 * @return
	 */
	@PostMapping(value="addApiDir")
	@ResponseBody
	public Result addDir(@CookieValue(value="nonousername",required=false) String userName, @RequestBody InterfaceDefinitionFront interfaceDefinitionFront){
		logger.info("开始新增接口目录");
		
		InterfaceDefinition interfaceDefinition = ApiConvert.convertFront2Api(interfaceDefinitionFront);
		interfaceDefinition.setType(false);
		interfaceDefinition.setOptstatus((short)0);
		interfaceDefinition.setCreatedBy(userName);
		interfaceDefinition.setCreatedTime(LocalDateTime.now());
		InterfaceDefinition api = interfaceDefinitionService.add(interfaceDefinition);
		logger.info("开始新增接口目录成功，接口ID：", api.getId());
		return ResultUtil.success(api);
	}
	
	/**
	 * 新增api
	 * @param interfaceDetail
	 * @return
	 */
	@PostMapping(value="addApi")
	@ResponseBody
	public Result addApi(@CookieValue(value="nonousername",required=false) String userName, @RequestBody @Valid InterfaceDefinitionFront interfaceDefinitionFront, BindingResult bindingResult){
		logger.info("开始新增接口");
		
		InterfaceDefinition interfaceDefinition = ApiConvert.convertFront2Api(interfaceDefinitionFront);
		
		if(bindingResult.hasErrors()){
			String erroMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
			logger.error("接口校验失败：{}", erroMsg);
			return ResultUtil.error(ResultCode.VALIDATION_ERROR.getCode(), erroMsg);
		}else{
			interfaceDefinition.setOptstatus((short)0);
			interfaceDefinition.setType(true);
			interfaceDefinition.setCreatedBy(userName);
			interfaceDefinition.setCreatedTime(LocalDateTime.now());
			InterfaceDefinition api = interfaceDefinitionService.add(interfaceDefinition);
			logger.info("开始新增接口成功，接口ID：", api.getId());
			return ResultUtil.success(api);
		}
	}
	
	/**
	 * 更新api
	 * @param userName
	 * @param interfaceDefinitionFront
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value="updateApi")
	@ResponseBody
	public Result updateApi(@CookieValue(value="nonousername",required=false) String userName, @RequestBody @Valid InterfaceDefinitionFront interfaceDefinitionFront, BindingResult bindingResult){
		logger.info("开始修改api信息");
		
		InterfaceDefinition interfaceDefinition = ApiConvert.convertFront2Api(interfaceDefinitionFront);
		
		if(bindingResult.hasErrors()){
			String erroMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
			logger.error("接口校验失败：{}", erroMsg);
			return ResultUtil.error(ResultCode.VALIDATION_ERROR.getCode(), erroMsg);
		}else{
			Optional<InterfaceDefinition> optApi = Optional.ofNullable(interfaceDefinitionService.findById(interfaceDefinition.getId()));
			Optional<InterfaceDefinition> updatedOptApi = optApi.map(a->interfaceDefinitionService.update(a));
			
			if(updatedOptApi.isPresent()){
				logger.info("修改api信息成功");
				InterfaceDefinition updatedApi = updatedOptApi.get();
				updatedApi.setUpdatedTime(LocalDateTime.now());
				updatedApi.setUpdatedBy(userName);
				return ResultUtil.success(updatedApi);
			}else{
				logger.error("更新api失败");
				return ResultUtil.error(ResultCode.VALIDATION_ERROR.getCode(), "要更新的记录不存在");
			}
		}
	}
	
	/**
	 * 删除api
	 * @param userName
	 * @param id
	 * @return
	 */
	@GetMapping(value="delApi")
	@ResponseBody
	public Result delApi(@CookieValue(value="nonousername",required=false) String userName, @RequestParam(required=true) Integer id){
		logger.info("开始删除api，id：{}", id);
		
		InterfaceDefinition api = interfaceDefinitionService.findById(id);
		
		if(null != api){
			api.setOptstatus((short)2);
			api.setUpdatedBy(userName);
			interfaceDefinitionService.update(api);
			logger.info("删除api成功，id:{}", id);
		}else{
			logger.error("待删除的api不存在, id:{}", id);
			return ResultUtil.error(ResultCode.EMPTY_ERROR.getCode(), "待删除的api不存在");
		}
		
		return ResultUtil.success();
	}
	
	/**
	 * 删除apiDir
	 * @param userName
	 * @param id
	 * @return
	 */
	@GetMapping(value="delApiDir")
	@ResponseBody
	public Result delApiDir(@CookieValue(value="nonousername",required=false) String userName, @RequestParam(required=true) Integer id){
		logger.info("开始删除apiDir，id：{}", id);
		interfaceDefinitionService.delApiDir(userName, id);
		logger.info("开始删除apiDir成功，id：{}", id);
		return ResultUtil.success();
	}
	
	/**
	 * 获取api目录节点
	 * @param id
	 * @return
	 */
	@GetMapping(value="getApi")
	@ResponseBody
	public Result getApi(@RequestParam(required=true) Integer id){
		
		logger.info("开始查询api信息");
		
		Optional<InterfaceDefinition> optApi = Optional.ofNullable(interfaceDefinitionService.findById(id));
		
		if(optApi.isPresent()){
			logger.info("查询api信息成功， id：{}", id);
			InterfaceDefinition api = optApi.get();
			InterfaceDefinitionFront apiFront = ApiConvert.convertApi2Front(api);
			return ResultUtil.success(apiFront);
		}else{
			logger.error("查询api信息失败， id：{}", id);
			return ResultUtil.error(ResultCode.EMPTY_ERROR.getCode(), "查询记录不存在");
		}
	}
	
	/**
	 * 根据父节点Id查询子节点树
	 * @param pId
	 * @return
	 */
	@GetMapping(value="getApiTreeByPId")
	@ResponseBody
	public Result getApiTreeByPId(@RequestParam Integer pId){
		logger.info("开始查询api树信息");
		List<InterfaceDefinition> interfaceDefinitions = interfaceDefinitionService.findByPId(pId);
		List<InterfaceDefinitionFront> frontInterfaceDefinitions = new ArrayList<InterfaceDefinitionFront>();
		interfaceDefinitions.forEach(x->{
			frontInterfaceDefinitions.add(ApiConvert.convertApi2Front(x));
		});
		return ResultUtil.success(frontInterfaceDefinitions);
	}
	
	@GetMapping(value="getSameApis")
	@ResponseBody
	public Result getSameApis(@RequestParam Integer id){
		logger.info("根据id获取相同api");
		
		InterfaceDefinition api = interfaceDefinitionService.findById(id);
		String system = api.getSystem();
		String module = api.getModule();
		String urlAddress = api.getUrlAddress();
		List<InterfaceDefinition> apis = interfaceDefinitionService.findBySystemAndModuleAndUrlAddress(system, module, urlAddress);
		
		return ResultUtil.success(apis);
	}
	
>>>>>>> 8df8ce158e08f8a03033838c57e7b1c744b1a8f3
}
