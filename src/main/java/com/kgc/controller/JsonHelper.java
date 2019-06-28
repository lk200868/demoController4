package com.kgc.controller;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
//import com.kbao.core.common.exception.BusinessException;
//import com.kbao.core.common.utils.date.DateHelper;


/**
 * JavaBean 与 json 间的映射转换工具类, 提供javaBean 与 Json 间的转换方法, 即javaBean序列化/反序列化
 * 注意: 
 * 	1. javaBean 必须为需要进行序列化与反序列化的Field提供get/set方法
 *  2. javaBean 序列化时默认会过滤掉null值的Field, 即不会对此类Filed进行序列化
 *  3. javaBean 序列化时, 如果针对Bean中某一个Field限制不做序列化, 可在类定义的上面一行通过"@JsonIgnoreProperties(value={"field1", "field2", ...})"注解方式申请不做序列化的Field
 *     例: 在对User进行序列化时, 不希望对password进行序列化
 *          @JsonIgnoreProperties(value = {"password"})
 *     		public class User{
 *     			...
 *     			private String name;
 *     			private String password;
 *     			...
 *     
 *          }
 *  4. 在进行序列化/反序列化时, 默认日期对象只对java.util.Date, java.sql.Timestamp这二个进行了处理, 如JavaBean中存在其它日期类型, 序列化/反序列化时将会存在问题. 
 *     默认的日期格式为:  yyyy-MM-dd HH:mm
 *     
 * 调用方法：
 *  一. 直接使用JsonHelper的静态方式调用：所有序列化/反序列化都按照默认行式处理
 *      JsonHelper.obj2Json(...);
 *      JsonHelper.json2Obj(...);
 *      
 *  二. 直接JsonHelper.init() 返回JsonHelper对象形式，可动态拼接序列化/反序列化配置
 *      JsonHelper.init().xxx().xxx().xxx().toJson(...);
 *      JsonHelper.init().xxx().xxx().xxx().toObj(...);
 * 
 * @version 1.0 
 *
 */
public class JsonHelper{	
	static final String DYNC_INCLUDE = "DYNC_INCLUDE";
	static final String DYNC_FILTER = "DYNC_FILTER";

	@JsonFilter(DYNC_FILTER)
	private interface DynamicFilter {
	}

	@JsonFilter(DYNC_INCLUDE)
	private interface DynamicInclude {
	}

	private ObjectMapper mapper = JsonMapper.getInstance();
	
	public static JsonHelper init() {  
        return new JsonHelper();  
    }  
	
	private JsonHelper() {  
		setDefaultSerializationConfig(mapper);
    } 
	
	/**
	 * 配置序列时日期格式
	 * @param dateFormat
	 * @return
	 */
	public JsonHelper dateFormat(String dateFormat){
		JsonMapper.registerSerializerDateModule(mapper, dateFormat);
		return this;
	}
	
	/**
	 * 配置序列化时，类中属性字段是否进行序列化
	 * @param clazz
	 * @param include: 指定仅包含的属性字段。配置后仅此处理字段会进行序列化输出. 无需指定时可配置null或""
	 * @param filter：指定需要过滤的属性字段。配置后这些字段将不会进行序列化输出. 无需指定时可配置null或""
	 * @return
	 */
	public JsonHelper filter(Class<?> clazz, String include, String filter) {
		if (StringUtils.isNotBlank(include)) {
			mapper.setFilterProvider(new SimpleFilterProvider().addFilter(DYNC_INCLUDE, SimpleBeanPropertyFilter.filterOutAllExcept(include.split(","))));
			mapper.addMixIn(clazz, DynamicInclude.class);
		} else if (StringUtils.isNotBlank(filter)) {
			mapper.setFilterProvider(new SimpleFilterProvider().addFilter(DYNC_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(filter.split(","))));
			mapper.addMixIn(clazz, DynamicFilter.class);
		}
		return this;
	}
	
	/**
	 * 充列化值配置
	 * 	  Include.Include.ALWAYS 全部进行序列化
		  Include.NON_DEFAULT 属性为默认值不序列化 
		  Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化 
		  Include.NON_NULL 属性为NULL 不序列化 
	 * @param type 
	 * @return
	 */
	public JsonHelper serializationInclusion(Include type){
		mapper.setSerializationInclusion(type); 
		return this;
	}
	
	/**
	 * JavaBean To Json的转换方法  
	 * @param obj
	 * @return
	 */
	public String toJson(Object obj){
		String result;
		try {
			result = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			result = new BusinessException("对象转json出错", e).toString();
		} 
		return result;
	}
	
