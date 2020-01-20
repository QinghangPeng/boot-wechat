package com.pqh.basic.wechat.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: VideoChunkVO
 * @Description:
 * @Author: jackson
 * @Date: 2020/1/19 下午7:22
 * @Version: v1.0
 */
@Data
@NoArgsConstructor
@ApiModel("文件分片传输对象")
public class VideoChunkVO {

    @ApiModelProperty("文件后缀名")
    private String sName;

    @ApiModelProperty("文件名")
    private String fileName;

    @ApiModelProperty("当前分片内容")
    private byte[] chunkFile;

    @ApiModelProperty("文件大小")
    private Long fileSize;

    @ApiModelProperty("分段起始点")
    private Long offset;

}
