package moe.sayuri.nana.rest.access;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiReturn<T> {
    private T data;
    private String message;
    private String code;
    private boolean success;

    public ApiReturn(T data) {
        this.data = data;
        code = "200";
        message = "SUCCESS";
        success = true;
    }
}
