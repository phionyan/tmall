package com.phion.tmall.util;
 /**
  * 让响应信息更规范，前后端交互时比较好用
  * @author 15037
  *
  */
public class Result {
    public static int SUCCESS_CODE = 200;
    public static int FAIL_CODE = 400;
     
    int code;//应答状态码
    String message;//应答信息
    Object data;//额外数据
     
    private Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    //成功状态
    public static Result success() {
        return new Result(SUCCESS_CODE,null,null);
    }
    //成功附加额外数据
    public static Result success(Object data) {
        return new Result(SUCCESS_CODE,"",data);
    }
    //失败加失败描述
    public static Result fail(String message) {
        return new Result(FAIL_CODE,message,null);
    }
 
    public int getCode() {
        return code;
    }
 
    public void setCode(int code) {
        this.code = code;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
    public Object getData() {
        return data;
    }
 
    public void setData(Object data) {
        this.data = data;
    }
     
}