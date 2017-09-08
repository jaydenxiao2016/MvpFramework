package com.jaydenxiao.common.https;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.security.aes.AESCoder;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 类名：JsonResponseBodyConverter.java
 * 描述：json返回统一解密处理
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 作者：xsf
 * 创建时间：2017/9/8
 * 最后修改时间：2017/9/8
 */

public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson mGson;//gson对象
    private final TypeAdapter<T> adapter;

    /**
     * 构造器
     */
    public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.mGson = gson;
        this.adapter = adapter;
    }

    /**
     * 转换
     *
     * @param value
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        //统一解密
        String data = AESCoder.decrypt(value.string(), JsonRequestBodyConverter.KEY);
        LogUtils.loge("接口返回数据：" + data);
        try {
            return adapter.fromJson(data);
        } finally {
            value.close();
        }
    }
}