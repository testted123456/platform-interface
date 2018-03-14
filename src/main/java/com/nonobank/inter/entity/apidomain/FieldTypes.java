package com.nonobank.inter.entity.apidomain;

/**
 * Created by tangrubei on 16/12/7.
 */
public enum FieldTypes {
    STRING_TYPE("String",(short)0), LONG_TYPE("Long",(short)1),
    INTEGER_TYPE("Integer",(short)2),BIGDECIMAL_TYPE("BigDecimal",(short)3),
    BOOLEAN_TYPE("Boolean",(short)4),DATE_TYPE("Date",(short)5),ARRAY_TYPE("Array",(short)6),
    OBJECT_TYPE("Object",(short)7),STRING_ARRAY_TYPE("String[]",(short)8),LIST_TYPE("List",(short)9),
    OTHER_TYPE("Other",(short)10);

    private String field_type;
    private short field_type_value;

    private FieldTypes(String field_type,short field_type_value){
        this.field_type = field_type;
        this.field_type_value = field_type_value;
    }

    public static String getFieldType(short field_type_value){
        String revalue = "";
        for (FieldTypes field:values()){
            if (field.getField_type_value()==field_type_value){
                revalue =  field.getField_type();
                break;
            }
        }
        return revalue;
    }

    public static short getFieldValue(String field_type){
        short reshort = 10;
        for (FieldTypes field:values()){
            if (field.getField_type().equals(field_type)){
                reshort =  field.getField_type_value();
                break;
            }
        }
        return reshort;
    }


    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        this.field_type = field_type;
    }

    public short getField_type_value() {
        return field_type_value;
    }

    public void setField_type_value(short field_type_value) {
        this.field_type_value = field_type_value;
    }
}
