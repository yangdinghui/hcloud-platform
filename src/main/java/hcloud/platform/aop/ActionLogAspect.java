package hcloud.platform.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * description 描述这个类的主要功能、用途
 * 创建时间 2019/9/1
 *
 * @author 杨丁辉
 */
@Slf4j
@Aspect //声明一个切面
@Component
public class ActionLogAspect {

    private LogRecord logRecord;

    public ActionLogAspect() {
        log.info("加载日志拦截类");
        logRecord = new LogRecord();
    }

    //    @Pointcut(
//            "(@annotation(hcloud.platform.aop.ActionLog) || execution(public * hcloud.platform.modules..*.*(..)))"
//                    + " && !execution(public * hcloud.platform.modules.plalog.controller.PlaLogController.list())"
//                    + " && !execution(public * hcloud.platform.modules.sendmail.*.*(..))"
//                    + " && !execution(public * hcloud.platform.modules.server.controller.TestServer.pushPost(..))"
//                    + " && !execution(public * hcloud.platform.modules.sendmail.controller.MailController.sendMessageScheduled())"
//    )
    @Pointcut("@annotation(hcloud.platform.aop.ActionLog)")
    private void annotationPointcut() {
    }

    @Around("annotationPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        return logRecord.doRecord(joinPoint);
    }
}
