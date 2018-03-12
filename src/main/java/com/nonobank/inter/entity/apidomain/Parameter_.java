
package com.nonobank.inter.entity.apidomain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Parameter_ implements Serializable {

    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("optional")
    @Expose
    private Boolean optional;
    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("description")
    @Expose
    private String description;


    @SerializedName("allowedValues")
    @Expose
    private List<String> allowedValues;

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
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
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

    public List<String> getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(List<String> allowedValues) {
        this.allowedValues = allowedValues;
    }
}
