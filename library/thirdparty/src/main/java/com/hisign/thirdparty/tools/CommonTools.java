package com.hisign.thirdparty.tools;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *@类名 CommonTools
 *@描述  常用工具方法,目前是上报中用到的工具类
 *@公司 北京海鑫科金高科技股份有限公司
 *@作者 杨培尧
 *@版本 V1.0
 *@创建时间 2016年6月3日
 *@最后修改时间 2016年6月4日
 */
public class CommonTools {
	
	/**
	 * 日期格式，yyyy-MM-dd HH:mm:ss
	 */
	private static String datePattern1 = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式，yyyyMMddHHmmss
	 */
	private static String datePattern2 = "yyyyMMddHHmmss";
	/**
	 * 日期格式，yyyy-MM-dd
	 */
	private static String datePattern3 = "yyyy年MM月dd日";
	/**
	 * 日期格式，yyyy年MM月dd日
	 */
	private static String datePattern4 = "yyyy-MM-dd";
	/**
	 * 日期格式，yyyy年MM月dd日HH时mm分
	 */
	private static String datePattern5 = "yyyy年MM月dd日HH时mm分";
	
	
	/**
	 *@创建人 杨培尧
	 *@创建时间 2016年6月4日 
	 *@方法说明 时间格式转换，从源时间格式到目标时间格式
	 *@param sourseDate
	 *@param datePatternSourse,源日期格式
	 *@param datePatternTarget,目标日期格式
	 *@return toDate
	 */
	public static String formatMyDate(String sourseDate, String datePatternSourse, String datePatternTarget){
		if(TextUtils.isEmpty(sourseDate)){
			return null;
		}
		String toDate=null;
		SimpleDateFormat sourformat = new SimpleDateFormat(datePatternSourse);
		SimpleDateFormat toformat = new SimpleDateFormat(datePatternTarget);
		try {
			Date date=sourformat.parse(sourseDate);
			toDate=toformat.format(date);
		} catch (ParseException e) {
			return null;
		}
		if(TextUtils.isEmpty(toDate)){
			return sourseDate;
		}
		return toDate;
	}
	
	/**
	 *@创建人 杨培尧
	 *@创建时间 2016年6月3日 
	 *@方法说明   时间格式转换，从yyyy-MM-dd HH:mm:ss到yyyyMMddHHmmss
	 *@param sourseDate
	 *@return toDate
	 */
	public static String formatTime(String sourseDate){
		if(TextUtils.isEmpty(sourseDate)){
			return null;
		}
		String toDate=null;
		SimpleDateFormat sourformat = new SimpleDateFormat(datePattern1);
		SimpleDateFormat toformat = new SimpleDateFormat(datePattern2);
		try {
			Date date=sourformat.parse(sourseDate);
			toDate=toformat.format(date);
		} catch (ParseException e) {
			return null;
		}
		if(TextUtils.isEmpty(toDate)){
			return sourseDate;
		}
		return toDate;
	}
	
	/**
	 *@创建人 杨培尧
	 *@创建时间 2016年6月3日 
	 *@方法说明   时间格式转换，从yyyy年MM月dd日到yyyy-MM-dd
	 *@param sourseDate
	 *@return toDate
	 */
	public static String formatTime2(String sourseDate){
		if(TextUtils.isEmpty(sourseDate)){
			return null;
		}
		String toDate=null;
		SimpleDateFormat sourformat = new SimpleDateFormat(datePattern3);
		SimpleDateFormat toformat = new SimpleDateFormat(datePattern4);
		try {
			Date date=sourformat.parse(sourseDate);
			toDate=toformat.format(date);
		} catch (ParseException e) {
			return null;
		}
		if(TextUtils.isEmpty(toDate)){
			return sourseDate;
		}
		return toDate;
	}
	
	/**
	 *@创建人 杨培尧
	 *@创建时间 2016年6月3日 
	 *@方法说明   时间格式转换，从yyyy-MM-dd到yyyyMMddHHmmss
	 *@param sourseDate
	 *@return toDate
	 */
	public static String formatTime3(String sourseDate){
		if(TextUtils.isEmpty(sourseDate)){
			return null;
		}
		String toDate=null;
		SimpleDateFormat sourformat = new SimpleDateFormat(datePattern4);
		SimpleDateFormat toformat = new SimpleDateFormat(datePattern1);//暂时适配服务器当前版本
//		SimpleDateFormat toformat = new SimpleDateFormat(datePattern2);
		try {
			Date date=sourformat.parse(sourseDate);
			toDate=toformat.format(date);
		} catch (ParseException e) {
			return null;
		}
		if(TextUtils.isEmpty(toDate)){
			return sourseDate;
		}
		return toDate;
	}
	
