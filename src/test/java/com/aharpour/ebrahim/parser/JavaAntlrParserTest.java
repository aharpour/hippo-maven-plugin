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
package com.aharpour.ebrahim.parser;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.model.HippoBeanClass;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class JavaAntlrParserTest {

	@Test
	public void parseTest() throws UnsupportedEncodingException {
		String path = URLDecoder.decode(ClassLoader.getSystemResource("Textdocument.txt").getPath(), "utf8");
		JavaParser parser = new JavaAntlrParser(null);
		Map<String, HippoBeanClass> result = parser.parse(new File(path));
		Assert.assertEquals(1, result.size());
		Assert.assertEquals("generated.beans.Textdocument", result.get("mavenhippoplugindemo:textdocument")
				.getFullyQualifiedName());
	}
}
