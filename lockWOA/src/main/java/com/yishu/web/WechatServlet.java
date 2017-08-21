package com.yishu.web;

import com.yishu.util.CheckUtil;
import com.yishu.util.MessageUtil;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class WechatServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String signature=req.getParameter("signature");
        String timestamp=req.getParameter("timestamp");
        String nonce=req.getParameter("nonce");
        String echostr=req.getParameter("echostr");
        PrintWriter out=resp.getWriter();
        if(CheckUtil.checkSignature(signature,timestamp,nonce)){
            out.print(echostr);
        }
    }

    /**
     * 接收并处理用户消息(由用户发送给公众号的消息,经微信服务器post转发的).
     * 当普通微信用户向公众账号发消息时，微信服务器将POST消息的XML数据包到开发者填写的URL上。
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out=resp.getWriter();
        try {
            Map<String,String> map= MessageUtil.xmlToMap(req);
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
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }

    }
}
/*接收到粉丝的消息(由微信服务器转发给开发者后台),内容如下:
    ToUserName	开发者微信号
    FromUserName	发送方帐号（一个OpenID）
    CreateTime	消息创建时间 （整型）
    MsgId	消息id，64位整型

    MsgType	text
    Content	文本消息内容

    MsgType	image
    PicUrl	图片链接（由系统生成）
    MediaId	图片消息媒体id，可以调用多媒体文件下载接口拉取数据。

    MsgType	voice语音
    MediaId	语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
    Format	语音格式，如amr，speex等
    Recognition	语音识别结果，UTF8编码

    MsgType	video视频
    MediaId	视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
    ThumbMediaId	视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。

    MsgType	location
    Location_X	地理位置维度
    Location_Y	地理位置经度
    Scale	地图缩放大小
    Label	地理位置信息

    MsgType	link链接
    Title	消息标题
    Description	消息描述
    Url	消息链接
*/
/*自动回复给粉丝的消息(由微信服务器转发给粉丝),内容如下:
    ToUserName	接收方帐号（收到的OpenID）
    FromUserName	开发者微信号
    CreateTime	消息创建时间 （整型）

    MsgType	text
    Content	回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）

    MsgType	image
    MediaId	通过素材管理中的接口上传多媒体文件，得到的id。

    MsgType	语音，voice
    MediaId	通过素材管理中的接口上传多媒体文件，得到的id

    MsgType	video
    MediaId	通过素材管理中的接口上传多媒体文件，得到的id
    Title	非必须	视频消息的标题
    Description	非必须	视频消息的描述

    MsgType	music
    ThumbMediaId	缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
    Title	非必须	音乐标题
    Description	非必须	音乐描述
    MusicURL	非必须	音乐链接
    HQMusicUrl	非必须	高质量音乐链接，WIFI环境优先使用该链接播放音乐

    MsgType	news
    ArticleCount	图文消息个数，限制为8条以内
    Articles	多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应
    Title	图文消息标题
    Description	图文消息描述
    PicUrl	图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
    Url	点击图文消息跳转链接
 */