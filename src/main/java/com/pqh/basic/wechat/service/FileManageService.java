package com.pqh.basic.wechat.service;

import com.pqh.basic.wechat.NcccConst;
import com.pqh.basic.wechat.error.ServiceError;
import com.pqh.basic.wechat.feign.FileManageFeign;
import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.util.RedisUtil;
import com.pqh.basic.wechat.util.RestClientHelper;
import com.pqh.basic.wechat.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

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
    private static final Long BLOCK_MAX_SIZE = 1048576L;

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
                vo.setFileSize(file.getSize());
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

    /**
     *  此代码废弃
     * @param fileId
     * @param request
     * @param response
     */
    public void video(String fileId,HttpServletRequest request, HttpServletResponse response) {
        try{
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String range = request.getHeader("range");
            long offset = 0;
            if (StringUtils.isNotBlank(range)) {
                if (range.endsWith("-")) {
                    offset = Long.valueOf(range.replaceAll("bytes=", "").replaceAll("-", ""));
                } else {

                }

            }
            log.info("开始调用远程服务：{}", LocalDateTime.now().format(format));
            RestResponse<FileVideoVO> result = feign.findRangeVideo(fileId,offset);
            FileVideoVO vo = RestClientHelper.getRestData(result);
            log.info("结束调用远程服务：{}", LocalDateTime.now().format(format));

            response.reset();
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            response.setContentType("video/" + vo.getSName());
            response.addHeader("Content-Disposition" ,"attachment;filename=\"" + URLEncoder.encode(vo.getFileName(), "UTF-8")+ "\"");
            response.setContentLength(vo.getFileSize());
            String contentRange =  new StringBuffer("bytes ").append(vo.getOffset() + "").append("-")
                    .append((vo.getOffset() + vo.getRealSize()) + "").append("/").append(vo.getFileSize() + "").toString();
            response.addHeader("Content-Range", contentRange);
            response.addHeader("Accept-Ranges", "bytes");
            response.addHeader("Etag", "W/\"" + vo.getFileSize() + "-" + range + "\"");
            InputStream is = new ByteArrayInputStream(vo.getChunkFile());
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = is.read(bytes)) != -1) {
                response.getOutputStream().write(bytes,0,read);
            }
            response.getOutputStream().close();

        } catch(Exception e) {
            log.error(" error:{}",e);
        }
    }

    public void videoByRange(String fileId, HttpServletRequest request, HttpServletResponse response) {

        //从request中获取请求范围
        String range = request.getHeader("Range");
        //防止预请求
        if (range == null || !range.startsWith("bytes=")){
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        //通过range头获取，开始和结束offset，长度
        String[] rangeDatas;
        rangeDatas = range.split("=")[1].split("-");
        int start = Integer.parseInt(rangeDatas[0]);

        //如果不是ios浏览器，可能会不填写end，如0-，所以默认传回的end为start加10m的大小
        int end = rangeDatas.length > 1 ? Integer.parseInt(rangeDatas[1]):start+ NcccConst.DEFAULT_VIDEO_SIZE;
        int requestSize = end - start + 1;

        //防止ios一次请求数据太多，造成内存溢出 ios比较特殊  需要特殊处理
        String browserDetails = request.getHeader("User-Agent");
        if (browserDetails.toLowerCase().contains("safari") && !browserDetails.toLowerCase().contains("chrome")) {
            if (requestSize > NcccConst.LIMIT_VIDEO_SIZE){
                end = start+NcccConst.DEFAULT_VIDEO_SIZE;
                requestSize = end - start + 1;
            }
        }

        //获取文件字节数组
        RestResponse<VideoChunkVO> responseBody = feign.getvideo(fileId,start,requestSize);
        VideoChunkVO chunkVO = responseBody.getData();
        byte[] bytes = chunkVO.getChunkFile();
        long totalLength = chunkVO.getFileSize();

        //设置响应头
        response.setContentType("video/" + chunkVO.getSName());
        response.setHeader("Accept-Ranges", "bytes");
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        response.setContentLength(requestSize);
        response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + totalLength);

        //将数组改为流
        ServletOutputStream out = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        //创建缓存数组
        byte[] buffer = new byte[4096];
        try {
            out = response.getOutputStream();
            while (true){
                int i = bis.read(buffer);
                if (i >= 0 && i < buffer.length){
                    //未读满
                    out.write(buffer,0,i);
                    break;
                }else if (i == buffer.length){
                    //读满
                    out.write(buffer,0,buffer.length);
                }else {
                    //读完
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                out.close();
                bis.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void find(String fileId) {
        try{
            List<byte[]> list = new ArrayList<>();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            log.info("开始调用远程服务：{}", LocalDateTime.now().format(format));
            RestResponse<FileVideoVO> response = feign.findVideo(fileId,0);
            FileVideoVO vo = RestClientHelper.getRestData(response);
            list.add(vo.getChunkFile());
            Integer chunkCount = vo.getChunkCount();
            /*if (chunkCount > 1) {
                for (int i = 1; i < chunkCount; i++) {
                    RestResponse<FileVideoVO> otherResult = feign.findVideo(fileId,i);
                    FileVideoVO vos = RestClientHelper.getRestData(otherResult);
                    list.add(vos.getChunkFile());
                }
            }*/
            log.info("结束调用远程服务：{}", LocalDateTime.now().format(format));
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            HttpServletResponse httpServletResponse = ((ServletRequestAttributes) attributes).getResponse();
            HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
            String ranchange = request.getHeader("range");
            log.info("header range is:{}",ranchange);

            httpServletResponse.reset();

            httpServletResponse.setContentType("video/" + vo.getSName());
            httpServletResponse.addHeader("Content-Disposition" ,"attachment;filename=\"" + URLEncoder.encode(vo.getFileName(), "UTF-8")+ "\"");
            httpServletResponse.setContentLength(vo.getFileSize());
            httpServletResponse.addHeader("Content-Range", "" + Integer.valueOf(vo.getFileSize() - 1));
            httpServletResponse.addHeader("Accept-Ranges", "bytes");
            httpServletResponse.addHeader("Etag", "W/\"9767057-1323779115364\"");
            for (byte[] chunk : list) {
                InputStream is = new ByteArrayInputStream(chunk);
                int read = 0;
                byte[] bytes = new byte[10485760];
                while ((read = is.read(bytes)) != -1) {
                    httpServletResponse.getOutputStream().write(bytes,0,read);
                }
                is.close();
            }
            if (list.size() > 1) {
                log.info("大文件写出完毕============================");
            }
            httpServletResponse.getOutputStream().close();

        } catch(Exception e) {
            log.error("find video error:{}",e);
        }

    }

    public RestResponse checkFile(MultipartFile file) {
        try{
            FileInfoVO vo = new FileInfoVO();
            vo.setSysResoure("phtestvideo");
            vo.setSysFunction("test");
            vo.setFileName(file.getOriginalFilename());
            vo.setFileSize(file.getSize());
            vo.setFileCode(UUID.randomUUID().toString().replaceAll("-",""));
            RestResponse<FileUploadVO> response = feign.checkFile(vo);
            FileUploadVO restData = RestClientHelper.getRestData(response);
            restData.setMd5(vo.getFileCode());
            return RestResponse.success(restData);
        } catch(Exception e) {
            log.error(" error:{}",e);
            return RestResponse.error(ServiceError.UN_KNOW_NULL);
        }
    }
}
