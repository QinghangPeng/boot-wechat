package com.pqh.basic.wechat.error;

import com.pqh.basic.wechat.response.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ControllerErrorAdvice {

    /**
     * 参数验证不通过的异常处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public RestResponse errorHandler(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        return RestResponse.error(ServiceError.findByCode(ex.getBindingResult().getFieldError().getDefaultMessage()));
    }
}
