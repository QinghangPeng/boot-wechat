package com.pqh.basic.wechat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: FileVideoVO
 * @Description:
 * @Author: jackson
 * @Date: 2020/1/16 上午10:19
 * @Version: v1.0
 */
@Data
@NoArgsConstructor
public class FileVideoVO {

    @ApiModelProperty("文件后缀名")
    private String sName;

    @ApiModelProperty("文件名")
    private String fileName;

    @ApiModelProperty("分片数量")
    private Integer chunkCount;

    @ApiModelProperty("当前分片")
    private Integer chunkNum;

    @ApiModelProperty("当前分片内容")
    private byte[] chunkFile;

    @ApiModelProperty("文件大小")
    private Integer fileSize;

    public FileVideoVO(String sName, String fileName, Integer chunkCount, Integer chunkNum, byte[] chunkFile,Integer fileSize) {
        this.sName = sName;
        this.fileName = fileName;
        this.chunkCount = chunkCount;
        this.chunkNum = chunkNum;
        this.chunkFile = chunkFile;
        this.fileSize = fileSize;
    }
}
