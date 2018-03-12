
package com.nonobank.inter.entity.apidomain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Success implements Serializable {

    @SerializedName("fields")
    @Expose
    private Fields_ fields;
    @SerializedName("examples")
    @Expose
    private List<Example> examples = new ArrayList<Example>();

    /**
     * 
     * @return
     *     The fields
     */
    public Fields_ getFields() {
        return fields;
    }

    /**
     * 
     * @param fields
     *     The fields
     */
    public void setFields(Fields_ fields) {
        this.fields = fields;
    }

    /**
     * 
     * @return
     *     The examples
     */
    public List<Example> getExamples() {
        return examples;
    }

    /**
     * 
     * @param examples
     *     The examples
     */
    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

}
