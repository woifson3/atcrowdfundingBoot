package com.atguigu.scw.common.Template;

import com.atguigu.scw.common.utils.HttpUtils;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class SmsTemplate {

	@Value("${sms.host}") //只能在組件中使用
	private String host;
	@Value("${sms.path}") //只能在組件中使用
	private String path;
	@Value("${sms.method}") //只能在組件中使用
	private String method;
	@Value("${sms.appcode}") //只能在組件中使用
	private String appcode;
	// 发送短信的方法
	public boolean sendSms(Map<String, String> querys) {
//		String host = "http://dingxin.market.alicloudapi.com";
//		String path = "/dx/sendSms";
//		String method = "POST";
//		String appcode = "75cb9e7f5fc94db9a7b7ac1524d05f6a";
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		/* 参数值每次调用都不一样，需要作为方法参数传入
		 * Map<String, String> querys = new HashMap<String, String>();
		querys.put("mobile", "15890345670");
		querys.put("param", "code:hehehe");
		querys.put("tpl_id", "TP1711063");*/
		Map<String, String> bodys = new HashMap<String, String>();

		try {
			/**
			 * 重要提示如下: HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			//System.out.println(response.toString());
			// 获取response的body
			// System.out.println(EntityUtils.toString(response.getEntity()));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
