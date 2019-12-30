package com.pqh.basic.wechat.error;

/**
 * @ClassName: BasicExternalException
 * @Description:
 * @Author: jackson
 * @Date: 2019/11/12 14:37
 * @Version: v1.0
 */
public class BasicWechatException extends RuntimeException {

    private static final long serialVersionUID = -4742832112872227456L;

    private ServiceError serviceError;

    public BasicWechatException() {
        super();
    }

    public BasicWechatException(ServiceError serviceError) {
        this.serviceError = serviceError;
    }

    public BasicWechatException(String message, ServiceError serviceError) {
        super(message);
        this.serviceError = serviceError;
    }

    public BasicWechatException(Throwable cause, ServiceError serviceError) {
        super(cause);
        this.serviceError = serviceError;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ServiceError getServiceError() {
        return serviceError;
    }

    public void setServiceError(ServiceError serviceError) {
        this.serviceError = serviceError;
    }
}
