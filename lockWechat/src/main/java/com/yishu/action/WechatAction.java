/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.action;

import com.yishu.util.MessageUtil;
import com.yishu.util.WechatCheckSignature;
import org.dom4j.DocumentException;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-28 11:21 admin
 * @since JDK1.7
 */
public class WechatAction extends BaseAction {
    private org.slf4j.Logger logger= LoggerFactory.getLogger("WechatAction");

    private String signature;// 微信加密签名
    private String timestamp;// 时间戳
    private String nonce;// 随机数
    //    private String token = "yishutech";// 自定义token
    private String echostr;// 随机字符串
    PrintWriter out = null;// response.getWriter();;

    /**wx/authdev.action
     * 微信为了认证开发者服务器,发送认证请求.
     * 处理该请求,加工token和请求参数timestamp、nonce与signature比对,判断该请求是否来自微信.如果是,返回echostr.
     */
    public void authdev() throws IOException, ServletException, DocumentException {
        try{
            // 设置发送文件类型 及编码方式 charset 不能省略 防止乱码
            response.setContentType("text/html; charset=utf-8");
            out = response.getWriter();
        }
        catch (IOException e1){
            e1.printStackTrace();
        }
//        logger.debug("******signature********" + signature);
//        logger.debug("******timestamp********" + timestamp);
//        logger.debug("******nonce********" + nonce);
//        logger.debug("******echostr********" + echostr);
        /*
        if (signature == null || timestamp == null || nonce == null){
            return;
        }
        if(null!=echostr && WechatCheckSignature.checkSignature(signature,timestamp,nonce)){
            out.print(echostr);
            return;
        }
        */
        if(null!=echostr){
            out.print(echostr);
            return;
        }
        // 处理其余请求
//        String encoding = request.getContentType();
//        System.out.println(encoding + "encoding");
//        request.setCharacterEncoding("UTF-8");
//        request.setCharacterEncoding(encoding);
        Map<String,String> map= MessageUtil.xmlToMap(request);
        this.handleMessage(map, out);
    }

