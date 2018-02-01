package com.yishu.pojo;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-02-01 10:17 admin
 * @since JDK1.7
 */
public class JsonDto {
    private boolean success;
    private String errmsg;
    private BizDto biz;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public BizDto getBiz() {
        return biz;
    }

    public void setBiz(BizDto biz) {
        this.biz = biz;
    }

    public JsonDto() {
    }
    //流程正常success=true,errmsg=null;返回success&biz
    public JsonDto(BizDto biz) {
        this.success = true;
        this.biz = biz;
    }
    //流程异常success=false,errmsg=null;返回success&errmsg
    public JsonDto(String errmsg) {
        this.success = false;
        this.errmsg = errmsg;
    }
    public static final JsonDto EXCEPTION= new JsonDto("获取并处理数据的过程发生异常");
    public static final JsonDto NULL= new JsonDto("未获取到任何数据");
}

/*
public class JsonDto {
    private boolean success;
    private String errmsg;
    private BizDto biz;

    //流程正常success=true,errmsg=null;返回success&biz
    public JsonDto(BizDto biz) {
        this.success = true;
        this.biz = biz;
    }
    //流程异常success=false,errmsg=null;返回success&errmsg
    public JsonDto(String errmsg) {
        this.success = false;
        this.errmsg = errmsg;
    }
    public static final JsonDto EXCEPTION= new JsonDto("获取并处理数据的过程发生异常");
    public static final JsonDto NULL= new JsonDto("未获取到任何数据");
}
class BizDto{
    private int code;
    private String msg;
    private Object data;

    public BizDto(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BizDto(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public BizDto(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BizDto(Object data) {
        this.code = 0;
        this.data = data;
    }

    public static final BizDto NO_RESULT= new BizDto(1,"没有符合当前检索条件的结果");
}
 */