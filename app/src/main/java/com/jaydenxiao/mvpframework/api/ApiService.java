package com.jaydenxiao.mvpframework.api;

import com.jaydenxiao.mvpframework.bean.CommonResponse;
import com.jaydenxiao.mvpframework.bean.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * des:apiService
 * Created by xsf
 * on 2016.06.15:47
 */
public interface ApiService {


    @POST("{url}")
    Observable<User> requestLogin(
            @Path("url") String url,
            @Body String string);

    /**
     * 上传多文件
     */
    @Multipart
    @POST("{url}")
    Observable<CommonResponse> requestUploadMoreWork(
            @Path("url") String url,
            @FieldMap Map<String, String> maps,
            @Part List<MultipartBody.Part> parts);

    /**
     * 上传单文件
     * @param url
     * @param params
     * @param part
     * @return
     */
    @Multipart
    @POST
    Observable<CommonResponse> requestUploadOneWork(
            @Url() String url,
            @PartMap Map<String, RequestBody> params,
            @Part MultipartBody.Part part);


    /**
     * 下载apk文件
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadApk(@Url String url);


}
