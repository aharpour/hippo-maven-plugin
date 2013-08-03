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
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;

import net.sourceforge.mavenhippo.jaxb.Node;
import net.sourceforge.mavenhippo.jaxb.Property;
import net.sourceforge.mavenhippo.model.ContentTypeBean;
import static net.sourceforge.mavenhippo.utils.Constants.PropertyName;
import static net.sourceforge.mavenhippo.utils.Constants.NodeType;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.logging.Log;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class ContentTypeDefinitionFinder {

	private final Log log;

	private final File namespaceFolder;
	private final int maximumDepth;
	private final BidiMap namespaces = new DualHashBidiMap();

	@SuppressWarnings("unchecked")
	public ContentTypeDefinitionFinder(File namespaceFolder, int maximumDepthOfScan, Log log,
			Map<String, String> namespaces) {
		if (!namespaceFolder.exists() || !namespaceFolder.isDirectory()) {
			throw new IllegalArgumentException("namespaceFolder parameter needs to be a directory.");
		}
		if (log == null) {
			throw new IllegalArgumentException("log can not be null.");
		}
		this.namespaceFolder = namespaceFolder;
		this.maximumDepth = maximumDepthOfScan;
		this.log = log;
		this.namespaces.putAll(namespaces);
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

	@SuppressWarnings("unchecked")
	private ContentTypeBean generateContentTypeIfPossible(File xml) {
		ContentTypeBean result = null;
		try {
			Node unmarshaled = JAXB.unmarshal(xml, Node.class);
			Property primaryTypeProperty = unmarshaled.getPropertyByName(PropertyName.JCR_PRIMARY_TYPE);
			if (primaryTypeProperty != null && NodeType.TEMPLATE_TYPE.equals(primaryTypeProperty.getSingleValue())
					&& StringUtils.isBlank(unmarshaled.getMerge())) {
				result = new ContentTypeBean(unmarshaled, namespaces.inverseBidiMap());
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
