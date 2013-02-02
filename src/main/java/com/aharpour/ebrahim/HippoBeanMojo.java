package com.aharpour.ebrahim;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.InstantiationStrategy;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.util.ClasspathResourceScanner;
import org.hippoecm.hst.util.ObjectConverterUtils;
import org.xml.sax.SAXException;

/**
 * Mojo Description. @Mojo( name = "<goal-name>" ) is the minimal required
 * annotation.
 *
 * @since 7.8
 */
@Mojo(name = "generate", executionStrategy = "always", inheritByDefault = true, instantiationStrategy = InstantiationStrategy.SINGLETON, defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresDirectInvocation = true, requiresOnline = false, requiresProject = true, requiresReports = false, threadSafe = false)
@Execute(goal = "generate", phase = LifecyclePhase.GENERATE_SOURCES)
public class HippoBeanMojo extends AbstractMojo {
	private static final String BEANS_ANNOTATED_CLASSES_PARAM = "hst-beans-annotated-classes";

	private static final String BEANS_ANNOTATED_CLASSES_CONF_PARAM_ERROR_MSG = "Please check HST-2 Content Beans Annotation configuration as servlet context parameter.\n"
			+ "You can set a servlet context parameter named 'hst-beans-annotated-classes' with xml or classes location filter.\n"
			+ "For example, '/WEB-INF/beans-annotated-classes.xml' or 'classpath*:org/examples/beans/**/*.class'";

	/**
	 * @since 7.8
	 */
	@Parameter(alias = "myAlias", property = "a.property", defaultValue = "an expression with ${variables} eventually", readonly = true, required = false)
	private String parameter;

	@Component
	private MavenSession session;

	@Component
	private MavenProject project;

	@Component
	private MojoExecution mojo;

	@Component
	private Settings settings;

	@Override
	public void execute() throws MojoExecutionException {
		ContextParameterExtractor contextParameterExtractor = new ContextParameterExtractor(getDeploymentDescriptor());
		Map<String, HippoBeanClass> beansOnClassPath = getBeansOnClassPath(contextParameterExtractor);
		Map<String, HippoBeanClass> beansInProject = getBeansInProject(contextParameterExtractor);
		generateBeans(beansOnClassPath, beansInProject);

	}

	private void generateBeans(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
		Map<String, HippoBeanClass> toBeGenerated = getBeansToBeGenerate();

		for (Iterator<String> nodeTypeIterator = toBeGenerated.keySet().iterator(); nodeTypeIterator.hasNext();) {
			String nodeType = nodeTypeIterator.next();
			generateBean(toBeGenerated.get(nodeType));
		}

	}

	private void generateBean(HippoBeanClass hippoBeanClass) {
		// TODO Auto-generated method stub

	}

	private Map<String, HippoBeanClass> getBeansToBeGenerate() {
		Map<String, HippoBeanClass> result = new HashMap<String, HippoBeanClass>();
		// TODO Auto-generated method stub
		return result;
	}

	private Map<String, HippoBeanClass> getBeansInProject(ContextParameterExtractor contextParameterExtractor) {
		Map<String, HippoBeanClass> result = new HashMap<String, HippoBeanClass>();
		// TODO Auto-generated method stub
		return result;
	}

	private Map<String, HippoBeanClass> getBeansOnClassPath(ContextParameterExtractor contextParameterExtractor)
			throws MojoExecutionException {
		try {
			Map<String, HippoBeanClass> result = new HashMap<String, HippoBeanClass>();
			String contextParameter = contextParameterExtractor.getContextParameter(BEANS_ANNOTATED_CLASSES_PARAM);
			List<Class<? extends HippoBean>> annotatedClasses = getAnnotatedClasses(contextParameter);
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

	private List<Class<? extends HippoBean>> getAnnotatedClasses(String contextParameter) throws IOException,
			SAXException, ParserConfigurationException, MojoExecutionException {
		List<Class<? extends HippoBean>> annotatedClasses;
		if (contextParameter.startsWith("classpath*:")) {
			ClasspathResourceScanner scanner = MetadataReaderClasspathResourceScanner.newInstance();
			annotatedClasses = ObjectConverterUtils.getAnnotatedClasses(scanner,
					StringUtils.split(contextParameter, ", \t\r\n"));
		} else {
			URL xmlConfURL = ClassLoader.getSystemClassLoader().getResource(contextParameter);
			if (xmlConfURL == null) {
				throw new MojoExecutionException(BEANS_ANNOTATED_CLASSES_CONF_PARAM_ERROR_MSG);
			}
			annotatedClasses = ObjectConverterUtils.getAnnotatedClasses(xmlConfURL);
		}
		return annotatedClasses;
	}

	private File getDeploymentDescriptor() {
		return new File(project.getFile().getParentFile().getAbsolutePath() + "/src/main/webapp/WEB-INF/web.xml");
	}

	public static class HippoBeanClass {
		public final String packageName;
		public final String name;
		public final String nodeType;

		public HippoBeanClass(String packageName, String name, String nodeType) {
			this.packageName = packageName;
			this.name = name;
			this.nodeType = nodeType;
		}

		public String getFullyQualifiedName() {
			return packageName + "." + name;
		}

	}

}