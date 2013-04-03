package com.aharpour.ebrahim.utils;

import org.junit.Assert;
import org.junit.Test;

public class NammingUtilsTest {

	@Test
	public void capitalizeTheFirstLetterTest() {
		Assert.assertEquals("MyClass", NammingUtils.capitalizeTheFirstLetter("myClass"));
		Assert.assertEquals("M", NammingUtils.capitalizeTheFirstLetter("m"));
		Assert.assertEquals("6", NammingUtils.capitalizeTheFirstLetter("6"));
		Assert.assertEquals("", NammingUtils.capitalizeTheFirstLetter(""));
	}

	@Test
	public void decapitalizeTheFirstLetterTest() {
		Assert.assertEquals("myClass", NammingUtils.decapitalizeTheFirstLetter("myClass"));
		Assert.assertEquals("m", NammingUtils.decapitalizeTheFirstLetter("m"));
		Assert.assertEquals("6", NammingUtils.decapitalizeTheFirstLetter("6"));
		Assert.assertEquals("", NammingUtils.decapitalizeTheFirstLetter(""));
	}

	@Test
	public void removeInitialNumbersTest() {
		Assert.assertEquals("my_class", NammingUtils.removeInitialNumbers("34my_class"));
		Assert.assertEquals("my_class2", NammingUtils.removeInitialNumbers("34my_class2"));

	}

	@Test
	public void stringToClassNameTest() {
		Assert.assertEquals("MyClass", NammingUtils.stringToClassName("myClass"));
		Assert.assertEquals("MyClass", NammingUtils.stringToClassName("my_class"));
		Assert.assertEquals("MyClass", NammingUtils.stringToClassName("34my_class"));
		Assert.assertEquals("MyClass2", NammingUtils.stringToClassName("34my_class2"));
		Assert.assertEquals("My234class", NammingUtils.stringToClassName("my_%234class"));
	}

	@Test
	public void stringToMethodNameTest() {
		Assert.assertEquals("myClass", NammingUtils.stringToMethodName("myClass"));
		Assert.assertEquals("myClass", NammingUtils.stringToMethodName("my_class"));
		Assert.assertEquals("myClass", NammingUtils.stringToMethodName("34my_class"));
		Assert.assertEquals("myClass2", NammingUtils.stringToMethodName("34my_class2"));
		Assert.assertEquals("my234class", NammingUtils.stringToMethodName("my_%234class"));
	}

}
