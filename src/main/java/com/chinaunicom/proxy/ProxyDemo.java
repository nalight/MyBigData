package com.chinaunicom.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;

public class ProxyDemo {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {

		// 返回一个实现了指定接口的proxy的class对象
		Class clazz = Proxy.getProxyClass(ProxyDemo.class.getClassLoader(), Collection.class);

		System.out.println(clazz);
		System.out.println(clazz.getName());
		// 查看该对象的构造方法
		Constructor[] con = clazz.getConstructors();

		Method[] method = clazz.getMethods();

		Class[] c = clazz.getInterfaces();
		for (Constructor temp : con) {
			System.out.println(temp);
		}

		for (Method temp : method) {
			System.out.println(temp);
		}
		for (Class temp : c) {// interface java.util.Collection 说明这个类实现了这个接口
			System.out.println(temp);
		}
		// 获取构造方法
		Constructor constructor = clazz.getConstructor(InvocationHandler.class);

		class myInvocationHandler implements InvocationHandler {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				// TODO Auto-generated method stub
				return null;
			}

		}
		Object proxydObj = constructor.newInstance(new myInvocationHandler());
		// 这里应该返回proxy但是可以强制转换为被代理的对象，jdk中写明 并且以下的强制转换操作将会成功（而不抛出 ClassCastException）：
		// (Foo) proxy
		System.out.println(proxydObj);
		Collection collection = (Collection) constructor.newInstance(new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

				// System.out.println(proxy);proxy是生成的代理类
				return null;
			}
		});// 代理实例调用方法时，内部调用InvocationHandler.invoke();我们需要吧呗代理的实例传进来以便代理类调用他的真正方法。
			// 代理只是增强原接口的实现类，
			// collection.add("test");
		Collection collection2 = (Collection) constructor.newInstance(new InvocationHandler() {
			ArrayList list = new ArrayList();

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				//增强代理类的逻辑
				//调用呗代理的类
				Object ret = method.invoke(list, args);
			
				return ret;
			}
		});

		collection2.add("test");
	}
}
