package com.aharpour.ebrahim;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Assert;
import org.junit.Test;

public class ContextParameterExtractorTest {

	@Test
	public void test() throws MojoExecutionException {
		File deploymentDescriptor = new File(ClassLoader.getSystemResource("web.xml").getFile());
		ContextParameterExtractor contextParameterExtractor = new ContextParameterExtractor(deploymentDescriptor);
		String value = contextParameterExtractor.getContextParameter("hst-beans-annotated-classes");
		Assert.assertEquals("classpath*:nl/smile/optelec/site/beans/**/*.class", value);
	}

}
