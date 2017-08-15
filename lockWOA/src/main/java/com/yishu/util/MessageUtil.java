package com.yishu.util;

import com.thoughtworks.xstream.XStream;
import com.yishu.domain.News;
import com.yishu.domain.NewsMessage;
import com.yishu.domain.TextMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.SAXParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 微信,消息处理工具类
 */
public class MessageUtil {
    public static final String MESSAGE_TEXT="text";
    public static final String MESSAGE_IMAGE="image";
    public static final String MESSAGE_VOICE="voice";
    public static final String MESSAGE_VIDEO="video";
    public static final String MESSAGE_LINK="link";
    public static final String MESSAGE_LOCATION="location";
    public static final String MESSAGE_EVENT="event";
    public static final String MESSAGE_SUBSCRIBE="subscribe";
    public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
    public static final String MESSAGE_CLICK="click";
    public static final String MESSAGE_VIEW="view";

    /**
     * XML转为Map
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String,String> map = new HashMap<String,String>();
        SAXReader reader= new SAXReader();
        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);
        Element root= doc.getRootElement();
        List<Element> list = root.elements();
        for(Element e:list){
            map.put(e.getName(),e.getText());
        }
        ins.close();
        return map;
    }

    /**
     * 将文本消息(MsgType="text")对象转为XML
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage){
        XStream xstream = new XStream();
        xstream.alias("xml",textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * 将图文消息(MsgType="image")对象转为XML
     * @param newsMessage
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage){
        XStream xstream = new XStream();
        xstream.alias("xml",newsMessage.getClass());
        xstream.alias("item",new News().getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     * 主菜单(返回的消息内容,是TextMessage文本消息对象的组件content)
     * @return
     */
    public static String menuText(){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("欢迎您关注亿数智能锁公众号。请按照菜单提示进行操作：\n\n");
        stringBuffer.append("1、添加网关");
        stringBuffer.append("2、添加锁");
        stringBuffer.append("回复？调出此菜单");
        return stringBuffer.toString();
    }

    public static String firstMenu(){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("添加网关需要扫描网关验证码");
        return stringBuffer.toString();
    }

    public static String secondMenu(){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("添加门锁需要关联网关");
        return stringBuffer.toString();
    }

    /**
     * 组装文本消息(MsgType="text")TextMessage,再转XML
     * @param toUserName
     * @param fromUserName
     * @param content
     * @return
     */
    public static String initText(String toUserName,String fromUserName,String content){
        TextMessage text=new TextMessage();
        text.setFromUserName(fromUserName);
        text.setToUserName(toUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent("您发送的消息是："+content);
        return textMessageToXml(text);
    }

    /**
     * 组装图文消息(MsgType="image")NewsMessage,再转XML
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initNewsMessage(String toUserName,String fromUserName){
        String message=null;
        List<News> newsList=new ArrayList<News>();
        NewsMessage newsMessage=new NewsMessage();
        News news=new News();
        news.setTitle("NewsMessage");
        news.setDescription("图文消息");
        news.setPicUrl("http://112.25.233.122:80/loclWOA/resources/img/NewsMessage.jpg");
        news.setUrl("www.yishutech.com");
        newsList.add(news);
        newsMessage.setFromUserName(fromUserName);
        newsMessage.setToUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.MESSAGE_IMAGE);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());
        message=newsMessageToXml(newsMessage);
        return message;
    }
}