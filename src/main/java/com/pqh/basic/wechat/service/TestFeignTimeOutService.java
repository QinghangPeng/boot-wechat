package com.pqh.basic.wechat.service;

import com.pqh.basic.wechat.error.ServiceError;
import com.pqh.basic.wechat.feign.BasicDataFeign;
import com.pqh.basic.wechat.response.RestResponse;
import com.pqh.basic.wechat.util.RestClientHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TestFeignTimeOutService
 * @Description:
 * @Author: jackson
 * @Date: 2019/12/30 下午9:13
 * @Version: v1.0
 */
@Service
@Slf4j
public class TestFeignTimeOutService {

    @Autowired
    private BasicDataFeign feign;

    public RestResponse testFeign() {
        long now = System.currentTimeMillis();
        try{
            List<Map<String, String>> data = new ArrayList<>();
            log.info("开始尝试连接远程服务:{}",now);
            RestResponse<List<Map<String,String>>> response = feign.qryPark("011","01");
            data = RestClientHelper.getRestData(response);
            log.info("===========================");
            return RestResponse.success(data);
        } catch(Exception e) {
            log.error("尝试连接失败，花费时间:{}",System.currentTimeMillis() - now);
            log.error("testFeign error:{}",e);
            return RestResponse.error(ServiceError.UN_KNOW_NULL);
        }
    }


    public static int max3(int a,int b,int c) {
        return (a > b) ? (a > c ? a : c) : (b > c ? b : c);
    }

    public static int divideAndConquer(int[] list,int left,int right) {
        //存放左右子问题的解
        int maxLeftSum,maxRightSum;
        //存放跨分界线的解
        int maxLeftBorderSum,maxRightBorderSum;

        int leftBorderSum,rightBorderSum;
        int center,i;

        //递归终止条件，子列只有一个数字
        if (left == right) {
            if (list[left] > 0) {
                return list[left];
            }
            return 0;
        }

        //分的过程
        center = (left + right) / 2;
        //递归求左子列和
        maxLeftSum = divideAndConquer(list,left,center);
        //递归求右子列和
        maxRightSum = divideAndConquer(list,center + 1,right);

        //跨分界线的最大子列和
        maxLeftBorderSum = 0;
        leftBorderSum = 0;
        for (i = center; i >= left; i--) {
            leftBorderSum += list[i];
            if (leftBorderSum > maxLeftBorderSum) {
                maxLeftBorderSum = leftBorderSum;
            }
        }

        maxRightBorderSum = 0;
        rightBorderSum = 0;
        for (i = center + 1; i <= right ; i++) {
            rightBorderSum += list[i];
            if (rightBorderSum > maxRightBorderSum) {
                maxRightBorderSum = rightBorderSum;
            }
        }

        //返回治的结果
        return max3(maxLeftSum,maxRightSum,maxLeftBorderSum + maxRightBorderSum);
    }

    public static int maxSubseqSum3(int[] list, int n) {
        return divideAndConquer(list,0, n - 1);
    }

    public static void main(String[] args) {
        int[] list = new int[]{4,-3,5,-2,-1,2,6,-2};
        int maxSum = maxSubseqSum3(list, list.length);
        System.out.println(maxSum);
    }
}