	public <T> T toObj(String json, TypeReference<T> typeRef) throws JsonParseException, JsonMappingException, IOException{
		return mapper.readValue(json, typeRef);
	}
	
	public <T> T toObj(String json, Class<?> parametrized, Class<?>... parameterClasses) throws JsonParseException, JsonMappingException, IOException{
		JavaType javaType = mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
		return mapper.readValue(json, javaType);
	}
	
	/**
	 * JavaBean To Json的转换方法  
	 * 		例: 要将一个Object转成json, 即
	 * 			JsohHelper.Obj2Json(obj);
	 * 注意:此方法日期对象默认在序列化时输出格式为 "yyyy-MM-dd HH:mm"
	 * @param 待转换的对象实例 (支持Object, 数组, 集合) 
	 * @return 序列化后的json字符串
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String  obj2Json(Object obj){
		return obj2Json(obj, DateHelper.DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * JavaBean To Json的转换方法  
	 * 		例: 要将一个Object转成json, 即
	 * 			JsohHelper.Obj2Json(obj, dateformat);
	 * @param obj: 待序列化的对象
	 * @param dateFormat: 序列化过程中日期对象的输出格式
	 * @return
	 */
	public static String obj2Json(Object obj, String dateFormat){
		ObjectMapper mapper = JsonMapper.getInstance();  
		//注册日期处理方法
		JsonMapper.registerSerializerDateModule(mapper, dateFormat);
		//设置默认序列化配置
		setDefaultSerializationConfig(mapper);
		String result;
		try {
			result = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			result = new BusinessException("对象转json出错", e).toString();
		} 
		return result;
	}
	
	/**
	 * Json To JavaBean(包含ArrayList, Map, 数组等集合类) 的转换方法
	 *      例: 将json字符串转为User类
	 *      	JsonHelper.json2Obj(jsonString, new TypeReference<User>() {});
	 *          将json字符串转为集合类(ArrayList, Map, Object[]等)
	 *          JsonHelper.json2Obj(jsonString, new TypeReference<ArrayList<User>>() {});
	 * @param Json格式的字符串
	 * @param typeRef: 转换至目标类的类型定义, new TypeReference<?>(){} 中定义的泛型类, 即为要传换的目标对象, 隐式声明的对象类内无需写任何代码
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T json2Obj(String json, TypeReference<T> typeRef) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = JsonMapper.getInstance();
		//注册日期处理方法
		JsonMapper.registerDeserializerDateModule(mapper, DateHelper.DEFAULT_DATE_FORMAT);
		//设置默认反序列化配置
		setDefaultDeserializationConfig(mapper);
		return mapper.readValue(json, typeRef);
	}
	
	/**
	 * 
	 * @param json
	 * @param parametrized
	 * @param parameterClasses
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T json2Obj(String json, Class<?> parametrized, Class<?>... parameterClasses) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = JsonMapper.getInstance();
		//注册日期处理方法
		JsonMapper.registerDeserializerDateModule(mapper, DateHelper.DEFAULT_DATE_FORMAT);
		//设置默认反序列化配置
		setDefaultDeserializationConfig(mapper);
		JavaType javaType = mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
		return mapper.readValue(json, javaType);
	}
	
	/**
	 * Json To JavaBean: Json 字符串转换成javaBean 
	 * 		例: 要将一段json字符串转为User类, 即 
	 * 			User u = JsohHelper.json2Obj(jsonString, User.class);
	 * @param json: Json格式的字符串
	 * @param valueType: 反序列化的javaBean类. 
	 * @return
	 * @throws IOException
	 */
	public static <T> T json2Obj(String json, Class<T> valueType) throws IOException{
		ObjectMapper mapper = JsonMapper.getInstance();
		//注册日期处理方法
		JsonMapper.registerDeserializerDateModule(mapper, DateHelper.DEFAULT_DATE_FORMAT);
		//设置默认反序列化配置
		setDefaultDeserializationConfig(mapper);
		return mapper.readValue(json,valueType);
	}	
	
	/**
	 * 设置默认序列化参数
	 * @param mapper
	 */
	private static void setDefaultSerializationConfig(ObjectMapper mapper){
		
	}
	
	/**
	 * 设置默认反序列化参数
	 * @param mapper
	 */
	private static void setDefaultDeserializationConfig(ObjectMapper mapper){
	}	
	
}
