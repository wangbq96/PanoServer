package com.pano.panoserver.model;

/**
 * Created by wangboquan on 17/2/20.
 */

public class Message {

    private boolean result;

    private String message;

    private Object data;

    public Message(boolean result, String message, Object data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}