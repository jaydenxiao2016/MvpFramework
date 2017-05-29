package com.jaydenxiao.mvpframework.app;

import android.text.TextUtils;

import com.jaydenxiao.common.commonutils.SPUtils;
import com.jaydenxiao.mvpframework.bean.User;

/**
 * App内存缓存
 */
public class AppCache {
    private volatile static AppCache instance;
    private User user;
    //当前选择城市
    private String city;
    //当前选择城市id
    private String substationId;
    //当前定位的城市
    private String cityLocation;
    private AppCache() {
    }

    public static AppCache getInstance() {
        if (null == instance) {
            synchronized (AppCache.class) {
                if (instance == null) {
                    instance = new AppCache();
                }
            }
        }
        return instance;
    }


    /**
     * 获取当前城市
     *
     * @return
     */
    public String getCity() {
        if (TextUtils.isEmpty(city)) {
            //获取本地
            String city = SPUtils.getSharedStringData(AppConstant.CITY);
            if (TextUtils.isEmpty(city)) {
                city = "云浮";
            }
            this.city = city;
        }
        return city;
    }

    /**
     * 设置当前城市
     *
     * @return
     */
    public void setCity(String city) {
        if (!TextUtils.isEmpty(city))
            //保存到本地
            SPUtils.setSharedStringData(AppConstant.CITY, city);
        this.city = city;
    }

    /**
     * 获取城市id
     *
     * @return
     */
    public String getSubstationId() {
        if (TextUtils.isEmpty(substationId)) {
            //获取本地
            String cityId = SPUtils.getSharedStringData(AppConstant.CITY_ID);
            if (TextUtils.isEmpty(cityId)) {
                cityId = "1";
            }
            this.substationId = cityId;
        }
        return substationId;
    }

    /**
     * 设置当前城市Id
     *
     * @return
     */
    public void setSubstationId(String cityId) {
        if (TextUtils.isEmpty(cityId))
            //保存到本地
            SPUtils.setSharedStringData(AppConstant.CITY_ID, cityId);
        this.substationId = cityId;
    }

    /**
     * 设置当前定位到的城市
     *
     * @return
     */
    public void setCityLocation(String cityLocation) {
        if (TextUtils.isEmpty(cityLocation))
            //保存到本地
            SPUtils.setSharedStringData(AppConstant.CITY_LOCATION, cityLocation);
        this.cityLocation = cityLocation;
    }

    /**
     * 设置当前定位到的城市
     *
     * @return
     */
    public String getCityLocation() {
        if (TextUtils.isEmpty(substationId)) {
            //获取本地定位到的城市
            this.cityLocation = SPUtils.getSharedStringData(AppConstant.CITY_LOCATION);
            if (TextUtils.isEmpty(cityLocation)) {
                cityLocation = "云浮";
            }
        }
        return cityLocation;
    }

}
