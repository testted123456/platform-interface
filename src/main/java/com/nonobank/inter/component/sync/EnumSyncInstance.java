package com.nonobank.inter.component.sync;

/**
 * Created by tangrubei on 2018/3/13.
 */
public enum EnumSyncInstance {

    INSTANCE("aa2"), INSTANCE2("aa3");

    private String value;

    private EnumSyncInstance(String value){
        this.value = "aaa";
    }

    public String getValue(){
        return value;
    }

    public static void main(String[] args) {
        System.out.println(EnumSyncInstance.INSTANCE.getValue());
    }

//    public void
}
