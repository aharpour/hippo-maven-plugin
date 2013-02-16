package com.aharpour.ebrahim;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.logging.Log;

import com.aharpour.ebrahim.model.HippoBeanClass;

public class BeanGenerator {

	private final ContentTypeDefinitionFinder contentTypeDefinitionFinder;

	public BeanGenerator(BeanGeneratorConfig config) {
		contentTypeDefinitionFinder = new ContentTypeDefinitionFinder(config.namespaceFolder,
				config.maximumDepthOfScan, config.log);
	}

	public void generateBeans(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
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

		return result;
	}

	public static class BeanGeneratorConfig {
		public BeanGeneratorConfig(Log log, String namespaceLocation) {
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
		}

		private final Log log;
		private final File namespaceFolder;
		public final int maximumDepthOfScan = 10;
	}

}
