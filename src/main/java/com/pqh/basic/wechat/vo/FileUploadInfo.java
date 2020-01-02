package com.pqh.basic.wechat.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.List;

/**
 * @ClassName: FileUploadInfo
 * @Description:
 * @Author: jackson
 * @Date: 2019/11/7 17:39
 * @Version: v1.0
 */
@Data
@NoArgsConstructor
@ApiModel("文件上传")
public class FileUploadInfo {


    @ApiModelProperty("秘钥")
    private String encryptCode;

    @ApiModelProperty("文件后缀名")
    private String fileSuffixName;

    @NotNull(message = "00100010")
    @ApiModelProperty("文件二进制")
    private byte[] fileBytes;

    @ApiModelProperty("文件大小")
    private Long fileSize;

    /*@ApiModelProperty("文件流")
    private InputStream fileStream;*/

    /*@ApiModelProperty("文件byte[]string")
    private String fileEncodeBytes;*/

}
