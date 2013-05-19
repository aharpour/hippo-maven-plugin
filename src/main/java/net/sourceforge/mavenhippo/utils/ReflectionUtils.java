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
package net.sourceforge.mavenhippo.utils;

import java.lang.reflect.Constructor;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.mavenhippo.gen.ClasspathAware;
import net.sourceforge.mavenhippo.gen.ContentTypeItemHandler;
import net.sourceforge.mavenhippo.gen.PackageHandler;
import net.sourceforge.mavenhippo.gen.annotation.Weight;
import net.sourceforge.mavenhippo.gen.impl.DefaultItemHandler;
import net.sourceforge.mavenhippo.model.HippoBeanClass;

import org.reflections.Reflections;


/**
 * @author Ebrahim Aharpour
 * 
 */
public class ReflectionUtils {

	public static SortedSet<Class<? extends ContentTypeItemHandler>> getHandlerClasses(String packageToSearch,
			ClassLoader classLoader) {
		SortedSet<Class<? extends ContentTypeItemHandler>> handlers = getSubclassesOfType(packageToSearch,
				ContentTypeItemHandler.class, classLoader);
		handlers.remove(DefaultItemHandler.class);
		return handlers;
	}

	public static <T> SortedSet<Class<? extends T>> getSubclassesOfType(String packageToSearch, Class<T> clazz,
			ClassLoader classLoader) {
		SortedSet<Class<? extends T>> result = new TreeSet<Class<? extends T>>(new WeightedClassComparator());
		Reflections reflections;
		if (classLoader != null) {
			reflections = new Reflections(packageToSearch, classLoader);
		} else {
			reflections = new Reflections(packageToSearch);

		}
		result.addAll(reflections.getSubTypesOf(clazz));
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

	public static Object instantiate(Class<? extends ClasspathAware> clazz,
			Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
			ClassLoader classLoader, Set<String> namespace) {
		try {
			Constructor<? extends ClasspathAware> constructor = clazz.getConstructor(Map.class, Map.class,
					ClassLoader.class, Set.class);
			return constructor.newInstance(beansOnClassPath, beansInProject, classLoader, namespace);
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
