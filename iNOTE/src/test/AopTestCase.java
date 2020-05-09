package test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.note.aop.DemoBean;

public class AopTestCase {
	ApplicationContext ctx;
	@Before
	public void init(){
		ctx = new ClassPathXmlApplicationContext("spring-aop.xml");
	}
	@Test
	public void test(){
		DemoBean db = ctx.getBean("demoBean",DemoBean.class);
		db.test(null);
	}
}
