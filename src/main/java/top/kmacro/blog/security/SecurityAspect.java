package top.kmacro.blog.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.kmacro.blog.exception.TokenException;
import top.kmacro.blog.model.vo.Response;
import top.kmacro.blog.web.WebContext;

import java.lang.reflect.Method;

/**
 * Created by Zhangkh on 2017-09-06.
 */
@Aspect
@Component
public class SecurityAspect {

    private static final String TOKEN_NAME = "X-Token";

    @Autowired
    private TokenManager tokenManager;

    @Pointcut("execution(public * top.kmacro.blog.controller..*.*(..))")
    public void security(){}

    @Around("security()")
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        // 从切点上获取目标方法
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        // 若目标方法忽略了安全性检查，则直接调用目标方法
        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
            return pjp.proceed();
        }
        // 从 request header 中获取当前 token
        String token = WebContext.getRequest().getHeader(TOKEN_NAME);
        // 检查 token 有效性
        if (StringUtils.isEmpty(token) || !tokenManager.checkToken(token)) {
            WebContext.getResponse().getWriter().write("{ \"code\": -1, \"msg\":\"Token is invalid\"}");
            return null;
        }
        // 更新 token 过期时间
        tokenManager.flushToken(token);
        // 调用目标方法
        return pjp.proceed();
    }
}
