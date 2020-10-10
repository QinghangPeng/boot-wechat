package com.pqh.basic.wechat.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: FileInfoVO
 * @Description:
 * @Author: Jackson Peh
 * @CreateTime: 2019-10-17 09:24
 * @Version: 1.0
 **/
@Data
@NoArgsConstructor
@ApiModel("文件基本信息")
public class FileInfoVO implements Serializable {


    @NotBlank(message = "00100001")
    @NotNull(message = "00100001")
    @ApiModelProperty(value = "系统来源")
    private String sysResoure;

    @ApiModelProperty(value = "系统功能（可以为空）")
    private String sysFunction;

    @NotBlank(message = "00100002")
    @NotNull(message = "00100002")
    @ApiModelProperty(value = "文件名")
    private String fileName;

    @NotNull(message = "00100003")
    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    @NotBlank(message = "00100004")
    @NotNull(message = "00100004")
    @ApiModelProperty(value = "文件md5值")
    private String fileCode;

    @JsonIgnore
    private String url;

    /**
     *  加密code
     */
    @JsonIgnore
    private String encryptCode;

}
