package com.nonobank.inter.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by tangrubei on 2018/3/12.
 */



public class GitRequestEntity {


    public static interface ValidateSyncApi {};
    public static interface ValidateGetBranchs {};

    @NotNull(message = "系统不能为空",groups = {ValidateSyncApi.class,ValidateGetBranchs.class})
    @Length(message = "系统不能为空",min = 1,groups = {ValidateSyncApi.class,ValidateGetBranchs.class})
    private String system;


    @NotNull(message = "别名不能为空",groups = {ValidateSyncApi.class})
    @Length(message = "别名不能为空",min = 1,groups = {ValidateSyncApi.class})
    private String alias;



    @NotNull(message = "git地址不能为空",groups = {ValidateSyncApi.class,ValidateGetBranchs.class})
    @Length(message = "git地址不能为空",min = 1,groups = {ValidateSyncApi.class,ValidateGetBranchs.class})
    private String gitAddress;

    @NotNull(message = "分支不能为空",groups = {ValidateSyncApi.class})
    @Length(message = "分支不能为空",min = 1,groups = {ValidateSyncApi.class})
    private String branch;

    @NotNull(message = "版本号不能为空",groups = {ValidateSyncApi.class})
    @Length(message = "版本号不能为空",min = 0,groups = {ValidateSyncApi.class})
    private String versionCode;

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getGitAddress() {
        return gitAddress;
    }

    public void setGitAddress(String gitAddress) {
        this.gitAddress = gitAddress;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
