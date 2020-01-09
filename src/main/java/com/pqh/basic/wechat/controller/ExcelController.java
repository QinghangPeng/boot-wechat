package com.pqh.basic.wechat.controller;

import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName: ExcelController
 * @Description:
 * @Author: jackson
 * @Date: 2020/1/9 下午12:34
 * @Version: v1.0
 */
@RestController
@Api("excel")
public class ExcelController {

    @Autowired
    private ExcelService service;

    @ApiOperation(value = "读取excel")
    @RequestMapping(value = "/read_excel", method = RequestMethod.POST)
    public RestResponse readExcel(@RequestParam("excel") MultipartFile excel) {
        return service.readExcel(excel);
    }
}
