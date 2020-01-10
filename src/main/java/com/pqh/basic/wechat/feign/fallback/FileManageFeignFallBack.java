package com.pqh.basic.wechat.feign.fallback;

import com.pqh.basic.wechat.error.ServiceError;
import com.pqh.basic.wechat.feign.FileManageFeign;
import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.vo.BigFileUploadVO;
import com.pqh.basic.wechat.vo.FileUploadInfo;
import com.pqh.basic.wechat.vo.FileUploadVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @ClassName: FileManageFeignFallBack
 * @Description:
 * @Author: jackson
 * @Date: 2019/12/31 下午4:31
 * @Version: v1.0
 */
public class FileManageFeignFallBack implements FileManageFeign {


    @Override
    public RestResponse create(@Valid FileUploadInfo info) {
        return RestResponse.error(ServiceError.SERVICE_CALL_ERROR);
    }

    @Override
    public RestResponse<FileUploadVO> create(String code, MultipartFile file) {
        return RestResponse.error(ServiceError.SERVICE_CALL_ERROR);
    }

    @Override
    public RestResponse<FileUploadVO> createWithChunk(@Valid BigFileUploadVO bigFile) {
        return RestResponse.error(ServiceError.SERVICE_CALL_ERROR);
    }

}
