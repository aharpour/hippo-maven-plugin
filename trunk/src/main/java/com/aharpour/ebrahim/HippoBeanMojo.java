package com.aharpour.ebrahim;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

import com.aharpour.ebrahim.model.HippoBeanClass;
import com.aharpour.ebrahim.utils.ContextParameterExtractor;

/**
 * Mojo Description. @Mojo( name = "<goal-name>" ) is the minimal required
 * annotation.
 *
 * @since 7.8
 */
@Mojo(name = "generate", executionStrategy = "always", inheritByDefault = true, instantiationStrategy = InstantiationStrategy.SINGLETON, defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresDirectInvocation = true, requiresOnline = false, requiresProject = true, requiresReports = false, threadSafe = false)
@Execute(goal = "generate", phase = LifecyclePhase.GENERATE_SOURCES)
public class HippoBeanMojo extends AbstractMojo {

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
		ContextParameterExtractor contextParamExtractor = new ContextParameterExtractor(getDeploymentDescriptor());

		Map<String, HippoBeanClass> beansOnClassPath = new ClassPathBeanFinder()
				.getBeansOnClassPath(contextParamExtractor);
		Map<String, HippoBeanClass> beansInProject = new SourceCodeBeanFinder(getProjectFolder())
				.getBeansInProject(contextParamExtractor);

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

	private File getDeploymentDescriptor() {
		return new File(getProjectFolder().getAbsolutePath() + "/src/main/webapp/WEB-INF/web.xml");
	}

	private File getProjectFolder() {
		return project.getFile().getParentFile();
	}

}