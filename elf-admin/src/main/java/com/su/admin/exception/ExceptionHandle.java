package com.su.admin.exception;

import com.su.common.Constants;
import com.su.common.entity.ResponseMessage;
import com.su.common.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String Handle(Exception e){
        if (e instanceof AppException){
            AppException appException = (AppException) e;
            return ResponseMessage.error(appException.getErrorCode(), appException.getMessage());

        }else {
            //将系统异常以打印出来
            logger.error(e.getMessage(), e);
            return ResponseMessage.error(Constants.SERVER_ERROR, "内部错误");
        }

    }

}
