package com.kgc.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

//import com.kbao.core.common.SpringContext;

/**
 * 上传文件的实体对象
 * 用于针对Controller中上传的文件进行统一封装
 * 
 *
 */
public class UploadFile {
	/**
	 * 上传时原文件名称
	 */
	private String srcName;
	
	/**
	 * 文件的context-type, 详见contexttype.properties
	 */
	private String contextType;
	
	/**
	 * 文件后缀名称，统一转换为小写
	 */
	private String suffix;
	
	/**
	 * 文件大小
	 */
	private long size;
	/**
	 * 临时文件
	 */
	private File file ;
	
	
	public UploadFile(MultipartFile mFile) throws IllegalStateException, IOException{
		srcName = mFile.getOriginalFilename();
		contextType = mFile.getContentType();
		suffix = srcName.substring(srcName.lastIndexOf(".")+1, srcName.length());
		size = mFile.getSize();
		//文件存储路径
		String path = SpringContext.getWebRoot() + File.separator + "tmp";
		File dir = new File(path);
		dir.mkdirs();
		if (mFile != null && StringUtils.isNotBlank(mFile.getOriginalFilename()) ) {
			file = new File(path + File.separator + UUID.randomUUID() + "." + suffix);
			// 上传
			mFile.transferTo(file);
		}
	}


	public String getSrcName() {
		return srcName;
	}


	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}


	public String getContextType() {
		return contextType;
	}


	public void setContextType(String contextType) {
		this.contextType = contextType;
	}


	public String getSuffix() {
		return suffix;
	}


	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}


	public long getSize() {
		return size;
	}


	public void setSize(long size) {
		this.size = size;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}
	
}
