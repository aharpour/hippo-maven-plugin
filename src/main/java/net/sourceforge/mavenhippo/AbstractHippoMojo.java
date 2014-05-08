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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;

/**
 * @author Ebrahim Aharpour
 * 
 */
public abstract class AbstractHippoMojo extends AbstractMojo {

    @Component
    private MavenSession session;

    @Component
    private MojoExecution mojo;

    @Component
    private Settings settings;

    @Component
    private MavenProject project;

    /**
     * Package name of generated beans.
     */
    @Parameter(alias = "base.package", property = "basePackage", defaultValue = "generated.beans", readonly = false, required = false)
    private String basePackage;

    /**
     * The output directory of the generated Java beans.
     */
    @Parameter(alias = "source.root", property = "sourceRoot", defaultValue = "${project.build.directory}/generated-sources/beans/", readonly = false, required = false)
    private File sourceRoot;

    /**
     * Maximum recursion depth.
     */
    @Parameter(required = true, defaultValue = "10", property = "maximumDepthOfScan", alias = "maximum.depth.of.scan")
    private int maximumDepthOfScan;

    private ClassLoader projectClassloader;

    protected ClassLoader getProjectClassloader() throws MojoExecutionException {
        try {
            if (projectClassloader == null) {
                Set<Artifact> artifacts = project.getArtifacts();
                List<URL> urls = new ArrayList<URL>();
                for (Artifact artifact : artifacts) {
                    urls.add(artifact.getFile().toURI().toURL());
                }
                if (getLog().isDebugEnabled()) {
                    for (URL url : urls) {
                        getLog().debug("Project dependency URL: " + url.toString());
                    }
                }
                projectClassloader = new URLClassLoader(urls.toArray(new URL[0]), this.getClass().getClassLoader());
            }
            return projectClassloader;
        } catch (MalformedURLException e) {
            throw new MojoExecutionException(e.getLocalizedMessage(), e);
        }

    }

    protected String[] parsePackageName(String basePackage) {
        String[] result;
        if (StringUtils.isNotBlank(basePackage)) {
            result = basePackage.trim().split("\\.");
        } else {
            result = new String[0];
        }
        return result;
    }

    public MavenSession getSession() {
        return session;
    }

    public MojoExecution getMojo() {
        return mojo;
    }

    public Settings getSettings() {
        return settings;
    }

    public MavenProject getProject() {
        return project;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public File getSourceRoot() {
        return sourceRoot;
    }

    public int getMaximumDepthOfScan() {
        return maximumDepthOfScan;
    }

    public void setSession(MavenSession session) {
        this.session = session;
    }

    public void setMojo(MojoExecution mojo) {
        this.mojo = mojo;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setProject(MavenProject project) {
        this.project = project;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setSourceRoot(File sourceRoot) {
        this.sourceRoot = sourceRoot;
    }

    public void setMaximumDepthOfScan(int maximumDepthOfScan) {
        this.maximumDepthOfScan = maximumDepthOfScan;
    }

}
