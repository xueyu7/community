package com.xy.community.advice;

import com.xy.community.dto.ResultDTO;
import com.xy.community.exception.CustomizeErrorCode;
import com.xy.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeErrorHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    Object handle(Throwable e, Model model, HttpServletRequest request) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            //返回json
            if (e instanceof CustomizeException){
                return ResultDTO.errorOf((CustomizeException)e);
            }else {
                return ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
        }else {
            //错误页面跳转
            if (e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else {
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