    protected void handleMessage(Map<String,String> map, PrintWriter out) {
            String fromUserName=map.get("FromUserName"); //发送方帐号（关注公众号用户的OpenID）
            String toUserName=map.get("ToUserName"); //开发者微信号
            String msgType=map.get("MsgType");
            String content=map.get("Content");
            String message=null;
            if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
                if       (content.equals("0")){
                    message=MessageUtil.initNewsMessage(toUserName,fromUserName);
                }else if (content.equals("1")){
                    message=MessageUtil.initText(toUserName,fromUserName,MessageUtil.firstMenu());
                }else if (content.equals("2")){
                    message=MessageUtil.initText(toUserName,fromUserName,MessageUtil.secondMenu());
                }else if (content.equals("3")){
                    message=MessageUtil.initImageMessage(toUserName,fromUserName);
                }else if (content.equals("?")||content.equals("？")){
                    message=MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());
                }
            }else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
                String eventType=map.get("Event");
                if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
                    message=MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());
                }
            }
            System.out.println(message);
            out.print(message);
            out.close();
    }

    /*
    // 解析 xml数据
    private void handleMassage(String reqStr, PrintWriter out){
        BasicBSONObject reqBSON = DOM4JTool.decodeXML(reqStr);

        logger.debug("消息为" + reqBSON.toString());
        String openid = reqBSON.getString("FromUserName");// 消息来自哪里
        String MsgType = reqBSON.getString("MsgType");// 获取用户的信息类型
        String to = reqBSON.getString("ToUserName");// 获取公众号id
        PushMassage pu = new PushMassage();
        // 根据消息类型处理
        // 处理事件
        if (MsgType.equalsIgnoreCase("event")){
            // 事件类型
            String Event = reqBSON.getString("Event");
            if (Event == null){
                return;
            }

            if (Event.equalsIgnoreCase("subscribe")) // 关注含扫码关注
            {
                this.HandelSubscribe(openid, out, to);
            }
            else if (Event.equalsIgnoreCase("unsubscribe")) // 取消关注
            {
                this.HandelUnSubscribe(openid);
            }
            else if (Event.equalsIgnoreCase("SCAN")) // 扫描二维码
            {

            }

            else if (Event.equalsIgnoreCase("CLICK"))// 点击自定义菜单事件
            {
                String eventkey = reqBSON.getString("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
            }
            else if (Event.equalsIgnoreCase("VIEW"))// 点击超链接菜单的点击事件
            {

            }
        }
        // 用户普通文本消息
        else if (MsgType.equalsIgnoreCase("text")){
            out.print(pu.pushTextMess(openid, to, "亲！\n你说的话太高深，小漫一时无法理解！"));
        }
        // 用户普通图片消息
        else if (MsgType.equalsIgnoreCase("image")){
            out.print(pu.pushTextMess(openid, to, "亲！\n你发的是图片奥！"));
        }
        // 用户普通语音消息
        else if (MsgType.equalsIgnoreCase("voice")){
            out.print(pu.pushTextMess(openid, to, "亲！\n你发的是语音奥！"));
        }
        // 用户普通视频消息
        else if (MsgType.equalsIgnoreCase("video")){
            out.print(pu.pushTextMess(openid, to, "亲！\n你发的是视频奥！"));
        }
        // 用户地理位置消息
        else if (MsgType.equalsIgnoreCase("location")){
            out.print(pu.pushTextMess(openid, to, "亲！\n你发的是位置奥，一定要找到你！"));
        }
    }

    // 用户关注处理
    private void HandelSubscribe(String openid, PrintWriter out, String ouropenid){
        PushMassage pu = new PushMassage();
        out.print(pu.pushTextMess(openid, ouropenid, "终于等到你了 ，亲！\n欢迎关注漫行网！"));
        User user=wxservice.findUserByopenid(openid);
        if(user==null){
            user = new User();
            user.setOpenid(openid);
            Map<String,String> mm= this.HandelNickName(openid);
            String nickname =mm.get("nickname");
            String imgurl=mm.get("headimgurl");
            user.setNickname(nickname);
            user.setImgurl(imgurl);
            user.setCreatetime(DataUtil.fromDate24H());
            // 保存用户
            int val = wxservice.addSubscribe(user);
            if (val == 1){
                logger.debug("保存用户信息成功" + openid);
            }
        }else{
            Map<String,String> mm = this.HandelNickName(openid);
            user.setNickname(mm.get("nickname"));
            user.setImgurl(mm.get("headimgurl"));
            int val = wxservice.UnSubscribe2(user);
            if (val == 1){
                logger.debug("修改用户信息成功" + openid);
            }
        }
    }

    // 取消关注处理
    private void HandelUnSubscribe(String openid){
        User user = new User();
        user.setOpenid(openid);
        user.setUntime(DataUtil.fromDate24H());
        wxservice.UnSubscribe(user);
    }

    // 获取用户昵称
    private Map<String,String> HandelNickName(String openid){
        String access_token = GetToken.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
                + access_token + "&openid=" + openid + "&lang=zh_CN";
        String str = HttpUtil.getUrl(url);
        String nickname = "";
        String headimgurl="";
        Map<String,String> mm=new HashMap<String, String>();
        try{
            str = new String(str.getBytes("ISO-8859-1"), "utf-8");
            JSONObject json = JSONObject.fromObject(str);
            nickname = json.getString("nickname");
            // 去除用户的emoji表情
            nickname = EmojiFilter.filterEmoji(nickname);
            headimgurl= json.getString("headimgurl");
            mm.put("nickname", nickname);
            mm.put("headimgurl", headimgurl);
        }
        catch (UnsupportedEncodingException e){
            logger.error("获取用户昵称异常");
            e.printStackTrace();
        }
        return mm;
    }
    */

    public String getSignature()
    {
        return signature;
    }
    public void setSignature(String signature)
    {
        this.signature = signature;
    }
    public String getTimestamp()
    {
        return timestamp;
    }
    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }
    public String getNonce()
    {
        return nonce;
    }
    public void setNonce(String nonce)
    {
        this.nonce = nonce;
    }
    public String getEchostr()
    {
        return echostr;
    }
    public void setEchostr(String echostr)
    {
        this.echostr = echostr;
    }
}
