package io.renren.common.utils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

public class HMACSHA1 {
		  
	    private static final String MAC_NAME = "HmacSHA1";    
	    private static final String ENCODING = "UTF-8"; 
	    private static final String ENCRYPTKEY = "bbb1694f0b8157dfbc2a521434466cac";
	      
	    /**  
	     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名  
	     * @param encryptText 被签名的字符串  
	     * @param encryptKey  密钥  
	     * @return  
	     * @throws Exception  
	     */    
	    public static String HmacSHA1Encrypt(String encryptText) throws Exception     
	    {           
	        byte[] data=ENCRYPTKEY.getBytes(ENCODING);  
	        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称  
	        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);   
	        //生成一个指定 Mac 算法 的 Mac 对象  
	        Mac mac = Mac.getInstance(MAC_NAME);   
	        //用给定密钥初始化 Mac 对象  
	        mac.init(secretKey);    
	          
	        byte[] SHA1 = encryptText.getBytes(ENCODING);    
	        //完成 Mac 操作   
	        byte[] SHA1result = mac.doFinal(SHA1);   
	        
	        //base64加密
	        String base64 = (new BASE64Encoder()).encodeBuffer(SHA1result);  
	        return base64;
	    }  
	    
	    public static void main(String[] args) {
	    	try {
	    		String s = HmacSHA1Encrypt("appVersion1.0uuid3821938902183908921");
	    		String ss = HmacSHA1Encrypt("appVersion1.0areaID410101latitudelongitudeplatformiPhone 6srequestTime1501662361205systemTypeiOSsystemVersion10.3.2tokenuuid5355A834-F547-4410-BA8A-511C1ECA5CC6"); 
				String sss= HmacSHA1Encrypt("appVersion1.0areaID410101latitudelongitudeplatformiPhone 6srequestTime1501661509318systemTypeiOSsystemVersion10.3.2tokenuuid5355A834-F547-4410-BA8A-511C1ECA5CC6");
	    		System.out.println(s);
				System.out.println(ss);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
