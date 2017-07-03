package com.yishu.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by admin on 2017/5/12.
 */
public class JsonUtil {

    private static final Logger logger= LoggerFactory.getLogger(JsonUtil.class);
    private ObjectMapper objectMapper=new ObjectMapper();
    private JsonGenerator jsonGenerator=null;
    public ObjectNode rootNode=objectMapper.createObjectNode();

    JsonNode readRoot=null;
    JsonNode resultNode=null;
    JsonNode dataNode=null;
    int result=-100;
    StringWriter stringWriter=new StringWriter();
    String data=null;
    int dataInt;

    public String writeObject(Object obj){
        try {
            data=objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(null==data){data="";}
        return data;
    }

    public int getResult(String str){
        try {
            readRoot=objectMapper.readTree(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultNode=readRoot.path("result");
        result=resultNode.asInt();
        return result;
    }

    public JsonNode getDataNode(String str){
        try {
            readRoot=objectMapper.readTree(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultNode=readRoot.path("result");
        result=resultNode.asInt();
        if(0>result){
            return null;
        }
        dataNode=readRoot.path("data");
        return dataNode;
    }

    public int getIntFromDataNode(String str){//{result:xx;data:{...}}
//        result=getResult(str);//获取result
        dataNode=getDataNode(str);
        if(0==result){
            dataInt=dataNode.asInt();
            return dataInt;
        }
        return -100;
    }

    /*
    //List<t_object>---->>JSON
    public String listToJson(List list){
        ObjectMapper objectMapper = new ObjectMapper();
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
 */
    //采用树模型JsonNode,将Map转换为Json字符串{sign:xx,xx:xx,xx:xx,...}格式.
    //问题：objectMapper.writeTree(jsonGenerator,rootNode);
        //jsonGenerator=objectMapper.getFactory().createGenerator(stringWriter);
        //即writeTree最终将JSON输出到流,由流StringWriter输出为String对象却存在数据叠加问题.

    public String writeTreeToString(HashMap map){
        logger.info("map",map.toString());
        stringWriter.flush();
        data=null;
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
        map.clear();
        try {
            jsonGenerator=objectMapper.getFactory().createGenerator(stringWriter);

            if (jsonGenerator== null){
                stringWriter.close();
            }
            objectMapper.writeTree(jsonGenerator,rootNode);
            data=stringWriter.toString();
            stringWriter.flush();
            stringWriter.close();
            jsonGenerator.flush();
            jsonGenerator.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        logger.info("data:",data);
        return data;
    }

}
