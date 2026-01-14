package com.galhelper.dialogue.common.model;

/**
 * ClassName: BaseController
 * Package: com.galhelper.dialogue.common.model
 * Description:
 *
 * @author aya
 * @date 2026/1/13 - 9:42
 * @project galhelper-ai-service
 */
public class BaseController {

    /**
     * 返回成功封装
     */
    protected <T> Result<T> success(T data) {
        return Result.success(data);
    }

    /**
     * 返回成功封装（无数据）
     */
    protected Result<Void> success() {
        return Result.success(null);
    }

    /**
     * 常用失败封装
     */
    protected Result<Void> error(String message) {
        return Result.error(message);
    }

    /**
     * 自定义错误码失败封装
     */
    protected Result<Void> error(Integer code, String message) {
        return Result.error(code, message);
    }
}
