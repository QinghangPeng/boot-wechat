package com.pqh.basic.wechat.feign;

import com.pqh.basic.wechat.feign.fallback.FileManageFeignFallBack;
import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.vo.*;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @InterfaceName: FileManageFeign
 * @Description:
 * @Author: jackson
 * @Date: 2019/12/31 下午4:31
 * @Version: v1.0
 */
@FeignClient(value = "nccc-basic-file-manage",url = "http://10.253.100.12:32365/",fallback = FileManageFeignFallBack.class,configuration = FileManageFeign.MultipartSupportConfig.class)
@Primary
public interface FileManageFeign {

    @PostMapping("/file_manages/upload")
    RestResponse create(@Valid @RequestBody FileUploadInfo info);

    @PostMapping(value = "/file_manages/upload/{code}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    RestResponse<FileUploadVO> create(@PathVariable("code") String code, @RequestPart("file") MultipartFile file);

    @PostMapping(path = "/file_manages/chunk/upload")
    RestResponse<FileUploadVO> createWithChunk(@Valid @RequestBody BigFileUploadVO bigFile);

    @RequestMapping(path = "/file_manages/check",method = RequestMethod.POST)
    RestResponse<FileUploadVO> checkFile(FileInfoVO vo);

    @GetMapping("/file_manages/video")
    RestResponse<FileVideoVO> findVideo(@ApiParam("文件url") @RequestParam("fileId") String fileId,
                                        @ApiParam("第几片(从0片开始)") @RequestParam("chunkNum") Integer chunkNum);

    /**
     *  此代码废弃
     * @param fileId
     * @param offset
     * @return
     */
    @GetMapping("/file_manages/range/video")
    RestResponse<FileVideoVO> findRangeVideo(@ApiParam("文件url") @RequestParam("fileId") String fileId,
                                        @ApiParam("文件分片起始点") @RequestParam("offset") Long offset);

    @GetMapping("/file_manages/range/video")
    RestResponse<VideoChunkVO> getvideo(@ApiParam("文件url") @RequestParam("fileId") String fileId,
                                        @ApiParam("文件分片起始点") @RequestParam("offset") Integer offset,
                                        @ApiParam("文件分片大小") @RequestParam("chunkSize") Integer chunkSize);

    @Configuration
    class MultipartSupportConfig {
        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;
        @Bean
        public Encoder feignFormEncoder () {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }
    }
}
