package com.yishu.util;

import com.thoughtworks.xstream.XStream;
import com.yishu.domain.*;
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
    //消息类型 MsgType
    public static final String MESSAGE_TEXT="text";
    public static final String MESSAGE_NEWS="news";//图文消息类型
    public static final String MESSAGE_IMAGE="image";//图片消息类型
    public static final String MESSAGE_MUSIC="music";
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

    public static String imageMessageToXml(ImageMessage imageMessage){
        XStream xstream = new XStream();
        xstream.alias("xml",imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }

    public static String musicMessageToXml(MusicMessage musicMessage){
        XStream xstream = new XStream();
        xstream.alias("xml",musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

    public static String messageToXml(Class<? extends BaseMessage> message){
        XStream xstream = new XStream();
        xstream.alias("xml",message.getClass());
        return xstream.toXML(message);
    }

    /**
     * 组装文本消息bean(MsgType="text")TextMessage,再转为XML
     * @param toUserName
     * @param fromUserName
     * @param content
     * @return
     */
    public static String initText(String toUserName,String fromUserName,String content){
        TextMessage text=new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent("您发送的消息是："+content);
        return textMessageToXml(text);
    }

    /**
     * 组装图文消息bean(MsgType="news")NewsMessage,再转为XML
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initNewsMessage(String toUserName,String fromUserName){
        News news=new News();
        news.setTitle("门锁管理");
        news.setDescription("图文消息:门锁管理webapp入口");
        news.setPicUrl("http://112.25.233.122:80/loclWOA/resources/media/webapp_entrance.png");
//        news.setUrl("www.yishutech.com");
        news.setUrl("http://112.25.233.122:80/lockWOA/jsp/main.jsp"); //点击用户点击该图片,即访问门锁管理页面
        NewsMessage newsMessage=new NewsMessage();
        List<News> newsList=new ArrayList<News>();
        newsList.add(news);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setToUserName(fromUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());
        return newsMessageToXml(newsMessage);
    }

    /**
     * 组装图片消息bean(MsgType="image")ImageMessage,再转为XML
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initImageMessage(String toUserName,String fromUserName){
        Image image=new Image();
        image.setMediaId("eab2s43fa"); //上传图片文件后获得的MediaId
        ImageMessage imageMessage=new ImageMessage();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setMsgType(MESSAGE_IMAGE);
        imageMessage.setCreateTime(new Date().getTime());
        imageMessage.setImage(image);
        return imageMessageToXml(imageMessage);
    }

    public static String initMusicMessage(String toUserName,String fromUserName){
        Music music=new Music();
        music.setThumbMediaId("er54d90il86j"); //上传音乐文件后获得的ThumbMediaId
        music.setTitle("Love Story meets Viva La Vida");
        music.setDescription("纯音乐 <<假如爱有天意>>");
        music.setMusicUrl("http://112.25.233.122:8000/lockWOA/media/Love Story meets Viva La Vida.mp3");
        music.setHQMusicUrl("http://112.25.233.122:8000/lockWOA/media/Love Story meets Viva La Vida.mp3");
        MusicMessage musicMessage=new MusicMessage();
        musicMessage.setMusic(music);
        musicMessage.setFromUserName(toUserName);
        musicMessage.setToUserName(fromUserName);
        musicMessage.setMsgType(MESSAGE_MUSIC);
        musicMessage.setCreateTime(new Date().getTime());
        return musicMessageToXml(musicMessage);
    }

    /**
     * 定义文本消息对象 TextMessage bean 的 content
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
}