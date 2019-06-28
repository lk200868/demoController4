package com.kgc.controller;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 请求request 请求体
 * 
 * @author amos
 */
public class RequestStr {

	/**
	 * 请求体数据流获取
	 * 
	 * @author amos
	 * @param request
	 * @return 请求流字符串
	 */
	public static String getRequestStr(HttpServletRequest request) {
		BufferedReader in;
		try {
			Object requestStr = request.getAttribute("RequestStr");
			if (null != requestStr) {
				return requestStr.toString();
			}
			in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			/*千万不可去除！！*/
			request.setAttribute("RequestStr", sb.toString());
			return sb.toString();
		} catch (IOException e) {
			return "";
		}
	}

}
