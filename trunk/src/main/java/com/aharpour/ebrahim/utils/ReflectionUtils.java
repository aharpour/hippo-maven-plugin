/*
 *    Copyright 2013 Ebrahim Aharpour
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   
 *   Partially sponsored by Smile B.V
 */
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
import com.aharpour.ebrahim.gen.PackageHandler;
import com.aharpour.ebrahim.gen.annotation.Weight;
import com.aharpour.ebrahim.gen.impl.DefaultItemHandler;
import com.aharpour.ebrahim.model.HippoBeanClass;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class ReflectionUtils {

	public static SortedSet<Class<? extends ContentTypeItemHandler>> getHandlerClasses(String packageToSearch) {
		SortedSet<Class<? extends ContentTypeItemHandler>> handlers = getSubclassesOfType(packageToSearch,
				ContentTypeItemHandler.class);
		handlers.remove(DefaultItemHandler.class);
		return handlers;
	}

	public static <T> SortedSet<Class<? extends T>> getSubclassesOfType(String packageToSearch, Class<T> clazz) {
		SortedSet<Class<? extends T>> result = new TreeSet<Class<? extends T>>(new WeightedClassComparator());
		Reflections reflections = new Reflections(packageToSearch);
		Set<Class<? extends T>> classes = reflections.getSubTypesOf(clazz);
		result.addAll(classes);
		return result;
	}

	public static Object instantiate(Class<? extends ContentTypeItemHandler> clazz,
			Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
			Set<String> namespaces, PackageHandler packageHandler) {
		try {
			Constructor<? extends ContentTypeItemHandler> constructor = clazz.getConstructor(Map.class, Map.class,
					Set.class, PackageHandler.class);
			return constructor.newInstance(beansOnClassPath, beansInProject, namespaces, packageHandler);
		} catch (Exception e) {
			throw new RuntimeException();
		}
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
