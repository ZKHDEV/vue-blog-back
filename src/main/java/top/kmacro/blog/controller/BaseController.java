package top.kmacro.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import top.kmacro.blog.model.vo.Response;
import top.kmacro.blog.web.WebContext;

/**
 * Created by Zhangkh on 2017-09-02.
 */
public abstract class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    protected Response FormatValidMessage(BindingResult result){
        FieldError fieldError = result.getFieldError();
        String[] errors = fieldError.getDefaultMessage().split(":");

        return new Response(
                Integer.valueOf(errors[0]),
                errors[1]
        );
    }
}
