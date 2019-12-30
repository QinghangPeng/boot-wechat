package com.pqh.basic.wechat.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pqh.basic.wechat.error.IError;

import java.util.HashMap;
import java.util.Map;

public class RestResponse<T> {
    public static final String DEFAULT_DATA_KEY = "data";
    public static final String DEFAULT_DATAS_KEY = "datas";
    public static final String SUCCESS_CODE = "00000000";
    private String code;
    private String msg;
    private Long pageIndex;
    private Long pageCount;
    private Long pageSize;
    private Long totalCount;
    private T data;
    private T datas;
    @JsonIgnore
    private Map<String, T> any;

    public RestResponse() {
        this.code = "00000000";
        this.msg = "成功";
    }

    public static RestResponse success() {
        RestResponse response = new RestResponse();
        return response;
    }

    public static RestResponse success(Object data) {
        return success((String)DEFAULT_DATA_KEY, (Object)data);
    }

    public static RestResponse success(String key, Object data) {
        return success(key, data, (Object)null);
    }

    public static RestResponse success(Object data, Object pageInfo) {
        return success(DEFAULT_DATAS_KEY, data, pageInfo);
    }


    public static RestResponse success(String key, Object data, Object pageInfo) {
        RestResponse response = success();
        if (key.equals(DEFAULT_DATA_KEY)){
            response.data = data;
        }else if(DEFAULT_DATAS_KEY.equals(key)){
            response.datas = data;
        }else{
            response.put(key, data);
        }
        if(pageInfo != null) {
            response.page(pageInfo);
        }
        return response;
    }


    public static RestResponse error(IError error) {
        RestResponse response = new RestResponse();
        response.code = error.getErrorCode();
        response.msg = error.getErrorMessage();
        return response;
    }

    public RestResponse put(T any) {
        if(this.any == null) {
            this.any = new HashMap();
        }

        this.any.put(DEFAULT_DATA_KEY, any);
        return this;
    }

    public RestResponse put(String key, T data) {
        if(data == null) {
            return this;
        } else {
            if(this.any == null) {
                this.any = new HashMap();
            }

            this.any.put(key, data);
            return this;
        }
    }

    public RestResponse put(Map<String, T> any) {
        if(this.any == null) {
            this.any = new HashMap();
        }

        this.any.putAll(any);
        return this;
    }

    @JsonAnyGetter
    public Map<String, T> anyGetter() {
        return this.any;
    }

    @JsonAnySetter
    public void anySetter(String name, T value) {
        if(this.any == null) {
            this.any = new HashMap();
        }

        this.any.put(name, value);
    }

    public Long getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public Long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private RestResponse page(Object page) {
//        this.pageIndex = Long.parseLong(""+page.getPageNum());
//        this.pageCount = Long.parseLong(""+page.getPages());
//        this.pageSize = Long.parseLong(""+page.getPageSize());
//        this.totalCount = page.getTotal();
        return this;
    }
}
