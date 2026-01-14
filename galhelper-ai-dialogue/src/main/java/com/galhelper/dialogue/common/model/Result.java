package com.galhelper.dialogue.common.model;

import com.galhelper.dialogue.common.enums.ResultCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * ClassName: Result
 * Package: com.galhelper.dialogue.common.model
 * Description:
 *
 * @author aya
 * @date 2026/1/13 - 9:44
 * @project galhelper-ai-service
 */
@Data
public class Result<T> {
    private Integer code;    // 状态码 (0:成功, 500:系统异常, 401:未登录等)
    private String msg;  // 提示信息
    private T data;      // 业务数据

    // 快捷成功返回
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    // 快捷失败返回
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SYSTEM_ERROR.getCode());
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    // 快捷失败返回
    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
}
