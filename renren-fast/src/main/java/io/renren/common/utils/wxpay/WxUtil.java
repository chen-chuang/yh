package io.renren.common.utils.wxpay;

import io.renren.common.utils.alipay.ConfigUtil;

public class WxUtil {
	
	public static final String ORDER_PAY = "https://api.mch.weixin.qq.com/pay/unifiedorder"; // 统一下单

	public static final String ORDER_PAY_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery"; // 支付订单查询

	public static final String ORDER_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund"; // 申请退款

	public static final String ORDER_REFUND_QUERY = "https://api.mch.weixin.qq.com/pay/refundquery"; // 申请退款

	public static final String APP_ID = ConfigUtil.getProperty("wx.appid");

	public static final String MCH_ID = ConfigUtil.getProperty("wx.mchid");

	public static final String API_SECRET = ConfigUtil.getProperty("wx.api.secret");

}
