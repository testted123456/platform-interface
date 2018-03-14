package com.nonobank.inter.component.sync;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangrubei on 2018/3/13.
 */
public enum SyncContext {

    INSTANCE;

    private Map<String,String> map;

    SyncContext(){
        map = new HashMap<>();
    }

    public Map<String, String> getMap() {
        return map;
    }





    public static void main(String[] args) {
        Map a = SyncContext.INSTANCE.getMap();
        a.put("aa","bbb");
        Map b = SyncContext.INSTANCE.getMap();
        b.put("cc","dd");
        System.out.println("ok");
    }


}
