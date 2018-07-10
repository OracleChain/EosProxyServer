package com.oraclechain.eosio.exceptions;


import com.oraclechain.eosio.dto.MessageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //JSON

    //捕捉到的异常
    @ExceptionHandler(value = ExceptionsFC.class)
    public ResponseEntity<MessageResult> handleServiceException(ExceptionsFC exception) {
        Integer code = exception.getErrorCode().getMsg_id();
        String msg = ErrorCodeEnumFC.getMsgById(code);
        String data = exception.getMessage();
        log.info("--->{code:" + code + ",msg:" + msg + ",data:" + data + "}");
        return new ResponseEntity(new MessageResult(msg, code, data), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = ExceptionsChain.class)
    public ResponseEntity<MessageResult> handleServiceException(ExceptionsChain exception) {
        Integer code = exception.getErrorCode().getMsg_id();
        String msg = ErrorCodeEnumChain.getMsgById(code);
        String data = exception.getMessage();
        log.info("--->{code:" + code + ",msg:" + msg + ",data:" + data + "}");
        return new ResponseEntity(new MessageResult(msg, code, data), HttpStatus.BAD_REQUEST);
    }

    //其他异常
    @ExceptionHandler
    @ResponseStatus
    public ResponseEntity<MessageResult> hadleServerException(Exception exception) {

        exception.printStackTrace();
        HttpStatus httpStatus = HttpStatus.OK;
        Integer code = ErrorCodeEnumChain.unknown_error_exception.getMsg_id();
        String msg = ErrorCodeEnumChain.getMsgById(code);

        Class exceptionClazz = exception.getClass();
        if (Objects.equals(HttpRequestMethodNotSupportedException.class, exceptionClazz)) {
            code = ErrorCodeEnumChain.not_supported_exception.getMsg_id();
            msg = exception.getMessage();
        }
        else if (Objects.equals(NoHandlerFoundException.class, exceptionClazz)) {
            code = ErrorCodeEnumChain.not_supported_exception.getMsg_id();
            msg = exception.getMessage();
        }
        else if (Objects.equals(MissingServletRequestParameterException.class, exceptionClazz)) {
            code = ErrorCodeEnumChain.not_supported_exception.getMsg_id();
            msg = exception.getMessage();
        }
        log.warn("-->{code:" + code + ",msg:" + msg + "}");
        return new ResponseEntity(new MessageResult(msg, code, ""), httpStatus);
    }

}
