package com.aharpour.ebrahim.parser;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.model.HippoBeanClass;

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
