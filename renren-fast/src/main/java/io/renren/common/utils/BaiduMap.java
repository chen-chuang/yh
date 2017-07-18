package io.renren.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaiduMap {

	public static String getGeocoderLatitude(String address) {
		String ll = "";
		BufferedReader in = null;
		try {
			address = URLEncoder.encode(address, "UTF-8");
			URL tirc = new URL("http://api.map.baidu.com/geocoder?address=" + address + "&output=json&key="
					+ "F9efd0733fd85edf1452a16dae56c89c");
			in = new BufferedReader(new InputStreamReader(tirc.openStream(), "UTF-8"));
			String res;
			StringBuilder sb = new StringBuilder("");
			while ((res = in.readLine()) != null) {
				sb.append(res.trim());
			}
			String str = sb.toString();
			if (StringUtils.isNotEmpty(str)) {
				int lngStart = str.indexOf("lng\":");
				int lngEnd = str.indexOf(",\"lat");
				int latEnd = str.indexOf("},\"precise");
				if (lngStart > 0 && lngEnd > 0 && latEnd > 0) {
					String lng = str.substring(lngStart + 5, lngEnd);
					String lat = str.substring(lngEnd + 7, latEnd);
					ll = lng+","+lat;
					System.out.println(lng + "\r\n" + lat);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ll;
	}

	public static void getposition(String latitude, String longitude) throws MalformedURLException {
		BufferedReader in = null;
		/*URL tirc = new URL("http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location=" + latitude + "," + longitude
				+ "&output=json&pois=1&ak=" + "F9efd0733fd85edf1452a16dae56c89c");*/
		
		URL tirc = new URL("http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location=34.774965,113.738512&output=json&pois=1&ak=F9efd0733fd85edf1452a16dae56c89c");
		
		try {
			in = new BufferedReader(new InputStreamReader(tirc.openStream(), "UTF-8"));
			String res;
			StringBuilder sb = new StringBuilder("");
			while ((res = in.readLine()) != null) {
				sb.append(res.trim());
			}
			String str = sb.toString();
			System.out.println(str);
			ObjectMapper mapper = new ObjectMapper();
			if (StringUtils.isNotEmpty(str)) {
				JsonNode jsonNode = mapper.readTree(str);
				jsonNode.findValue("status").toString();
				JsonNode resultNode = jsonNode.findValue("result");
				JsonNode locationNode = resultNode.findValue("formatted_address");
				System.out.println(locationNode);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		getGeocoderLatitude("河南郑州金水区商务内环路28号");
		try {
			getposition("113.738512","34.774965");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
