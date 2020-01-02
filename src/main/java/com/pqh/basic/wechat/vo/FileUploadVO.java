package com.pqh.basic.wechat.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: FileUploadVO
 * @Description:
 * @Author: Jackson Peh
 * @CreateTime: 2019-10-18 09:38
 * @Version: 1.0
 **/
@Data
@NoArgsConstructor
@ApiModel("文件上传")
public class FileUploadVO {

    /**
     *  用于判断是已上传还是未上传：1,已上传;2,未上传
     */
    @ApiModelProperty("用于判断文件是否上传：1,已上传;2,未上传")
    private Integer code;

    @ApiModelProperty("文件url")
    private String url;

    @ApiModelProperty("加密串")
    private String encryptCode;

    @ApiModelProperty("文件名字")
    private String fileName;

}
