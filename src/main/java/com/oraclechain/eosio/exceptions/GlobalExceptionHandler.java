package com.oraclechain.eosio.exceptions;

import com.oraclechain.eosio.dto.MessageResult;
        import io.lettuce.core.RedisConnectionException;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.validation.BindingResult;
        import org.springframework.validation.FieldError;
        import org.springframework.web.HttpRequestMethodNotSupportedException;
        import org.springframework.web.bind.MethodArgumentNotValidException;
        import org.springframework.web.bind.MissingServletRequestParameterException;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
        import org.springframework.web.servlet.NoHandlerFoundException;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //捕捉到的异常
    @ExceptionHandler(value = ExceptionsFC.class)
    public ResponseEntity<MessageResult> handleServiceException(ExceptionsFC exception) {
        Integer code = exception.getErrorCode().getMsg_id();
        String msg = ErrorCodeEnumFC.getMsgById(code);
        String data = exception.getMessage();
        log.info("X--->{code:" + code + ",msg:" + msg + ",what:" + data + "}");
        return new ResponseEntity(new MessageResult(msg, code, data), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = ExceptionsChain.class)
    public ResponseEntity<MessageResult> handleServiceException(ExceptionsChain exception) {
        Integer code = exception.getErrorCode().getMsg_id();
        String msg = ErrorCodeEnumChain.getMsgById(code);
        String data = exception.getMessage();
        log.info("X--->{code:" + code + ",msg:" + msg + ",what:" + data + "}");
        return new ResponseEntity(new MessageResult(msg, code, data), HttpStatus.BAD_REQUEST);
    }


    //其他异常
    @ExceptionHandler
    @ResponseStatus
    public ResponseEntity<MessageResult> hadleServerException(Exception exception) {

        //默认打印错误，并且返回server error
        exception.printStackTrace();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        Integer code = ErrorCodeEnumChain.unknown_error_exception.getMsg_id();
        String msg = ErrorCodeEnumChain.getMsgById(code);

        //不支持的method
        if(exception instanceof HttpRequestMethodNotSupportedException) {
            httpStatus = HttpStatus.NOT_FOUND;
            code = ErrorCodeEnumChain.not_supported_exception.getMsg_id();
            msg = exception.getMessage();
        }
        //没有handler
        else if (exception instanceof NoHandlerFoundException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            code = ErrorCodeEnumChain.not_supported_exception.getMsg_id();
            msg = exception.getMessage();
        }
        //缺失parameter
        else if (exception instanceof MissingServletRequestParameterException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            code = ErrorCodeEnumChain.not_supported_exception.getMsg_id();
            msg = exception.getMessage();
        }
        else if (exception instanceof RedisConnectionException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            code = ErrorCodeEnumChain.redis_connection_exception.getMsg_id();
            msg = exception.getMessage();
        }
        else if(exception instanceof MethodArgumentTypeMismatchException){
            httpStatus = HttpStatus.BAD_REQUEST;
            code = ErrorCodeEnumChain.request_format_exception.getMsg_id();
            msg = exception.getMessage();
        }
        else if(exception instanceof MethodArgumentNotValidException){
            httpStatus = HttpStatus.BAD_REQUEST;
            code = ErrorCodeEnumChain.request_format_exception.getMsg_id();
            BindingResult bindingResult = ((MethodArgumentNotValidException) exception).getBindingResult();
            msg = "parameter validate error:";
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                msg += fieldError.getDefaultMessage() + ", ";
            }
        }
        log.warn("X--->{code:" + code + ",msg:" + msg + ",what:" + exception.getMessage());
        return new ResponseEntity(new MessageResult(msg, code, exception.getMessage()), httpStatus);
    }

}
