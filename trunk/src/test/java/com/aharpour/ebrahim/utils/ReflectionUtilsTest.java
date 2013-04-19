package com.aharpour.ebrahim.utils;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.gen.ContentTypeItemHandler;
import com.aharpour.ebrahim.handlers.Handler0;
import com.aharpour.ebrahim.handlers.Handler1;
import com.aharpour.ebrahim.handlers.Handler2;

public class ReflectionUtilsTest {

	@Test
	public void getListOfHandlerClassesTest() throws InterruptedException {
		Set<Class<? extends ContentTypeItemHandler>> handlers = ReflectionUtils
				.getListOfHandlerClasses("com.aharpour.ebrahim.handlers");
		Assert.assertArrayEquals(new Class[] { Handler2.class, Handler1.class, Handler0.class },
				handlers.toArray(new Class[3]));

	}

}