	/**
	 *@创建人 杨培尧
	 *@创建时间 2016年6月3日 
	 *@方法说明  时间格式转换，简单字符替换，0补位，为应对不同字段不同的时间格式
	 *@param sourseDate
	 *@return toDate
	 */
	public static String formatDate(String sourseDate){
		if(TextUtils.isEmpty(sourseDate)){
			return null;
		}
		sourseDate=sourseDate.replace("-", "");
		sourseDate=sourseDate.replace(":", "");
		sourseDate=sourseDate.replace(" ", "");
		int adds=0;
		if(14>sourseDate.length()){
			adds=14-sourseDate.length();
		}
		for (int i = 0; i < adds; i++) {
			sourseDate=sourseDate+"0";
		}
		return sourseDate;
	}
	
	/**
	 *@创建人 杨培尧
	 *@创建时间 2016年8月15日 
	 *@方法说明  时间格式转换,汉子格式转纯数字，针对痕迹物证收集时间格式
	 *@param sourseDate
	 *@return toDate
	 */
	public static String formatDateUnChi(String sourseDate){
		if(TextUtils.isEmpty(sourseDate)){
			return null;
		}
		Pattern pat = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher mat=pat.matcher(sourseDate);
		if(mat.find()){
			SimpleDateFormat sourformat = new SimpleDateFormat(datePattern5);
			SimpleDateFormat toformat = new SimpleDateFormat(datePattern2);
			try {
				Date date=sourformat.parse(sourseDate);
				sourseDate=toformat.format(date);
			} catch (ParseException e) {
				return null;
			}
		}else{
			sourseDate=sourseDate.replace("-", "");
			sourseDate=sourseDate.replace(":", "");
			sourseDate=sourseDate.replace(" ", "");
		}
		int adds=0;
		if(14>sourseDate.length()){
			adds=14-sourseDate.length();
		}
		for (int i = 0; i < adds; i++) {
			sourseDate=sourseDate+"0";
		}
		return sourseDate;
	}
	
	/**
     * 压缩整个文件夹中的所有文件，生成指定名称的zip压缩包
     * @param filepath 文件所在目录
     * @param zippath 压缩后zip文件名称
     * @param dirFlag zip文件中第一层是否包含一级目录，true包含；false没有
     * 2015年6月9日
     */
    public static File zipMultiFile(String filepath , String zippath, boolean dirFlag) {
        try {
            File file = new File(filepath);// 要被压缩的文件夹
            File zipFile = new File(zippath);
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for(File fileSec:files){
                    if(dirFlag){
                        recursionZip(zipOut, fileSec, file.getName() + File.separator);
                    }else{
                        recursionZip(zipOut, fileSec, "");
                    }
                }
            }
            zipOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new File(zippath);
    }
     
