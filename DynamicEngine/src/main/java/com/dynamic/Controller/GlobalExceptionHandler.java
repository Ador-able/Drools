package com.dynamic.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lxy
 * @version 1.0
 * @date 2019/8/5 16:14
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<String> ClassNotFoundExceptionHandler(HttpServletRequest request, Exception ex)
    {
        logger.info("找不到规则模型！");
        ResponseEntity<String> responseEntity=new ResponseEntity<String>("找不到规则模型！",HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler({IllegalAccessException.class,InstantiationException.class})
    public ResponseEntity<String> InstanceExceptionHandler(HttpServletRequest request, Exception ex)
    {
        logger.info("未能实例化规则模型！");
        ResponseEntity<String> responseEntity=new ResponseEntity<String>("未能实例化规则模型！",HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> ExceptionHandler(HttpServletRequest request, Exception ex)
    {
        logger.info("未知错误！");
        ResponseEntity<String> responseEntity=new ResponseEntity<String>("未知错误",HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }
}
