package com.aharpour.ebrahim.utils;

import java.util.HashMap;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.gen.ClasspathAware;
import com.aharpour.ebrahim.gen.ContentTypeItemHandler;
import com.aharpour.ebrahim.gen.impl.DefaultItemHandler;
import com.aharpour.ebrahim.handlers.Handler0;
import com.aharpour.ebrahim.handlers.Handler1;
import com.aharpour.ebrahim.handlers.Handler2;
import com.aharpour.ebrahim.model.HippoBeanClass;

public class ReflectionUtilsTest {

	@Test
	public void getListOfHandlerClassesTest() throws InterruptedException {
		Set<Class<? extends ContentTypeItemHandler>> handlers = ReflectionUtils
				.getHandlerClasses("com.aharpour.ebrahim.handlers");
		Assert.assertArrayEquals(new Class[] { Handler2.class, Handler1.class, Handler0.class },
				handlers.toArray(new Class[3]));
	}

	@Test
	public void instantiateTest() {
		Object instantiate = ReflectionUtils.instantiate(DefaultItemHandler.class,
				new HashMap<String, HippoBeanClass>(), new HashMap<String, HippoBeanClass>());
		Assert.assertEquals(true, instantiate instanceof ClasspathAware);
	}

}
