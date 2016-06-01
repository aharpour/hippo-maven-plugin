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

import java.util.*;

import net.sourceforge.mavenhippo.gen.*;
import net.sourceforge.mavenhippo.handlers.*;
import net.sourceforge.mavenhippo.model.HippoBeanClass;

import org.junit.Assert;
import org.junit.Test;


import static org.junit.Assert.assertArrayEquals;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class ReflectionUtilsTest {

	Class[] supperClassHandlers = {SuperClassHandler4.class, SuperClassHandler3.class, SuperClassHandler2.class, SuperClassHandler1.class};
	@Test
	public void getListOfHandlerClassesTest() throws InterruptedException {
		Set<Class<? extends ContentTypeItemHandler>> handlers = ReflectionUtils.getHandlerClasses(
				"net.sourceforge.mavenhippo.handlers", null);
		assertArrayEquals(new Class[] { Handler2.class, Handler1.class, Handler0.class },
				handlers.toArray(new Class[3]));
	}

	@Test
	public void instantiateTestHandler() {
		Object instantiate = ReflectionUtils.instantiate(DefaultItemHandler.class,
				new HashMap<String, HippoBeanClass>(), new HashMap<String, HippoBeanClass>(), new HashSet<String>(),
				null);
		Assert.assertEquals(true, instantiate instanceof ClasspathAware);
	}

	@Test
	public void instantiateTest() {
		Object instantiate = ReflectionUtils.instantiate(DefaultSupperClassHandler.class,
				new HashMap<String, HippoBeanClass>(), new HashMap<String, HippoBeanClass>(), null, null, null);
		Assert.assertEquals(true, instantiate instanceof ClasspathAware);
	}

	@Test
	public void getSubclassesOfTypeTest() {
		SortedSet<Class<? extends PackageHandler>> types = ReflectionUtils.getSubclassesOfType("net",
				PackageHandler.class, null);
		Assert.assertEquals(1, types.size());
		Assert.assertEquals(DefaultPackageHandler.class, types.iterator().next());
	}

	@Test
	public void checkWeightSorting1(){
		SortedSet<Class<? extends SupperClassHandler>> result = new TreeSet<>(new ReflectionUtils.WeightedClassComparator());
		result.add(SuperClassHandler1.class);
		result.add(SuperClassHandler2.class);
		result.add(SuperClassHandler3.class);
		result.add(SuperClassHandler4.class);


		assertArrayEquals(result.toArray(),supperClassHandlers);
	}

	@Test
	public void checkWeightSorting2(){
		SortedSet<Class<? extends SupperClassHandler>> result = new TreeSet<>(new ReflectionUtils.WeightedClassComparator());
		result.add(SuperClassHandler1.class);
		result.add(SuperClassHandler4.class);
		result.add(SuperClassHandler3.class);
		result.add(SuperClassHandler2.class);

		assertArrayEquals(result.toArray(),supperClassHandlers);
	}

	@Test
	public void checkWeightSorting3(){
		SortedSet<Class<? extends SupperClassHandler>> result = new TreeSet<>(new ReflectionUtils.WeightedClassComparator());
		result.add(SuperClassHandler4.class);
		result.add(SuperClassHandler2.class);
		result.add(SuperClassHandler3.class);
		result.add(SuperClassHandler1.class);

		assertArrayEquals(result.toArray(),supperClassHandlers);
	}

	@Test
	public void checkWeightSorting4(){
		SortedSet<Class<? extends SupperClassHandler>> result = new TreeSet<>(new ReflectionUtils.WeightedClassComparator());
		result.add(SuperClassHandler3.class);
		result.add(SuperClassHandler1.class);
		result.add(SuperClassHandler2.class);
		result.add(SuperClassHandler4.class);

		assertArrayEquals(result.toArray(),supperClassHandlers);
	}

}
