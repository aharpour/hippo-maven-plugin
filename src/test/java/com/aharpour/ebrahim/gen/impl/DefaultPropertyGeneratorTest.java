package com.aharpour.ebrahim.gen.impl;

import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.ImportRegistry;
import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer.Type;

public class DefaultPropertyGeneratorTest {

	@Test
	public void singlePropertyTest() {
		ClassReference returnType = new ClassReference(String.class);
		ImportRegistry importRegistry = new ImportRegistry();
		DefaultPropertyGenerator generator = new DefaultPropertyGenerator(Utils.mockAnalyzerResult(Type.PROPERTY,
				returnType), Utils.mockItem("mavenhippoplugindemo:title", false), importRegistry);
		Assert.assertEquals("private String title;", generator.getFragment());
	}

	@Test
	public void multipleValuePropertyTest() {
		ClassReference returnType = new ClassReference(String.class);
		ImportRegistry importRegistry = new ImportRegistry();
		DefaultPropertyGenerator generator = new DefaultPropertyGenerator(Utils.mockAnalyzerResult(Type.PROPERTY,
				returnType), Utils.mockItem("mavenhippoplugindemo:title", true), importRegistry);
		Assert.assertEquals("private List<String> title;", generator.getFragment());
	}

	@Test
	public void classRegistrationTest() {
		ClassReference returnType = new ClassReference(String.class);
		ImportRegistry importRegistry = new ImportRegistry();
		// we register the java.awt.List class first to make it use the fully
		// qualified name for the java.util.List
		importRegistry.register(new ClassReference(java.awt.List.class));

		DefaultPropertyGenerator generator = new DefaultPropertyGenerator(Utils.mockAnalyzerResult(Type.PROPERTY,
				returnType), Utils.mockItem("mavenhippoplugindemo:title", true), importRegistry);
		Assert.assertEquals("private java.util.List<String> title;", generator.getFragment());
	}

}
