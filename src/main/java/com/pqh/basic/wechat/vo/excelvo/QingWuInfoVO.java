package com.pqh.basic.wechat.vo.excelvo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: QingWuInfoVO
 * @Description:
 * @Author: jackson
 * @Date: 2020/1/9 下午8:33
 * @Version: v1.0
 */
@Data
@NoArgsConstructor
public class QingWuInfoVO extends BaseRowModel {

    @ExcelProperty(value = "公安机关机构代码", index = 0)
    private String jgCode;

    @ExcelProperty(value = "地铁站点编码", index = 1)
    private String stationCode;

    @ExcelProperty(value = "地铁站点名称", index = 2)
    private String stationName;

    @ExcelProperty(value = "公交线路编码", index = 3)
    private String gjlineCode;

    @ExcelProperty(value = "公交公司名称", index = 4)
    private String gjgsmc;

    @ExcelProperty(value = "勤务姓名", index = 5)
    private String qwxm;

    @ExcelProperty(value = "勤务联系电话", index = 6)
    private String qwMobile;

    @ExcelProperty(value = "警员编号", index = 7)
    private String jybh;

    @ExcelProperty(value = "勤务类别代码", index = 8)
    private String qwlb;
}
