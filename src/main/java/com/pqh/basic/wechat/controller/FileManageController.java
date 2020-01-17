package com.pqh.basic.wechat.controller;

import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.service.FileManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @PostMapping("/file_upload")
    public RestResponse upload(@RequestParam("code") String code, @RequestParam("file") MultipartFile file,
                               @RequestParam("fileMd5") String fileMd5) {
        return service.upload(code,file,fileMd5);
    }

    @ApiOperation("测试文件下载")
    @GetMapping("/file_download")
    public void find (@ApiParam("文件url") @RequestParam("fileId") String fileId) {
        service.find(fileId);
    }

    @ApiOperation("测试文件上传分块数量")
    @GetMapping("/file_upload/{key}")
    public RestResponse getSize(@PathVariable String key) {
        return service.getSize(key);
    }
}
