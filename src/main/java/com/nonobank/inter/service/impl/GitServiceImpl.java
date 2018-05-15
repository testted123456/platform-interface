package com.nonobank.inter.service.impl;

import com.nonobank.inter.component.sync.IfromAComponent;
import com.nonobank.inter.component.sync.SyncContext;
import com.nonobank.inter.service.GitService;
import com.nonobank.inter.util.FileUtil;
import com.nonobank.inter.util.GitUtil;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationHome;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.nonobank.inter.component.remoteEntity.RemoteTestCase;
import com.nonobank.inter.component.sync.IfromAComponent;
import com.nonobank.inter.component.sync.SyncContext;
import com.nonobank.inter.service.GitService;
import com.nonobank.inter.util.FileUtil;
import com.nonobank.inter.util.GitUtil;

/**
 * Created by tangrubei on 2018/3/8.
 */

@Service
class GitServiceImpl implements GitService {


    private final static String FEATURE_FLAG = "feature";

    private final static String HOTFIX_FLAG = "hotfix";

    private final static String SLASH_FLAG = "/";

    private final static String HEAD_FLAG = "HEAD";


    @Value("${codePath}")
    private String codePath;

    @Value("${apidocPath}")
    private String apidocPath;

    @Value("${checkReportPath}")
    private String checkReportPath;

    private String username = "tangrubei";

    private String password = "Pass2018@";

    @Autowired
    private IfromAComponent ifromAComponent;

    @Autowired
    private RemoteTestCase remoteTestCase;

//    private static Logger log = LogManager.getLogger(GitServiceImpl.class);
	public static Logger logger = LoggerFactory.getLogger(GitServiceImpl.class);


    /**
     * 获取git远程资源信息
     *
     * @param system     系统名称
     * @param gitAddress 系统地址
     * @return
     * @throws GitAPIException
     */
    @Override
    public List<Object> getRemoteRepositories(String system, String gitAddress) throws GitAPIException {
        CredentialsProvider credentialsProvider = GitUtil.createCredentialsProvider(username, password);
        List<Object> respostories = GitUtil.getRemoteRepositories(gitAddress, credentialsProvider);
        List<Object> branshs = respostories.stream().map(obj -> {
            ObjectIdRef.PeeledNonTag ref = (ObjectIdRef.PeeledNonTag) obj;
            String rename = "";
            if (ref.getName().indexOf(FEATURE_FLAG + SLASH_FLAG) != -1) {
                rename = ref.getName().substring(ref.getName().lastIndexOf(FEATURE_FLAG));
            } else if (ref.getName().indexOf(HOTFIX_FLAG + SLASH_FLAG) != -1) {
                rename = ref.getName().substring(ref.getName().lastIndexOf(HOTFIX_FLAG));
            } else if (ref.getName().indexOf(SLASH_FLAG) != -1) {
                rename = ref.getName().substring(ref.getName().lastIndexOf(SLASH_FLAG) + 1);
            } else {
                rename = ref.getName();
            }
            return rename;
        }).collect(Collectors.toList()).stream().filter(s -> !HEAD_FLAG.equals(s)).collect(Collectors.toList());
        return branshs;
    }

    /**
     * 同步分支信息
     *
     * @param alias       系统萌宠
     * @param gitAddress  git地址
     * @param branch      分支名称
     * @param versionCode 版本号
     * @throws GitAPIException
     * @throws IOException
     */
    @Override
    @Async
    public void syncBranch(String system, String alias, String gitAddress, String branch, String versionCode) {

        try {
            CredentialsProvider credentialsProvider = GitUtil.createCredentialsProvider(username, password);
            String remoteVersionCode = GitUtil.getGitBranchVersionCode(gitAddress, branch, credentialsProvider);
            if (versionCode.equals(remoteVersionCode)) {
//            版本号一直，无需同步，直接返回
                return;
            }

            SyncContext.INSTANCE.getMap().put(system + branch, "running");

            ApplicationHome home = new ApplicationHome(this.getClass());
            File branchCodeDir = new File(home.getDir(), String.format("%s/%s/%s", codePath, system, branch));
            File apidochDir = new File(home.getDir(), String.format("%s/%s/%s", apidocPath, system, branch));
//      删除已存在的代码，并创建文件夹
            if (branchCodeDir.exists()) {
                FileUtil.deleteFile(branchCodeDir);
            } else {
                branchCodeDir.mkdirs();
            }
            System.out.println("开始克隆代码");
            GitUtil.cloneCode(gitAddress, branchCodeDir, branch, credentialsProvider);
            System.out.println("代码克隆结束");

            System.out.println("开始创建apidoc页面");
            GitUtil.createApisHtml(branchCodeDir.getPath(), apidochDir.getPath());
            System.out.println("创建apidoc页面结束");


            String apidataJsonPath = apidochDir.getPath() + "/api_data.json";
            String projectJsonPath = apidochDir.getPath() + "/api_project.json";

            String apiDataContent = FileUtil.readFile(apidataJsonPath);
            String projectContent = FileUtil.readFile(projectJsonPath);
            ifromAComponent.syncApidoc(alias, branch, projectContent, apiDataContent);

        } catch (GitAPIException | IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            SyncContext.INSTANCE.getMap().remove(system + branch);

        }


    }


}
