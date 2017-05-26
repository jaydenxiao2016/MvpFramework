package com.yuyh.library.imgsel.customcamera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.jaydenxiao.common.commonutils.PermissionUtil;
import com.jaydenxiao.common.compressorutils.FileUtil;
import com.jaydenxiao.common.imagePager.BigImagePagerActivity;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yuyh.library.imgsel.R;
import com.yuyh.library.imgsel.utils.FileUtils;
import com.yuyh.library.imgsel.utils.PressImageUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yuyh.library.imgsel.ImgSelActivity.INTENT_ORIGINAL_RESULT;
import static com.yuyh.library.imgsel.ImgSelActivity.INTENT_PRESS_RESULT;

/**
 * 自定义拍照
 */
public class CustomCameraActivity extends BaseActivity implements SurfaceHolder.Callback, View.OnClickListener {
    private Camera mCamera;
    private int cameraPosition = 0;//0代表前置摄像头,1代表后置摄像头,默认打开前置摄像头
    SurfaceHolder holder;
    SurfaceView mSurfaceView;
    ImageButton openLight;
    private ImageView lookPictureIv;
    View focusIndex;
    View bootomRly;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    private float pointX, pointY;
    static final int FOCUS = 1;            // 聚焦
    static final int ZOOM = 2;            // 缩放
    private int mode;                      //0是聚焦 1是放大
    //放大缩小
    int curZoomValue = 0;
    private float dist;
    Camera.Parameters parameters;
    private Handler handler = new Handler();
    boolean safeToTakePicture = true;
    RxPermissions rxPermissions;
    /**
     * 当前压缩图片文件
     */
    private File currentPressFile;
    /**
     * 拍照原图存放位置
     */
    private String filePathOriginal = "";
    private int type;
    private static final String FILE_PATH_ORIGINAL_KEY = "FILE_PATH_ORIGINAL_KEY";

    private static final String TYPE = "TYPE";//表箱和用户

    /**
     * 拍照压缩存放位置
     */
    private String filePathPress = "";
    private static final String FILE_PATH_PRESS_KEY = "FILE_PATH_PRESS_KEY";

    /**
     * 用户id
     */
    private String loginUserId = "";
    private static final String LOGIN_USER_ID_KEY = "LOGIN_USER_ID_KEY";
    /**
     * 终端用户编号或表箱编号
     */
    private String userIdOrBoxId = "";
    private static final String USER_ID_OR_BOX_ID_KEY = "USER_ID_OR_BOX_ID_KEY";

    /**
     * 照片流水编号
     */
    private int photoSerialNo = 0;
    private static final String PHOTO_SERIAL_NO_KEY = "PHOTO_SERIAL_NO_KEY";
    /**
     * 本次进来所拍的所有图片原图本地链接
     */
    private ArrayList<String> originalPhotoList;
    /**
     * 本次进来所拍的所有图片压缩图本地链接
     */
    private ArrayList<String> pressPhotoList;

    /* 图像数据处理完成后的回调函数 */
    private Camera.PictureCallback mJpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            getRxManager().add(Observable.create(new Observable.OnSubscribe<String>() {

                @Override
                public void call(Subscriber<? super String> subscriber) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), CameraUtil.getInstance().getMatrix(), true);
                        saveImageToGallery(getBaseContext(), bitmap);
                        mCamera.stopPreview();
                        mCamera.startPreview();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                    }
                    safeToTakePicture = true;
                    subscriber.onNext("");
                    subscriber.onCompleted();
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                @Override
                public void onStart() {
                    super.onStart();
                    showProgressDialog("处理中...");
                }

                @Override
                public void onCompleted() {
                    cancelLoadingDialog();

                }

                @Override
                public void onError(Throwable e) {
                    cancelLoadingDialog();
                    showCustomToast("保存照片出错了", R.drawable.ic_toast_warm);
                }