    private static void recursionZip(ZipOutputStream zipOut, File file, String baseDir) throws Exception {
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File fileSec:files){
                recursionZip(zipOut, fileSec, baseDir + file.getName() + File.separator);
            }
        }else{
            byte[] buf = new byte[1024];
            InputStream input = new FileInputStream(file);
            zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
            int len;
            while((len = input.read(buf)) != -1){
                zipOut.write(buf, 0, len);
            }
            input.close();
        }
    }
	
	
	
	/**
	 * 删除目录(包括：目录里的所有文件)
	 * @param filePath
	 * @return
	 */
	public static boolean deleteDirectory(File filePath) {
		if(null==filePath||(!filePath.exists())){
			return false;
		}
		if (filePath.isDirectory()) {
            String[] children = filePath.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDirectory(new File(filePath, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return filePath.delete();
	}

	/**
	 * 移动文件夹至目标文件夹
	 *@创建人 杨培尧
	 *@创建时间 2016年8月9日 
	 *@方法说明 
	 *@param sourcePath
	 *@param targetPath
	 */
	public static boolean movedFiles(String sourcePath, String targetPath){
		boolean status=false;
		if(!new File(targetPath).exists()){
			new File(targetPath).mkdirs();
		}
		try {
			File sourceFile=new File(sourcePath);
			if(null!=sourceFile&&sourceFile.exists()){
				File[] file = sourceFile.listFiles();
				if(file.length>0){
					for (int i = 0; i < file.length; i++) {  
						if (file[i].isFile()) {  
							// 复制文件   
							copyFile(file[i],new File(targetPath+file[i].getName()));
						}  
						if (file[i].isDirectory()) {  
							String sourceDir=sourcePath+ File.separator+file[i].getName();
							String targetDir=targetPath+ File.separator+file[i].getName();
							// 复制目录   
							copyDirectiory(sourceDir, targetDir);  
						}  
					} 
					status=true;
				}
			}
		} catch (Exception e) {
			status=false;
		}
		return status;
	}
	
	/**
	 * 获取一个文件夹下的所有文件
	 * @param root 路径
	 * @param suffix 指定后缀名
	 * @return
	 */
	public static List<File> listPathFiles(String root, String suffix) {
		List<File> allDir = new ArrayList<File>();
		SecurityManager checker = new SecurityManager();
		File path = new File(root);
		checker.checkRead(root);
		File[] files = path.listFiles();
		if(null!=files&&0<files.length){
			for (File f : files) {
				if (f.isFile()){
					if(TextUtils.isEmpty(suffix)){
						allDir.add(f);
					}else{
						if(f.getName().toLowerCase(Locale.getDefault()).endsWith(suffix)){
							allDir.add(f);
						}
					}
				}
				if (f.isDirectory())
					allDir.addAll(listPathFiles(f.getAbsolutePath(),suffix));
			}
		}
		return allDir;
	}
	
	/**
	 * 复制文件
	 */
	private static void copyFile(File sourceFile, File targetFile) throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);
		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);
		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();
		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}

	/**
	 * 复制文件夹
	 */
	private static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
		// 新建目标目录
		(new File(targetDir)).mkdirs();
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}
	
	/**
	 *@方法说明  日志文件打包，暂弃，备用
	 *@param filePath,源文件路径
	 *@param zipPath,生成压缩包路径
	 *@return File
	 */
	public static boolean zip(String filePath, String zipPath) {
//		try {
//			ZipFile zFile = new ZipFile(zipPath);
//			ZipParameters parameters = new ZipParameters();
//			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
//			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
//			zFile.createZipFile(new File(filePath), parameters);
//		} catch (ZipException e) {
//			return false;
//		}
		return true;
	}
	
	private static char[] base64EncodeChars = new char[]{
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '+', '/'};
	/**
	 * 服务器端base64加密方法
	 * @param data
	 * @return
	 */
	public static String encode(byte[] data) {
		StringBuffer sb = new StringBuffer();
		int len = data.length;
		int i = 0;
		int b1, b2, b3;
		while (i < len) {
			b1 = data[i++] & 0xff;
			if (i == len) {
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
				sb.append("==");
				break;
			}
			b2 = data[i++] & 0xff;
			if (i == len) {
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
				sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
				sb.append("=");
				break;
			}
			b3 = data[i++] & 0xff;
			sb.append(base64EncodeChars[b1 >>> 2]);
			sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
			sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
			sb.append(base64EncodeChars[b3 & 0x3f]);
		}
		return sb.toString();
	}
	
	/**
	 * 获取最新的错误日志
	 *@创建人 杨培尧
	 *@创建时间 2016年9月8日 
	 *@方法说明 
	 *@return
	 *@备注1 【注释人:杨培尧;时间:2016年9月8日;原因:】
	 *@备注2 【修改人:杨培尧;时间:2016年9月8日;原因:】
	 *@异常
	 */
	public static String getErrorLog(){
		File rootfile=new File(LogFileTools.SDK_ROOT_PATH+ LogFileTools.LogReport+"response_error/");
		if(null!=rootfile&&rootfile.exists()&&rootfile.isDirectory()){
			ArrayList<File> files=(ArrayList<File>) listPathFiles(rootfile.getAbsolutePath(), null);
			if(null!=files&&0<files.size()){
				for (int i = 0; i < files.size()-1; i++) {
					long time1=getMytime(files.get(i));
					long time2=getMytime(files.get(i+1));
					if(time1<time2){
						files.remove(i);
					}else{
						files.remove(i+1);
					}
					i--;
				}
				if(null!=files.get(0)){
					return getFileContent(files.get(0));
				}
			}
			return null;
		}else{
			return null;
		}
	}
	
	private static long getMytime(File file){
		if(null==file||!file.exists()){
			return 0L;
		}
		try {
			String name=file.getName();
			String time=name.substring(0, 23);
			SimpleDateFormat sourformat=new SimpleDateFormat("yyyy-MM-dd__HH.mm.ssSSS", Locale.getDefault());
			Date date = sourformat.parse(time);
			return date.getTime();
		} catch (Exception e) {
			return 0L;
		}
	}
	
	/**
	 * 获取文件内容
	 *@创建人 杨培尧
	 *@创建时间 2016年9月8日 
	 *@方法说明 
	 *@param file
	 *@return
	 *@备注1 【注释人:杨培尧;时间:2016年9月8日;原因:】
	 *@备注2 【修改人:杨培尧;时间:2016年9月8日;原因:】
	 *@异常
	 */
	public static String getFileContent(File file){
		try {
			FileInputStream inStream = new FileInputStream(file);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
			}
			outStream.close();
			inStream.close();
			return outStream.toString("GBK");
		} catch (Exception e) {
			return null;
		}
	}
	
}
