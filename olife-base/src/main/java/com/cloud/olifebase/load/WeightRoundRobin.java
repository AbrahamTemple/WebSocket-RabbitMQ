package com.cloud.olifebase.load;

import com.cloud.olifebase.domian.OPhone;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.25
 * @GitHub https://github.com/AbrahamTemple/
 * @description: 负载均衡的加权轮询调度算法获取通话号码
 */

/**
 *  算法流程：
 *  假设有一组服务器 S = {S0, S1, …, Sn-1}
 *  有相应的权重，变量currentIndex表示上次选择的服务器
 *  权值currentWeight初始化为0，currentIndex初始化为-1 ，当第一次的时候返回 权值取最大的那个服务器，
 *  通过权重的不断递减 寻找 适合的服务器返回，直到轮询结束，权值返回为0
 */

public class WeightRoundRobin {

    /**上次选择的服务器*/
    private int currentIndex = -1;
    /**当前调度的权值*/
    private int currentWeight = 0;
    /**最大权重*/
    private int maxWeight;
    /**权重的最大公约数*/
    private int gcdWeight;
    /**空闲服务的电话数*/
    private int phoneCount;
    /**电话集*/
    private List<OPhone> phones;

    public WeightRoundRobin(Builder builder) {
        currentIndex = builder.currentIndex;
        currentWeight = builder.currentWeight;
        maxWeight = builder.maxWeight;
        gcdWeight = builder.gcdWeight;
        phoneCount = builder.phoneCount;
        phones = builder.phones;
    }

    /** 链式调用 */
    public static class Builder {

        /**上次选择的服务器*/
        private int currentIndex = -1;
        /**当前调度的权值*/
        private int currentWeight = 0;
        /**最大权重*/
        private int maxWeight;
        /**权重的最大公约数*/
        private int gcdWeight;
        /**空闲服务的电话数*/
        private int phoneCount;
        /**电话集*/
        private List<OPhone> phones;

        public Builder(){}

        public WeightRoundRobin create(){
            return new WeightRoundRobin(this);
        }

        public Builder setPhones(List<OPhone> phones) {
            this.phones = phones;
            return this;
        }

        public int greaterCommonDivisor(int a, int b) {
            BigInteger aBig = new BigInteger(String.valueOf(a));
            BigInteger bBig = new BigInteger(String.valueOf(b));
            return aBig.gcd(bBig).intValue();
        }

        public int greatestCommonDivisor(List<OPhone> servers) {
            int divisor = 0;
            for (int index = 0, len = servers.size(); index < len - 1; index++) {
                if (index == 0) {
                    divisor = greaterCommonDivisor(
                            servers.get(index).getWeight(), servers.get(index + 1).getWeight());
                } else {
                    divisor = greaterCommonDivisor(divisor, servers.get(index).getWeight());
                }
            }
            return divisor;
        }

        public int greatestWeight(List<OPhone> servers) {
            int weight = 0;
            for (OPhone server : servers) {
                if (weight < server.getWeight()) {
                    weight = server.getWeight();
                }
            }
            return weight;
        }

        public Builder init() {
            maxWeight = greatestWeight(phones); // 得到最大权重
            gcdWeight = greatestCommonDivisor(phones); // 最大公约数
            phoneCount = phones.size(); // 总数
            return this;
        }

    }

    public OPhone getPhone() {
        while (true) {
            currentIndex = (currentIndex + 1) % phoneCount;
            if (currentIndex == 0) {
                currentWeight = currentWeight - gcdWeight;
                if (currentWeight <= 0) {
                    currentWeight = maxWeight;
                    if (currentWeight == 0) {
                        return null;
                    }
                }
            }
            if (phones.get(currentIndex).getWeight() >= currentWeight) {
                return phones.get(currentIndex);
            }
        }
    }

//    public void init() {
//        maxWeight = greatestWeight(phones); // 得到最大权重
//        gcdWeight = greatestCommonDivisor(phones); // 最大公约数
//        phoneCount = phones.size(); // 总数
//    }

    public static void main(String[] args){

        List<OPhone> phones = new ArrayList<OPhone>();
        phones.add(new OPhone("13106789112", 1));
        phones.add(new OPhone("16752042001", 2));
        phones.add(new OPhone("19754524045", 3));
        phones.add(new OPhone("12074527570", 4));
        phones.add(new OPhone("17575420455", 5));
        phones.add(new OPhone("13783534101", 4));
        phones.add(new OPhone("15934231210", 3));
        phones.add(new OPhone("18274534347", 2));
//        WeightRoundRobin weightRoundRobin = new WeightRoundRobin(phones);
//        weightRoundRobin.init();
        WeightRoundRobin weightRoundRobin = new WeightRoundRobin.Builder()
                .setPhones(phones)
                .init()
                .create();

        for(int i = 0; i < 15; i++){
            OPhone phone = weightRoundRobin.getPhone();
            System.out.println(phone);
        }
    }

}