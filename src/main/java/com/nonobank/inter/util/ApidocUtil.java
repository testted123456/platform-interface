package com.nonobank.inter.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.nonobank.inter.entity.apidomain.Apidoc;
import com.nonobank.inter.entity.apidomain.Parameter;
import com.nonobank.inter.entity.apidomain.Parameter_;
import com.nonobank.inter.entity.apidomain.Success;
import com.nonobank.inter.entity.apidomain.Success200;

/**
 * Created by tangrubei on 2017/3/28.
 */
public class ApidocUtil {


    private final static String[] MAPTYPE = {"json", "list/dto/map", "map", "map", "object", "json", "xmap"};

    private final static String[] LIST_TYPE = {"xlist", "list", "areadto[]", "object[]", "xlist", "datadictdto[]", "blacklistdto[]", "systemconfigdto[]", "array", "schooldto[]", "articles[]", "coursedto[]", "educationdto[]", "siteflashsetting[]", "bankdto[]"};

    private final static String[] ORIGINAL_TYPES = {"string", "long", "bigdecimal", "integer", "boolean", "tring", "decimal", "date"};


    public static List<Apidoc> loadApi(String jsonUrl) throws IOException {
        Gson gson = new Gson();
        URL url = new URL(jsonUrl);
        InputStream in = url.openStream();
        String myResult = IOUtils.toString(in, "UTF-8");
        JsonReader reader = new JsonReader(new StringReader(myResult));
        reader.setLenient(true);
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(reader);
        Type apidocTypeList = new TypeToken<List<Apidoc>>() {
        }.getType();
        List<Apidoc> apidocList = gson.fromJson(jsonElement, apidocTypeList);
        return apidocList;
    }

    public static List<Apidoc> loadApiStr(String jsonStr) throws IOException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(jsonStr));
        reader.setLenient(true);
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(reader);
        Type apidocTypeList = new TypeToken<List<Apidoc>>() {
        }.getType();
        List<Apidoc> apidocList = gson.fromJson(jsonElement, apidocTypeList);
        return apidocList;
    }


    private static Object ceateObject(Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (obj instanceof Success200) {
            obj = (Success200) obj;
        } else if (obj instanceof Parameter_) {
            obj = (Parameter_) obj;
        }
        Method typeMethod = obj.getClass().getMethod("getType");
        Method desMethod = obj.getClass().getMethod("getDescription");
        if (Arrays.asList(LIST_TYPE).contains(String.valueOf(typeMethod.invoke(obj)).toLowerCase())) {
            return new ArrayList();
        } else if (typeMethod.invoke(obj) == null) {
            return String.valueOf(desMethod.invoke(obj));
        } else if (Arrays.asList(MAPTYPE).contains(String.valueOf(typeMethod.invoke(obj)).toLowerCase()) || !(Arrays.asList(ORIGINAL_TYPES).contains((String.valueOf(typeMethod.invoke(obj)).toLowerCase())))) {
            return new HashMap<String, Object>();
        } else {
            return String.valueOf(desMethod.invoke(obj));
        }
    }


    public static String response2json(Success success) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Map<String, Object> remap = new HashMap<String, Object>();
        if (success != null && success.getFields() != null && success.getFields().getSuccess200() != null) {
            List<Success200> success200List = success.getFields().getSuccess200();
            for (Success200 success200 : success200List) {
                String[] field = success200.getField().split("\\.");
                if (field.length == 1 && remap.get(field[0]) == null) {
                    remap.put(field[0], ceateObject(success200));
                } else if (field.length > 1) {
                    Object currentObject = null;
                    for (int i = 0; i < field.length - 1; i++) {
                        if (currentObject == null) {
                            currentObject = remap.get(field[i]);
                        } else {
                            if (currentObject instanceof Map) {
                                currentObject = ((Map) currentObject).get(field[i]);
                            } else if (currentObject instanceof List) {
                                currentObject = ((List) currentObject).get(0);
                            }
                        }
                    }
                    if (currentObject instanceof List) {
                        if (((List) currentObject).size() > 0) {
                            currentObject = ((List) currentObject).get(0);
                        } else {
                            ((List) currentObject).add(new HashMap<String, Object>());
                            currentObject = ((List) currentObject).get(0);
                        }
                    }
                    Object obj = ceateObject(success200);
                    if (!(currentObject instanceof Map)) {
                        currentObject = new HashMap<>();
                    }
                    ((Map) currentObject).put(field[field.length - 1], obj);
                }
            }
            return ((JSONObject) JSON.toJSON(remap)).toJSONString();
        } else {
            return "";
        }
    }


    public static String parameter2json(Parameter parameter) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Map<String, Object> remap = new HashMap<String, Object>();
        if (parameter != null && parameter.getFields() != null) {
            List<Parameter_> parameters = parameter.getFields().getParameter();
            for (Parameter_ parameter_ : parameters) {
                String[] field = parameter_.getField().split("\\.");
                if (field.length == 1 && remap.get(field[0]) == null) {
                    remap.put(field[0], ceateObject(parameter_));
                } else if (field.length > 1) {
                    Object currentObject = null; 
                    for (int i = 0; i < field.length - 1; i++) {
                        if (currentObject == null) {
                            currentObject = remap.get(field[i]);
                        } else {
                            if (currentObject instanceof Map) {
                                currentObject = ((Map) currentObject).get(field[i]);
                            } else if (currentObject instanceof List) {
                                currentObject = ((List) currentObject).get(0);
                                
                                if (currentObject instanceof Map) {
                                    currentObject = ((Map) currentObject).get(field[i]);
                                }
                            }
                        }
                    }
                    if (currentObject instanceof List) {
                        if (((List) currentObject).size() > 0) {
                            currentObject = ((List) currentObject).get(0);
                        } else {
                            ((List) currentObject).add(new HashMap<String, Object>());
                            currentObject = ((List) currentObject).get(0);
                        }
                    }
                    Object obj = ceateObject(parameter_);
                    
                    if (!(currentObject instanceof Map)) {
                        currentObject = new HashMap<>();
//                        ((Map) currentObject).put(field[field.length - 1], obj);
                    }else if(currentObject instanceof Map){
                    	/*int size = ((Map) currentObject).size();
                    	
                    	if(size > 0){
                    		((Map) currentObject).forEach((k,v)->{
                    			if(v instanceof Map){
                    				((Map)v).put(field[field.length - 1], obj);
                    			}
                    		});
                    	}else{
                    		 ((Map) currentObject).put(field[field.length - 1], obj);
                    	}*/
                    }
                    
                    ((Map) currentObject).put(field[field.length - 1], obj);
                }
            }
            return ((JSONObject) JSON.toJSON(remap)).toJSONString();
        } else {
            return "";
        }


    }

}
