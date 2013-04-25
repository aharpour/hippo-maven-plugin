package com.aharpour.ebrahim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.logging.Log;

import com.aharpour.ebrahim.FileManager.FileManagerException;
import com.aharpour.ebrahim.gen.BeanGenerator;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.ContentTypeBean.ContentTypeException;
import com.aharpour.ebrahim.model.HippoBeanClass;

public class BeanCreator {

	private final ContentTypeDefinitionFinder contentTypeDefinitionFinder;
	private final Map<String, HippoBeanClass> beansOnClassPath;
	private final Map<String, HippoBeanClass> beansInProject;
	private final HashSet<String> namespaces = new HashSet<String>();
	private final BeanGenerator generator;
	private final FileManager fileManager;
	private final File sourceRoot;

	public BeanCreator(BeanGeneratorConfig config, Map<String, HippoBeanClass> beansOnClassPath,
			Map<String, HippoBeanClass> beansInProject) throws FileManagerException {
		contentTypeDefinitionFinder = new ContentTypeDefinitionFinder(config.namespaceFolder,
				config.maximumDepthOfScan, config.log);
		this.beansOnClassPath = beansOnClassPath;
		this.beansInProject = beansInProject;
		this.sourceRoot = config.sourceRoot;
		fileManager = new FileManager(sourceRoot, config.log);
		this.generator = new BeanGenerator(beansOnClassPath, beansInProject, config.packageToSearch,
				config.basePackage, namespaces);
	}

	public void createBeans() throws ContentTypeException, FileManagerException {
		Map<String, ContentTypeBean> toBeGenerated = getBeansToBeGenerate();

		for (Iterator<String> nodeTypeIterator = toBeGenerated.keySet().iterator(); nodeTypeIterator.hasNext();) {
			String nodeType = nodeTypeIterator.next();
			// createBean(toBeGenerated.get(nodeType)); TODO
		}

	}

	private void createBean(ContentTypeBean contentType) throws FileManagerException, FileNotFoundException {
		String[] packages = generator.getPackage(contentType);
		String className = generator.getClassName(contentType);
		File pack = fileManager.getPackage(packages);
		// new PrintWriter(); TODO

	}

	private Map<String, ContentTypeBean> getBeansToBeGenerate() throws ContentTypeException {
		Map<String, ContentTypeBean> result = new HashMap<String, ContentTypeBean>();
		List<ContentTypeBean> contentTypeBeans = contentTypeDefinitionFinder.getContentTypeBeans();
		for (ContentTypeBean contentTypeBean : contentTypeBeans) {
			namespaces.add(contentTypeBean.getNamespace());
			result.put(contentTypeBean.getFullyQualifiedName(), contentTypeBean);
		}
		return result;
	}

	public static class BeanGeneratorConfig {
		public BeanGeneratorConfig(Log log, String namespaceLocation, String[] basePackage, String packageToSearch,
				File sourceRoot) {
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
		}

		private final Log log;
		private final File namespaceFolder;
		private final String[] basePackage;
		private final String packageToSearch;
		private final File sourceRoot;
		public final int maximumDepthOfScan = 10;
	}

}
