package com.aharpour.ebrahim;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.InstantiationStrategy;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

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
public class HippoBeanMojo extends AbstactHippoMojo {

	@Parameter(alias = "namespace.location", property = "namespaceLocation", defaultValue = "${project.parent.basedir.absolutePath}/bootstrap/configuration/src/main/resources/namespaces", readonly = true, required = false)
	private String namespaceLocation;

	@Parameter(alias = "base.package", property = "basePackage", defaultValue = "generated.beans", readonly = true, required = false)
	private String basePackage;

	@Parameter(alias = "package.to.search", property = "packageToSearch", defaultValue = "", readonly = true, required = false)
	private String packageToSearch;

	@Parameter(alias = "source.root", property = "sourceRoot", defaultValue = "${project.build.directory}/generated-sources/beans/", readonly = true, required = false)
	private File sourceRoot;

	@Parameter(alias = "deployment.descriptor", property = "deploymentDescriptor", defaultValue = "${project.basedir}/src/main/webapp/WEB-INF/web.xml", readonly = true, required = false)
	private File deploymentDescriptor;

	@Parameter(alias = "site.folder", property = "siteFolder", defaultValue = "${project.basedir}", readonly = true, required = false)
	private File siteFolder;

	@Parameter(required = true)
	private Map<String, String> namespaces;

	@Override
	public void execute() throws MojoExecutionException {
		ContextParameterExtractor contextParamExtractor = new ContextParameterExtractor(getDeploymentDescriptor());

		Map<String, HippoBeanClass> beansOnClassPath = new ClassPathBeanFinder()
				.getBeansOnClassPath(contextParamExtractor);
		Map<String, HippoBeanClass> beansInProject = new SourceCodeBeanFinder(getSiteFolder())
				.getBeansInProject(contextParamExtractor);

		BeanGeneratorConfig config = new BeanGeneratorConfig(getLog(), namespaceLocation,
				parseBasePackage(basePackage), packageToSearch, sourceRoot);
		new BeanCreator(config, beansOnClassPath, beansInProject, namespaces).createBeans();
		project.addCompileSourceRoot(sourceRoot.getAbsolutePath());

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
		return deploymentDescriptor;
	}

	private File getSiteFolder() {
		return siteFolder;
	}

}