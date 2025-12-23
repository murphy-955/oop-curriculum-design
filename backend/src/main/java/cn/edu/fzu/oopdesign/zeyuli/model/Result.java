package cn.edu.fzu.oopdesign.zeyuli.model;

import cn.edu.fzu.oopdesign.zeyuli.enm.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> success() {
        return new Result<>(0, "success", null);
    }

    public static <T> Result<T> error(StatusCodeEnum enm) {
        return new Result<>(enm.getCode(), enm.getMessage(), null);
    }

    public static <T> Result<Object> error(StatusCodeEnum enm, Object message) {
        return new Result<>(enm.getCode(), enm.getMessage(), message);
    }
}