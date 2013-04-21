package com.aharpour.ebrahim.gen.impl;

import org.junit.Assert;
import org.junit.Test;

public class DefaultPackageGeneratorTest {

	@Test
	public void basicTest() {
		DefaultPackageGenerator packageGenerator = new DefaultPackageGenerator(new String[] { "generated", "beans" });
		Assert.assertArrayEquals(new String[] { "generated", "beans" }, packageGenerator.getPackage());
		Assert.assertEquals("generated.beans", packageGenerator.getPackageName());
		Assert.assertEquals("package generated.beans;", packageGenerator.getFragment());
	}

	@Test
	public void emptyPackageTest() {
		DefaultPackageGenerator packageGenerator = new DefaultPackageGenerator(new String[] {});
		Assert.assertArrayEquals(new String[] {}, packageGenerator.getPackage());
		Assert.assertEquals("", packageGenerator.getPackageName());
		Assert.assertEquals("", packageGenerator.getFragment());
	}
}
