package com.yishu.action;

import com.yishu.domain.User;
import com.yishu.service.IWXService;
import com.yishu.util.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.bson.BasicBSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class WechatAction extends BaseAction{

    private Logger logger = Logger.getLogger(WechatAction.class);
    private String signature;// 微信加密签名
    private String timestamp;// 时间戳
    private String nonce;// 随机数
    private String token = "aaren0125ITREN";// 自定义token
    private String echostr;// 随机字符串
    PrintWriter out = null;// response.getWriter();;
    @Autowired
    private IWXService wxservice;

    /**
     * 微信请求链接
     */
    public void authdev()
    {
        try
        {
            // 设置发送文件类型 及编码方式 charset 不能省略 防止乱码
            response.setContentType("text/html; charset=utf-8");
            out = response.getWriter();
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        logger.debug("******signature********" + signature);
        logger.debug("******timestamp********" + timestamp);
        logger.debug("******nonce********" + nonce);
        logger.debug("******echostr********" + echostr);
        if (signature == null || timestamp == null || nonce == null)
        {
            return;
        }
        // 开发者校验
        if (echostr != null && SHA1.authWXDEV(signature, timestamp, nonce, token))
        {
            out.print(echostr);
            return;
        }
        // 处理其余请求
        byte[] buff = null;
        int length = request.getContentLength();
        String encoding = request.getContentType();
        System.out.println(encoding + "encoding");
        if (length > 0)
        {
            buff = new byte[length];
        }
        else
        {
            buff = new byte[1024 * 1024 * 2];
        }
        int temp = 0, readedLen = 0;
        InputStream in = null;
        String reqStr; // xml数据
        try
        {
            in = request.getInputStream();
            while ((temp = in.read(buff, readedLen, buff.length - readedLen)) > 0)
            {
                readedLen += temp;
            }
            reqStr = new String(buff, 0, readedLen, "UTF-8");
            // 处理用户消息
            this.handleMassage(reqStr, out);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    // 解析 xml数据
    private void handleMassage(String reqStr, PrintWriter out)
    {
        BasicBSONObject reqBSON = DOM4JTool.decodeXML(reqStr);

        logger.debug("消息为" + reqBSON.toString());
        String openid = reqBSON.getString("FromUserName");// 消息来自哪里
        String MsgType = reqBSON.getString("MsgType");// 获取用户的信息类型
        String to = reqBSON.getString("ToUserName");// 获取公众号id
        PushMassage pu = new PushMassage();
        // 根据消息类型处理
        // 处理事件
        if (MsgType.equalsIgnoreCase("event"))
        {
            // 事件类型
            String Event = reqBSON.getString("Event");
            if (Event == null)
            {
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
        else if (MsgType.equalsIgnoreCase("text"))
        {
            out.print(pu.pushTextMess(openid, to, "亲！\n你说的话太高深，小漫一时无法理解！"));
        }
        // 用户普通图片消息
        else if (MsgType.equalsIgnoreCase("image"))
        {
            out.print(pu.pushTextMess(openid, to, "亲！\n你发的是图片奥！"));
        }
        // 用户普通语音消息
        else if (MsgType.equalsIgnoreCase("voice"))
        {
            out.print(pu.pushTextMess(openid, to, "亲！\n你发的是语音奥！"));
        }
        // 用户普通视频消息
        else if (MsgType.equalsIgnoreCase("video"))
        {
            out.print(pu.pushTextMess(openid, to, "亲！\n你发的是视频奥！"));
        }
        // 用户地理位置消息
        else if (MsgType.equalsIgnoreCase("location"))
        {
            out.print(pu.pushTextMess(openid, to, "亲！\n你发的是位置奥，一定要找到你！"));
        }
    }

    // 用户关注处理
    private void HandelSubscribe(String openid, PrintWriter out, String ouropenid)
    {
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
            if (val == 1)
            {
                logger.debug("保存用户信息成功" + openid);
            }
        }else{
            Map<String,String> mm = this.HandelNickName(openid);
            user.setNickname(mm.get("nickname"));
            user.setImgurl(mm.get("headimgurl"));
            int val = wxservice.UnSubscribe2(user);
            if (val == 1)
            {
                logger.debug("修改用户信息成功" + openid);
            }
        }
    }

    // 取消关注处理
    private void HandelUnSubscribe(String openid)
    {
        User user = new User();
        user.setOpenid(openid);
        user.setUntime(DataUtil.fromDate24H());
        wxservice.UnSubscribe(user);
    }

    // 获取用户昵称
    private Map<String,String> HandelNickName(String openid)
    {
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
        catch (UnsupportedEncodingException e)
        {
            logger.error("获取用户昵称异常");
            e.printStackTrace();
        }
        return mm;
    }

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