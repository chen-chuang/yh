package io.renren.common.utils.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;

/**
 * 创建时间：2016年11月10日 下午7:09:08
 *
 * alipay支付
 *
 * @author andy
 * @version 2.2
 */

public class AlipayUtil {
	public static String SELLER_ID = "";

	public static String ALIPAY_APPID = "";

	public static String APP_PRIVATE_KEY = null;

	public static String ALIPAY_PUBLIC_KEY = null;

	static {
		try {
			APP_PRIVATE_KEY = "";

			ALIPAY_PUBLIC_KEY = "";

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 统一收单交易创建接口
	private static AlipayClient alipayClient = null;

	public static AlipayClient getAlipayClient() {
		if (alipayClient == null) {
			synchronized (AlipayUtil.class) {
				if (null == alipayClient) {
					alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", ALIPAY_APPID,
							APP_PRIVATE_KEY, AlipayConstants.FORMAT_JSON, AlipayConstants.CHARSET_UTF8,
							ALIPAY_PUBLIC_KEY);
				}
			}
		}
		return alipayClient;
	}
}
