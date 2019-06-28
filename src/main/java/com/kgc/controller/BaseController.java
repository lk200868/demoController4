package com.kgc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;



public abstract class BaseController {
	private static Logger logger = LoggerFactory.getLogger(BaseController.class.getName());
	@Autowired
	CommonsMultipartResolver multipartResolver;

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;
	
	protected int pageNum = 1;
	
	protected int pageSize = 10;
	
	protected String sort = "";
	
	protected String redirectURL;
	
	protected <T> Pagination<T> initPagination(){
		logger.debug("开始处理request中的分页参数");
		/*
		if(StringUtils.isNotBlank(request.getParameter("pageNum"))){
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		if(StringUtils.isNotBlank(request.getParameter("pageSize"))){
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		if(StringUtils.isNotBlank(request.getParameter("sort"))){
			sort = request.getParameter("sort");
		}
		*/
		/**
		 * easyui dategrid 分页参数为page=X&rows=X&sort=x&order=ASC|DESC
		 */
		if(StringUtils.isNotBlank(request.getParameter("page"))){
			pageNum = Integer.parseInt(request.getParameter("page"));
		}
		if(StringUtils.isNotBlank(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		if(StringUtils.isNotBlank(request.getParameter("sort"))){
			sort = request.getParameter("sort");
		}
		if(StringUtils.isNotBlank(request.getParameter("order"))){
			sort += " " + request.getParameter("order");
		}
		return new Pagination<T>(pageNum, pageSize, sort);
	}

	@ExceptionHandler
	public String exception(HttpServletRequest request, Exception e) {
		JsonReturnTemplate json = new JsonReturnTemplate(JsonReturnTemplate.RETURN_STATUS_FAILED);
		json.setMsg(e.getMessage());
		return JsonHelper.init().toJson(json);
	}
	
	/**
	 * 处理文件上传，支持多个文件处理
	 * 所有文件会暂时存储于临时目录下，临时目录会定期清空，读取文件后面根据业务规则将文件转存至各自业务目录下
	 * @return Map<String, List<UploadFile>>:
	 *              String: 表单中文件上传参数名称
	 *              List<UploadFile> : 对应参数名称下的多个文件列表
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public Map<String, List<UploadFile>> initUploadFile(HttpServletRequest request) throws IllegalStateException, IOException {
		Map<String, List<UploadFile>> tmpFileMap = new HashMap<String, List<UploadFile>>();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		//CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			//MultipartHttpServletRequest multiRequest =  multipartResolver.resolveMultipart(request);
			// 获取multiRequest 中所有的文件名
			MultiValueMap<String, MultipartFile> fileMap = multiRequest.getMultiFileMap();
			
			for(Map.Entry<String,List<MultipartFile>> entry : fileMap.entrySet()){
				if(tmpFileMap.get(entry.getKey()) == null){
					List<UploadFile> fileList = new ArrayList<UploadFile>();
					tmpFileMap.put(entry.getKey(), fileList);
				}
				for(MultipartFile f : entry.getValue()){
					if(!f.isEmpty()){
						tmpFileMap.get(entry.getKey()).add(new UploadFile(f));
					}
				}
				 
			}
		}
		return tmpFileMap;
	}

	/**
	 * 
	 * @Description 统一校验处理
	 * @since V1.0
	 * @param result
	 */
	public void checkValidator(BindingResult result) {
		if(!result.hasErrors()) {
			return;
		}	
		
		StringBuffer sb = new StringBuffer("");
		List<ObjectError> errors = result.getAllErrors();
		for(ObjectError e : errors) {
			sb.append(e.getDefaultMessage()).append(",");
		}
		sb.substring(0, sb.lastIndexOf(",")-1);
		
		throw new BusinessException(sb.toString());
		
	}
}
