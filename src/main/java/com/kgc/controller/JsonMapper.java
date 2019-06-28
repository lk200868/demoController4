package com.kgc.controller;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonParser;

public class JsonMapper {
	private static ObjectMapper mapper = new ObjectMapper();
	
	static { // if you need to change default configuration:
		//mapper.configure(SerializationConfig.Feature.USE_STATIC_TYPING, true); // faster this way, not default
	}
	  
    private JsonMapper() {   
    }   
  
    public static ObjectMapper getInstance() {
    	//mapper = new ObjectMapper();
    	//兼容单引号
    	mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    	//兼容field name 无双引号
    	mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    	//兼容value值内有反斜杠"/"
    	//mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
    	mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    	
    	//去掉默认的时间戳格式  
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);  
        //设置为中国时区  
		mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
		mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);  
        //空值不序列化  
		mapper.setSerializationInclusion(Include.NON_NULL);  
        //反序列化时，属性不存在的兼容处理  
		mapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);  
        //序列化时，日期的统一格式  
		mapper.setDateFormat(new SimpleDateFormat(DateHelper.DEFAULT_FULL_DATE_FORMAT));  
  
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);  
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);  
        return mapper;   
    }
    
	public static void registerSerializerDateModule(ObjectMapper mapper, String dateFormat){
		JsonValueProcessors.DEFAULT_MODULE.addSerializer(java.util.Date.class, new JsonValueProcessors.JsonDateSerializer(dateFormat));
		JsonValueProcessors.DEFAULT_MODULE.addSerializer(java.sql.Timestamp.class, new JsonValueProcessors.JsonTimestampSerializer(dateFormat));
		JsonValueProcessors.DEFAULT_MODULE.addSerializer(java.sql.Clob.class, new JsonValueProcessors.JsonClobSerializer());
		mapper.registerModule(JsonValueProcessors.DEFAULT_MODULE);
	}
	
	public static void registerDeserializerDateModule(ObjectMapper mapper, String dateFormat){
		JsonValueProcessors.DEFAULT_MODULE.addDeserializer(java.util.Date.class, new JsonValueProcessors.JsonDateDeserializer());
		JsonValueProcessors.DEFAULT_MODULE.addDeserializer(java.sql.Timestamp.class, new JsonValueProcessors.JsonTimestampDeserializer());
		mapper.registerModule(JsonValueProcessors.DEFAULT_MODULE);
	}
}
