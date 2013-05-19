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
package net.sourceforge.mavenhippo.gen.impl;

import net.sourceforge.mavenhippo.gen.impl.DefaultPackageGenerator;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ebrahim Aharpour
 * 
 */
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
