package com.hisign.thirdparty.qrcode;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 描述：
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：张绪飞
 * 创建时间:2016/12/8
 * 最后修改时间:2016/12/8
 */
public class ZXingCapture {
    public static void init(Context context) {
        if (context == null) {
            return;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenHeightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(context, dm.widthPixels);
        DisplayUtil.screenHeightDip = DisplayUtil.px2dip(context, dm.heightPixels);
    }
}
