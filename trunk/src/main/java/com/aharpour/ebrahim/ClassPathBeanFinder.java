package com.aharpour.ebrahim;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.util.ClasspathResourceScanner;
import org.hippoecm.hst.util.ObjectConverterUtils;
import org.xml.sax.SAXException;

import com.aharpour.ebrahim.model.HippoBeanClass;
import com.aharpour.ebrahim.utils.Constants;
import com.aharpour.ebrahim.utils.ContextParameterExtractor;
import com.aharpour.ebrahim.utils.MetadataReaderClasspathResourceScanner;

public class ClassPathBeanFinder {

	private static final String BEANS_ANNOTATED_CLASSES_CONF_PARAM_ERROR_MSG = "Please check HST-2 Content Beans Annotation configuration as servlet context parameter.\n"
			+ "You can set a servlet context parameter named 'hst-beans-annotated-classes' with xml or classes location filter.\n"
			+ "For example, '/WEB-INF/beans-annotated-classes.xml' or 'classpath*:org/examples/beans/**/*.class'";

	public Map<String, HippoBeanClass> getBeansOnClassPath(ContextParameterExtractor contextParameterExtractor)
			throws MojoExecutionException {
		try {
			Map<String, HippoBeanClass> result = new HashMap<String, HippoBeanClass>();
			String beansAnnotatedClassesParam = contextParameterExtractor
					.getContextParameter(Constants.ContextParameters.BEANS_ANNOTATED_CLASSES_PARAM);
			List<Class<? extends HippoBean>> annotatedClasses = getAnnotatedClasses(beansAnnotatedClassesParam);
			for (Class<? extends HippoBean> clazz : annotatedClasses) {
				if (clazz.isAnnotationPresent(Node.class)) {
					Node annotation = clazz.getAnnotation(Node.class);
					String nodeType = annotation.jcrType();
					result.put(nodeType, new HippoBeanClass(clazz.getPackage().getName(), clazz.getSimpleName(),
							nodeType));
				}
			}
			return result;
		} catch (Exception e) {
			throw new MojoExecutionException(
					"The following exception was throw while trying to get annotated classes.", e);
		}
	}

	private List<Class<? extends HippoBean>> getAnnotatedClasses(String beansAnnotatedClassesParam) throws IOException,
			SAXException, ParserConfigurationException, MojoExecutionException {
		List<Class<? extends HippoBean>> annotatedClasses;
		if (beansAnnotatedClassesParam.startsWith("classpath*:")) {
			ClasspathResourceScanner scanner = MetadataReaderClasspathResourceScanner.newInstance();
			annotatedClasses = ObjectConverterUtils.getAnnotatedClasses(scanner,
					StringUtils.split(beansAnnotatedClassesParam, ", \t\r\n"));
		} else {
			URL xmlConfURL = ClassLoader.getSystemClassLoader().getResource(beansAnnotatedClassesParam);
			if (xmlConfURL == null) {
				throw new MojoExecutionException(BEANS_ANNOTATED_CLASSES_CONF_PARAM_ERROR_MSG);
			}
			annotatedClasses = ObjectConverterUtils.getAnnotatedClasses(xmlConfURL);
		}
		return annotatedClasses;
	}

}
