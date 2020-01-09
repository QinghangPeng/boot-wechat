package com.pqh.basic.wechat.vo.excelvo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: BusStationVO
 * @Description:
 * @Author: jackson
 * @Date: 2020/1/9 下午3:14
 * @Version: v1.0
 */
@Data
@NoArgsConstructor
public class BusStationVO extends BaseRowModel {

    @ExcelProperty(value = "ID", index = 0)
    private Integer id;

    @ExcelProperty(value = "XZQHDM", index = 1)
    private String xzqhdm;

    @ExcelProperty(value = "GJXLBM", index = 2)
    private String gjxlbm;

    @ExcelProperty(value = "GJZDBM", index = 3)
    private String gjzdbm;

    @ExcelProperty(value = "GJZDMC", index = 4)
    private String gjzdmc;

    @ExcelProperty(value = "KSSJ", index = 5)
    private String kssj;

    @ExcelProperty(value = "JSSJ", index = 6)
    private String jssj;
}
