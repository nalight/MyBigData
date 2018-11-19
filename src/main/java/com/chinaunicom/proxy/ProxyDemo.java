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

		// ����һ��ʵ����ָ���ӿڵ�proxy��class����
		Class clazz = Proxy.getProxyClass(ProxyDemo.class.getClassLoader(), Collection.class);

		System.out.println(clazz);
		System.out.println(clazz.getName());
		// �鿴�ö���Ĺ��췽��
		Constructor[] con = clazz.getConstructors();

		Method[] method = clazz.getMethods();

		Class[] c = clazz.getInterfaces();
		for (Constructor temp : con) {
			System.out.println(temp);
		}

		for (Method temp : method) {
			System.out.println(temp);
		}
		for (Class temp : c) {// interface java.util.Collection ˵�������ʵ��������ӿ�
			System.out.println(temp);
		}
		// ��ȡ���췽��
		Constructor constructor = clazz.getConstructor(InvocationHandler.class);

		class myInvocationHandler implements InvocationHandler {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				// TODO Auto-generated method stub
				return null;
			}

		}
		Object proxydObj = constructor.newInstance(new myInvocationHandler());
		// ����Ӧ�÷���proxy���ǿ���ǿ��ת��Ϊ������Ķ���jdk��д�� �������µ�ǿ��ת����������ɹ��������׳� ClassCastException����
		// (Foo) proxy
		System.out.println(proxydObj);
		Collection collection = (Collection) constructor.newInstance(new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

				// System.out.println(proxy);proxy�����ɵĴ�����
				return null;
			}
		});// ����ʵ�����÷���ʱ���ڲ�����InvocationHandler.invoke();������Ҫ���´����ʵ���������Ա�����������������������
			// ����ֻ����ǿԭ�ӿڵ�ʵ���࣬
			// collection.add("test");
		Collection collection2 = (Collection) constructor.newInstance(new InvocationHandler() {
			ArrayList list = new ArrayList();

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				//��ǿ��������߼�
				//�����´������
				Object ret = method.invoke(list, args);
			
				return ret;
			}
		});

		collection2.add("test");
	}
}
