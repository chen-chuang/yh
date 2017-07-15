package io.renren.common.utils;

import java.io.File;

public class FileUtils {
	
	public static void makeDir(String path) {

		File f = new File(path);
		if (!f.exists()) {			
			f.mkdir();
		} 
	}

}
