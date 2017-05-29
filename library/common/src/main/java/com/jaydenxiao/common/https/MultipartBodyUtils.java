package com.jaydenxiao.common.https;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by xsf on 2017/2/21.
 */

public class MultipartBodyUtils {
    /**
     * 将文件路径数组封装为{@link List <MultipartBody.Part>}
     *
     * @param key       对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     *                  同一个name值，实际情况中有可能需要多个
     * @param filePaths 文件路径数组
     * @param imageType 文件类型
     */
    public static List<MultipartBody.Part> files2Parts(String key,
                                                       String[] filePaths, MediaType imageType) {
        List<MultipartBody.Part> parts = new ArrayList<>(filePaths.length);
        for (String filePath : filePaths) {
            File file = new File(filePath);
            // 根据类型及File对象创建RequestBody（okhttp的类）
            RequestBody requestBody = RequestBody.create(imageType, file);
            // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
            MultipartBody.Part part = MultipartBody.Part.
                    createFormData(key, file.getName(), requestBody);
            // 添加进集合
            parts.add(part);
        }
        return parts;
    }

    public static MultipartBody.Part files2Part(String key,
                                                String filePath, MediaType imageType) {
        File file = new File(filePath);
        // 根据类型及File对象创建RequestBody（okhttp的类）
        RequestBody requestBody = RequestBody.create(imageType, file);
        // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
        MultipartBody.Part part = MultipartBody.Part.
                createFormData(key, file.getName(), requestBody);
        return part;
    }

    public static HashMap<String, RequestBody> RequestBody(HashMap<String, String> box) {
        HashMap<String, RequestBody> map = new HashMap<>();
        Iterator iter = box.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            map.put(key, RequestBody.create(
                    MediaType.parse("multipart/form-data"), val));
        }
        return map;
    }
}
