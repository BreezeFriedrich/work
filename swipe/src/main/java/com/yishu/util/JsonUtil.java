package com.yishu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

/**
 * Created by admin on 2017/5/12.
 */
public class JsonUtil {

    //List<t_object>---->>JSON
    public String listToJson(List list){
        ObjectMapper objectMapper = new ObjectMapper();

//        Iterator it =list.iterator();
//        while (it.hasNext()){
//            it.next();
//        }
        String data=null;
        try {
            data=objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return data;
    }

    public String mapToJson(Map map){
        ObjectMapper objectMapper = new ObjectMapper();
        String data=null;
        try {
            data=objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return data;
    }
}
