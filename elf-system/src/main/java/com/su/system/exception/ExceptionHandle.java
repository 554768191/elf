package com.su.system.exception;

import com.su.common.CodeEnum;
import com.su.common.Constants;
import com.su.common.entity.ResponseMessage;
import com.su.common.exception.CommonException;
import org.mybatis.spring.MyBatisSystemException;
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
    public ResponseMessage Handle(Exception e){
        if (e instanceof CommonException){
            CommonException exception = (CommonException) e;
            return ResponseMessage.error(exception.getErrorCode(), exception.getMessage());

        }else if (e instanceof MyBatisSystemException){
            return ResponseMessage.error(CodeEnum.SQL_ERROR);

        }else {
            //将系统异常以打印出来
            logger.error(e.getMessage(), e);
            return ResponseMessage.error(Constants.SERVER_ERROR, "内部错误");
        }

    }

}
