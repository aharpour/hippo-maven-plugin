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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import net.sourceforge.mavenhippo.gen.ClasspathAware;
import net.sourceforge.mavenhippo.gen.ContentTypeItemHandler;
import net.sourceforge.mavenhippo.gen.DefaultItemHandler;
import net.sourceforge.mavenhippo.gen.DefaultPackageHandler;
import net.sourceforge.mavenhippo.gen.DefaultSupperClassHandler;
import net.sourceforge.mavenhippo.gen.PackageHandler;
import net.sourceforge.mavenhippo.gen.ReflectionUtils;
import net.sourceforge.mavenhippo.handlers.Handler0;
import net.sourceforge.mavenhippo.handlers.Handler1;
import net.sourceforge.mavenhippo.handlers.Handler2;
import net.sourceforge.mavenhippo.model.HippoBeanClass;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class ReflectionUtilsTest {

	@Test
	public void getListOfHandlerClassesTest() throws InterruptedException {
		Set<Class<? extends ContentTypeItemHandler>> handlers = ReflectionUtils.getHandlerClasses(
				"net.sourceforge.mavenhippo.handlers", null);
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

}
