package com.kgc.controller;

//import com.kbao.core.common.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName PolicyBaseController
 * @Description TODO
 * @Author Amos
 * @Version V1.0
 * @Since JDK1.8
 * @Date 2017/9/29 17:44
 */
public class PolicyBaseController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(BusinessException.class.getName());

    /**
     * @Description 非业务异常统一处理
     * @Since JDK1.8
     * @Author Amos
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler
    public String exception(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        logger.error(e.getMessage());
        ResponseStr responseStr = new ResponseStr(null, MessageProperty.ERROR_SERVICE_CODE,false,MessageProperty.getInstance().getProperty(MessageProperty.ERROR_SERVICE_CODE));
        return JsonHelper.init().toJson(responseStr);
    }

    /**
     * @Description 必填参数异常
     * @Since JDK1.8
     * @Author Amos
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler
    public String exception(HttpServletRequest request, MissingServletRequestParameterException e) {
        e.printStackTrace();
        logger.error(e.getMessage());
        ResponseStr responseStr = new ResponseStr(null, MessageProperty.ERROR_PARAM_CODE,false,MessageProperty.getInstance().getProperty(MessageProperty.ERROR_PARAM_CODE));
        return JsonHelper.init().toJson(responseStr);
    }
    /**
     * @Description 业务异常统一处理
     * @Since JDK1.8
     * @Author Amos
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler
    public String exception(HttpServletRequest request, BusinessException e) {
        e.printStackTrace();
        logger.error(e.getMessage());
       ResponseStr responseStr = new ResponseStr(null, e.getCode(),false,e.getMessage());
        return JsonHelper.init().toJson(responseStr);
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
            sb.append(MessageProperty.getInstance().getProperty(e.getDefaultMessage())).append(",");
        }
        sb.substring(0, sb.lastIndexOf(",")-1);

        throw new BusinessException(sb.toString());

    }
}
