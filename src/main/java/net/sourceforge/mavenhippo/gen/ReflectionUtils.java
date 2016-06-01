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
package net.sourceforge.mavenhippo.gen;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.*;

import net.sourceforge.mavenhippo.gen.annotation.Weight;
import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.HippoBeanClass;
import net.sourceforge.mavenhippo.utils.exceptions.ReflectionExceptoin;

import org.reflections.Reflections;

/**
 * @author Ebrahim Aharpour
 * 
 */
public final class ReflectionUtils {

    private ReflectionUtils() {
    }

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
        for (String packageToSearchIn : packageToSearch.split(",")) {
            if (classLoader != null) {
                reflections = new Reflections(packageToSearchIn, classLoader);
            } else {
                reflections = new Reflections(packageToSearchIn);
            }
            Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(clazz);
            for (Class<? extends T> subtype : subTypes) {
                if (subtype != null) {
                    result.add(subtype);
                }
            }
        }
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
            throw new ReflectionExceptoin(e.getMessage(), e);
        }
    }

    public static Object instantiate(Class<? extends ClasspathAware> clazz,
            Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
        try {
            Constructor<? extends ClasspathAware> constructor = clazz.getConstructor(Map.class, Map.class);
            return constructor.newInstance(beansOnClassPath, beansInProject);
        } catch (Exception e) {
            throw new ReflectionExceptoin(e.getMessage(), e);
        }
    }

    public static Object instantiate(Class<? extends ClasspathAware> clazz,
            Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
            ClassLoader classLoader, Set<String> namespace, Map<String, ContentTypeBean> mixins) {
        try {
            Constructor<? extends ClasspathAware> constructor = clazz.getConstructor(Map.class, Map.class,
                    ClassLoader.class, Set.class, Map.class);
            return constructor.newInstance(beansOnClassPath, beansInProject, classLoader, namespace, mixins);
        } catch (Exception e) {
            throw new ReflectionExceptoin(e.getMessage(), e);
        }
    }

    @SuppressWarnings("rawtypes")
    public static class WeightedClassComparator implements Comparator<Class>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Class o1, Class o2) {
            int result;
            if (o1 != null) {
                if (o1.equals(o2)) {
                    result = 0;
                } else {
                    double differential = getWeight(o1) - getWeight(o2);
                    result = (int) (differential >= 0 ? Math.ceil(differential) + 1 : Math.floor(differential));
                }
            } else if (o2 == null) {
                result = 0;
            } else {
                result = -1;
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
