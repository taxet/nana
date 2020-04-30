package moe.sayuri.nana.rest.access;

import lombok.extern.slf4j.Slf4j;
import moe.sayuri.nana.core.exception.BaseException;
import moe.sayuri.nana.core.type.MdcName;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorAdvice {
    private void logToMdc(String result,Throwable e) {
        MDC.put(MdcName.result.name(), result);
        MDC.put(MdcName.exception.name(), e.getClass().getName());
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity<ApiReturn<String>> handleBaseException(BaseException e) {
        log.info("Exception when user access config: ", e);
        logToMdc(e.getStatus().toString(), e);
        ApiReturn<String> apiReturn =
                new  ApiReturn<>(e.getMessage(), e.getMessage(), String.valueOf(e.getStatus().value()), false);
        return new ResponseEntity<>(apiReturn, e.getStatus());
    }


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<ApiReturn<String>> handleThrowable(Throwable e){
        log.info("Internal error: ", e);
        logToMdc(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e);
        String errMsg = e.getClass().toString() + ": " + e.getMessage();
        ApiReturn<String> apiReturn =
                new ApiReturn<>("Internal Error.", errMsg,
                        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), false);
        return new ResponseEntity<>(apiReturn, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
