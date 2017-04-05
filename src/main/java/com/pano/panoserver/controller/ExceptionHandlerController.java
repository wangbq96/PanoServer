package com.pano.panoserver.controller;

import com.pano.panoserver.exception.ExistException;
import com.pano.panoserver.exception.NotFoundException;
import com.pano.panoserver.model.ErrorMessage;
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
    public String handler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        System.out.print(e.getClass().getName());
        return new ErrorMessage(e.getClass().getName()).toString();
    }
}
