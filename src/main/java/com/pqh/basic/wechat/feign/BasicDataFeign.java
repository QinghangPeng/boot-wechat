package com.pqh.basic.wechat.feign;

import com.pqh.basic.wechat.feign.fallback.BasicDataFeignFallBack;
import com.pqh.basic.wechat.response.RestResponse;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @InterfaceName: BasicDataFeign
 * @Description:
 * @Author: jackson
 * @Date: 2019/12/30 下午9:15
 * @Version: v1.0
 */
@FeignClient(value = "nccc-basic-data",fallback = BasicDataFeignFallBack.class)
@Primary
public interface BasicDataFeign {

    @GetMapping("/basic_park")
    RestResponse qryPark(@RequestParam(value = "parkCode",required = false) String parkCode,
                         @RequestParam(value = "lineCode",required = false) String lineCode);
}
