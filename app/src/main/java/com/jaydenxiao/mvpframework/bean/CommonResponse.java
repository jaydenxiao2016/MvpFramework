package com.jaydenxiao.mvpframework.bean;

/**
 * 类名：CommonResponse.java
 * 描述：
 * 作者：xsf
 * 创建时间：2017/1/16
 * 最后修改时间：2017/1/16
 */

public class CommonResponse {
    private String msg;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "msg='" + msg + '\'' +
                ", success=" + success +
                '}';
    }
}
