package com.yishu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by admin on 2017/5/12.
 */
public class JsonUtil {

    //List<t_object>---->>JSON
    public String listToJsonArray(List list){
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
}
