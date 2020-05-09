package cn.tedu.note.aop;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//Aspect 切面 -- 横切面

@Component
@Aspect //配合 <aop:aspectj-autoproxy/>
public class DemoAspect {
	/**
	 * 切入点：cn.tedu.note.web.AccountController
	 *  	的所有方法
	 * 通知：@Before 在调用方法之前时候执行
	 */
	@Before("execution(* randomCode(..))")

	public void hello(){
		System.out.println("Hello World!");
	}
}







