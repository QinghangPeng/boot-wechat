package com.pqh.basic.wechat.controller;

import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.service.FileManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName: FileManageController
 * @Description:
 * @Author: jackson
 * @Date: 2019/12/31 下午4:37
 * @Version: v1.0
 */
@Api(tags = "测试文件远程调用")
@RestController
public class FileManageController {

    @Autowired
    private FileManageService service;

    @ApiOperation("测试文件上传")
    @PostMapping("/file_upload/{code}")
    public RestResponse upload(@PathVariable String code, @RequestParam("file") MultipartFile file) {
        return service.upload(code,file);
    }

    @ApiOperation("测试文件上传分块数量")
    @GetMapping("/file_upload/{key}")
    public RestResponse getSize(@PathVariable String key) {
        return service.getSize(key);
    }
}
