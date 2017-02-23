package com.pano.panoserver.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangboquan on 17/2/20.
 */
public class ErrorMessage extends Message {
    public ErrorMessage(String message) {
        super(false, message, null);
    }
}
