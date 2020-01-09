package com.pqh.basic.wechat.service;

import com.pqh.basic.wechat.dao.ExcelMapper;
import com.pqh.basic.wechat.error.ServiceError;
import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.util.ExcelImportUtil;
import com.pqh.basic.wechat.util.RestClientHelper;
import com.pqh.basic.wechat.vo.excelvo.BusLineVO;
import com.pqh.basic.wechat.vo.excelvo.BusStationVO;
import com.pqh.basic.wechat.vo.excelvo.MetroLineStaionVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;

/**
 * @ClassName: ExcelService
 * @Description:
 * @Author: jackson
 * @Date: 2020/1/9 下午12:36
 * @Version: v1.0
 */
@Service
@Slf4j
public class ExcelService {

    /**
     *  区划代码
     */
    private static final String qhdm = "5101";

    @Resource
    private ExcelMapper mapper;

    public RestResponse readExcel(MultipartFile excel) {
        try{
//            buildBusLine(excel);
//            buildBusStation(excel);
            buildMetroAnBao(excel);
            return RestResponse.success();
        } catch(Exception e) {
            log.error("readExcel error:{}",e);
            return RestResponse.error(ServiceError.UN_KNOW_NULL);
        }
    }

    private void buildMetroAnBao(MultipartFile excel) throws Exception{
        List<MetroLineStaionVO> list = ExcelImportUtil.readExcel(excel.getInputStream(),MetroLineStaionVO.class);
        for (MetroLineStaionVO m : list) {
            String lineCode = m.getDtxlbm();
            //线路编码
            if (lineCode.length() < 3) {
                String temp = "0" + lineCode;
                m.setDtxlbm(qhdm + temp);
            } else {
                m.setDtxlbm(qhdm + lineCode);
            }
            //站点编码
            String stationCode = m.getDtzdbm();
            if (lineCode.equals("10")) {
                stationCode = "A" + stationCode.substring(2);
                m.setDtzdbm(m.getDtxlbm() + stationCode);
            } else {
                stationCode = stationCode.substring(1);
                m.setDtzdbm(m.getDtxlbm() + stationCode);
            }

        }
        File file = new File("/home/stealhao/下载/地铁站点信息测试.xlsx");
        ExcelImportUtil.writeExcel(file,list);

    }

    /**
     *  生成公交站点线路表
     * @param excel
     * @throws Exception
     */
    private void buildBusStation(MultipartFile excel) throws Exception{
        List<BusStationVO> list = ExcelImportUtil.readExcel(excel.getInputStream(),BusStationVO.class);
        int start = 1;
        String lastBusLineCode = "";
        for (BusStationVO b : list) {
            String busLineCode = b.getGjxlbm();

            //处理公交线路编码
            if (busLineCode.length() < 3) {
                String tempCode = "000";
                if (busLineCode.length() == 1) {
                    tempCode = "00" + busLineCode;
                } else {
                    tempCode = "0" + busLineCode;
                }
                busLineCode = qhdm + tempCode;
            } else if (busLineCode.length() > 3) {
                String code = mapper.queryCodeByName(busLineCode);
                if (StringUtils.isBlank(code)) {
                    busLineCode = qhdm + RestClientHelper.random(3);
                } else {
                    busLineCode = code;
                }
            } else {
                if (RestClientHelper.isContainChinese(busLineCode)) {
                    String code = mapper.queryCodeByName(busLineCode);
                    if (StringUtils.isBlank(code)) {
                        busLineCode = qhdm + RestClientHelper.random(3);
                    } else {
                        busLineCode = code;
                    }
                } else {
                    busLineCode = qhdm + busLineCode;
                }
            }
            b.setGjxlbm(busLineCode);

            //处理公交站点编码
            if (StringUtils.isBlank(lastBusLineCode) || busLineCode.equals(lastBusLineCode)) {
                String stationCode = b.getGjxlbm() + String.format("%03d",start);
                b.setGjzdbm(stationCode);
            } else {
                start = 1;
                String stationCode = b.getGjxlbm() + String.format("%03d",start);
                b.setGjzdbm(stationCode);
            }

            lastBusLineCode = busLineCode;
            start ++;
        }

        File file = new File("/home/stealhao/下载/公交站点测试.xlsx");
        ExcelImportUtil.writeExcel(file,list);
    }

    /**
     *  生成公交线路表
     * @param excel
     * @throws Exception
     */
    private void buildBusLine(MultipartFile excel) throws Exception{
        List<BusLineVO> list = ExcelImportUtil.readExcel(excel.getInputStream(), BusLineVO.class);
//            list.forEach(b -> log.info("当前行信息:{}",b.toString()));
        //处理公交线路编码问题
        for (BusLineVO b : list) {
            String busLineCode = b.getGjxlbm();
            if (busLineCode.length() < 3) {
                String tempCode = "000";
                if (busLineCode.length() == 1) {
                    tempCode = "00" + busLineCode;
                } else {
                    tempCode = "0" + busLineCode;
                }
                busLineCode = qhdm + tempCode;
            } else if (busLineCode.length() > 3) {
                String tempCode = RestClientHelper.random(3);
                busLineCode = qhdm + tempCode;
            } else {
                if (RestClientHelper.isContainChinese(busLineCode)) {
                    busLineCode = qhdm + RestClientHelper.random(3);
                } else {
                    busLineCode = qhdm + busLineCode;
                }
            }
            b.setGjxlbm(busLineCode);
        }
        mapper.insertExcel(list);
        File file = new File("/home/stealhao/下载/公交站点测试.xlsx");
        ExcelImportUtil.writeExcel(file,list);
    }
}
