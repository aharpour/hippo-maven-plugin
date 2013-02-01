package com.aharpour.ebrahim;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
		try {
			System.out.println("*****************************************");
			File webDescriptorFile = new File(project.getFile().getParentFile().getAbsolutePath()
					+ "/src/main/webapp/WEB-INF/web.xml");
			if (webDescriptorFile.exists()) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(webDescriptorFile);
				NodeList contextParamElements = doc.getElementsByTagName("context-param");
				for (int i = 0; i < contextParamElements.getLength(); i++) {
					Element contextParam = getElement(contextParamElements.item(i));
					if (contextParam != null) {
						NodeList paramNames = contextParam.getElementsByTagName("param-name");
						if (paramNames.getLength() == 1) {
							Node paramName = paramNames.item(0);
							System.out.println(getElement(paramName).getTextContent());
						}
					}
				}
			} else {
				throw new MojoExecutionException("could not find web descriptor file");
			}
			System.out.println("*****************************************");
		} catch (SAXException e) {
			throw new MojoExecutionException("SAXException while parsing Web Discriptor", e);
		} catch (IOException e) {
			throw new MojoExecutionException("IOException while parsing Web Discriptor", e);
		} catch (ParserConfigurationException e) {
			throw new MojoExecutionException("ParserConfigurationException while parsing Web Discriptor", e);
		}
	}

	private Element getElement(Node contextParam) {
		Element result = null;
		if (contextParam.getNodeType() == Node.ELEMENT_NODE) {
			result = ((Element) contextParam);
		}
		return result;
	}
}