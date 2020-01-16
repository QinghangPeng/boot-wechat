package com.pqh.basic.wechat.service;

import com.pqh.basic.wechat.error.ServiceError;
import com.pqh.basic.wechat.feign.FileManageFeign;
import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.util.RedisUtil;
import com.pqh.basic.wechat.util.RestClientHelper;
import com.pqh.basic.wechat.vo.BigFileUploadVO;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    @Autowired
    private RedisUtil redisUtil;

    /**
     *  文件分块支持最大size 1M
     */
    private static final Long BLOCK_MAX_SIZE = 20971520L;

    private static final String FILE_CHUNK_KEY = "file:chunk:";

    public RestResponse upload(String code, MultipartFile file,String fileMd5) {
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
            //调用redis分块存储
            byte[][] bytes = splitBytes(file.getBytes(), BLOCK_MAX_SIZE.intValue());
            Map<String,byte[]> map = new HashMap<>();
            int k = 0;
            for (byte[] chunk : bytes) {
//                map.put(k + "",chunk);
                redisUtil.hashSet(FILE_CHUNK_KEY + fileMd5,String.valueOf(k),chunk,5);
                k++;
            }
//            boolean b = redisUtil.hashSetAll(FILE_CHUNK_KEY + "9f70ee4529d4761463b298dfe2a0dc30", map);
            int count = redisUtil.hashSize(FILE_CHUNK_KEY + fileMd5);
            log.info("chunk count = {}",count);
            FileUploadVO restData = new FileUploadVO();
            if (count == bytes.length) {
                BigFileUploadVO vo = new BigFileUploadVO();
                vo.setChunkCounts(bytes.length);
                vo.setEncryptCode(code);
                vo.setFileSuffixName("mp4");
                vo.setFileSize(52428800L);
                vo.setFileCode(fileMd5);
                RestResponse<FileUploadVO> response = feign.createWithChunk(vo);
                restData = RestClientHelper.getRestData(response);
            }

            /*RestResponse<FileUploadVO> response = feign.create(code,file);
            FileUploadVO restData = RestClientHelper.getRestData(response);*/

            return RestResponse.success(restData);
        } catch(Exception e) {
            log.error("upload file error:{}",e);
            return RestResponse.error(ServiceError.UN_KNOW_NULL);
        }
    }

    private static byte[] byteMergerAll(byte[]... values) {
        int length_byte = 0;
        for (int i = 0; i < values.length; i++) {
            length_byte += values[i].length;
        }
        byte[] all_byte = new byte[length_byte];
        int countLength = 0;
        for (int i = 0; i < values.length; i++) {
            byte[] b = values[i];
            System.arraycopy(b, 0, all_byte, countLength, b.length);
            countLength += b.length;
        }
        return all_byte;
    }

    public List<byte[]> toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        List<byte[]> list = new ArrayList<>();
        byte[] buffer = new byte[61858764];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            list.add(output.toByteArray());
        }

        return list;
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

    public RestResponse getSize(String key) {
        try{
            int count = redisUtil.hashSize(FILE_CHUNK_KEY + key);
            log.info("chunk count = {}",count);
            return RestResponse.success();
        } catch(Exception e) {
            log.error("getSize error:{}",e);
            return RestResponse.error(ServiceError.UN_KNOW_NULL);
        }
    }
}
