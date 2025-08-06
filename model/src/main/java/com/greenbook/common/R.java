package com.greenbook.common;

import lombok.Data;

@Data
public class R<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> R success() {
        R r = new R();
        r.setCode(200);
        return r;
    }

    public static <T> R error() {
        R r = new R();
        r.setCode(500);
        return r;
    }

    public static <T> R success(String msg, T data) {
        R r = new R();
        r.setCode(200);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static <T> R error(int code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}