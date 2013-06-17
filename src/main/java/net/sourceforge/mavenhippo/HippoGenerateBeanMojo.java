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
import java.util.Map;

import net.sourceforge.mavenhippo.BeanCreator.BeanGeneratorConfig;
import net.sourceforge.mavenhippo.model.HippoBeanClass;
import net.sourceforge.mavenhippo.utils.ContextParameterExtractor;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.InstantiationStrategy;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;


/**
 * @author Ebrahim Aharpour
 * 
 */
@Mojo(name = "generate", executionStrategy = "always", inheritByDefault = true, instantiationStrategy = InstantiationStrategy.SINGLETON, defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresDirectInvocation = true, requiresOnline = false, requiresProject = true, requiresReports = false, threadSafe = false)
@Execute(goal = "generate", phase = LifecyclePhase.GENERATE_SOURCES)
public class HippoGenerateBeanMojo extends AbstractHippoMojo {

	@Parameter(alias = "namespace.location", property = "namespaceLocation", defaultValue = "${project.parent.basedir.absolutePath}/bootstrap/configuration/src/main/resources/namespaces", readonly = false, required = false)
	private String namespaceLocation;

	/**
	 * The java package to be scanned for custom handlers. By default it is set to "" that means by default it will scan to whole classpath.
	 */
	@Parameter(alias = "package.to.search", property = "packageToSearch", defaultValue = "", readonly = false, required = false)
	private String packageToSearch;

	/**
	 * Deployment descriptor of your project. The Deployment Descriptor is used to extract value of "hst-beans-annotated-classes". 
	 * By default it is set to ${project.basedir}/src/main/webapp/WEB-INF/web.xml
	 * In the future there is going to be another parameter which accept the value of "hst-beans-annotated-classes" directly,
	 * So that plug-in developers can also take advantage of this plug-in.
	 */
	@Parameter(alias = "deployment.descriptor", property = "deploymentDescriptor", defaultValue = "${project.basedir}/src/main/webapp/WEB-INF/web.xml", readonly = false, required = false)
	private File deploymentDescriptor;

	/**
	 * The src directory of your project. This directory is being used to search for java files which define Hippo beans. 
	 * By default it is set to ${project.build.sourceDirectory}
	 * 
	 */
	@Parameter(alias = "source.directory", property = "sourceDirectory", defaultValue = "${project.build.sourceDirectory}", readonly = false, required = false)
	private File sourceDirectory;

	@Parameter(required = true)
	private Map<String, String> namespaces;

	@Override
	public void execute() throws MojoExecutionException {
		ContextParameterExtractor contextParamExtractor = new ContextParameterExtractor(getDeploymentDescriptor());

		Map<String, HippoBeanClass> beansOnClassPath = new ClassPathBeanFinder(getProjectClassloader())
				.getBeansOnClassPath(contextParamExtractor);
		Map<String, HippoBeanClass> beansInProject = new SourceCodeBeanFinder(sourceDirectory, maximumDepthOfScan,
				getLog()).getBeansInProject(contextParamExtractor);

		BeanGeneratorConfig config = new BeanGeneratorConfig(getLog(), namespaceLocation,
				parsePackageName(basePackage), packageToSearch, sourceRoot, maximumDepthOfScan);
		new BeanCreator(config, beansOnClassPath, beansInProject, namespaces, getProjectClassloader()).createBeans();
		project.addCompileSourceRoot(sourceRoot.getAbsolutePath());

	}

	private File getDeploymentDescriptor() {
		return deploymentDescriptor;
	}

}