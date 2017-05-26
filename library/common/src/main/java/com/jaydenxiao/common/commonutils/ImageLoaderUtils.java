package com.jaydenxiao.common.commonutils;


import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaydenxiao.common.R;
import com.jaydenxiao.common.baseapp.BaseAppConfig;
import com.jaydenxiao.common.baseapp.BaseApplication;

import java.io.File;

/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class ImageLoaderUtils {

    /**
     * @param context   上下文
     * @param imageView
     * @param url       本地或在线url
     */
    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        url = getHttpImageUrl(url);
        Glide.with(BaseApplication.getAppContext()).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_glide_loading_picture)
                .error(R.drawable.ic_glide_error_picture)
                .crossFade().into(imageView);
    }

    /**
     * @param context   上下文
     * @param imageView
     * @param resId     本地资源id
     */
    public static void display(Context context, ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(BaseApplication.getAppContext()).load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_glide_loading_picture)
                .error(R.drawable.ic_glide_error_picture)
                .crossFade().into(imageView);
    }

    /**
     * @param context   上下文
     * @param imageView
     * @param file      file文件
     */
    public static void display(Context context, ImageView imageView, File file) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(BaseApplication.getAppContext()).load(file)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_glide_loading_picture)
                .error(R.drawable.ic_glide_error_picture)
                .crossFade().into(imageView);
    }

    /**
     * 根据url显示圆形图片
     *
     * @param context   上下文
     * @param imageView
     * @param url       本地货在线url
     */
    public static void displayRound(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        url = getHttpImageUrl(url);
        Glide.with(BaseApplication.getAppContext()).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_glide_loading_picture)
                .error(R.drawable.ic_glide_error_picture)
                .centerCrop().transform(new GlideRoundTransformUtil(context)).into(imageView);
    }

    /**
     * 根据本地资源id显示圆形图片
     *
     * @param context
     * @param imageView
     * @param resId
     */
    public static void displayRound(Context context, ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(BaseApplication.getAppContext()).load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_glide_loading_picture)
                .error(R.drawable.ic_glide_error_picture)
                .centerCrop().transform(new GlideRoundTransformUtil(context)).into(imageView);
    }

    /**
     * 显示小缩略图
     *
     * @param context   上下文
     * @param imageView
     * @param url       本地或在线url
     */
    public static void displaySmallPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        url = getHttpImageUrl(url);
        Glide.with(BaseApplication.getAppContext()).load(url).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_glide_loading_picture)
                .error(R.drawable.ic_glide_error_picture)
                .thumbnail(0.5f)
                .into(imageView);
    }

    /**
     * 显示大图
     *
     * @param context   上下文
     * @param imageView
     * @param url       本地或在线url
     */
    public static void displayBigPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        url = getHttpImageUrl(url);
        Glide.with(BaseApplication.getAppContext()).load(url).asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_glide_loading_picture)
                .error(R.drawable.ic_glide_error_picture)
                .into(imageView);
    }


    /**
     * @param context     上下文
     * @param imageView
     * @param url         本地或在线url
     * @param placeholder 自定义设置占位图片
     * @param error       自定义设置占位图片
     */
    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        url = getHttpImageUrl(url);
        Glide.with(BaseApplication.getAppContext()).load(url).placeholder(placeholder)
                .error(error).crossFade().into(imageView);
    }

    /**
     * 加载联系人
     *
     * @param context   上下文
     * @param imageView
     * @param bytes     图片byte[]
     */
    public static void displayContact(Context context, ImageView imageView, byte[] bytes) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(BaseApplication.getAppContext()).load(bytes)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_glide_loading_picture)
                .error(R.drawable.ic_glide_error_picture)
                .centerCrop().transform(new GlideRoundTransformUtil(context)).into(imageView);
    }

    /**显示头像（带默认头像图片）
     * @param context   上下文
     * @param imageView
     * @param url
     */
    public static void displayAvatar(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(BaseApplication.getAppContext()).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.icon_default_avater)
                .error(R.drawable.icon_default_avater)
                .crossFade().into(imageView);
    }
    /**显示头像（带默认头像图片）
     * @param context   上下文
     * @param imageView
     * @param resId
     */
    public static void displayAvatar(Context context, ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(BaseApplication.getAppContext()).load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.icon_default_avater)
                .error(R.drawable.icon_default_avater)
                .crossFade().into(imageView);
    }

    /**
     * 获取拼接后的图片链接
     *
     * @param url
     * @return
     */
    private static String getHttpImageUrl(String url) {
        if (isLocalImg(url)) {
            return url;
        }
        if (!TextUtils.isEmpty(url)) {
            return url.contains("http") ? url : BaseAppConfig.IMAGE_SERVER + url;
        } else {
            return "";
        }
    }

    /**
     * 判断是否是本地图片
     *
     * @param url
     * @return
     */
    private static boolean isLocalImg(String url) {
        if (!TextUtils.isEmpty(url) && url.contains("/storage/emulated/")) {
            return true;
        }
        return false;
    }


}
