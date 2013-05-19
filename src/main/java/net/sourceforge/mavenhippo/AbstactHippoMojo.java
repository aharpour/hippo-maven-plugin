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

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;

/**
 * @author Ebrahim Aharpour
 * 
 */
public abstract class AbstactHippoMojo extends AbstractMojo {

	@Component
	protected MavenSession session;

	@Component
	protected MojoExecution mojo;

	@Component
	protected Settings settings;

	@Component
	protected MavenProject project;

	protected ClassLoader getProjectClassloader() throws MojoExecutionException {
		try {
			Set<Artifact> artifacts = project.getArtifacts();
			List<URL> urls = new ArrayList<URL>();
			for (Artifact artifact : artifacts) {
				urls.add(artifact.getFile().toURI().toURL());
			}
			return new URLClassLoader(urls.toArray(new URL[0]));
		} catch (MalformedURLException e) {
			throw new MojoExecutionException(e.getLocalizedMessage(), e);
		}

	}

}
