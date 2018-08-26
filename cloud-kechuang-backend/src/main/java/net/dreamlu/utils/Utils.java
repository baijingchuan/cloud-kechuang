package net.dreamlu.utils;

import javax.servlet.http.HttpServletRequest;

public class Utils {
	
	  
	
	  
	
	/**
	 * 获取客户端IP  by ycj 20161009
	 * @param HttpServletRequest request
	 * @return
	 */
	public static String getClientIP(HttpServletRequest request) {
		 String ip = request.getHeader("x-forwarded-for"); 
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("Proxy-Client-IP"); 
	        } 
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("WL-Proxy-Client-IP"); 
	        } 
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getRemoteAddr(); 
	        } 
		return ip;
	}
}
