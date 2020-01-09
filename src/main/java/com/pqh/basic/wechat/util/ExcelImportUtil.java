package com.pqh.basic.wechat.util;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.pqh.basic.wechat.util.excelutil.ExcelListener;

import java.io.*;
import java.util.List;

/**
 * @ClassName: ExcelImportUtil
 * @Description:
 * @Author: jackson
 * @Date: 2020/1/9 上午11:55
 * @Version: v1.0
 */
public class ExcelImportUtil {

    /**
     * 从Excel中读取文件，读取的文件是一个DTO类，该类必须继承BaseRowModel
     * 具体实例参考 ： MemberMarketDto.java
     * 参考：https://github.com/alibaba/easyexcel
     * 字符流必须支持标记，FileInputStream 不支持标记，可以使用BufferedInputStream 代替
     * BufferedInputStream bis = new BufferedInputStream(new FileInputStream(...));
     *
     * @param inputStream 文件输入流
     * @param clazz       继承该类必须继承BaseRowModel的类
     * @return 读取完成的list
     */
    public static <T extends BaseRowModel> List<T> readExcel(final InputStream inputStream, final Class<? extends BaseRowModel> clazz) {
        if (null == inputStream) {
            throw new NullPointerException("the inputStream is null!");
        }
        AnalysisEventListener listener = new ExcelListener();
        //读取xls 和 xlxs格式
        //如果POI版本为3.17，可以如下声明
        ExcelReader reader = new ExcelReader(inputStream, null, listener);
        //判断格式，针对POI版本低于3.17
        //ExcelTypeEnum excelTypeEnum = valueOf(inputStream);
        //ExcelReader reader = new ExcelReader(inputStream, excelTypeEnum, null, listener);
        reader.read(new com.alibaba.excel.metadata.Sheet(1, 1, clazz));

        return ((ExcelListener) listener).getData();
    }

    /**
     * 需要写入的Excel，有模型映射关系
     *
     * @param file  需要写入的Excel，格式为xlsx
     * @param list 写入Excel中的所有数据，继承于BaseRowModel
     */
    public static void writeExcel(final File file, List<? extends BaseRowModel> list) throws IOException{
        OutputStream out = new FileOutputStream(file);
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet,  有模型映射关系
            Class t = list.get(0).getClass();
            com.alibaba.excel.metadata.Sheet sheet = new com.alibaba.excel.metadata.Sheet(1, 0, t);
            writer.write(list, sheet);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
