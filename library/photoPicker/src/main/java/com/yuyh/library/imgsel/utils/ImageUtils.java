/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yuyh.library.imgsel.utils;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
	/**
	 * 保存文件
	 *
	 * @param bm
	 * @throws IOException
	 */
	public static void savePhotoFile(Bitmap bm, File file)
			throws IOException {
		if(bm!=null) {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
		}
	}
	/**
	 * 保存文件
	 * 
	 * @param bm
	 * @throws IOException
	 */
	public static void savePhotoFile(Bitmap bm, String file)
			throws IOException {
		File dirFile = new File(file);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		if(bm!=null) {
			File myCaptureFile = new File(file);
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(myCaptureFile));
			bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
		}
	}
	public static void savePhotoFile(File oldfile, String savePath)
			throws IOException {
		try {
			int bytesum = 0;
			int byteread = 0;
			if (oldfile.exists()) { //文件存在时
				InputStream inStream = new FileInputStream(oldfile); //读入原文件
				FileOutputStream fs = new FileOutputStream(savePath);
				byte[] buffer = new byte[1444];
				int length;
				while ( (byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; //字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		}
		catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}

	}

}
