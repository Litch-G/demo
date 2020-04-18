package com.example.demo.spark;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class StringExu {
    public static Map<String,Integer> exu(String path){
        Map<String,Integer> map= new HashMap<>();
        map.put("comedy",0);
        map.put("log",0);
        map.put("sci_fi",0);
        map.put("suspense",0);
        map.put("comic",0);
       // nice.main("F:\\upload\\demo__testuser_userdata.txt");
        try {
            File file = new File("f:\\userInfo\\demo_"+path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String strLine = null;
            int lineCount = 1;
            while(null != (strLine = reader.readLine())){
             strLine = strLine.replaceAll("\\(|\\)", "");
             String[] info = strLine.split(",");
             for(Map.Entry<String, Integer> entry : map.entrySet()){
                    String mapKey = entry.getKey();
                    Integer mapValue = entry.getValue();
                    System.out.println(mapKey+":"+mapValue);
                    if (mapKey.equals(info[0])){
                        map.put(info[0],Integer.parseInt(info[1]));
                    }
                    else {
                        System.out.println("error into map!");
                    }
             }
             lineCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
        System.out.println(map);
        return map;
    }
}
