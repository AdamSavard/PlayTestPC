package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * Use this to give classes the ability to get and set properties from strings
 */
public class Model {
	
	public Object get(String propertyName){
		if(propertyName == null || propertyName.length() < 1) return null;
		Class<?> c = this.getClass();
		String getMethodName = "get" + propertyName;
		Method method;
		try {
			method = c.getDeclaredMethod(getMethodName);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
		if(method == null) return null;
		Object output = null;
		try {
			output = method.invoke(this);
		} catch (IllegalAccessException | IllegalArgumentException| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
		return output;
	}

	public Object set(String propertyName, Object value){
		if(propertyName == null || propertyName.length() < 1) return null;
		Class<?> c = this.getClass();
		String setMethodName = "set" + propertyName;
	    Method method;
		try {
			method = c.getMethod(setMethodName, value.getClass());
		} catch (NoSuchMethodException | SecurityException e) {
			System.out.println("Model.set error: " + c + ", " + setMethodName + ", " + value.getClass());
			return null;
		}
		if(method == null) return null;
		Object output = null;
		try {
			output = method.invoke(this, value);
		} catch (IllegalAccessException | IllegalArgumentException| InvocationTargetException e) {
			System.out.println("Model.set error: " + c + ", " + setMethodName + ", " + value.getClass());
			e.printStackTrace();
			return null;
		}
		return output;
	}


}
