package com.kgc.controller;

import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * 
 * 
 */
public class JsonValueProcessors {
	public static SimpleModule DEFAULT_MODULE = new SimpleModule("DefaultModule");

	public JsonValueProcessors() {

	}
	
	public static class JsonClobSerializer extends JsonSerializer<Clob> {
		@Override
		public void serialize(Clob value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
			try {
				gen.writeString(value.getSubString(1, (int) value.length()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * java.util.date 类型序列化生成器
	 * 
	 *
	 */
	public static class JsonDateSerializer extends JsonSerializer<Date> {
		private SimpleDateFormat format = DateHelper.DEFAULT_SIMPLE_DATE_FORMAT; 
		public JsonDateSerializer(){
			super();
		}
		public JsonDateSerializer(String dateFormat){
			super();
			this.format = new SimpleDateFormat(dateFormat);
		}
		
		@Override
		public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
			String formattedDate = this.format.format(date);
			gen.writeString(formattedDate);
		}
	}

	/**
	 * java.sql.timestamp 类型序列化生成器
	 * 
	 *
	 */
	public static class JsonTimestampSerializer extends JsonSerializer<Timestamp> {
		private SimpleDateFormat format = DateHelper.DEFAULT_SIMPLE_DATE_FORMAT; 
		public JsonTimestampSerializer(){
			super();
		}
		public JsonTimestampSerializer(String dateFormat){
			super();
			this.format = new SimpleDateFormat(dateFormat);
		}
		@Override
		public void serialize(Timestamp date, JsonGenerator gen, SerializerProvider provider) throws IOException,	JsonProcessingException {
			String formattedDate = this.format.format(date);
			gen.writeString(formattedDate);
		}
	}
	
	/**
	 * java.util.Date 类型反序列化解析器
	 * 
	 *
	 */
	public static class JsonDateDeserializer extends JsonDeserializer<Date> {
		@Override
		public Date deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
			Date date = null;
			if(arg0.getText() != null && arg0.getText().trim().length() > 0){
				date = DateHelper.parseStringToDate(arg0.getText());
			}
			return date;
		}
	}
	
	/**
	 * java.sql.Timestamp 类型反序列化解析器
	 * 
	 *
	 */
	public static class JsonTimestampDeserializer extends JsonDeserializer<Timestamp> {
		@Override
		public Timestamp deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
			Timestamp date = null;
			if(arg0.getText() != null && arg0.getText().trim().length() > 0){
				date =  DateHelper.parseStringToTimestamp(arg0.getText());
			} 
			return date;
		}

	}

}
