package com.nonobank.inter.service;

import com.nonobank.inter.component.result.Result;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by tangrubei on 2018/3/8.
 */
public interface GitService {

    public List<Object> getRemoteRepositories(String system,String gitAddress) throws GitAPIException;

    public void syncBranch(String system,String alias, String gitAddress, String branch, String versionCode) throws GitAPIException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;


    public void checkCode(String system, String branch);
}
