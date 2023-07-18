package com.reply.airbnbdemo.enums;

public enum PropertyEnum {

    ONE(getName("A")), TWO(getName("B"));

    private static String getName(String value){
        return switch (value){
            case "A": yield "A1";
            case "B": yield "B1";
            default: yield "C";
        };
    }

    final String value;

    PropertyEnum(String value){
        this.value = value;
    }
}
