package hcloud.platform.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * description 描述这个类的主要功能、用途
 * 创建时间 2019/9/1
 *
 * @author 杨丁辉
 */
@Component
public class LogRecord {

    private static final Logger log = LoggerFactory.getLogger(LogRecord.class);

    public Object doRecord(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        String token = request.getHeader("token");
        String type = request.getHeader("content-type");
        log.info("===================================");
        log.info("==>requestURI：{}", requestURI);
        log.info("==>token：{}", token);
        log.info("==>type：{}", type);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("==>请求方法：{}", method.getName());
        Object obj = joinPoint.proceed();
        String response = JSONObject.toJSONString(obj);
        log.info("<== 响应内容：{}", response);
        return obj;
    }
}
