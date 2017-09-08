package com.jaydenxiao.common.https;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.security.aes.AESCoder;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * 类名：JsonRequestBodyConverter.java
 * 描述：json请求参数统一加密处理
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 作者：xsf
 * 创建时间：2017/9/8
 * 最后修改时间：2017/9/8
 */

public class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    public static String KEY;

    /**
     * 构造器
     */

    public JsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }


    @Override
    public RequestBody convert(T value) throws IOException {
        //加密
        LogUtils.loge("请求接口加密前数据：" + value.toString());
        String data=AESCoder.encrypt(JSON.toJSON(value).toString(), KEY=AESCoder.getKey());
        LogUtils.loge("请求接口加密后数据：" + data);
        return RequestBody.create(MEDIA_TYPE, data);
    }

}
