package com.hysm.util;

import com.hysm.domain.Article;
import com.hysm.domain.BasicMsg;
import com.hysm.domain.ImageMsg;
import com.hysm.domain.MusicMsg;
import com.hysm.domain.NewsMsg;
import com.hysm.domain.TextMsg;
import com.hysm.domain.VideoMsg;
import com.hysm.domain.VoiceMsg;

//被动发送信息
public class PushMassage
{

    private final StringBuffer msgBuf = new StringBuffer("<xml>\n");;

    // 发送文本消息
    public static String pushTextMess(String to, String from, String content)
    {
        StringBuffer sb = new StringBuffer();

        sb.append("<xml><ToUserName><![CDATA[");
        sb.append(to);
        sb.append("]]></ToUserName><FromUserName><![CDATA[");
        sb.append(from);
        sb.append("]]></FromUserName><CreateTime>");
        sb.append(System.currentTimeMillis() / 1000);
        sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
        sb.append(content);
        sb.append("]]></Content></xml>");
        return sb.toString();

    }

    /**
     * 创建
     */
    public static PushMassage create()
    {
        return new PushMassage();
    }

    /**
     * 创建消息体前缀
     * 
     * @param msg
     *            输出消息实体
     */
    void msgPrefix(BasicMsg msg)
    {
        msgBuf.append("<ToUserName><![CDATA[").append(msg.getToUserName())
                .append("]]></ToUserName>\n");
        msgBuf.append("<FromUserName><![CDATA[").append(msg.getFromUserName())
                .append("]]></FromUserName>\n");
        msgBuf.append("<CreateTime>").append(msg.getCreateTime())
                .append("</CreateTime>\n");
        msgBuf.append("<MsgType><![CDATA[").append(msg.getMsgType())
                .append("]]></MsgType>\n");
    }

    /**
     * 被动文本消息
     * 
     * @param msg
     *            文本消息
     */
    public PushMassage text(TextMsg msg)
    {
        msgPrefix(msg);
        msgBuf.append("<Content><![CDATA[").append(msg.getContent())
                .append("]]></Content>\n");
        return this;
    }

    /**
     * 被动图像消息
     * 
     * @param msg
     *            图像消息
     */
    public PushMassage image(ImageMsg msg)
    {
        msgPrefix(msg);
        msgBuf.append("<Image>");
        msgBuf.append("<MediaId><![CDATA[").append(msg.getMediaId())
                .append("]]></MediaId>\n");
        msgBuf.append("</Image>");
        return this;
    }

    /**
     * 被动语音消息
     * 
     * @param msg
     *            音频消息
     */
    public PushMassage voice(VoiceMsg msg)
    {
        msgPrefix(msg);
        msgBuf.append("<Voice>");
        msgBuf.append("<MediaId><![CDATA[").append(msg.getMediaId())
                .append("]]></MediaId>\n");
        msgBuf.append("</Voice>\n");
        return this;
    }

    /**
     * 被动视频消息
     * 
     * @param msg
     *            视频消息
     */
    public PushMassage video(VideoMsg msg)
    {
        msgPrefix(msg);
        msgBuf.append("<Video>");
        msgBuf.append("<MediaId><![CDATA[").append(msg.getMediaId())
                .append("]]></MediaId>\n");
        msgBuf.append("<Title><![CDATA[").append(msg.getTitle())
                .append("]]></Title>\n");
        msgBuf.append("<Description><![CDATA[").append(msg.getDescription())
                .append("]]></Description>\n");
        msgBuf.append("</Video>\n");
        return this;
    }

    /**
     * 被动音乐消息
     * 
     * @param msg
     *            音乐消息
     */

    public PushMassage music(MusicMsg msg)
    {
        msgPrefix(msg);
        msgBuf.append("<Music>");
        msgBuf.append("<Title><![CDATA[").append(msg.getTitle())
                .append("]]></Title>\n");
        msgBuf.append("<Description><![CDATA[").append(msg.getDescription())
                .append("]]></Description>\n");
        msgBuf.append("<MusicUrl><![CDATA[").append(msg.getMusicUrl())
                .append("]]></MusicUrl>\n");
        msgBuf.append("<HQMusicUrl><![CDATA[").append(msg.getHQMusicUrl())
                .append("]]></HQMusicUrl>\n");
        msgBuf.append("<ThumbMediaId><![CDATA[").append(msg.getThumbMediaId())
                .append("]]></ThumbMediaId>\n");
        msgBuf.append("</Music>\n");
        return this;
    }

    /**
     * 被动多图文消息
     * 
     * @param msg
     *            图文消息
     */
    public PushMassage news(NewsMsg msg)
    {
        msgPrefix(msg);
        StringBuffer arts_buf = new StringBuffer("<Articles>\n");
        StringBuffer item_buf = new StringBuffer();
        for (Article art : msg.getArticles())
        {
            item_buf.setLength(0);
            item_buf.append("<item>\n");
            item_buf.append("<Title><![CDATA[").append(art.getTitle())
                    .append("]]></Title>\n");
            item_buf.append("<Description><![CDATA[")
                    .append(art.getDescription()).append("]]></Description>\n");
            item_buf.append("<PicUrl><![CDATA[").append(art.getPicUrl())
                    .append("]]></PicUrl>\n");
            item_buf.append("<Url><![CDATA[").append(art.getUrl())
                    .append("]]></Url>\n");
            item_buf.append("</item>\n");
            arts_buf.append(item_buf);
        }
        arts_buf.append("</Articles>\n");
        msgBuf.append("<ArticleCount>").append(msg.getCount())
                .append("</ArticleCount>\n");
        msgBuf.append(arts_buf);
        return this;
    }

    /**
     * AES加密信息
     * 
     * @param xml
     *            密文消息
     * @param msgSignature
     *            消息签名
     * @param timeStamp
     *            时间戳
     * @param nonce
     *            随机字符
     */
    public String encrypt(String xml, String msgSignature, String timeStamp,
            String nonce)
    {
        msgBuf.setLength(0);
        msgBuf.append("<xml>\n");
        msgBuf.append("<Encrypt><![CDATA[").append(xml)
                .append("]]></Encrypt>\n");
        msgBuf.append("<MsgSignature><![CDATA[").append(msgSignature)
                .append("]]></MsgSignature>\n");
        msgBuf.append("<TimeStamp>").append(timeStamp).append("</TimeStamp>\n");
        msgBuf.append("<Nonce><![CDATA[").append(nonce).append("]]></Nonce>\n");
        msgBuf.append("</xml>");
        return msgBuf.toString();
    }

    /**
     * 输出回复消息
     * 
     * @return 回复消息
     */
    public String build()
    {
        msgBuf.append("</xml>");

        return new String(msgBuf);
    }

}
