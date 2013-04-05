package com.aharpour.ebrahim.gen.impl;

import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.ImportRegistry;

public class DefaultPropertyGeneratorTest {

	
	
	
	@Test
	public void getFragmentTest() {
		ClassReference refString = new ClassReference(String.class);
		DefaultPropertyGenerator generator = new DefaultPropertyGenerator(refString, "title");
		Assert.assertEquals("private java.lang.String title;", generator.getFragment());
		ImportRegistry importRegistry = new ImportRegistry();
		importRegistry.register(refString);
		Assert.assertEquals("private String title;", generator.getFragment());
	}
	
}
