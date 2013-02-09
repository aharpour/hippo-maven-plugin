package com.aharpour.ebrahim;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.logging.Log;

import com.aharpour.ebrahim.jaxb.Node;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.utils.Constants;

public class ContentTypeDefinitionFinder {


	private final Log log;

	private final File namespaceFolder;
	private final int maximumDepth;

	public ContentTypeDefinitionFinder(File namespaceFolder, int maximumDepthOfScan, Log log) {
		if (!namespaceFolder.exists() || !namespaceFolder.isDirectory()) {
			throw new IllegalArgumentException("namespaceFolder parameter needs to be a directory.");
		}
		if (log == null) {
			throw new IllegalArgumentException("log can not be null.");
		}
		this.namespaceFolder = namespaceFolder;
		this.maximumDepth = maximumDepthOfScan;
		this.log = log;
	}

	public List<ContentTypeBean> getContentTypeBeans() {
		return findContentTypesRecursively(namespaceFolder, 0);
	}

	private List<ContentTypeBean> findContentTypesRecursively(File folder, int depth) {
		List<ContentTypeBean> result = new ArrayList<ContentTypeBean>();
		File[] xmlFiles = folder.listFiles(xmlFileFilter);
		for (File xml : xmlFiles) {
			ContentTypeBean contentType = generateContentTypeIfPossible(xml);
			if (contentType != null) {
				result.add(contentType);
			}
		}
		if (depth < maximumDepth) {
			File[] subfolders = folder.listFiles(folderFilter);
			for (File subfolder : subfolders) {
				result.addAll(findContentTypesRecursively(subfolder, depth + 1));
			}
		}

		return result;
	}

	private ContentTypeBean generateContentTypeIfPossible(File xml) {
		ContentTypeBean result = null;
		try {
			Node unmarshaled = JAXB.unmarshal(xml, Node.class);
			String nodeType = unmarshaled.getPropertyByName(Constants.PropertyName.JCR_PRIMARY_TYPE).getSingleValue();
			if (Constants.NodeType.TEMPLATE_TYPE.equals(nodeType)) {
				result = new ContentTypeBean(unmarshaled);
			}
		} catch (DataBindingException e) {
			log.info("\"" + xml.getName() + "\" is being ignored.");
		}
		return result;
	}

	private static FilenameFilter xmlFileFilter = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			boolean result = false;
			if (StringUtils.isNotBlank(name) && name.endsWith(".xml")) {
				result = true;
			}
			return result;
		}
	};

	private static FileFilter folderFilter = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}

	};

}
