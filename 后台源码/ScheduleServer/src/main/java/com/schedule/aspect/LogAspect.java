package com.schedule.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @version 创建时间：2017年4月22日 下午9:07:08
 *
 */
public class LogAspect {
	
	Logger logger = Logger.getLogger(LogAspect.class);
	String logStr = null;

	public void logBefore(JoinPoint jp) {
		logStr = jp.getTarget().getClass().getName() + "类的" + jp.getSignature().getName() + "方法开始执行------start------";
		logger.info(logStr);
	}

	public Object logArround(ProceedingJoinPoint point) throws Throwable {
		Object result = null;
		try {
			result = point.proceed();
		} catch (Throwable e) {
			logStr="方法："+point.getTarget().getClass()+"."+point.getSignature().getName()+"()";
            logStr=logStr+"错误信息如下：["+e+"]";
            logger.error(logStr);
            throw e;
		}
		return result;
	}

	public void logAfter(JoinPoint jp) {
		logStr = jp.getTarget().getClass().getName() + "类的" + jp.getSignature().getName() + "方法执行结束------End------";
		logger.info(logStr);
	}


	
}
