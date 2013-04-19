package com.aharpour.ebrahim.utils;

import java.lang.reflect.Constructor;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.reflections.Reflections;

import com.aharpour.ebrahim.gen.ClasspathAware;
import com.aharpour.ebrahim.gen.ContentTypeItemHandler;
import com.aharpour.ebrahim.gen.annotation.Weight;
import com.aharpour.ebrahim.gen.impl.DefaultItemHandler;
import com.aharpour.ebrahim.model.HippoBeanClass;

public class ReflectionUtils {

	public static SortedSet<Class<? extends ContentTypeItemHandler>> getHandlerClasses(String packageToSearch) {
		SortedSet<Class<? extends ContentTypeItemHandler>> result = new TreeSet<Class<? extends ContentTypeItemHandler>>(
				new WeightedClassComparator());
		Reflections reflections = new Reflections(packageToSearch);
		Set<Class<? extends ContentTypeItemHandler>> handlers = reflections.getSubTypesOf(ContentTypeItemHandler.class);
		handlers.remove(DefaultItemHandler.class);
		result.addAll(handlers);
		return result;
	}

	public static Object instantiate(Class<? extends ClasspathAware> clazz,
			Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
		try {
			Constructor<? extends ClasspathAware> constructor = clazz.getConstructor(Map.class, Map.class);
			return constructor.newInstance(beansOnClassPath, beansInProject);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@SuppressWarnings("rawtypes")
	public static class WeightedClassComparator implements Comparator<Class> {

		@Override
		public int compare(Class o1, Class o2) {
			int result;
			if (o1.equals(o2)) {
				result = 0;
			} else {
				double differential = getWeight(o1) - getWeight(o2);
				result = (int) (differential >= 0 ? Math.ceil(differential) + 1 : Math.floor(differential));
			}
			return result;
		}

		private double getWeight(Class<?> clazz) {
			double result;
			if (clazz.isAnnotationPresent(Weight.class)) {
				result = clazz.getAnnotation(Weight.class).value();
			} else {
				result = 0;
			}
			return result;
		}
	}

}
