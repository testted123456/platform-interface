package com.nonobank.inter.entity.apidomain;

/**
 * Created by tangrubei on 16/12/7.
 */
public enum ApiCollectionType {
    ListType("List"),ObjectType("Object"),StringArray("String[]");


    private String type;
    private ApiCollectionType(String type){
        this.type = type;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
