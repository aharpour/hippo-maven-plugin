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
package net.sourceforge.mavenhippo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import net.sourceforge.mavenhippo.model.HippoBeanClass;
import net.sourceforge.mavenhippo.utils.ContextParameterExtractor;

import org.apache.maven.plugin.MojoExecutionException;
import org.hippoecm.hst.content.beans.Node;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class ClassPathBeanFinderTest {

	private static final String BEAN1_NODE_NAME = "namespace:node1";
	private static final String BEAN2_NODE_NAME = "namespace:node2";
	private static String BEAN1_NAME = "net.sourceforge.mavenhippo.beans.package1.Bean1";
	private static String BEAN2_NAME = "net.sourceforge.mavenhippo.beans.package2.Bean2";

	@Test
	public void beanFindingTest() throws MojoExecutionException, ClassNotFoundException, UnsupportedEncodingException {
		File deploymentDescriptor = new File(URLDecoder.decode(ClassLoader.getSystemResource("web.xml").getFile(),
				"utf8"));
		ContextParameterExtractor contextParameterExtractor = new ContextParameterExtractor(deploymentDescriptor);
		Map<String, HippoBeanClass> beansOnClassPath = new ClassPathBeanFinder(getClass().getClassLoader())
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
