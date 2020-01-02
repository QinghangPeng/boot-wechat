package com.pqh.basic.wechat.feign;

import com.pqh.basic.wechat.feign.fallback.BasicDataFeignFallBack;
import com.pqh.basic.wechat.feign.fallback.FileManageFeignFallBack;
import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.vo.FileUploadInfo;
import com.pqh.basic.wechat.vo.FileUploadVO;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @InterfaceName: FileManageFeign
 * @Description:
 * @Author: jackson
 * @Date: 2019/12/31 下午4:31
 * @Version: v1.0
 */
@FeignClient(value = "nccc-basic-file-manage",fallback = FileManageFeignFallBack.class,configuration = FileManageFeign.MultipartSupportConfig.class)
@Primary
public interface FileManageFeign {

    @PostMapping("/file_manages/upload")
    RestResponse create(@Valid @RequestBody FileUploadInfo info);

    @PostMapping(value = "/file_manages/upload/{code}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    RestResponse<FileUploadVO> create(@PathVariable("code") String code, @RequestPart("file") MultipartFile file);

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