                @Override
                public void onNext(String s) {
                    cancelLoadingDialog();
                    if (currentPressFile != null) {
                        //预览
                        ImageLoaderUtils.display(mContext, lookPictureIv, currentPressFile.getAbsolutePath());
                    }
                }
            }));
        }
    };

    /**
     * 入口
     *
     * @param activity
     * @param RequestCode
     */
    public static void startActivity(Activity activity, String savePathOriginal, String savePathPress, String loginUserId, String userIdOrBoxId, int photoSerialNo, int RequestCode) {
        Intent intent = new Intent(activity, CustomCameraActivity.class);
        intent.putExtra(FILE_PATH_ORIGINAL_KEY, savePathOriginal);
        intent.putExtra(FILE_PATH_PRESS_KEY, savePathPress);
        intent.putExtra(LOGIN_USER_ID_KEY, loginUserId);
        intent.putExtra(USER_ID_OR_BOX_ID_KEY, userIdOrBoxId);
        intent.putExtra(PHOTO_SERIAL_NO_KEY, photoSerialNo);
        activity.startActivityForResult(intent, RequestCode);
    }

    /**
     * 入口
     *
     * @param fragment
     * @param RequestCode
     */
    public static void startActivity(Fragment fragment, String savePathOriginal, String savePathPress, String loginUserId, String userIdOrBoxId, int photoSerialNo, int RequestCode) {
        Intent intent = new Intent(fragment.getActivity(), CustomCameraActivity.class);
        intent.putExtra(FILE_PATH_ORIGINAL_KEY, savePathOriginal);
        intent.putExtra(FILE_PATH_PRESS_KEY, savePathPress);
        intent.putExtra(LOGIN_USER_ID_KEY, loginUserId);
        intent.putExtra(USER_ID_OR_BOX_ID_KEY, userIdOrBoxId);
        intent.putExtra(PHOTO_SERIAL_NO_KEY, photoSerialNo);
        fragment.startActivityForResult(intent, RequestCode);
    }

    /**
     * 入口
     *
     * @param Type        1.表箱；2.用户
     * @param fragment
     * @param RequestCode
     */
    public static void startActivity(Fragment fragment, int Type, String savePathOriginal, String savePathPress, String loginUserId, String userIdOrBoxId, int photoSerialNo, int RequestCode) {
        Intent intent = new Intent(fragment.getActivity(), CustomCameraActivity.class);
        intent.putExtra(TYPE, Type);
        intent.putExtra(FILE_PATH_ORIGINAL_KEY, savePathOriginal);
        intent.putExtra(FILE_PATH_PRESS_KEY, savePathPress);
        intent.putExtra(LOGIN_USER_ID_KEY, loginUserId);
        intent.putExtra(USER_ID_OR_BOX_ID_KEY, userIdOrBoxId);
        intent.putExtra(PHOTO_SERIAL_NO_KEY, photoSerialNo);
        fragment.startActivityForResult(intent, RequestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_custom_camera;
    }

    @Override
    public void attachPresenterView() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        SetTranslanteBar();
        CameraUtil.init(this);
        initView();
        initData();
    }

    private void initView() {
        type = getIntent().getIntExtra(TYPE, 0);
        filePathOriginal = getIntent().getStringExtra(FILE_PATH_ORIGINAL_KEY);
        filePathPress = getIntent().getStringExtra(FILE_PATH_PRESS_KEY);
        loginUserId = getIntent().getStringExtra(LOGIN_USER_ID_KEY);
        userIdOrBoxId = getIntent().getStringExtra(USER_ID_OR_BOX_ID_KEY);
        photoSerialNo = getIntent().getIntExtra(PHOTO_SERIAL_NO_KEY, 0) + 1;
        if (FileUtils.isSdCardAvailable()) {
            filePathOriginal = Environment.getExternalStorageDirectory().getAbsolutePath() + filePathOriginal;
        } else {
            filePathOriginal = Environment.getRootDirectory().getAbsolutePath() + filePathOriginal;
        }
        if (FileUtils.isSdCardAvailable()) {
            filePathPress = Environment.getExternalStorageDirectory().getAbsolutePath() + filePathPress;
        } else {
            filePathPress = Environment.getRootDirectory().getAbsolutePath() + filePathPress;
        }
        rxPermissions = RxPermissions.getInstance(this);
        mSurfaceView = (SurfaceView) findViewById(R.id.my_surfaceView);
        openLight = (ImageButton) findViewById(R.id.openLight);
        focusIndex = findViewById(R.id.focus_index);
        bootomRly = findViewById(R.id.bootomRly);
        lookPictureIv = (ImageView) findViewById(R.id.lookPictureIv);

        ImageView image1 = (ImageView) findViewById(R.id.back);
        ImageView next = (ImageView) findViewById(R.id.lookPictureIv);
        Button button = (Button) findViewById(R.id.takePhoto);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.cameraSwitch);

        image1.setOnClickListener(this);
        next.setOnClickListener(this);
        button.setOnClickListener(this);
        openLight.setOnClickListener(this);
        imageButton2.setOnClickListener(this);

        CameraUtil.getInstance().registerSensor(this);
    }

    protected void initData() {
        originalPhotoList = new ArrayList<>();
        pressPhotoList = new ArrayList<>();
        holder = mSurfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(this); // 回调接口

        bootomRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    // 主点按下
                    case MotionEvent.ACTION_DOWN:
                        pointX = event.getX();
                        pointY = event.getY();
                        mode = FOCUS;
                        break;
                    // 副点按下
                    case MotionEvent.ACTION_POINTER_DOWN:
                        dist = spacing(event);
                        // 如果连续两点距离大于10，则判定为多点模式
                        if (spacing(event) > 10f) {
                            mode = ZOOM;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = FOCUS;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == FOCUS) {
                        } else if (mode == ZOOM) {
                            float newDist = spacing(event);
                            if (newDist > 10f) {
                                float tScale = (newDist - dist) / dist;
                                if (tScale < 0) {
                                    tScale = tScale * 10;
                                }
                                addZoomIn((int) tScale);
                            }
                        }
                        break;
                }
                return false;
            }
        });
        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pointFocus((int) pointX, (int) pointY);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(focusIndex.getLayoutParams());
                layout.setMargins((int) pointX - 60, (int) pointY - 60, 0, 0);
                focusIndex.setLayoutParams(layout);
                focusIndex.setVisibility(View.VISIBLE);
                ScaleAnimation sa = new ScaleAnimation(3f, 1f, 3f, 1f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                sa.setDuration(800);
                focusIndex.startAnimation(sa);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        focusIndex.setVisibility(View.INVISIBLE);
                    }
                }, 700);
            }
        });
    }

    private void addZoomIn(int delta) {
        try {
            Camera.Parameters params = mCamera.getParameters();
            Log.d("Camera", "Is support Zoom " + params.isZoomSupported());
            if (!params.isZoomSupported()) {
                return;
            }
            curZoomValue += delta;
            if (curZoomValue < 0) {
                curZoomValue = 0;
            } else if (curZoomValue > params.getMaxZoom()) {
                curZoomValue = params.getMaxZoom();
            }

            if (!params.isSmoothZoomSupported()) {
                params.setZoom(curZoomValue);
                mCamera.setParameters(params);
                return;
            } else {
                mCamera.startSmoothZoom(curZoomValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //定点对焦的代码
    private void pointFocus(int x, int y) {
        mCamera.cancelAutoFocus();
        parameters = mCamera.getParameters();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            showPoint(x, y);
        }
        mCamera.setParameters(parameters);
        autoFocus();
    }

    /**
     * 两点的距离
     */
    private float spacing(MotionEvent event) {
        if (event == null) {
            return 0;
        }
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void showPoint(int x, int y) {
        if (parameters.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> areas = new ArrayList<Camera.Area>();
            //xy变换了
            int rectY = -x * 2000 / CameraUtil.screenWidth + 1000;
            int rectX = y * 2000 / CameraUtil.screenHeight - 1000;

            int left = rectX < -900 ? -1000 : rectX - 100;
            int top = rectY < -900 ? -1000 : rectY - 100;
            int right = rectX > 900 ? 1000 : rectX + 100;
            int bottom = rectY > 900 ? 1000 : rectY + 100;
            Rect area1 = new Rect(left, top, right, bottom);
            areas.add(new Camera.Area(area1, 800));
            parameters.setMeteringAreas(areas);
        }

        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
    }

    //实现自动对焦
    private void autoFocus() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mCamera == null) {
                    return;
                }
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success) {
                            setupCamera(camera);//实现相机的参数初始化
                        }
                    }
                });
            }
        };
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mCamera != null) {
            mCamera.stopPreview();
            startPreview(mCamera, holder);
            autoFocus();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera != null) {
            startPreview(mCamera, holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    public void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera(cameraPosition);
            if (mCamera != null) {
                if (holder != null) {
                    startPreview(mCamera, holder);
                }
            }
        }
        CameraUtil.getInstance().enableSensor();
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
        CameraUtil.getInstance().unregisterSersor();
    }

    /**
     * 闪光灯开关   开->关->自动
     *
     * @param mCamera
     */
    private void turnLight(Camera mCamera) {
        if (mCamera == null || mCamera.getParameters() == null
                || mCamera.getParameters().getSupportedFlashModes() == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        String flashMode = mCamera.getParameters().getFlashMode();
        List<String> supportedModes = mCamera.getParameters().getSupportedFlashModes();
        if (Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)
                && supportedModes.contains(Camera.Parameters.FLASH_MODE_ON)) {//关闭状态
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            mCamera.setParameters(parameters);
            openLight.setImageResource(R.drawable.camera_flash_on);
        } else if (Camera.Parameters.FLASH_MODE_ON.equals(flashMode)) {//开启状态
            if (supportedModes.contains(Camera.Parameters.FLASH_MODE_AUTO)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                openLight.setImageResource(R.drawable.camera_flash_auto);
                mCamera.setParameters(parameters);
            } else if (supportedModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                openLight.setImageResource(R.drawable.camera_flash_off);
                mCamera.setParameters(parameters);
            }
        } else if (Camera.Parameters.FLASH_MODE_AUTO.equals(flashMode)
                && supportedModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            openLight.setImageResource(R.drawable.camera_flash_off);
        }
    }

    /**
     * 获取Camera实例
     */
    private Camera getCamera(int id) {
        Camera camera = null;
        try {
            camera = Camera.open(id);
        } catch (Exception e) {
            //无牌照权限提示
            noPermissionTip();
        }
        return camera;
    }

    /**
     * 预览相机
     */
    private void startPreview(Camera camera, SurfaceHolder holder) {
        try {
            setupCamera(camera);
            camera.setPreviewDisplay(holder);
            //亲测的一个方法 基本覆盖所有手机 将预览矫正
            CameraUtil.getInstance().setCameraDisplayOrientation(this, cameraPosition, camera);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置
     */
    private void setupCamera(Camera camera) {
        try {
            Camera.Parameters parameters = camera.getParameters();

            List<String> focusModes = parameters.getSupportedFocusModes();
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                // Autofocus mode is supported 自动对焦
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }

            Camera.Size previewSize = CameraUtil.findBestPreviewResolution(camera);
            parameters.setPreviewSize(previewSize.width, previewSize.height);

            Camera.Size pictrueSize = CameraUtil.getInstance().getPropPictureSize(parameters.getSupportedPictureSizes(), 1920);

            parameters.setPictureSize(pictrueSize.width, pictrueSize.height);
            parameters.setJpegQuality(100); // 设置照片质量
            // 设置场景模式
            parameters.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
            // 设置白平衡
            parameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
            // 设置曝光补偿
            parameters.setExposureCompensation(0);
            // 设置反冲带
            parameters.setAntibanding(Camera.Parameters.ANTIBANDING_AUTO);
            // 设置颜色效果
            parameters.setColorEffect(Camera.Parameters.EFFECT_NONE);
            camera.setParameters(parameters);

            int picHeight = CameraUtil.screenWidth * previewSize.width / previewSize.height;
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(CameraUtil.screenWidth, picHeight);
            mSurfaceView.setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
            noPermissionTip();
        }

    }

    /**
     * 无拍照权限
     */
    private void noPermissionTip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomCameraActivity.this);
        builder.setCancelable(false);
        builder.setMessage("您未授权程序允许拍照,请在权限管理中允许")
                .setTitle("提示").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PermissionUtil.startSettingIntent(CustomCameraActivity.this);
                dialog.dismiss();
                finish();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    //将bitmap保存在本地，然后通知图库更新
    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 创建原图文件夹
        if (!FileUtil.fileIsExists(filePathOriginal)) {
            FileUtils.createDir(filePathOriginal);
        }
        // 创建压缩图文件夹
        if (!FileUtil.fileIsExists(filePathPress)) {
            FileUtils.createDir(filePathPress);
        }
        String fileName = "";
        String nowTime = formatter.format(new Date());
        //照片命名规则：图片名称=用户编号_表箱编号或用户编号_时间流水_图片张数流水号.jpg；
        if (type == 0) {
            fileName = loginUserId + "_" + userIdOrBoxId + "_" + photoSerialNo + "_" + nowTime + ".jpg";
        } else if (type == 1) {
            fileName = userIdOrBoxId + "_" + photoSerialNo + "_" + nowTime + ".jpg";//表箱
        } else if (type == 2) {
            fileName = loginUserId + "_" + userIdOrBoxId + "_" + photoSerialNo + "_" + nowTime + ".jpg";//用户
        }
        //压缩图片（用于上传）
        currentPressFile = new File(filePathPress, fileName);
        //原型图片
        File originalFile = new File(filePathOriginal, fileName);
        try {
            //原图超过5M压缩
            byte[] compressByteArray = PressImageUtil.compressBitmap(bmp);
            FileOutputStream fos = new FileOutputStream(originalFile);
            fos.write(compressByteArray);
            fos.flush();
            fos.close();
            //压缩图片（用于上传）
            PressImageUtil.pressImage(originalFile.getAbsolutePath(), currentPressFile);
            //原图图片
            originalPhotoList.add(originalFile.getAbsolutePath());
            //压缩图图片
            pressPhotoList.add(currentPressFile.getAbsolutePath());
            photoSerialNo = photoSerialNo + 1;
//            // 其次把原图文件插入到系统图库
//            try {
//                MediaStore.Images.Media.insertImage(context.getContentResolver(), originalFile.getAbsolutePath(), fileName, null);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(context, "保存图片失败", Toast.LENGTH_SHORT).show();
//            }
//            // 最后通知图库更新
//            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + originalFile)));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "保存图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.back == id) {
            //回传
            rebackResult();
            finish();
        } else if (R.id.takePhoto == id) {
            rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new DefaultSubscriber<Permission>() {
                @Override
                public void onNext(Permission permission) {
                    if (permission.granted) {
                        if (safeToTakePicture) {
                            safeToTakePicture = false;
                            mCamera.takePicture(null, null, mJpeg);
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
                        builder.setCancelable(false);
                        builder.setMessage("您未授权读取本地相册权限,将无法打开相册,请在权限管理中开启存储权限")
                                .setTitle("提示").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri packageURI = Uri.parse("package:" + getPackageName());
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                startActivity(intent);
                                dialog.dismiss();
                                finish();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        builder.create().show();

                    }
                }
            });
        } else if (id == R.id.openLight) {
            turnLight(mCamera);
        } else if (R.id.cameraSwitch == id) {
            releaseCamera();
            cameraPosition = (cameraPosition + 1) % mCamera.getNumberOfCameras();
            mCamera = getCamera(cameraPosition);
            if (holder != null) {
                startPreview(mCamera, holder);
            }
        } else if (R.id.lookPictureIv == id) {
            if (originalPhotoList.size() > 0) {
                BigImagePagerActivity.startImagePagerActivity(CustomCameraActivity.this, originalPhotoList, originalPhotoList.size() - 1);
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //回传
            rebackResult();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    /**
     * 回传原图和压缩图数组
     */
    private void rebackResult() {
        if (originalPhotoList != null && pressPhotoList != null) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra(INTENT_ORIGINAL_RESULT, originalPhotoList);
            intent.putStringArrayListExtra(INTENT_PRESS_RESULT, pressPhotoList);
            setResult(RESULT_OK, intent);
        }
    }
}
