package com.example.myrpc6.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * 随机负载均衡
 */
public class RandomLoadBalance implements LoadBalance {
    @Override
    public String balance(List<String> addressList) {
        Random random = new Random();
        int choose = random.nextInt(addressList.size());
        System.out.println("负载均衡选择了第" + choose + "个服务器");
        return addressList.get(choose);
    }
}
