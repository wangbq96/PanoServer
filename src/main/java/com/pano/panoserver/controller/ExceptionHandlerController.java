package com.pano.panoserver.controller;

import com.pano.panoserver.exception.ExistException;
import com.pano.panoserver.exception.NotFoundException;
import com.pano.panoserver.model.ErrorMessage;
import com.pano.panoserver.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangboquan on 17/2/20.
 */
@Controller
public class ExceptionHandlerController {
    @ExceptionHandler
    @ResponseBody
    public Message handler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        return new ErrorMessage(e.getClass().getName());
    }
}
