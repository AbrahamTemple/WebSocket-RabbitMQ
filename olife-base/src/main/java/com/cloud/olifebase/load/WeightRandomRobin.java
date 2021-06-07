package com.cloud.olifebase.load;

import com.cloud.olifebase.domian.OPhone;

import java.util.*;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.25
 * @GitHub https://github.com/AbrahamTemple/
 * @description: 负载均衡的加权随机调度算法获取通话号码
 */
public class WeightRandomRobin {

    Map<String, Integer> phoneMap;

    public WeightRandomRobin(Builder builder){
        phoneMap = builder.phoneMap;
    }

    /** 链式调用 */
    public static class Builder {
        /**电话集*/
        private List<OPhone> phoneList;
        Map<String, Integer> phoneMap;

        public WeightRandomRobin create(){
            return new WeightRandomRobin(this);
        }

        public Builder setPhones(List<OPhone> phoneList) {
            this.phoneList = phoneList;
            return this;
        }

        public Builder init(){
            phoneMap =  new HashMap<>();
            phoneList.forEach( p-> phoneMap.put(p.getNumber(),p.getWeight()));
            return this;
        }
    }

    public String getPhone(){
        if(phoneMap == null || phoneMap.size() == 0) return null;

        Integer sum = 0;
        Set<Map.Entry<String, Integer>> entrySet = phoneMap.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            sum += iterator.next().getValue();
        }
        Integer rand = new Random().nextInt(sum) + 1;
        for(Map.Entry<String, Integer> entry : entrySet){
            rand -= entry.getValue();
            if(rand <=0){
                return entry.getKey();
            }
        }
        return null;
    }

}
