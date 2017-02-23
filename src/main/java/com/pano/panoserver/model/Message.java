package com.pano.panoserver.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangboquan on 17/2/20.
 */
public class Message {
    private boolean result;
    private String message;
    private Object data;
    private Map<String, Object> map;

//    // 成功 无返回数据
//    public Message(boolean result) {
//        this.result = result;
//        this.message = null;
//        this.data = null;
//        map = new HashMap<>();
//    }
//
//    // 成功 有返回数据
//    public Message(boolean result, Object data) {
//        this.result = result;
//        this.message = null;
//        this.data = data;
//        map = new HashMap<>();
//    }
//
//    // 失败
//    public Message(boolean result, String message) {
//        this.result = result;
//        this.message = message;
//        this.data = null;
//        map = new HashMap<>();
//    }

    public Message(boolean result, String message, Object data) {
        this.result = result;
        this.message = message;
        this.data = data;
        map = new HashMap<>();
    }

    @Override
    public String toString() {
        map.put("result", result);
        map.put("message", message);
        map.put("data", data);
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}