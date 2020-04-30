package com.bchy.utils;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {
	 public final static String IMG_PATH_PREFIX = "static/upload/imgs";
	 public final static String PICTURE_PATH_PREFIX = "static/upload/picture";

	    public static File getImgDirFile(){

	        // 构建上传文件的存放 "文件夹" 路径
	        String fileDirPath = new String("src/main/resources/" + IMG_PATH_PREFIX);
	        File fileDir = new File(fileDirPath);
	        if(!fileDir.exists()){
	            // 递归生成文件夹
	            fileDir.mkdirs();
	        }
	        return fileDir;
	    }
	    
	    public static File getPictureDirFile(String name){

	        // 构建上传文件的存放 "文件夹" 路径
	        String fileDirPath = new String("src/main/resources/"+ PICTURE_PATH_PREFIX+"/"+name);
	        File fileDir = new File(fileDirPath);
	        if(!fileDir.exists()){
	            // 递归生成文件夹
	            fileDir.mkdirs();
	        }
	        return fileDir;
	    }
}
