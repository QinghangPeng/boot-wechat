package com.pqh.basic.wechat.service;

import com.pqh.basic.wechat.error.ServiceError;
import com.pqh.basic.wechat.feign.BasicDataFeign;
import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.util.RestClientHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TestFeignTimeOutService
 * @Description:
 * @Author: jackson
 * @Date: 2019/12/30 下午9:13
 * @Version: v1.0
 */
@Service
@Slf4j
public class TestFeignTimeOutService {

    @Autowired
    private BasicDataFeign feign;

    public RestResponse testFeign() {
        long now = System.currentTimeMillis();
        try{
            List<Map<String, String>> data = new ArrayList<>();
            log.info("开始尝试连接远程服务:{}",now);
            RestResponse<List<Map<String,String>>> response = feign.qryPark("011","01");
            data = RestClientHelper.getRestData(response);
            log.info("===========================");
            return RestResponse.success(data);
        } catch(Exception e) {
            log.error("尝试连接失败，花费时间:{}",System.currentTimeMillis() - now);
            log.error("testFeign error:{}",e);
            return RestResponse.error(ServiceError.UN_KNOW_NULL);
        }
    }
}
