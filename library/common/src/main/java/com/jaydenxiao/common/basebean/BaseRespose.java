package com.jaydenxiao.common.basebean;

import java.io.Serializable;

/**
 * des:封装服务器返回数据
 * Created by xsf
 * on 2016.09.9:47
 */
public class BaseRespose <T> implements Serializable {
    public boolean done;
    public String msg;

    public T retdata;

    public boolean success() {
        return done;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getRetdata() {
        return retdata;
    }

    public void setRetdata(T retdata) {
        this.retdata = retdata;
    }

    @Override
    public String toString() {
        return "BaseRespose{" +
                "done=" + done +
                ", message='" + msg + '\'' +
                ", retval=" + retdata +
                '}';
    }
}
