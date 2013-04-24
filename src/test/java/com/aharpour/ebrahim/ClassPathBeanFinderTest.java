package com.aharpour.ebrahim;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.maven.plugin.MojoExecutionException;
import org.hippoecm.hst.content.beans.Node;
import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.model.HippoBeanClass;
import com.aharpour.ebrahim.utils.ContextParameterExtractor;

public class ClassPathBeanFinderTest {

	private static final String BEAN1_NODE_NAME = "namespace:node1";
	private static final String BEAN2_NODE_NAME = "namespace:node2";
	private static String BEAN1_NAME = "com.aharpour.ebrahim.beans.package1.Bean1";
	private static String BEAN2_NAME = "com.aharpour.ebrahim.beans.package2.Bean2";

	@Test
	public void beanFindingTest() throws MojoExecutionException, ClassNotFoundException, UnsupportedEncodingException {
		File deploymentDescriptor = new File(URLDecoder.decode(ClassLoader.getSystemResource("web.xml").getFile(),
				"utf8"));
		ContextParameterExtractor contextParameterExtractor = new ContextParameterExtractor(deploymentDescriptor);
		Map<String, HippoBeanClass> beansOnClassPath = new ClassPathBeanFinder()
				.getBeansOnClassPath(contextParameterExtractor);
		testBean(beansOnClassPath, BEAN1_NODE_NAME, BEAN1_NAME);
		testBean(beansOnClassPath, BEAN2_NODE_NAME, BEAN2_NAME);
	}

	private void testBean(Map<String, HippoBeanClass> beansOnClassPath, String nodeName, String beanName)
			throws ClassNotFoundException {
		HippoBeanClass bean1 = beansOnClassPath.get(nodeName);
		Assert.assertNotNull(bean1);
		Class<?> bean1Class = Class.forName(bean1.getFullyQualifiedName());

		Assert.assertEquals(beanName, bean1Class.getName());
		Node annotation = bean1Class.getAnnotation(Node.class);
		Assert.assertNotNull(annotation);
		Assert.assertEquals(nodeName, annotation.jcrType());
	}

}
