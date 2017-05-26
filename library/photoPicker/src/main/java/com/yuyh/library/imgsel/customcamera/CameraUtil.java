package com.yuyh.library.imgsel.customcamera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class CameraUtil {
    public static float scale;

    public static int densityDpi;

    public static float fontScale;

    public static int screenWidth;
    public static int screenHeight;
    private OrientationEventListener mOrientationListener;
    /**
     * 竖屏正
     */
    public static final int ORIENTATION_TYPE_PORTRAIT = 0;
    /**
     * 竖屏反
     */
    public static final int ORIENTATION_TYPE_PORTRAIT_O = 1;
    /**
     * 横屏	顺时针旋转90度
     */
    public static final int ORIENTATION_TYPE_LANDSCAPE_CW = 2;
    /**
     * 横屏	逆时针旋转90度
     */
    public static final int ORIENTATION_TYPE_LANDSCAPE_CCW = 3;
    /**
     * 当前屏幕方向
     */
    public static int orientationType;
    /**
     * 旋转角度
     */
    private int orientations;

    // 开始角度
    public static int degreeOriginal;
    // 旋转角度
    public static int degreeResult;

    public static void init(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        scale = dm.density;
        densityDpi = dm.densityDpi;
        fontScale = dm.scaledDensity;
        if (dm.widthPixels < dm.heightPixels) {
            screenWidth = dm.widthPixels;
            screenHeight = dm.heightPixels;
        } else {
            screenWidth = dm.heightPixels;
            screenHeight = dm.widthPixels;
        }
        Log.e("screen", "屏幕宽度是:" + screenWidth + " 高度是:" + screenHeight + " dp:" + scale + " fontScale:" + fontScale);
    }

    //降序
    private CameraDropSizeComparator dropSizeComparator = new CameraDropSizeComparator();
    //升序
    private CameraAscendSizeComparator ascendSizeComparator = new CameraAscendSizeComparator();
    private static CameraUtil myCamPara = null;

    private CameraUtil() {

    }

    public static CameraUtil getInstance() {
        if (myCamPara == null) {
            myCamPara = new CameraUtil();
            return myCamPara;
        } else {
            return myCamPara;
        }
    }

    /**
     * 保证预览方向正确
     *
     * @param activity
     * @param cameraId
     * @param camera
     */
    public void setCameraDisplayOrientation(Activity activity,
                                            int cameraId, Camera camera) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        degreeOriginal = degrees;
        degreeResult = result;
        camera.setDisplayOrientation(result);
    }

    /**
     * 注册感应器监听(注意：在oncreate方法注册)
     */
    public void registerSensor(Context context) {
        /**
         * 判断屏幕旋转角度
         */
        mOrientationListener = new OrientationEventListener(context, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                orientations = orientation;
                if (orientations > 325 || orientations <= 45) {
                    orientationType = ORIENTATION_TYPE_PORTRAIT;
                } else if (orientations > 45 && orientations <= 135) {
                    orientationType = ORIENTATION_TYPE_PORTRAIT_O;
                } else if (orientations > 135 && orientations < 225) {
                    orientationType = ORIENTATION_TYPE_LANDSCAPE_CW;
                } else {
                    orientationType = ORIENTATION_TYPE_LANDSCAPE_CCW;
                }

            }
        };
    }

    /**
     * 注册感应器监听(注意：在onresume方法激活)
     */
    public void enableSensor() {
        if (mOrientationListener != null) {//先判断下防止出现空指针异常
            mOrientationListener.enable();
        }
    }

    /**
     * 取消注册监听
     */
    public void unregisterSersor() {
        if (mOrientationListener != null) {//先判断下防止出现空指针异常
            mOrientationListener.disable();
        }
    }

    /**
     * 拍完照后转正角度
     */
    public Matrix getMatrix() {
//                        Matrix matrix = new Matrix();
//                        switch (cameraPosition) {
//                            case 0://前
//                                matrix.preRotate(90);
//                                break;
//                            case 1:
//                                matrix.preRotate(90);
//                                break;
//                        }
        Matrix matrix = new Matrix();
        matrix.reset();
        if (CameraUtil.orientationType == CameraUtil.ORIENTATION_TYPE_PORTRAIT) {
            matrix.setRotate(90 - (CameraUtil.degreeResult - CameraUtil.degreeOriginal - 90));
        } else if (CameraUtil.orientationType == CameraUtil.ORIENTATION_TYPE_PORTRAIT_O) {
            matrix.setRotate(180 - (CameraUtil.degreeResult - CameraUtil.degreeOriginal - 90));
        } else if (CameraUtil.orientationType == CameraUtil.ORIENTATION_TYPE_LANDSCAPE_CW) {
            matrix.setRotate(270 - (CameraUtil.degreeResult - CameraUtil.degreeOriginal - 90));
        } else {
            matrix.setRotate(0 - (CameraUtil.degreeResult - CameraUtil.degreeOriginal - 90));
        }
        return matrix;
    }

    /**
     * 获取所有支持的预览尺寸
     */
    public Size getPropPreviewSize(List<Size> list, int minWidth) {
        Collections.sort(list, ascendSizeComparator);

        int i = 0;
        for (Size s : list) {
            if ((s.width >= minWidth)) {
                break;
            }
            i++;
        }
        if (i == list.size()) {
            i = 0;//如果没找到，就选最小的size
        }
        return list.get(i);
    }

    /**
     * 获取所有支持的返回图片尺寸
     */
    public Size getPropPictureSize(List<Size> list, int minWidth) {
        Collections.sort(list, ascendSizeComparator);
        int i = 0;
        for (Size s : list) {
            if ((s.width >= minWidth)) {
                break;
            }
            i++;
        }
        if (i == list.size()) {
            i = list.size() / 2;//如果没找到，就选中间的size
        }
        return list.get(i);
    }

    public boolean equalRate(Size s, float rate) {
        float r = (float) (s.width) / (float) (s.height);
        if (Math.abs(r - rate) <= 0.03) {
            return true;
        } else {
            return false;
        }
    }

    //降序
    public class CameraDropSizeComparator implements Comparator<Size> {
        public int compare(Size lhs, Size rhs) {
            if (lhs.width == rhs.width) {
                return 0;
            } else if (lhs.width < rhs.width) {
                return 1;
            } else {
                return -1;
            }
        }

    }

    //升序
    public class CameraAscendSizeComparator implements Comparator<Size> {
        public int compare(Size lhs, Size rhs) {
            if (lhs.width == rhs.width) {
                return 0;
            } else if (lhs.width > rhs.width) {
                return 1;
            } else {
                return -1;
            }
        }

    }

    /**
     * 打印支持的previewSizes
     */
    public void printSupportPreviewSize(Camera.Parameters params) {
        List<Size> previewSizes = params.getSupportedPreviewSizes();
        for (int i = 0; i < previewSizes.size(); i++) {
            Size size = previewSizes.get(i);
        }

    }

    /**
     * 打印支持的pictureSizes
     */
    public void printSupportPictureSize(Camera.Parameters params) {
        List<Size> pictureSizes = params.getSupportedPictureSizes();
        for (int i = 0; i < pictureSizes.size(); i++) {
            Size size = pictureSizes.get(i);
        }
    }

    /**
     * 打印支持的聚焦模式
     */
    public void printSupportFocusMode(Camera.Parameters params) {
        List<String> focusModes = params.getSupportedFocusModes();
        for (String mode : focusModes) {
        }
    }

    /**
     * 打开闪关灯
     */
    public void turnLightOn(Camera mCamera) {
        if (mCamera == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters == null) {
            return;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();
        // Check if camera flash exists
        if (flashModes == null) {
            // Use the screen as a flashlight (next best thing)
            return;
        }
        String flashMode = parameters.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_ON.equals(flashMode)) {
            // Turn on the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            } else {
            }
        }
    }


    /**
     * 自动模式闪光灯
     */
    public void turnLightAuto(Camera mCamera) {
        if (mCamera == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters == null) {
            return;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();
        // Check if camera flash exists
        if (flashModes == null) {
            // Use the screen as a flashlight (next best thing)
            return;
        }
        String flashMode = parameters.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_AUTO.equals(flashMode)) {
            // Turn on the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            } else {
            }
        }
    }


    /**
     * 最小预览界面的分辨率
     */
    private static final int MIN_PREVIEW_PIXELS = 480 * 320;
    /**
     * 最大宽高比差
     */
    private static final double MAX_ASPECT_DISTORTION = 0.15;


    /**
     * 关闭闪光灯
     *
     * @param mCamera
     */
    public void turnLightOff(Camera mCamera) {
        if (mCamera == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters == null) {
            return;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();
        String flashMode = parameters.getFlashMode();
        // Check if camera flash exists
        if (flashModes == null) {
            return;
        }
        if (!Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            // Turn off the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            } else {
            }
        }
    }

    public static Size findBestPreviewResolution(Camera mCamera) {
        Camera.Parameters cameraParameters = mCamera.getParameters();
        Size defaultPreviewResolution = cameraParameters.getPreviewSize();

        List<Size> rawSupportedSizes = cameraParameters.getSupportedPreviewSizes();
        if (rawSupportedSizes == null) {
            return defaultPreviewResolution;
        }

        // 按照分辨率从大到小排序
        List<Size> supportedPreviewResolutions = new ArrayList<Size>(rawSupportedSizes);
        Collections.sort(supportedPreviewResolutions, new Comparator<Size>() {
            @Override
            public int compare(Size a, Size b) {
                int aPixels = a.height * a.width;
                int bPixels = b.height * b.width;
                if (bPixels < aPixels) {
                    return -1;
                }
                if (bPixels > aPixels) {
                    return 1;
                }
                return 0;
            }
        });

        StringBuilder previewResolutionSb = new StringBuilder();
        for (Size supportedPreviewResolution : supportedPreviewResolutions) {
            previewResolutionSb.append(supportedPreviewResolution.width).append('x').append(supportedPreviewResolution.height)
                    .append(' ');
        }

        // 移除不符合条件的分辨率
        double screenAspectRatio = (double) (screenWidth / screenHeight);
        Iterator<Size> it = supportedPreviewResolutions.iterator();
        while (it.hasNext()) {
            Size supportedPreviewResolution = it.next();
            int width = supportedPreviewResolution.width;
            int height = supportedPreviewResolution.height;

            // 移除低于下限的分辨率，尽可能取高分辨率
            if (width * height < MIN_PREVIEW_PIXELS) {
                it.remove();
                continue;
            }

            // 在camera分辨率与屏幕分辨率宽高比不相等的情况下，找出差距最小的一组分辨率
            // 由于camera的分辨率是width>height，我们设置的portrait模式中，width<height
            // 因此这里要先交换然preview宽高比后在比较
            boolean isCandidatePortrait = width > height;
            int maybeFlippedWidth = isCandidatePortrait ? height : width;
            int maybeFlippedHeight = isCandidatePortrait ? width : height;
            double aspectRatio = (double) maybeFlippedWidth / (double) maybeFlippedHeight;
            double distortion = Math.abs(aspectRatio - screenAspectRatio);

            // 找到与屏幕分辨率完全匹配的预览界面分辨率直接返回
            if (maybeFlippedWidth == screenWidth
                    && maybeFlippedHeight == screenHeight) {
                return supportedPreviewResolution;
            }

            if (distortion > MAX_ASPECT_DISTORTION) {
                it.remove();
                continue;
            }

        }

        // 如果没有找到合适的，并且还有候选的像素，则设置其中最大比例的，对于配置比较低的机器不太合适
        if (!supportedPreviewResolutions.isEmpty()) {
            Size largestPreview = supportedPreviewResolutions.get(0);
            return largestPreview;
        }

        // 没有找到合适的，就返回默认的

        return defaultPreviewResolution;
    }
}
