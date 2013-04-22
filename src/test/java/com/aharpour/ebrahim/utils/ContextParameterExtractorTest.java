package com.aharpour.ebrahim.utils;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Assert;
import org.junit.Test;

public class ContextParameterExtractorTest {

	@Test
	public void test() throws MojoExecutionException {
		File deploymentDescriptor = new File(ClassLoader.getSystemResource("web.xml").getFile().replace("%20", " "));
		ContextParameterExtractor contextParameterExtractor = new ContextParameterExtractor(deploymentDescriptor);
		String value = contextParameterExtractor.getContextParameter("hst-beans-annotated-classes");
		Assert.assertEquals(
				"classpath*:com/aharpour/ebrahim/beans/package1/**/*.class, classpath*:com/aharpour/ebrahim/beans/package2/**/*.class",
				value);
	}

}
