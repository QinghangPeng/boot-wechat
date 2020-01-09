package com.pqh.basic.wechat.util.excelutil;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ExcelListener
 * @Description:
 * @Author: jackson
 * @Date: 2020/1/9 下午12:45
 * @Version: v1.0
 */
public class ExcelListener<T extends BaseRowModel> extends AnalysisEventListener<T> {
    /**
     * 自定义用于暂时存储data。
     * 可以通过实例获取该值
     */
    private final List<T> data = new ArrayList<>();

    @Override
    public void invoke(T object, AnalysisContext context) {
        //数据存储
        data.add(object);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    public List<T> getData() {
        return data;
    }

}
