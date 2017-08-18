/**
 * 
 */
package com.hysm.util;

import com.hysm.domain.Article;
import com.hysm.domain.BasicMsg;
import com.hysm.domain.ImageMsg;
import com.hysm.domain.MusicMsg;
import com.hysm.domain.NewsMsg;
import com.hysm.domain.TextMsg;
import com.hysm.domain.VideoMsg;
import com.hysm.domain.VoiceMsg;

/**
 * @author john
 * 
 */
public class JsonMsgBuilder
{

    private final StringBuffer msgBuf = new StringBuffer("{");

    /**
     * 创建
     */
    public static JsonMsgBuilder create()
    {
        return new JsonMsgBuilder();
    }

    /**
     * 创建消息体前缀
     * 
     * @param msg
     *            客服消息
     */
    void msgPrefix(BasicMsg msg)
    {
        msgBuf.append("\"touser\":\"").append(msg.getToUserName())
                .append("\",");
        msgBuf.append("\"msgtype\":\"").append(msg.getMsgType()).append("\",");
    }

    /**
     * 文本客服消息
     * 
     * @param msg
     *            文消息
     */
    public JsonMsgBuilder text(TextMsg msg)
    {
        msgPrefix(msg);
        msgBuf.append("\"text\": {");
        msgBuf.append(" \"content\":\"").append(msg.getContent()).append("\"");
        msgBuf.append("}");
        return this;
    }

    /**
     * 图像客服消息
     * 
     * @param msg
     *            图像消息
     */
    public JsonMsgBuilder image(ImageMsg msg)
    {
        msgPrefix(msg);
        msgBuf.append("\"image\": {");
        msgBuf.append(" \"media_id\":\"").append(msg.getMediaId()).append("\"");
        msgBuf.append("}");
        return this;
    }

    /**
     * 语音客服消息
     * 
     * @param msg
     *            语音
     */
    public JsonMsgBuilder voice(VoiceMsg msg)
    {
        msgPrefix(msg);
        msgBuf.append("\"voice\": {");
        msgBuf.append(" \"media_id\":\"").append(msg.getMediaId()).append("\"");
        msgBuf.append("}");
        return this;
    }

    /**
     * 视频客服消息
     * 
     * @param msg
     *            视频消息
     */
    public JsonMsgBuilder video(VideoMsg msg)
    {
        msgPrefix(msg);
        msgBuf.append("\"video\": {");
        msgBuf.append(" \"media_id\":\"").append(msg.getMediaId())
                .append("\",");
        msgBuf.append(" \"thumb_media_id\":\"").append(msg.getThumbMediaId())
                .append("\",");
        msgBuf.append(" \"title\":\"").append(msg.getTitle()).append("\",");
        msgBuf.append(" \"description\":\"").append(msg.getDescription())
                .append("\"");
        msgBuf.append("}");
        return this;
    }

    /**
     * 音乐客服消息
     * 
     * @param msg
     *            音乐消息
     */
    public JsonMsgBuilder music(MusicMsg msg)
    {
        msgPrefix(msg);
        msgBuf.append("\"music\": {");
        msgBuf.append(" \"title\":\"").append(msg.getTitle()).append("\",");
        msgBuf.append(" \"description\":\"").append(msg.getDescription())
                .append("\",");
        msgBuf.append(" \"musicurl\":\"").append(msg.getMusicUrl())
                .append("\",");
        msgBuf.append(" \"hqmusicurl\":\"").append(msg.getHQMusicUrl())
                .append("\",");
        msgBuf.append(" \"thumb_media_id\":\"").append(msg.getThumbMediaId())
                .append("\"");
        msgBuf.append("}");
        return this;
    }

    /**
     * 多图文客服消息
     * 
     * @param msg
     *            图文消息
     */
    public JsonMsgBuilder news(NewsMsg msg)
    {
        msgPrefix(msg);
        StringBuffer arts_buf = new StringBuffer("\"articles\": [");
        StringBuffer art_buf = new StringBuffer();
        for (Article art : msg.getArticles())
        {
            art_buf.setLength(0);
            art_buf.append("{");
            art_buf.append(" \"title\":\"").append(art.getTitle())
                    .append("\",");
            art_buf.append(" \"description\":\"").append(art.getDescription())
                    .append("\",");
            art_buf.append(" \"picurl\":\"").append(art.getPicUrl())
                    .append("\",");
            art_buf.append(" \"url\":\"").append(art.getUrl());
            art_buf.append("\"},");
        }
        art_buf.deleteCharAt(art_buf.lastIndexOf(","));
        arts_buf.append(art_buf);
        arts_buf.append("]");
        msgBuf.append("\"news\": {");
        msgBuf.append(arts_buf);
        msgBuf.append("}");
        return this;
    }

    /**
     * 输出消息
     * 
     * @return json格式消息
     */
    public String build()
    {
        msgBuf.append("}");

        return new String(msgBuf);
    }

}
