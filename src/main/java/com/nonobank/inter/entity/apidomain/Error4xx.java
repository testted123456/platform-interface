
package com.nonobank.inter.entity.apidomain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.io.Serializable;

@Generated("org.jsonschema2pojo")
public class Error4xx implements Serializable {

    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("optional")
    @Expose
    private Boolean optional;
    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * 
     * @return
     *     The group
     */
    public String getGroup() {
        return group;
    }

    /**
     * 
     * @param group
     *     The group
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * 
     * @return
     *     The optional
     */
    public Boolean getOptional() {
        return optional;
    }

    /**
     * 
     * @param optional
     *     The optional
     */
    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    /**
     * 
     * @return
     *     The field
     */
    public String getField() {
        return field;
    }

    /**
     * 
     * @param field
     *     The field
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
