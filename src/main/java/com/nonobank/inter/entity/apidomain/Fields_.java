
package com.nonobank.inter.entity.apidomain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Fields_ implements Serializable {

    @SerializedName("Success 200")
    @Expose
    private List<Success200> success200 = new ArrayList<Success200>();

    /**
     * 
     * @return
     *     The success200
     */
    public List<Success200> getSuccess200() {
        return success200;
    }

    /**
     * 
     * @param success200
     *     The Success 200
     */
    public void setSuccess200(List<Success200> success200) {
        this.success200 = success200;
    }

}
