package io.renren.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.omg.PortableServer.ServantActivator;

public class FileUtils {
	
	public static void makeDir(String path) {

		File f = new File(path);
		if (!f.exists()) {			
			f.mkdir();
		} 
	}
	
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder("");
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
            	stringBuilder.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return stringBuilder.toString();
    }
    
    public static void main(String[] args) {
    	System.out.println(readFileByLines("D:\\新建文本文档.txt"));
	}
    
    public static void deleteFile(String filePath){
    	
    	if(StringUtils.isBlank(filePath)){
    		return;
    	}
    	try{
    		
    		File file = new File(filePath);
        	file.delete(); 
        	
    	}catch(Exception e){
    		e.printStackTrace();
    	}    	   	
    }

}
