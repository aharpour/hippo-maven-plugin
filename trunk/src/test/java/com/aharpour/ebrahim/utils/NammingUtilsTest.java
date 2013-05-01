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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ebrahim Aharpour
 * 
 */
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
