package com.aharpour.ebrahim.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Assert;
import org.junit.Test;

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
