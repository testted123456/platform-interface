
package com.nonobank.inter.entity.apidomain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Fields implements Serializable {

    @SerializedName("Parameter")
    @Expose
    private List<Parameter_> parameter = new ArrayList<Parameter_>();

    /**
     * 
     * @return
     *     The parameter
     */
    public List<Parameter_> getParameter() {
        return parameter;
    }

    /**
     * 
     * @param parameter
     *     The Parameter
     */
    public void setParameter(List<Parameter_> parameter) {
        this.parameter = parameter;
    }

}
