package com.pqh.basic.wechat.feign.fallback;

import com.pqh.basic.wechat.error.ServiceError;
import com.pqh.basic.wechat.feign.FileManageFeign;
import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.vo.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    public RestResponse<FileUploadVO> checkFile(FileInfoVO vo) {
        return null;
    }

    @Override
    public RestResponse<FileVideoVO> findVideo(String fileId, Integer chunkNum) {
        return RestResponse.error(ServiceError.SERVICE_CALL_ERROR);
    }

    @Override
    public RestResponse<FileVideoVO> findRangeVideo(String fileId,Long offset) {
        return RestResponse.error(ServiceError.SERVICE_CALL_ERROR);
    }

    @Override
    public RestResponse<VideoChunkVO> getvideo(String fileId, Integer offset, Integer chunkSize) {
        return RestResponse.error(ServiceError.SERVICE_CALL_ERROR);
    }


}
