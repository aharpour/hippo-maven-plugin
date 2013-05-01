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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.gen.ClasspathAware;
import com.aharpour.ebrahim.gen.ContentTypeItemHandler;
import com.aharpour.ebrahim.gen.PackageHandler;
import com.aharpour.ebrahim.gen.impl.DefaultItemHandler;
import com.aharpour.ebrahim.gen.impl.DefaultPackageHandler;
import com.aharpour.ebrahim.gen.impl.DefaultSupperClassHandler;
import com.aharpour.ebrahim.handlers.Handler0;
import com.aharpour.ebrahim.handlers.Handler1;
import com.aharpour.ebrahim.handlers.Handler2;
import com.aharpour.ebrahim.model.HippoBeanClass;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class ReflectionUtilsTest {

	@Test
	public void getListOfHandlerClassesTest() throws InterruptedException {
		Set<Class<? extends ContentTypeItemHandler>> handlers = ReflectionUtils
				.getHandlerClasses("com.aharpour.ebrahim.handlers");
		Assert.assertArrayEquals(new Class[] { Handler2.class, Handler1.class, Handler0.class },
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
				new HashMap<String, HippoBeanClass>(), new HashMap<String, HippoBeanClass>());
		Assert.assertEquals(true, instantiate instanceof ClasspathAware);
	}

	@Test
	public void getSubclassesOfTypeTest() {
		SortedSet<Class<? extends PackageHandler>> types = ReflectionUtils
				.getSubclassesOfType("", PackageHandler.class);
		Assert.assertEquals(1, types.size());
		Assert.assertEquals(DefaultPackageHandler.class, types.iterator().next());
	}

}
