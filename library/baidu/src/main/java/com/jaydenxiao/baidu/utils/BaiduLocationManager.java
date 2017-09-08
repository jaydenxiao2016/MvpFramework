package com.jaydenxiao.baidu.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.jaydenxiao.common.commonutils.NetWorkUtils;

/**
 * 百度地图定位服务
 *
 * @author xsf
 */
public class BaiduLocationManager {
    private LocationClient client = null;
    private static BaiduLocationManager baiduLocationManager;
    private LocationClientOption mOption;
    private Object objLock = new Object();
    private BDLocationListener locationListener;
    private LocationListener localGPSListener;
    private LocationManager locationManager;
    private OnLocationListener onLocationListener;
    public static final String BAIDU_TYPE = "1";
    public static final String GPS_TYPE = "2";
    private Context mContext;

    private BaiduLocationManager(Context locationContext) {
        mContext = locationContext;
        synchronized (objLock) {
            if (client == null) {
                client = new LocationClient(locationContext);
                client.setLocOption(getDefaultLocationClientOption());
            }
        }
    }

    /**
     * 获取单例
     *
     * @param context
     * @return
     */
    public static BaiduLocationManager getInstance(Context context) {
        if (null == baiduLocationManager) {
            synchronized (BaiduLocationManager.class) {
                if (baiduLocationManager == null) {
                    baiduLocationManager = new BaiduLocationManager(context);
                }
            }
        }
        return baiduLocationManager;
    }

    /**
     * 百度和本地监听
     *
     * @param onLocationListener
     */
    public void registerAllLocationListenter(OnLocationListener onLocationListener) {
        this.onLocationListener = onLocationListener;
        startBaiOrGpsLoation();
    }

    /***
     * 单独百度定位监听
     *
     * @param listener
     * @return
     */

    public BaiduLocationManager registerBaiduListener(BDLocationListener listener) {
        if (listener != null) {
            locationListener = listener;
            client.registerLocationListener(listener);
            client.start();
        }
        return this;
    }

    /***
     * 单独本地gps定位监听
     *
     * @param listener
     * @return
     */

    public BaiduLocationManager registerLoacalGPSListener(LocationListener listener) {
        if (listener != null) {
            localGPSListener = listener;
            initLocalGPS();
        }
        return this;
    }

    /***
     * 设置定位参数
     *
     * @return DefaultLocationClientOption
     */
    public LocationClientOption getDefaultLocationClientOption() {
        if (mOption == null) {
            mOption = new LocationClientOption();
            mOption.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            mOption.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
            mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
            mOption.setOpenGps(true);
            mOption.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        }
        return mOption;
    }

    /**
     * 初始化
     */
    private void initLocalGPS() {
        // 获取系统LocationManager服务
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
                1, localGPSListener);
    }

    /**
     * 开始定位，有网百度，无网gps
     */
    private void startBaiOrGpsLoation() {
        synchronized (objLock) {
            if (NetWorkUtils.isNetConnected(mContext)) {
                //百度定位
                if (client != null && !client.isStarted()) {
                    if (locationListener == null) {
                        locationListener = new BDLocationListener() {
                            @Override
                            public void onReceiveLocation(BDLocation bdLocation) {
                                if (bdLocation != null) {
                                    if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                                        if (NetWorkUtils.isNetConnected(mContext)) {
                                            Toast.makeText(mContext, "定位权限已被禁止", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    } else if (onLocationListener != null) {
                                        onLocationListener.onLocationChanged(null, bdLocation, BAIDU_TYPE);
                                    }

                                }
                            }

                            @Override
                            public void onConnectHotSpotMessage(String s, int i) {

                            }
                        };
                    }
                    registerBaiduListener(locationListener);
                    client.start();
                }
            } else {
                //本地gps定位
                if (localGPSListener == null) {
                    localGPSListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            if (onLocationListener != null && location != null) {
                                onLocationListener.onLocationChanged(location, null, GPS_TYPE);
                            }
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    };
                }
                // 获取系统LocationManager服务
                locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
                        1, localGPSListener);
                // 查询最后一次gps定位信息
                String bestProvider = locationManager.getBestProvider(getCriteria(), true);
                Location location = locationManager.getLastKnownLocation(bestProvider);
                if (onLocationListener != null&&location!=null) {
                    onLocationListener.onLocationChanged(location, null, GPS_TYPE);
                }
            }
        }
    }

    /**
     * 结束定位
     */
    public void unRegisterBaiduOrGPSLocation() {
        synchronized (objLock) {
            if (client != null && client.isStarted()) {
                client.unRegisterLocationListener(locationListener);
                client.stop();
            }
            if (locationManager != null) {
                locationManager.removeUpdates(localGPSListener);
                localGPSListener = null;
                locationManager = null;
            }
        }
    }

    /**
     * 返回查询条件
     *
     * @return
     */
    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(false);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }


    public interface OnLocationListener {
        void onLocationChanged(Location location, BDLocation bdLocation, String type);
    }


}
