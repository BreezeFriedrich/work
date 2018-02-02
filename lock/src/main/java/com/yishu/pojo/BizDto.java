package com.yishu.pojo;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-02-01 11:07 admin
 * @since JDK1.7
 */
public class BizDto<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /*
        public BizDto(int code, String msg, T data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }
        public BizDto(int code, T data) {
            this.code = code;
            this.data = data;
        }
    */

    public BizDto() {
    }
    public BizDto(T data) {
        this.code = 0;
        this.data = data;
    }
    public BizDto(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public static final BizDto EMPTY_RESULT = new BizDto(1,"没有符合当前检索条件的结果");
}