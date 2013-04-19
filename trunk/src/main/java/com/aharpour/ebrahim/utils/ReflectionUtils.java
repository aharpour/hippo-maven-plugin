package com.aharpour.ebrahim.utils;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.reflections.Reflections;

import com.aharpour.ebrahim.gen.ContentTypeItemHandler;
import com.aharpour.ebrahim.gen.annotation.Weight;
import com.aharpour.ebrahim.gen.impl.DefaultItemHandler;

public class ReflectionUtils {

	public static SortedSet<Class<? extends ContentTypeItemHandler>> getListOfHandlerClasses(String packageToSearch) {
		SortedSet<Class<? extends ContentTypeItemHandler>> result = new TreeSet<Class<? extends ContentTypeItemHandler>>(
				new Comparator<Class<? extends ContentTypeItemHandler>>() {

					@Override
					public int compare(Class<? extends ContentTypeItemHandler> o1,
							Class<? extends ContentTypeItemHandler> o2) {
						int result;
						if (o1.equals(o2)) {
							result = 0;
						} else {
							double differential = getWeight(o1) - getWeight(o2);
							result = (int) (differential >= 0 ? Math.ceil(differential) + 1 : Math.floor(differential));
						}
						return result;
					}
				});
		Reflections reflections = new Reflections(packageToSearch);
		Set<Class<? extends ContentTypeItemHandler>> handlers = reflections.getSubTypesOf(ContentTypeItemHandler.class);
		handlers.remove(DefaultItemHandler.class);
		result.addAll(handlers);
		return result;
	}

	private static double getWeight(Class<? extends ContentTypeItemHandler> clazz) {
		double result;
		if (clazz.isAnnotationPresent(Weight.class)) {
			result = clazz.getAnnotation(Weight.class).value();
		} else {
			result = 0;
		}
		return result;
	}

}
