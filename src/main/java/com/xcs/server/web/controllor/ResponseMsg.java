package com.xcs.server.web.controllor;

public class ResponseMsg {
    public static final String SUCCESS="成功";
    public static final String FAILED="失败";
    private String result;
    private Object msg;

    public ResponseMsg(String result, Object msg) {
        this.result = result;
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public static ResponseMsg getSuccess(Object msg){
        return  new ResponseMsg(ResponseMsg.SUCCESS,msg);
    }

    public static ResponseMsg getFailed(Object msg){
        return  new ResponseMsg(ResponseMsg.FAILED,msg);
    }
}
