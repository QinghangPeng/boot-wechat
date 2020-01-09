package com.pqh.basic.wechat.vo.excelvo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: BusLineVO
 * @Description:
 * @Author: jackson
 * @Date: 2020/1/9 下午12:49
 * @Version: v1.0
 */
@Data
@NoArgsConstructor
public class BusLineVO extends BaseRowModel {

    @ExcelProperty(value = "ID", index = 0)
    private Integer id;

    @ExcelProperty(value = "XZQHDM", index = 1)
    private String xzqhdm;

    @ExcelProperty(value = "GJXLBM", index = 2)
    private String gjxlbm;

    @ExcelProperty(value = "GJXLMC", index = 3)
    private String gjxlmc;

    @ExcelProperty(value = "QDZSMC_KSSJ", index = 4)
    private String startKssj;

    @ExcelProperty(value = "QDZSMC_JSSJ", index = 5)
    private String startJssj;

    @ExcelProperty(value = "ZDZSMC_KSSJ", index = 6)
    private String endKssj;

    @ExcelProperty(value = "ZDZSMC_JSSJ", index = 7)
    private String endJssj;

    @ExcelProperty(value = "GJXLQDZ", index = 8)
    private String gjxlqdz;

    @ExcelProperty(value = "GJXLZDZ", index = 9)
    private String gjxlzdz;


    @Override
    public String toString() {
        return "BusLineVO{" +
                "id=" + id +
                ", xzqhdm='" + xzqhdm + '\'' +
                ", gjxlbm='" + gjxlbm + '\'' +
                ", gjxlmc='" + gjxlmc + '\'' +
                ", startKssj='" + startKssj + '\'' +
                ", startJssj='" + startJssj + '\'' +
                ", endKssj='" + endKssj + '\'' +
                ", endJssj='" + endJssj + '\'' +
                ", gjxlqdz='" + gjxlqdz + '\'' +
                ", gjxlzdz='" + gjxlzdz + '\'' +
                '}';
    }
}
