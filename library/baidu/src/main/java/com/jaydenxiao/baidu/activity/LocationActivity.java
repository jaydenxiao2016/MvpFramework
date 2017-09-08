package com.jaydenxiao.baidu.activity;

import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.jaydenxiao.baidu.R;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.basemvp.BaseView;
import com.jaydenxiao.common.commonutils.DisplayUtil;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.NetWorkUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;


/**
 * 此demo用来展示如何结合定位SDK实现定位，并使用MyLocationOverlay绘制定位位置 同时展示如何使用自定义图标绘制并点击时弹出泡泡
 */
public class LocationActivity extends BaseActivity implements SensorEventListener, OnGetGeoCoderResultListener {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private GeoCoder mGeoCoder = null;
    private RadioGroup rgMapType, rgMapMode;
    private NormalTitleBar mNormalTitleBar;
    private TextClock mTextClock;
    private LinearLayout llOk;
    private TextView tvNowLocation, tvLot, tvLat, tvAddress;
    private RelativeLayout rlBottomRoot;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private String mAddress = "";
    private float mCurrentAccracy;
    private MyLocationData locData;
    private boolean isLocation = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_location;
    }

    @Override
    public BaseView attachPresenterView() {
        return null;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mNormalTitleBar = (NormalTitleBar) findViewById(R.id.normal_bar);
        rgMapType = (RadioGroup) findViewById(R.id.rg_map_type);
        rgMapMode = (RadioGroup) findViewById(R.id.rg_map_mode);
        mTextClock = (TextClock) findViewById(R.id.text_clock);
        llOk = (LinearLayout) findViewById(R.id.ll_ok);
        tvNowLocation = (TextView) findViewById(R.id.tv_now_location);
        rlBottomRoot = (RelativeLayout) findViewById(R.id.rl_location_info);
        tvLot = (TextView) findViewById(R.id.tv_baidu_lot);
        tvLat = (TextView) findViewById(R.id.tv_baidu_lat);
        tvAddress = (TextView) findViewById(R.id.tv_baidu_address);
        // 设置24时制显示格式
        mTextClock.setFormat24Hour("hh:mm:ss, EEEE");

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mCurrentMode = LocationMode.NORMAL;
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mLocClient = new LocationClient(this);
        // 定位初始化
        mLocClient.registerLocationListener(myListener);
        //标题
        mNormalTitleBar.setTitleText(getString(R.string.str_baidu_location_title));
        mNormalTitleBar.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //当前位置
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(0);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
        //每移动地图，搜索屏幕中点位置
        setMapStatusChangeListener();
        // 初始化搜索模块，注册事件监听
        mGeoCoder = GeoCoder.newInstance();
        mGeoCoder.setOnGetGeoCodeResultListener(this);


        //地图类型
        rgMapType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //普通地图
                if (R.id.rb_normal_type == checkedId) {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                }
                //卫星地图
                else if (R.id.rb_statellite_type == checkedId) {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                }
            }
        });
        //地图图层类型
        rgMapMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //普通图层
                if (R.id.rb_normal == checkedId) {
                    mCurrentMode = LocationMode.NORMAL;
                    mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                            mCurrentMode, true, mCurrentMarker));
                    MapStatus.Builder builder1 = new MapStatus.Builder();
                    builder1.overlook(0);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
                }
                //跟随图层
                else if (R.id.rb_following == checkedId) {
                    mCurrentMode = LocationMode.FOLLOWING;
                    mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                            mCurrentMode, true, mCurrentMarker));
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.overlook(0);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }
                //罗盘图层
                else if (R.id.rb_compass == checkedId) {
                    mCurrentMode = LocationMode.COMPASS;
                    mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                            mCurrentMode, true, mCurrentMarker));
                }
            }
        });
    }

    /**
     * 显示定位结果
     *
     * @param lot
     * @param lat
     * @param address
     */
    private void setLocationDataToView(double lot, double lat, String address) {
        mCurrentLat = lat;
        mCurrentLon = lot;
        mAddress = address;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mCurrentLat == 0 || mCurrentLon == 0) {
                    rlBottomRoot.setVisibility(View.GONE);
                    tvNowLocation.setVisibility(View.VISIBLE);
                    tvNowLocation.setText("定位失败");
                } else {
                    rlBottomRoot.setVisibility(View.VISIBLE);
                    tvNowLocation.setVisibility(View.GONE);
                    tvLot.setText(String.format(getString(R.string.str_lot), String.format("%.6f", mCurrentLon)));
                    tvLat.setText(String.format(getString(R.string.str_lat), String.format("%.6f", mCurrentLat)));
                    tvAddress.setText(TextUtils.isEmpty(mAddress) ? String.format(getString(R.string.str_address), String.valueOf("无网，未能查询到地址")) : String.format(getString(R.string.str_address, String.valueOf(mAddress)), String.valueOf(mAddress)));
                }
            }
        });

    }


    /*
    * 每移动地图，搜索屏幕中点位置
    */
    private void setMapStatusChangeListener() {
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChange(MapStatus arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
                if (NetWorkUtils.isNetConnected(mContext)) {
                    LogUtils.loge("Map:" + "onMapStatusChangeFinish");
                    LatLng ll = mBaiduMap.getProjection()
                            .fromScreenLocation(new Point(DisplayUtil.getViewWidth(mMapView) / 2, DisplayUtil.getViewHeight(mMapView) / 2));
                    LogUtils.loge("Map:" + "LatLng=" + ll);
                    Point p = mBaiduMap.getProjection().toScreenLocation(ll);
                    LogUtils.loge("Map:" + "p.x=" + p.x + "  p.y=" + p.y);
                    mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
                }else{
                    ToastUitl.showShort("无网络，无法进行地址调整");
                }
            }

            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    /**
     * 设置是否显示交通图
     *
     * @param view
     */
    public void setTraffic(View view) {
        mBaiduMap.setTrafficEnabled(((CheckBox) view).isChecked());
    }

    /**
     * 设置是否显示百度热力图
     *
     * @param view
     */
    public void setBaiduHeatMap(View view) {
        mBaiduMap.setBaiduHeatMapEnabled(((CheckBox) view).isChecked());
    }

    /**
     * 显示当前位置
     */
    public void onclickCurrentLocation(View view) {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result != null && result.getLocation() != null && !isLocation) {
            setLocationDataToView(result.getLocation().longitude, result.getLocation().latitude, result.getAddress());
        }
    }

    /**
     * 定位SDK监听函数, 需实现BDLocationListener里的方法
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mLocClient.getLocOption().setScanSpan(0);
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            setLocationDataToView(location.getLongitude(), location.getLatitude(), location.getAddrStr());
            isLocation = false;
        }

        @Override
        public void onConnectHotSpotMessage(String var1, int var2) {
        }
    }

    @Override
    protected void onPause() {
        //mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        //取消注册传感器监听
        //mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }


}
