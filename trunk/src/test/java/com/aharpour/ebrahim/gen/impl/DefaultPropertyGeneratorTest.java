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
package com.aharpour.ebrahim.gen.impl;

import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.ImportRegistry;
import com.aharpour.ebrahim.gen.Utils;
import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer.Type;

/**
 * @author Ebrahim Aharpour
 * 
 */
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
		Assert.assertEquals("private String[] title;", generator.getFragment());
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
		Assert.assertEquals("private String[] title;", generator.getFragment());
	}

}
