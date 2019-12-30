package com.pqh.basic.wechat.error;

public interface IError {
    /**
     * 获取别名
     * @return
     */
    String getNamespace();

    /**
     * 获取编码code
     * @return
     */
    String getErrorCode();

    /**
     * 获取错误信息
     * @return
     */
    String getErrorMessage();
}
