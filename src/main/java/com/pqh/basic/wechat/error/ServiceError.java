package com.pqh.basic.wechat.error;


public enum ServiceError implements IError {
    /**
     * 用于未定义错误
     */
    UN_KNOW_NULL("00000001", "未知错误"),

    /**
     *  用于调用服务
     */
    SERVICE_CALL_ERROR("00000002","服务调用失败"),
    ;
    String code;
    String msg;
    private static final String PREFIX = "PQH_BASIC_FEIGEN_";

    ServiceError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getNamespace() {
        return PREFIX;
    }

    @Override
    public String getErrorCode() {
        return PREFIX  + code;
    }

    @Override
    public String getErrorMessage() {
        return msg;
    }

    /**
     * 根据code查询error
     * @param code
     * @return
     */
    public static ServiceError findByCode(String code){
        for (ServiceError serviceError : ServiceError.values()){
            if (serviceError.code.equals(code)){
                return serviceError;
            }
        }
        return ServiceError.UN_KNOW_NULL;
    }
}
