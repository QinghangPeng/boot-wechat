package com.pqh.basic.wechat.util;


import com.pqh.basic.wechat.error.BasicWechatException;
import com.pqh.basic.wechat.error.ServiceError;
import com.pqh.basic.wechat.response.RestResponse;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-11-8 下午4:03
 */
public class RestClientHelper {

    public static <T> T getRestData(RestResponse<T> response) {
        if (RestResponse.SUCCESS_CODE.equals(response.getCode())) {
            return response.getData();
        } else {
            throw new BasicWechatException(ServiceError.SERVICE_CALL_ERROR);
        }
    }

}
