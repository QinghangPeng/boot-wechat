package com.pqh.basic.wechat.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.service.ExcelService;
import com.pqh.basic.wechat.vo.CustomCellWriteHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @ClassName: ExcelController
 * @Description:
 * @Author: jackson
 * @Date: 2020/1/9 下午12:34
 * @Version: v1.0
 */
@RestController
@Api("excel")
public class ExcelController {

    @Autowired
    private ExcelService service;

    @ApiOperation(value = "读取excel")
    @RequestMapping(value = "/read_excel", method = RequestMethod.POST)
    public RestResponse readExcel(@RequestParam("excel") MultipartFile excel) {
        return service.readExcel(excel);
    }

    @ApiOperation(value = "导出单个sheet的excel")
    @RequestMapping(value = "/writeExcel", method = RequestMethod.GET)
    public void writeExcel(@ApiIgnore HttpServletResponse response)  throws Exception{
        String title = "车辆基本信息表";
        String sheetName = "车辆基本信息";
        String fileName = URLEncoder.encode(title, "UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-disposition", "attachment;filename=" + fileName + ExcelTypeEnum.XLSX.getValue());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        /*ExcelWriter writer = new ExcelWriter(response.getOutputStream(), ExcelTypeEnum.XLS);
        Sheet sheet = new Sheet(1,0);

        sheet.setSheetName(sheetName);
        Table table = new Table(1);*/

        /*WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        //设置 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);*/

        ExcelWriter writer1 = EasyExcel.write(response.getOutputStream()).build();

        List<List<String>> headList = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        titles.add("标题1");
        titles.add("标题2");
        titles.add("标题3");
        titles.add("标题4");
        titles.forEach(s -> {
            List<String> headTitle = new ArrayList<>();
            headTitle.add(s);
            headList.add(headTitle);
        });
        WriteTable table = EasyExcel.writerTable(1)
                .head(headList)
                .registerWriteHandler(new CustomCellWriteHandler())
                .build();
        WriteSheet sheet1 = EasyExcel.writerSheet(0, sheetName)
                /*.registerWriteHandler(horizontalCellStyleStrategy)*/
                .build();
        List<String> list = new ArrayList<>();
        /*for (int i = 0; i < 4; i++) {
            list.add(i + "asdqweqsadsad");
        }*/

        writer1.write(list,sheet1,table);


        /*table.setHead(headList);
        writer.write0(null,sheet,table);*/
        writer1.finish();
        /*writer.finish();*/
    }
}
