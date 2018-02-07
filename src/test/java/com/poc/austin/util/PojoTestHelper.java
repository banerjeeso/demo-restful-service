package com.poc.austin.util;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class PojoTestHelper {

	public static void testObject(Object object) throws Exception {
		assertNotNull(object);
		Class clazz = object.getClass();
		Method[] methods = clazz.getMethods();

		for (Method method : methods) {
			if (method.getName().startsWith("hash")) {
				testHashMethods(object, method);
				continue;
			}
			if (method.getName().startsWith("equal")) {
				testEqualsMethods(object, method);
				continue;
			}
			if (method.getName().startsWith("toString")) {
				testToStringMethod(object, method);
				continue;
			}

			if (method.getName().startsWith("set") && method.getParameterTypes().length == 1
					&& Void.TYPE == method.getReturnType()) {
				testSetterMethod(object, method);
				continue;
			}

			if (method.getName().startsWith("get") && method.getParameterTypes().length == 0) {
				testGetterMethod(object, method);
				continue;
			}

			if ((method.getName().startsWith("is") || method.getName().startsWith("was") || method.getName()
					.startsWith("has")) && method.getParameterTypes().length == 0) {
				testGetterMethod(object, method);
				continue;
			}
		}
	}

	public static void testClass(Class clazz) throws Exception {
		assertNotNull(clazz);
		Object object = getInstance(clazz);
		if (object != null) {
			testObject(object);
		}
		if (clazz.isEnum()) {
			clazz.getEnumConstants();
		}
	}

	private static Object getInstance(Class clazz) throws NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException, Exception {
		if (!clazz.isEnum() && !Modifier.isAbstract(clazz.getModifiers())) {
			Constructor<Class> constructor = clazz.getDeclaredConstructor(new Class[0]);
			constructor.setAccessible(true);
			Object instance = constructor.newInstance(new Object[0]);
			return instance;
		}
		return null;
	}

	public static void testEqualsHashcodeMethod(Class clazz) throws Exception {
		Method[] methods = clazz.getMethods();
		Object object = clazz.newInstance();
		for (Method method : methods) {
			if (method.getName().startsWith("equal")) {
				testEqualsMethods(object, method);
				continue;
			}
			if (method.getName().startsWith("hash")) {
				testHashMethods(object, method);
				continue;
			}
			if (method.getName().startsWith("toString")) {
				testToStringMethod(object, method);
				continue;
			}

		}
	}

	private static void testEqualsMethods(Object object, Method method) throws Exception {
		assertTrue((Boolean) method.invoke(object, object));
		setupDefaulValues(object);
		method.invoke(object, getInstance(object.getClass()));
		method.invoke(getInstance(object.getClass()), object);
		Object object2 = getInstance(object.getClass());
		BeanUtils.copyProperties(object, object2);
		method.invoke(object, object2);
		assertFalse((Boolean) method.invoke(object, new Object()));
		Object object3 = null;
		assertFalse((Boolean) method.invoke(object, object3));
	}

	private static void setupDefaulValues(Object object) throws Exception {
		Method[] methods = object.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("set") && method.getParameterTypes().length == 1
					&& Void.TYPE == method.getReturnType()) {
				testSetterMethod(object, method);
				continue;
			}
		}
	}

	private static void testHashMethods(Object object, Method method) throws Exception {
		int result = (Integer) method.invoke(object);
		assertTrue(result > Integer.MIN_VALUE);
	}

	private static void testToStringMethod(Object object, Method method) throws Exception {
		String result = (String) method.invoke(object, new Object[] {});
		assertTrue((result != null) && (!result.equals("")));
	}

	private static void testGetterMethod(Object object, Method method) throws Exception {
		method.invoke(object);
	}

	private static void testSetterMethod(Object object, Method method) throws Exception {
		Class param = method.getParameterTypes()[0];

		if (String.class == param) {
			testStringSetter(object, method);
		} else if (Boolean.class == param) {
			testBooleanSetter(object, method);
		} else if (param.isAssignableFrom(List.class)) {
			testListSetter(object, method);
		} else if (BigDecimal.class == param) {
			testBigDecimalSetter(object, method);
		} else if (Calendar.class == param) {
			testCalendarSetter(object, method);
		} else if (Integer.class == param) {
			testIntegerSetter(object, method);
		} else if (Long.class == param) {
			testLongSetter(object, method);
		} else if (Float.class == param) {
			testFloatSetter(object, method);
		} else if (Double.class == param) {
			testDoubleSetter(object, method);
		} else if (Date.class == param) {
			testDateSetter(object, method);
		} else if (java.sql.Date.class == param) {
			testSqlDateSetter(object, method);
		}
	}

	private static void testIntegerSetter(Object instance, Method method) throws Exception {
		method.invoke(instance, (Object[]) new Integer[] { Integer.MIN_VALUE });
	}

	private static void testLongSetter(Object instance, Method method) throws Exception {
		method.invoke(instance, (Object[]) new Long[] { Long.MIN_VALUE });
	}

	private static void testFloatSetter(Object instance, Method method) throws Exception {
		method.invoke(instance, (Object[]) new Float[] { Float.MIN_VALUE });
	}

	private static void testDoubleSetter(Object instance, Method method) throws Exception {
		method.invoke(instance, (Object[]) new Double[] { Double.MIN_VALUE });
	}

	private static void testCalendarSetter(Object instance, Method method) throws Exception {
		method.invoke(instance, (Object[]) new Calendar[] { GregorianCalendar.getInstance() });
	}

	private static void testBigDecimalSetter(Object instance, Method method) throws Exception {
		method.invoke(instance, (Object[]) new BigDecimal[] { BigDecimal.ONE });
	}

	private static void testDateSetter(Object instance, Method method) throws Exception {
		method.invoke(instance, (Object[]) new Date[] { new Date() });
	}

	private static void testSqlDateSetter(Object instance, Method method) throws Exception {
		method.invoke(instance, (Object[]) new java.sql.Date[] { new java.sql.Date(System.currentTimeMillis()) });
	}

	private static void testListSetter(Object instance, Method method) throws Exception {
		method.invoke(instance, (Object[]) new List[] { new ArrayList() });
	}

	private static void testBooleanSetter(Object instance, Method method) throws Exception {
		method.invoke(instance, (Object[]) new Boolean[] { Boolean.TRUE });
	}

	private static void testStringSetter(Object instance, Method method) throws Exception {
		method.invoke(instance, (Object[]) new String[] { "test" });
	}


}