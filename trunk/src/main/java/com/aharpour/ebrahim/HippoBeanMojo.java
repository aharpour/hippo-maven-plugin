package com.aharpour.ebrahim;

import java.io.File;
import java.util.Map;

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

import com.aharpour.ebrahim.BeanCreator.BeanGeneratorConfig;
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

	@Parameter(alias = "namespace.location", property = "namespaceLocation", defaultValue = "${project.parent.basedir.absolutePath}/bootstrap/configuration/src/main/resources/namespaces", readonly = true, required = false)
	private String namespaceLocation;

	@Parameter(alias = "base.package", property = "basePackage", defaultValue = "generated.beans", readonly = true, required = false)
	private String basePackage;

	@Parameter(alias = "package.to.search", property = "packageToSearch", defaultValue = "", readonly = true, required = false)
	private String packageToSearch;

	@Parameter(alias = "source.root", property = "sourceRoot", defaultValue = "${project.build.directory}/generated-sources/", readonly = true, required = false)
	private File sourceRoot;

	@Parameter(required = true)
	private Map<String, String> namespaces;

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

		BeanGeneratorConfig config = new BeanGeneratorConfig(getLog(), namespaceLocation,
				parseBasePackage(basePackage), packageToSearch, sourceRoot);
		new BeanCreator(config, beansOnClassPath, beansInProject, namespaces).createBeans();

	}

	private String[] parseBasePackage(String basePackage) {
		String[] result;
		if (StringUtils.isNotBlank(basePackage)) {
			result = basePackage.trim().split("\\.");
		} else {
			result = new String[0];
		}
		return result;
	}

	private File getDeploymentDescriptor() {
		return new File(getProjectFolder().getAbsolutePath() + "/src/main/webapp/WEB-INF/web.xml");
	}

	private File getProjectFolder() {
		return project.getFile().getParentFile();
	}

}