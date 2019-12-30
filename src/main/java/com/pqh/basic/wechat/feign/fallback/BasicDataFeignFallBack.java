package com.pqh.basic.wechat.feign.fallback;

import com.pqh.basic.wechat.error.ServiceError;
import com.pqh.basic.wechat.feign.BasicDataFeign;
import com.pqh.basic.wechat.response.RestResponse;
import org.springframework.stereotype.Component;

/**
 * @ClassName: BasicDataFeignFallBack
 * @Description:
 * @Author: jackson
 * @Date: 2019/12/30 下午9:16
 * @Version: v1.0
 */
@Component
public class BasicDataFeignFallBack implements BasicDataFeign {

    @Override
    public RestResponse qryPark(String parkCode, String lineCode) {
        return RestResponse.error(ServiceError.UN_KNOW_NULL);
    }
}
