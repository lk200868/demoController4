package com.kgc.controller;

import java.sql.Timestamp;
import java.util.Date;

public class CommonUtils {

    /**
     * 将Exception中的堆栈信息转换成字符串
     * @param e
     * @return
     */
    public static String getExceptionStackTraceToString(Throwable e){
    	StringBuffer strBuf = new StringBuffer();
    	if(e != null){
	    	StackTraceElement[] stList = e.getStackTrace();
	    	StackTraceElement st = null;
	    	for(int i=0; i<stList.length; i++){
	    		st = stList[i];
	    		if(i==0){
	    			strBuf.append(e.getMessage()).append("\t").append(st.getClassName()).append(".").append(st.getMethodName())
	    				.append("(").append(st.getFileName()).append(":").append(st.getLineNumber()).append(")");
	    		}else{
	    			strBuf.append("\tat  ")
	    				.append(st.getClassName()).append(".").append(st.getMethodName())
	    				.append("(").append(st.getFileName()).append(":").append(st.getLineNumber()).append(")");
	    		}
	    	}
    	}
    	return strBuf.toString();
    }
    
    /**
     * 取得当前应用服务器时间
     * @return
     */
    public static Timestamp getCurentAppServerTimestamp(){
    	return new Timestamp(System.currentTimeMillis());
    }
    
    /**
     * 取得当前应用服务器时间(date)
     */
    public static Date getCurentAppServerDate(){
    	return new Date(getCurentAppServerTimestamp().getTime());
    }
    
    /**
     * 把得到的完整类名，首字母变小写
     * @param className
     * @return
     */
    public static String lowerFirstBeanName(String className){
    	
    	 String tempstring = className.substring(className.lastIndexOf(".")+1,className.lastIndexOf(".")+2);
         className =className.substring(className.lastIndexOf(".")+2,className.length());
         className =tempstring.toLowerCase()+className;
         return className;
    }
}
