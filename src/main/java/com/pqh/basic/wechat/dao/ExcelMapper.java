package com.pqh.basic.wechat.dao;

import com.pqh.basic.wechat.vo.excelvo.BusLineVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: ExcelMapper
 * @Description:
 * @Author: jackson
 * @Date: 2020/1/9 下午2:41
 * @Version: v1.0
 */
@Mapper
public interface ExcelMapper {

    void insertExcel(List<BusLineVO> list);

    String queryCodeByName(@Param("busLineCode") String busLineCode);
}
