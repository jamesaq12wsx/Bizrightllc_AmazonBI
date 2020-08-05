package com.analyze.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;

import com.analyze.controller.BaseController;

/**
 * 多数据切换拦截器
 * 
 * @author Administrator
 *
 */
public class DataSourceInterceptor implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {
	public static final String dataSourceUser = "dataSourceUser";
	/*public static final String dataSourceCraw = "dataSourceCraw";
	public static final String dataSourceGongJuXiang = "dataSourceGongJuXiang";*/

	// 注入request对象
//	@Autowired
//	private HttpServletRequest request;

	/**
	 * 每次执行service方法前都会调用before这个方法，判断需要选择哪个数据源
	 */
	public void before(Method m, Object[] args, Object target) throws Throwable {
		try {
			DbcontextHolder.setDbType(dataSourceUser);// 设置为主库

			String serviceName = target.getClass().getSimpleName();// service实现类的名字，如UserServiceImpl
																	// 某些需要切换数据源的service，那么可以在这里控制哦

			// 得到request对象，根据对象中的类目编码去决定当前应该执行哪个数据库。
			/*if (serviceName.equals("GongJuXiangServiceImpl")) {
				// 切换工具箱数据库
				DbcontextHolder.setDbType(dataSourceGongJuXiang);
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void after(JoinPoint point) {
		DbcontextHolder.clearContext();
	}

	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

	}

	/**
	 * 抛异常的话那就取默认源吧
	 * 
	 * @param method
	 * @param args
	 * @param target
	 * @param ex
	 * @throws Throwable
	 */
	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
		DbcontextHolder.setDbType(dataSourceUser);
	}
}
