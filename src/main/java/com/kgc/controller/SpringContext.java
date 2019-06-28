package com.kgc.controller;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class SpringContext implements ApplicationContextAware, ServletContextAware, InitializingBean // implements
																// ServletContextListener
																// //extends
																// servlet//ApplicationContextAware
																// //implements
																// AnnotatedBeanDefinition//ApplicationContextAware
{
	protected Logger logger = LoggerFactory.getLogger(SpringContext.class);
	private static ApplicationContext applicationContext;
	private static ServletContext servletContext;
	public static String webURL = null; // web上下文请求字符串
	private static String WEB_ROOT = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		logger.info("1 => StartupListener.setApplicationContext");
		SpringContext.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		logger.info("2 => StartupListener.setServletContext");
		SpringContext.servletContext = servletContext;
		if(StringUtils.isBlank(WEB_ROOT)){
			WEB_ROOT = SpringContext.servletContext.getRealPath("/");
		}
		System.out.println("WEB_ROOT:" + WEB_ROOT);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("3 => StartupListener.afterPropertiesSet");
	}
	
	public static ServletContext getServletContext() {
		return SpringContext.servletContext;
	}
	
	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	
	public static String getWebRoot(){
		
		return WEB_ROOT;
	}

	public static boolean containsBean(String dicId) {
		return applicationContext.containsBean(dicId);
	}

}