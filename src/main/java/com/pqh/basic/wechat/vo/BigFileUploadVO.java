package com.pqh.basic.wechat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName: BigFileUploadVO
 * @Description:
 * @Author: jackson
 * @Date: 2020/1/10 下午2:23
 * @Version: v1.0
 */
@Data
@NoArgsConstructor
public class BigFileUploadVO {


    @ApiModelProperty("秘钥")
    private String encryptCode;

    @ApiModelProperty("文件后缀名")
    private String fileSuffixName;

    private Integer chunkCounts;

    private Long fileSize;

    @ApiModelProperty(value = "文件md5值")
    private String fileCode;
}
