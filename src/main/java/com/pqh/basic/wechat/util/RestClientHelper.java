package com.pqh.basic.wechat.util;


import com.pqh.basic.wechat.error.BasicWechatException;
import com.pqh.basic.wechat.error.ServiceError;
import com.pqh.basic.wechat.response.RestResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-11-8 下午4:03
 */
public class RestClientHelper {

    public static <T> T getRestData(RestResponse<T> response) {
        if (RestResponse.SUCCESS_CODE.equals(response.getCode())) {
            return response.getData();
        } else {
            throw new BasicWechatException(ServiceError.SERVICE_CALL_ERROR);
        }
    }


    public static Object[] splitAry(byte[] ary, int subSize) {
        int count = ary.length % subSize == 0 ? ary.length / subSize : ary.length / subSize + 1;

        List<List<Byte>> subAryList = new ArrayList<List<Byte>>();

        for (int i = 0; i < count; i++) {
            int index = i * subSize;
            List<Byte> list = new ArrayList<Byte>();
            int j = 0;
            while (j < subSize && index < ary.length) {
                list.add(ary[index++]);
                j++;
            }
            subAryList.add(list);
        }

        Object[] subAry = new Object[subAryList.size()];

        for (int i = 0; i < subAryList.size(); i++) {
            List<Byte> subList = subAryList.get(i);
            byte[] subAryItem = new byte[subList.size()];
            for (int j = 0; j < subList.size(); j++) {
                subAryItem[j] = subList.get(j);
            }
            subAry[i] = subAryItem;
        }

        return subAry;
    }

}
