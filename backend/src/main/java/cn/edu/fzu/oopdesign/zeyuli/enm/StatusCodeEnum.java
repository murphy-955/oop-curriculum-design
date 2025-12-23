package cn.edu.fzu.oopdesign.zeyuli.enm;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态码枚举
 *
 * @author 李泽聿
 * @since 2025-12-23 15:34
 */
@AllArgsConstructor
@Getter
public enum StatusCodeEnum {
    SUCCESS(210, "成功"),
    FAILED_TO_SYNC_GANE_INFO(420, "同步游比赛息失败");

    private final int code;
    private final String message;
}
