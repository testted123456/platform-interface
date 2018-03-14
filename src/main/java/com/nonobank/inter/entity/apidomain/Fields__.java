
package com.nonobank.inter.entity.apidomain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Fields__ implements Serializable {

    @SerializedName("Error 4xx")
    @Expose
    private List<Error4xx> error4xx = new ArrayList<Error4xx>();

    /**
     * 
     * @return
     *     The error4xx
     */
    public List<Error4xx> getError4xx() {
        return error4xx;
    }

    /**
     * 
     * @param error4xx
     *     The Error 4xx
     */
    public void setError4xx(List<Error4xx> error4xx) {
        this.error4xx = error4xx;
    }

}
