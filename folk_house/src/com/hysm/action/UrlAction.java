package com.hysm.action;

import com.hysm.util.WxUrlVerifyUtil;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UrlAction extends BaseAction {

    /**
     * 微信公众号: 验证服务器地址的有效性<br>
     * 微信URL: http://IP:PORT/项目名称/wechatController/wechat.do<br>
     * 开发者通过检验signature对请求进行校验。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。<br>
     * 参考api: http://mp.weixin.qq.com/wiki/17/2d4265491f12608cd170a95559800f2d.html
     * @param request
     * @param response
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @date 2016年7月9日 上午11:10:30
     */

    public void verify(){
        HttpServletRequest request= ServletActionContext.getRequest();
        HttpServletResponse response=ServletActionContext.getResponse();
        String signature=request.getParameter("signature");
        String timestamp=request.getParameter("timestamp");
        String nonce=request.getParameter("nonce");
        String echostr=request.getParameter("echostr");
        String token = "aaren0125ITREN";//配置里面的token

        boolean flag = WxUrlVerifyUtil.checkSignature(token, signature, timestamp, nonce);
        if(flag){

                //验证成功,则原样返回echostr参数内容
//                response.getWriter().print(echostr);
            PrintWriter out = null;
            response.setContentType("text/html;charset=utf-8");
            try {
                out=response.getWriter();
                out.print(echostr);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (out != null)
                {
                    out.close();
                    out = null;
                }
            }
        }
    }

}