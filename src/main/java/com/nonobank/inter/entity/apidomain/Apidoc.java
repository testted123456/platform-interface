
package com.nonobank.inter.entity.apidomain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.io.Serializable;

@Generated("org.jsonschema2pojo")
public class Apidoc implements Serializable {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("parameter")
    @Expose
    private Parameter parameter;
    @SerializedName("success")
    @Expose
    private Success success;
    @SerializedName("error")
    @Expose
    private java.lang.Error error;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("groupTitle")
    @Expose
    private String groupTitle;

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group The group
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * @return The version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The parameter
     */
    public Parameter getParameter() {
        return parameter;
    }

    /**
     * @param parameter The parameter
     */
    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    /**
     * @return The success
     */
    public Success getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(Success success) {
        this.success = success;
    }

    /**
     * @return The error
     */
    public java.lang.Error getError() {
        return error;
    }

    /**
     * @param error The error
     */
    public void setError(java.lang.Error error) {
        this.error = error;
    }

    /**
     * @return The filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename The filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return The groupTitle
     */
    public String getGroupTitle() {
        return groupTitle;
    }

    /**
     * @param groupTitle The groupTitle
     */
    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

}
