package logic.utilities;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class SameValuesChecker {
	
	private SameValuesChecker() {
		throw new IllegalStateException("Utility class");
	}

	public static <T> boolean haveSamePropertyValues(Class<T> type, T t1, T t2) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		
		for(PropertyDescriptor pd: beanInfo.getPropertyDescriptors()) {
			Method m = pd.getReadMethod();
			Object o1 = m.invoke(t1);
			Object o2 = m.invoke(t2);
			if(!Objects.deepEquals(o1, o2))
				return false;
		}
		
		return true;
	}
	
}
