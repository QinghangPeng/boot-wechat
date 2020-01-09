package com.pqh.basic.wechat.util;


import com.pqh.basic.wechat.error.BasicWechatException;
import com.pqh.basic.wechat.error.ServiceError;
import com.pqh.basic.wechat.response.RestResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 随机码生成
     * @author Mo
     *
     * @param length 随机码长度
     * @return
     */
    public static String random(int length){
        /*
         * 这里直接数字代替，没用uuid.length()
         * */
        String uuid = UUID.randomUUID().toString().replace("-", "");

        int len = uuid.length();

        /*定义随机码字符串变量，初始化为""*/
        String random = "";

        /*
         * 循环截取UUID
         * len/length 每次循环截取的字符串长度
         * len%length 如果出现32长度除不尽的情况，取余数
         * */
        int subLen = len/length;
        int remainder = len%length;

        /*定义substring的两个参数*/
        int start = 0,end = 0;
        for(int i=0;i<length;i++){
            /*
             * 计算start和end的值
             * 这里涉及两种方法，一种是除不尽的时候，将截取长度分散到头部，一种是分散到尾部
             *
             * uuid的前部分是时间戳构成的，因此前部分截取越少，重复率越底
             * 固本方法采用了将多余的部分分散到尾部
             * */
            /*分散到尾部，如length为7的时候4,4,4,4,5,5,5*/
//			end = start + (length-i <= remainder ? 1 : 0)+subLen;
            /*分散到头部，如length为7 的时候5,5,5,5,4,4,4*/
            end = start + (i < remainder ? 1 : 0)+subLen;
            /*截取到的字符串*/
            String code = uuid.substring(start,end);
            /*对所截取的长度进行16位求和*/
            int count = 0;
            for(char c : code.toCharArray()){
                count += Integer.valueOf(String.valueOf(c),16);
            }
            /*将求和结果转化成36位，并增加到随机码中，36位包含了0-9a-z*/
            random += Integer.toString(count%36, 36);
            start = end;
        }
        /*返回随机码*/
        return random;
    }

    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

}
