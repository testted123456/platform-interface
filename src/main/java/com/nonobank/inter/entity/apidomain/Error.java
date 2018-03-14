
package com.nonobank.inter.entity.apidomain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Error implements Serializable {

    @SerializedName("fields")
    @Expose
    private Fields__ fields;
    @SerializedName("examples")
    @Expose
    private List<Example_> examples = new ArrayList<Example_>();

    /**
     * 
     * @return
     *     The fields
     */
    public Fields__ getFields() {
        return fields;
    }

    /**
     * 
     * @param fields
     *     The fields
     */
    public void setFields(Fields__ fields) {
        this.fields = fields;
    }

    /**
     * 
     * @return
     *     The examples
     */
    public List<Example_> getExamples() {
        return examples;
    }

    /**
     * 
     * @param examples
     *     The examples
     */
    public void setExamples(List<Example_> examples) {
        this.examples = examples;
    }

}
