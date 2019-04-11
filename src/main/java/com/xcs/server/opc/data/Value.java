package com.xcs.server.opc.data;

import java.util.Objects;

public class Value {
    private String value;
    private String type;

    public Value() {
    }

    public Value(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Double doubleValue() {
        if (!type.equals(Type.DOUBLE)) throw new RuntimeException("类型错误");
        return Double.valueOf(value);
    }

    public Integer integerValue() {
        if (!type.equals(Type.INTEGER)) throw new RuntimeException("类型错误");
        return Integer.valueOf(value);
    }

    public String stringValue() {
        if (!type.equals(Type.STRING)) throw new RuntimeException("类型错误");
        return value;
    }

    public Boolean booleanValue() {
        if (!type.equals(Type.BOOLEAN)) throw new RuntimeException("类型错误");
        return Boolean.valueOf(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value1 = (Value) o;
        return Objects.equals(value, value1.value) &&
                Objects.equals(type, value1.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }

    public static class Type {
        public static String DOUBLE="DOUBLE";
        public static String INTEGER="INTEGER";
        public static String STRING="STRING";
        public static String BOOLEAN="BOOLEAN";
    }
}
