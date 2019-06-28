package com.kgc.controller;

import com.github.pagehelper.PageInfo;
import com.kgc.entity.Stud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.kgc.service.RequestPage;
import com.kgc.service.StudService;

import javax.validation.Valid;

@Controller
@RequestMapping("/stud")
public class StudController {


    private static Logger logger = LoggerFactory.getLogger(StudController.class.getName());
	@Autowired
	private StudService studService;
	
	/**分页查询**/
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Object page(@RequestBody RequestPage page) {
		PageInfo<Stud> studPage = studService.page(page);
		ResponseStr responseStr = new ResponseStr(
				studPage,
				MessageProperty.SUCCESS_CODE,
				true,
				MessageProperty.getInstance().getProperty(MessageProperty.SUCCESS_CODE));
		return responseStr;
	}
	
	/**根据主键查询对象**/
    @RequestMapping(value = "/findbyid", method = RequestMethod.GET)
	@ResponseBody
	public Object findById(@RequestParam(value="id",required = true) Long id) {
		Stud stud= studService.selectByPrimaryKey(id);
			if(null == stud){
			throw new BusinessException(MessageProperty.ERROR_OBJECT_NOT_FOUND_CODE,
					MessageProperty.getInstance().getProperty(MessageProperty.ERROR_OBJECT_NOT_FOUND_CODE));
		}
		ResponseStr responseStr = new ResponseStr(
				stud,
				MessageProperty.SUCCESS_CODE,
				true,
				MessageProperty.getInstance().getProperty(MessageProperty.SUCCESS_CODE));
		return responseStr;
	}
	
	
	/*新增对象*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseStr insert(@RequestBody @Valid Stud stud, BindingResult result){
		//校验确认
//		checkValidator(result);
		int num = studService.insertSelective(stud);
		ResponseStr responseStr = new ResponseStr(
				stud,
				MessageProperty.SUCCESS_CODE,
				true,
				MessageProperty.getInstance().getProperty(MessageProperty.SUCCESS_CODE));
		return responseStr;

	}


	/*删除对象*/
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public ResponseStr delete(@RequestParam(value="id",required = true) Long id){
	   int num = studService.delete(id);
		if(num == 0) {
			throw new BusinessException(MessageProperty.ERROR_OBJECT_NOT_FOUND_CODE,
					MessageProperty.getInstance().getProperty(MessageProperty.ERROR_OBJECT_NOT_FOUND_CODE));
		}
		ResponseStr responseStr = new ResponseStr(
				num,
				MessageProperty.SUCCESS_CODE,
				true,
				MessageProperty.getInstance().getProperty(MessageProperty.SUCCESS_CODE));
		return responseStr;

	}
	
	
	
    /***修改对象*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseStr update(@RequestBody @Valid Stud stud, BindingResult result){
		//校验确认
//		checkValidator(result);
		int num = studService.updateByPrimaryKeySelective(stud);
		if(num == 0) {
			throw new BusinessException(MessageProperty.ERROR_OBJECT_NOT_FOUND_CODE,
					MessageProperty.getInstance().getProperty(MessageProperty.ERROR_OBJECT_NOT_FOUND_CODE));
		}
		ResponseStr responseStr = new ResponseStr(
				stud,
				MessageProperty.SUCCESS_CODE,
				true,
				MessageProperty.getInstance().getProperty(MessageProperty.SUCCESS_CODE));
		return responseStr;

	}
	

}