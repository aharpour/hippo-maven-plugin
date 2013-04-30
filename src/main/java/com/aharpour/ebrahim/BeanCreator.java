package com.aharpour.ebrahim;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import com.aharpour.ebrahim.FileManager.FileManagerException;
import com.aharpour.ebrahim.gen.BeanGenerator;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.ContentTypeBean.ContentTypeException;
import com.aharpour.ebrahim.model.HippoBeanClass;

import freemarker.template.TemplateException;

public class BeanCreator {

	private final ContentTypeDefinitionFinder contentTypeDefinitionFinder;
	private final BeanGenerator generator;
	private final FileManager fileManager;
	private final File sourceRoot;

	public BeanCreator(BeanGeneratorConfig config, Map<String, HippoBeanClass> beansOnClassPath,
			Map<String, HippoBeanClass> beansInProject, Map<String, String> namespaces) throws FileManagerException {
		contentTypeDefinitionFinder = new ContentTypeDefinitionFinder(config.namespaceFolder,
				config.maximumDepthOfScan, config.log, namespaces);
		this.sourceRoot = config.sourceRoot;
		this.fileManager = new FileManager(sourceRoot, config.log);
		this.generator = new BeanGenerator(beansOnClassPath, beansInProject, config.packageToSearch,
				config.basePackage, namespaces.keySet());
	}

	public void createBeans() throws MojoExecutionException {
		Map<String, ContentTypeBean> toBeGenerated = getBeansToBeGenerate();

		for (Iterator<String> nodeTypeIterator = toBeGenerated.keySet().iterator(); nodeTypeIterator.hasNext();) {
			String nodeType = nodeTypeIterator.next();
			createBean(toBeGenerated.get(nodeType));
		}

	}

	private void createBean(ContentTypeBean contentType) throws MojoExecutionException {
		try {
			String[] packages = generator.getPackage(contentType);
			String className = generator.getClassName(contentType);
			File pack = fileManager.getPackage(packages);
			File classFile = fileManager.getClassFile(pack, className);
			PrintWriter writer = new PrintWriter(classFile);
			writer.print(generator.generateBean(contentType));
			writer.close();
		} catch (ContentTypeException e) {
			throw new MojoExecutionException(e.getLocalizedMessage(), e);
		} catch (TemplateException e) {
			throw new MojoExecutionException(e.getLocalizedMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getLocalizedMessage(), e);
		}
	}

	private Map<String, ContentTypeBean> getBeansToBeGenerate() throws ContentTypeException {
		Map<String, ContentTypeBean> result = new HashMap<String, ContentTypeBean>();
		List<ContentTypeBean> contentTypeBeans = contentTypeDefinitionFinder.getContentTypeBeans();
		for (ContentTypeBean contentTypeBean : contentTypeBeans) {
			result.put(contentTypeBean.getFullyQualifiedName(), contentTypeBean);
		}
		return result;
	}

	public static class BeanGeneratorConfig {
		public BeanGeneratorConfig(Log log, String namespaceLocation, String[] basePackage, String packageToSearch,
				File sourceRoot, int maximumDepthOfScan) {
			if (log == null) {
				throw new IllegalArgumentException("Argument log is required");
			}
			if (StringUtils.isBlank(namespaceLocation)) {
				throw new IllegalArgumentException("Argument namespaceLocation is required");
			} else {
				this.namespaceFolder = new File(namespaceLocation);
				if (!namespaceFolder.exists() || !namespaceFolder.isDirectory()) {
					throw new IllegalArgumentException("Argument namespaceLocation must point to a folder.");
				}
			}
			this.log = log;
			this.basePackage = basePackage;
			this.packageToSearch = packageToSearch;
			this.sourceRoot = sourceRoot;
			this.maximumDepthOfScan = maximumDepthOfScan;
		}

		private final Log log;
		private final File namespaceFolder;
		private final String[] basePackage;
		private final String packageToSearch;
		private final File sourceRoot;
		public final int maximumDepthOfScan;
	}

}
