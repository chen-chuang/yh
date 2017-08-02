package io.renren.common.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class AppValidateUtils {
	
    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new Comparator<String>() {

					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}
				});

        sortMap.putAll(map);

        return sortMap;
    }
    
    public static String getSign(Map<String, String> map){
    	Map<String, String> sortMap = sortMapByKey(map);
    	StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : map.entrySet()) {
		   sb.append(entry.getKey()).append(entry.getValue());
		}
		
		String sign=null;
		
		try {
			sign = HMACSHA1.HmacSHA1Encrypt(sb.toString()).replaceAll("\r|\n", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sign;    	
    }

}
