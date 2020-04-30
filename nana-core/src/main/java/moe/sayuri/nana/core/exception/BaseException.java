package moe.sayuri.nana.core.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

    protected HttpStatus status;

    protected BaseException() {
        super();
        status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    protected BaseException(String msg) {
        super(msg);
        status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    protected BaseException(Throwable ex) {
        super(ex);
        status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    protected BaseException(HttpStatus status) {
        super();
    }
    protected BaseException(String msg, HttpStatus httpStatus) {
        super(msg);
        status = httpStatus;
    }
    protected BaseException(Throwable ex, HttpStatus httpStatus) {
        super(ex);
        status = httpStatus;
    }

    protected BaseException(String msg, Throwable ex, HttpStatus httpStatus) {
        super(msg, ex);
        status = httpStatus;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public static BaseException wrap(Throwable ex) {
        return new BaseException(ex);
    }
}
