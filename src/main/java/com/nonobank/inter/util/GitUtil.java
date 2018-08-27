package com.nonobank.inter.util;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tangrubei on 2018/3/7.
 */
public class GitUtil {



    public static CredentialsProvider createCredentialsProvider(String giturl){
        System.out.println("+++++++++++gitUrl is: " + giturl);
        if(giturl.endsWith("feserver.git")){
            System.out.println("+++++++++++feserver.git");
            return new UsernamePasswordCredentialsProvider("hanliangliang", "Hll123456");
        }else{
            return new UsernamePasswordCredentialsProvider("","");
        }
    }


    /**
     * 创建登陆认证
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    public static CredentialsProvider createCredentialsProvider(String userName,String password){
        return new UsernamePasswordCredentialsProvider(userName,password);
    }


    /**
     * 列出所有系统下所有分支
     * @param gitUrl git地址
     * @param cp 分支名称
     * @return
     * @throws GitAPIException
     */
    public static List<Object> getRemoteRepositories(String gitUrl,CredentialsProvider cp) throws GitAPIException {
        List<Object> ls = Git.lsRemoteRepository()
                .setRemote(gitUrl)
                .setCredentialsProvider(cp).call().
                        stream().
                        filter(git->git instanceof ObjectIdRef.PeeledNonTag).collect(Collectors.toList());
        return ls;
    }

    /**
     * 列出分支的版本
     * @param gitUrl git地址
     * @param branch 分支名称
     * @param cp
     * @return
     * @throws GitAPIException
     */
    public static String getGitBranchVersionCode(String gitUrl,String branch,CredentialsProvider cp) throws GitAPIException {
        String versionCode = Git.lsRemoteRepository()
                .setRemote(gitUrl)
                .setCredentialsProvider(cp).call().
                        stream().
                        filter(git->git instanceof ObjectIdRef.PeeledNonTag).collect(Collectors.toList()).stream().
                        filter(ref -> ref.getName().endsWith(branch)).collect(Collectors.toList()).get(0).getObjectId().getName();
        return versionCode;
    }

//    public static String getApidocVersionCode(String apiPath) throws IOException {
//        String stateJsonpath = apiPath+"/"+STATE_JSON_NAME;
//        if(new File(stateJsonpath).exists()){
//            String jsonStr = readFile(stateJsonpath);
//            JSONObject jsonObject = JSON.parseObject(jsonStr);
//            String version = (String) jsonObject.get("version");
//            return version;
//        }else{
//            return "";
//        }
//    }





    public static void cloneCode(String gitUrl, File directory, String branch,CredentialsProvider cp){
        try (Git git = Git.cloneRepository()
                .setURI(gitUrl)
                .setDirectory(directory)
                .setCredentialsProvider(cp)
                .setBranch(branch).setTimeout(120)
                .call()){
        } catch (GitAPIException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //    create api html

    /**
     * 依据api来创建thml报告
     * @param myappPath app所在路径
     * @param apidocPath
     * @return
     * @throws IOException
     */
    public static String createApisHtml(String myappPath,String apidocPath) throws IOException {
    	File file = new File(apidocPath);
       
    	if(!file.exists()){
        	file.mkdirs();
        }
    	
    	String os = System.getProperty("os.name");
    	if(os.toLowerCase().startsWith("win")){
    		Runtime rt = Runtime.getRuntime();
    		Process p = rt.exec("C:\\Users\\xinxiang\\AppData\\Roaming\\npm\\apidoc.cmd -i " + myappPath+" -o " + apidocPath);
            return p.toString();
    	}
        
        String command = "export PATH=$PATH:/usr/local/bin/;apidoc -i "+myappPath+" -o "+apidocPath;
        
        String[] cmds = { "/bin/sh", "-c", command };
        Process process = Runtime.getRuntime().exec(cmds);
        BufferedReader rd = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
		}
        return process.toString();
    }






    public static void main(String[] args) throws GitAPIException {

        CredentialsProvider cp  =new  UsernamePasswordCredentialsProvider("tangrubei", "Pass2018@");


//        List<Object> list = GitUtil.getRemoteRepositories("http://git.nonobank.com/trd-pay-acc/acc.git",cp);
//        String aa = GitUtil.getGitBranchVersionCode("http://git.nonobank.com/trd-pay-acc/acc.git","master",cp);
        System.out.println("ok");

        System.out.println(LocalDateTime.now());

//        Collection<Ref> ls = Git.lsRemoteRepository()
//                .setRemote("http://git.nonobank.com/trd-pay-acc/acc.git")
//                .setCredentialsProvider(cp).call();
//        List< Object> ls = Git.lsRemoteRepository()
//                .setRemote("http://git.nonobank.com/trd-pay-acc/acc.git")
//                .setCredentialsProvider(cp).call().
//                        stream().
//                        filter(git->git instanceof ObjectIdRef.PeeledNonTag).collect(Collectors.toList());

        System.out.println("ok");

//        try (Git git = Git.cloneRepository()
//                .setURI("http://git.nonobank.com/trd-pay-acc/acc.git")
//                .setDirectory(new File("/Users/tangrubei/Downloads/test"))
//                .setCredentialsProvider(cp)
//                .setBranch("master").setTimeout(120)
//                .call()){
//        } catch (GitAPIException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }



    }
}
