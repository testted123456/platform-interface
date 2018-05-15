package com.nonobank.inter.controller;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.nonobank.inter.component.convert.ApiConvert;
import com.nonobank.inter.component.result.Result;
import com.nonobank.inter.component.result.ResultCode;
import com.nonobank.inter.component.result.ResultUtil;
import com.nonobank.inter.component.security.manager.MyAccessDecisionManager;
import com.nonobank.inter.component.sync.SyncContext;
import com.nonobank.inter.entity.GitRequestEntity;
import com.nonobank.inter.entity.InterfaceDefinition;
import com.nonobank.inter.entity.InterfaceDefinitionFront;
import com.nonobank.inter.service.GitService;
import com.nonobank.inter.service.InterfaceDefinitionService;
import com.nonobank.inter.util.UserUtil;

@Controller
@RequestMapping(value = "api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InterfaceController {
	
	public static Logger logger = LoggerFactory.getLogger(InterfaceController.class);
	
	@Autowired
	InterfaceDefinitionService interfaceDefinitionService;

	@Autowired
	private GitService gitService;

	
	@Autowired
	MyAccessDecisionManager myAccessDecisionManager;
	
	/**
	 * reload url权限
	 * @return
	 */
	@GetMapping(value="initUrlRole")
	@ResponseBody
	public Result initUrlRole(){
		myAccessDecisionManager.initUrlMap();
		return ResultUtil.success();
	}
	
	/**
	 * 新增api目录节点
	 * @param
	 * @return
	 */
	@PostMapping(value="addApiDir")
	@ResponseBody
	public Result addDir(@RequestBody InterfaceDefinitionFront interfaceDefinitionFront){
		logger.info("开始新增接口目录");
		
		String userName = UserUtil.getUser();
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
	 * @param
	 * @return
	 */
	@PostMapping(value="addApi")
	@ResponseBody
	public Result addApi(@RequestBody @Valid InterfaceDefinitionFront interfaceDefinitionFront, BindingResult bindingResult){
		logger.info("开始新增接口");
		
		InterfaceDefinition interfaceDefinition = ApiConvert.convertFront2Api(interfaceDefinitionFront);
		
		if(bindingResult.hasErrors()){
			String erroMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
			logger.error("接口校验失败：{}", erroMsg);
			return ResultUtil.error(ResultCode.VALIDATION_ERROR.getCode(), erroMsg);
		}else{
			interfaceDefinition.setOptstatus((short)0);
			interfaceDefinition.setType(true);
			interfaceDefinition.setCreatedBy(UserUtil.getUser());
			interfaceDefinition.setCreatedTime(LocalDateTime.now());
			InterfaceDefinition api = interfaceDefinitionService.add(interfaceDefinition);
			logger.info("开始新增接口成功，接口ID：", api.getId());
			return ResultUtil.success(api);
		}
	}
	
	/**
	 * 更新api
	 * @param
	 * @param interfaceDefinitionFront
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value="updateApi")
	@ResponseBody
	public Result updateApi( @RequestBody @Valid InterfaceDefinitionFront interfaceDefinitionFront, BindingResult bindingResult){
		logger.info("开始修改api信息");
		
		if(null == interfaceDefinitionFront.getId()){
			return ResultUtil.error(ResultCode.VALIDATION_ERROR.getCode(), "要更新的接口不存在！");
		}
		
		InterfaceDefinition interfaceDefinition = ApiConvert.convertFront2Api(interfaceDefinitionFront);
		String userName = UserUtil.getUser();
		interfaceDefinition.setUpdatedBy(userName);
		interfaceDefinition.setUpdatedTime(LocalDateTime.now());
		interfaceDefinition = interfaceDefinitionService.update(interfaceDefinition);
		
		return ResultUtil.success(interfaceDefinition);
	}
	
	@PostMapping(value="updateApiDir")
	@ResponseBody
	public Result updateApiDir(@RequestBody InterfaceDefinitionFront interfaceDefinitionFront){
		logger.info("开始更新接口目录：{}", interfaceDefinitionFront.getName());
		
		if(interfaceDefinitionFront.getId() == null){
			return ResultUtil.error(ResultCode.VALIDATION_ERROR.getCode(), "要更新的记录不存在");
		}
		
//		InterfaceDefinition interfaceDefinition = new InterfaceDefinition();
//		interfaceDefinition.setId(interfaceDefinitionFront.getId());
//		interfaceDefinition.setName(interfaceDefinitionFront.getName());
//		interfaceDefinition.setDescription(interfaceDefinitionFront.getDescription());
//		interfaceDefinition.setUpdatedBy(UserUtil.getUser());
//		interfaceDefinition.setUpdatedTime(LocalDateTime.now());
//		interfaceDefinition.setpId(interfaceDefinitionFront.getpId());
//		interfaceDefinition.setOptstatus((short)0);
		InterfaceDefinition interfaceDefinition = ApiConvert.convertFront2Api(interfaceDefinitionFront);
		interfaceDefinition.setUpdatedBy(UserUtil.getUser());
		interfaceDefinition.setUpdatedTime(LocalDateTime.now());
		interfaceDefinition = interfaceDefinitionService.update(interfaceDefinition);
		return ResultUtil.success();
	}
	
	/**
	 * 删除api
	 * @param
	 * @param id
	 * @return
	 */
	@GetMapping(value="delApi")
	@ResponseBody
	public Result delApi( @RequestParam(required=true) Integer id){
		logger.info("开始删除api，id：{}", id);
		
		InterfaceDefinition api = interfaceDefinitionService.findById(id);
		
		if(null != api){
			api.setOptstatus((short)2);
			api.setUpdatedBy(UserUtil.getUser());
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
	 * @param
	 * @param id
	 * @return
	 */
	@GetMapping(value="delApiDir")
	@ResponseBody
	public Result delApiDir(@RequestParam(required=true) Integer id){
		logger.info("开始删除apiDir，id：{}", id);
		interfaceDefinitionService.delApiDir(UserUtil.getUser(), id);
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
	
	@GetMapping(value="getLastApis")
	@ResponseBody
	public Result getLastApi(@RequestParam String system, @RequestParam String module, @RequestParam String urlAddress, @RequestParam String branch){
		logger.info("根据id获取相同api");
		InterfaceDefinition api = interfaceDefinitionService.findBySystemAndModuleAndUrlAddress(system, module, urlAddress, branch);
		return ResultUtil.success(api);
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
		if (SyncContext.INSTANCE.getMap().get(gitSyncEntity.getSystem()+gitSyncEntity.getBranch())!=null){
			return ResultUtil.error(ResultCode.VALIDATION_ERROR.getCode(),"系统正在执行中，请稍等");
		}
		if (bindingResult.hasErrors()) {
			String erroMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
			logger.error("获取远程分支失败：{}", erroMsg);
			return ResultUtil.error(ResultCode.VALIDATION_ERROR.getCode(), erroMsg);
		}
		gitService.syncBranch(gitSyncEntity.getSystem(),gitSyncEntity.getAlias(),gitSyncEntity.getGitAddress(),gitSyncEntity.getBranch(),gitSyncEntity.getVersionCode());
		return ResultUtil.success();
	}
	
	@PostMapping(value="findByIds")
	@ResponseBody
	public Result findByIds(@RequestBody List<Integer> ids){
		logger.info("开始查找接口");
		List<InterfaceDefinition> apis = interfaceDefinitionService.findByIdIn(ids);
		return ResultUtil.success(apis);
	}


	@GetMapping(value = "getPath")
	@ResponseBody
	public String getPath() throws GitAPIException {
		ApplicationHome home = new ApplicationHome(this.getClass());
		return home.getDir().getPath();
	}
	
	@GetMapping(value = "searchApi")
	@ResponseBody
	public Result searchApi(@RequestParam String name, @RequestParam String urlAddress, @RequestParam String branch, @RequestParam String module, @RequestParam String system){
		logger.info("开始过滤接口");
		List<InterfaceDefinition> list = interfaceDefinitionService.serarchApi(name, urlAddress, branch, module, system, true);
		List<InterfaceDefinitionFront> idff = new ArrayList<>();
		list.forEach(x->{
			idff.add(ApiConvert.convertApi2Front(x));
		});
		return ResultUtil.success(idff);
	}
	
}
