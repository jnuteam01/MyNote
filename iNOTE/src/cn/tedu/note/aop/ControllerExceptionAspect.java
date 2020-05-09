package cn.tedu.note.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import cn.tedu.note.web.JsonResult;

/**
 * 利用Spring AOP 统一处理控制器异常
 */
@Component
@Aspect //控制器异常处理切面
public class ControllerExceptionAspect {
	
	@Around("bean(accountController)||"
			+ "bean(notebookController)")
	public Object process( 
			ProceedingJoinPoint joinPoint){
		try {
			System.out.println("开始调用控制器...");
			//调用实际的控制器
			Object val=joinPoint.proceed();
			System.out.println("正常结束");
			return val;
		} catch (Throwable e) {
			//目标方法执行出现异常
			e.printStackTrace();
			System.out.println("异常..");
			return new JsonResult(JsonResult.ERROR,e.getMessage());
		}
	}
}





