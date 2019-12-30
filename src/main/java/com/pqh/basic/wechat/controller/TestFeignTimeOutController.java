package com.pqh.basic.wechat.controller;

import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.service.TestFeignTimeOutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TestFeignTimeOutController
 * @Description:
 * @Author: jackson
 * @Date: 2019/12/30 下午9:09
 * @Version: v1.0
 */
@Api(tags = "测试feign超时时间")
@RestController
public class TestFeignTimeOutController {

    @Autowired
    private TestFeignTimeOutService service;

    @ApiOperation("测试feign超时")
    @GetMapping("/basic_data")
    public RestResponse testFeign() {
        return service.testFeign();
    }
}
