package com.pqh.basic.wechat.service;

import com.pqh.basic.wechat.error.ServiceError;
import com.pqh.basic.wechat.feign.FileManageFeign;
import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.util.RestClientHelper;
import com.pqh.basic.wechat.vo.FileUploadInfo;
import com.pqh.basic.wechat.vo.FileUploadVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * @ClassName: FileManageService
 * @Description:
 * @Author: jackson
 * @Date: 2019/12/31 下午4:39
 * @Version: v1.0
 */
@Slf4j
@Service
public class FileManageService {

    @Autowired
    private FileManageFeign feign;

    public RestResponse upload(String code, MultipartFile file) {
        try{
            /*FileUploadInfo info = new FileUploadInfo();
            info.setEncryptCode(code);
            info.setFileSize(file.getSize());
            info.setFileSuffixName(file.getOriginalFilename().
                    substring(file.getOriginalFilename().
                            lastIndexOf(".") + 1));
            byte[][] bytes = splitBytes(file.getBytes(), 1024 * 1024 * 50);
            info.setFileBytes(bytes);*/
//            info.setFileStream(file.getInputStream());

            RestResponse<FileUploadVO> response = feign.create(code,file);
            FileUploadVO restData = RestClientHelper.getRestData(response);
            return RestResponse.success(restData);
        } catch(Exception e) {
            log.error("upload file error:{}",e);
            return RestResponse.error(ServiceError.UN_KNOW_NULL);
        }
    }

    /**
     * 拆分byte数组
     *
     * @param bytes
     *            要拆分的数组
     * @param size
     *            要按几个组成一份
     * @return
     */
    public byte[][] splitBytes(byte[] bytes, int size) {
        double splitLength = Double.parseDouble(size + "");
        int arrayLength = (int) Math.ceil(bytes.length / splitLength);
        byte[][] result = new byte[arrayLength][];
        int from, to;
        for (int i = 0; i < arrayLength; i++) {
            from = (int) (i * splitLength);
            to = (int) (from + splitLength);
            if (to > bytes.length)
                to = bytes.length;
            result[i] = Arrays.copyOfRange(bytes, from, to);
        }
        return result;
    }
}
