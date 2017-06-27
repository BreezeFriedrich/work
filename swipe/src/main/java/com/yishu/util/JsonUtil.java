package com.yishu.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by admin on 2017/5/12.
 */
public class JsonUtil {

    private ObjectMapper objectMapper=new ObjectMapper();
    private JsonGenerator jsonGenerator=null;
    public ObjectNode rootNode=objectMapper.createObjectNode();

    JsonNode readRoot=null;
    JsonNode resultNode=null;
    JsonNode dataNode=null;
    int result=-100;

    //将Map转换为Json字符串{sign:xx,xx:xx,xx:xx,...}格式.
    public void writeTreeToString(HashMap map,String str){
        Iterator it=map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            Object val = entry.getValue();
            if(val.getClass().equals(int.class)){
                rootNode.put(key,(int)val);
            }else if (val.getClass().equals(long.class)){
                rootNode.put(key, (Long) val);
            }else if (val.getClass().equals(double.class)){
                rootNode.put(key, (Double) val);
            }else if(val.getClass().equals(String.class)){
                rootNode.put(key, (String) val);
            }else {
                //if (val.getClass().equals(List.class)||val.getClass().equals(Array.class)||val.getClass().equals(Map.class))
                rootNode.putPOJO(key,val);
            }
        }
        try {
            jsonGenerator=objectMapper.getFactory().createGenerator(new PrintWriter(str));
            objectMapper.writeTree(jsonGenerator,rootNode);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public JsonNode getDataNode(String str){
        try {
            readRoot=objectMapper.readTree(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultNode=readRoot.path("sign");
        result=resultNode.asInt();
        if(0>result){
            return null;
        }
        dataNode=readRoot.path("data");
        return dataNode;
    }

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
