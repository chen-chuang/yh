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
	public static String SELLER_ID = "2088721602929132";

	public static String ALIPAY_APPID = "2017082208322384";

	public static String APP_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKiCRFUoeubSoMVWOJ5S/ctzKkdv7LYYf23d4JMqdkSrJt86ImLbHHIkX851NKhf3XLcsulfefLTDXX24+1Js4idyNJd2zSmW4fi40yaxo6tPs9ODAL0OljgwUziOhHw9aqObqRIUxKAjaTrZ9lmCPIlW3tSvpx69RcKDgN/c5sLAgMBAAECgYBl7CUYvRtZchz8P2KPoPMRk98GxpQOsFKjSYkEerJ+Zmdkre0OjilugMHSRuJK1XFEgGlW7wD79tw1ddf3NpT4GdGV3DdpS6xvnm7EOfaTQQOJuf5cRUm/CTMaRkbehiEzB9wB7H5XHFXUrvpxDNL+dsU8+o3hR8WzKZUHdT4CQQJBANba3/R2choOARIulzwQy1RkCyEHdCBZFbAzUCaF/k5OggnXgvqUeCUVuCActYRzYdcCssr8K8G7p5dXKtRjrB8CQQDIx023yXiYdXFAn2fDsmw+GZK/lz77UCjFE+Fkvm4r+vON8qs+93Z8CYKkp1yqbnvV1xR6q3nvYDd/4zhJEfOVAkBvaLleaxjJd9+ITQ5wwAifLu0yaEoICAmd/02dXDe85Sq67bxmMRyHplZ+mERrdOUT97s8VDxgTe0eJb34ueJjAkEAiMh/Af6X0gXwqf3vSzt60rPJbg00MzJhFCJ/I4s4s228D7CLSdJ9PfOctV/vmYy7gOuOYCQCZjo+lTnPVriMIQJAQ/P8UpWt11/IL5XsL5M/+ed0OskB4E4fSCsttJ97fthHDYnFs6xJgSMcZ2aw1uwQCJ9XeNnmemSAMWSvyhJDhg==";

	public static String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

	static {
		try {
			APP_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKiCRFUoeubSoMVWOJ5S/ctzKkdv7LYYf23d4JMqdkSrJt86ImLbHHIkX851NKhf3XLcsulfefLTDXX24+1Js4idyNJd2zSmW4fi40yaxo6tPs9ODAL0OljgwUziOhHw9aqObqRIUxKAjaTrZ9lmCPIlW3tSvpx69RcKDgN/c5sLAgMBAAECgYBl7CUYvRtZchz8P2KPoPMRk98GxpQOsFKjSYkEerJ+Zmdkre0OjilugMHSRuJK1XFEgGlW7wD79tw1ddf3NpT4GdGV3DdpS6xvnm7EOfaTQQOJuf5cRUm/CTMaRkbehiEzB9wB7H5XHFXUrvpxDNL+dsU8+o3hR8WzKZUHdT4CQQJBANba3/R2choOARIulzwQy1RkCyEHdCBZFbAzUCaF/k5OggnXgvqUeCUVuCActYRzYdcCssr8K8G7p5dXKtRjrB8CQQDIx023yXiYdXFAn2fDsmw+GZK/lz77UCjFE+Fkvm4r+vON8qs+93Z8CYKkp1yqbnvV1xR6q3nvYDd/4zhJEfOVAkBvaLleaxjJd9+ITQ5wwAifLu0yaEoICAmd/02dXDe85Sq67bxmMRyHplZ+mERrdOUT97s8VDxgTe0eJb34ueJjAkEAiMh/Af6X0gXwqf3vSzt60rPJbg00MzJhFCJ/I4s4s228D7CLSdJ9PfOctV/vmYy7gOuOYCQCZjo+lTnPVriMIQJAQ/P8UpWt11/IL5XsL5M/+ed0OskB4E4fSCsttJ97fthHDYnFs6xJgSMcZ2aw1uwQCJ9XeNnmemSAMWSvyhJDhg==";

			ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

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
