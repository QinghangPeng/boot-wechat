package com.pqh.basic.wechat.vo.excelvo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: MetroLineStaionVO
 * @Description:
 * @Author: jackson
 * @Date: 2020/1/9 下午4:03
 * @Version: v1.0
 */
@Data
@NoArgsConstructor
public class MetroLineStaionVO extends BaseRowModel {

    @ExcelProperty(value = "地铁线路编码", index = 1)
    private String dtxlbm;

    @ExcelProperty(value = "地铁站点编码", index = 2)
    private String dtzdbm;

}
