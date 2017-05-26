package com.yuyh.library.imgsel.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * 类名：PressImageUtil.java
 * 描述：
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 作者：xsf
 * 创建时间：2017/2/22
 * 最后修改时间：2017/2/22
 */

public class PressImageUtil {
    /**
     * 压缩
     *
     * @param filePath
     */


    public static void pressImage(final String filePath, final File saveFile) {
        // TODO Auto-generated method stub
        Bitmap bitmap = null;
        try {
            bitmap = getBitmapFromFile(filePath, 600, 1000);
            if (bitmap != null) {
                ImageUtils.savePhotoFile(bitmap, saveFile);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (bitmap != null)
                bitmap.recycle();
        }

    }

    /**
     * 压缩
     *
     * @param filePath
     */


    public static void pressImage(final String filePath) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Bitmap bitmap = null;
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(filePath, options);
                    // Calculate inSampleSize
                    options.inSampleSize = 4;
                    // Decode bitmap with inSampleSize set
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeFile(filePath, options);
                    if (bitmap != null) {
                        ImageUtils.savePhotoFile(bitmap, filePath);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    if (bitmap != null)
                        bitmap.recycle();
                }
            }
        }).start();
    }


    public static Bitmap getBitmapFromFile(String pathName, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //options.inPurgeable = true;
        BitmapFactory.decodeFile(pathName, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(pathName, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 压缩bitmap图片(不能超过5M)
     *
     * @param bitmap bitmap图片
     * @return 压缩完返回字节数组
     */
    public static byte[] compressBitmap(Bitmap bitmap) {
        byte[] byteArray = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//把压缩后的数据存放到baos中
            int options = 100;
            while (FileUtils.formatFileSizeToM(baos.toByteArray().length) > 5 && options > 10) {  //循环判断如果压缩后图片是否大于5M,大于继续压缩
                baos.reset();//清空byteArrayOutputStream
                options -= 2;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            }
            byteArray = baos.toByteArray();
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
        return byteArray;
    }
}
