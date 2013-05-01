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

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class ContextParameterExtractorTest {

	@Test
	public void test() throws MojoExecutionException, UnsupportedEncodingException {
		File deploymentDescriptor = new File(URLDecoder.decode(ClassLoader.getSystemResource("web.xml").getFile(),
				"utf-8"));
		ContextParameterExtractor contextParameterExtractor = new ContextParameterExtractor(deploymentDescriptor);
		String value = contextParameterExtractor.getContextParameter("hst-beans-annotated-classes");
		Assert.assertEquals(
				"classpath*:com/aharpour/ebrahim/beans/package1/**/*.class, classpath*:com/aharpour/ebrahim/beans/package2/**/*.class",
				value);
	}

}
