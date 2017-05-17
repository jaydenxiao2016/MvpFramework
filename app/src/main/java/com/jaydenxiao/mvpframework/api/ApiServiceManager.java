package com.jaydenxiao.mvpframework.api;

import com.jaydenxiao.common.https.MultipartBodyUtils;
import com.jaydenxiao.common.https.RetrofitManager;
import com.jaydenxiao.mvpframework.BuildConfig;
import com.jaydenxiao.mvpframework.bean.CommonResponse;

import java.util.HashMap;
import java.util.UUID;

import okhttp3.MediaType;
import rx.Observable;

/**
 * des:ApiService管理
 * Created by xsf
 * on 2016.09.11:00
 */

public class ApiServiceManager {
    /**
     * 登录模块
     */
    private static final String URL_LOGIN = "mobileAction!login.action";
    protected static final ApiService service = RetrofitManager.getRetrofit(BuildConfig.BASE_URL).create(ApiService.class);

    //静态内部类创建单例
    private static class SingletonHolder {
        private static final ApiServiceManager INSTANCE = new ApiServiceManager();
    }

    //获取ApiServiceManager
    public static ApiServiceManager getInstance() {
        return ApiServiceManager.SingletonHolder.INSTANCE;
    }

    /**
     * 登录
     *
     * @param userId
     * @param pwd
     * @return
     */
    public Observable<CommonResponse> login(String userId, String pwd) {
        HashMap<String, String> loginInfo = new HashMap<>();
        loginInfo.put("type", "mobile");
        loginInfo.put("userId", userId);
        loginInfo.put("pwd", pwd);
        return service.requestLogin(URL_LOGIN, loginInfo);
    }

    /**
     * 上传图片
     *
     * @return
     */
    public Observable<CommonResponse> requestUloadPictue(String type, String areaId, String boxId, String fileName) {
        HashMap<String, String> box = null;
        box.put("AREA_UID_", areaId);
        box.put("descriptImg", "图片描述");// 图片描述
        box.put("attachmentFileName", "img_" + UUID.randomUUID().toString()
                + ".JPEG");
        return service.requestUploadOneWork("", MultipartBodyUtils.RequestBody(box), MultipartBodyUtils.files2Part("attachment", fileName, MediaType.parse("image/*")));
    }


    /**
     * 获取封装基础参数的hashmap
     */
    private HashMap<String, String> getBasicMap(String type) {
        HashMap<String, String> hashMap = new HashMap<>();
        return hashMap;
    }
}
