package com.cloud.olifebase.load;

import java.util.*;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.25
 * @GitHub https://github.com/AbrahamTemple/
 * @description: 负载均衡的加权随机算法
 */
public class WeightRandom {
    public static Map<String, Integer> servers = new HashMap<String, Integer>();

    static{
        servers.put("192.168.20.101", 1);
        servers.put("192.168.20.102", 2);
        servers.put("192.168.20.103", 3);
        servers.put("192.168.20.104", 4);
    }

    public static String selectServer(Map<String, Integer> servers){
        if(servers == null || servers.size() == 0) return null;

        Integer sum = 0;
        Set<Map.Entry<String, Integer>> entrySet = servers.entrySet();
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

    public static void main(String[] args){
        Map<String, Integer> map = new HashMap<String, Integer>();
        String key = null;
        for(int i = 0; i < 1000; i++){
            key = selectServer(servers);
            if(map.containsKey(key)){
                map.put(key, map.get(key) + 1);
            }else{
                map.put(key, 1);
            }
        }
        for(String key1 : map.keySet()){
            System.out.println(key1 + " " + map.get(key1));
        }
        System.out.println(selectServer(servers));
    }
}
