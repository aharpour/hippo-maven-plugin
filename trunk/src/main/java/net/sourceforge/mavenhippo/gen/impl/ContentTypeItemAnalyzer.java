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
package net.sourceforge.mavenhippo.gen.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import net.sourceforge.mavenhippo.gen.ClassReference;
import net.sourceforge.mavenhippo.gen.ClasspathAware;
import net.sourceforge.mavenhippo.gen.PackageHandler;
import net.sourceforge.mavenhippo.model.ContentTypeBean.Item;
import net.sourceforge.mavenhippo.model.HippoBeanClass;
import net.sourceforge.mavenhippo.utils.Constants.NodeType;
import net.sourceforge.mavenhippo.utils.NamespaceUtils;
import net.sourceforge.mavenhippo.utils.NammingUtils;

import org.apache.commons.lang3.StringUtils;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSetBean;


/**
 * @author Ebrahim Aharpour
 * 
 */
public class ContentTypeItemAnalyzer extends ClasspathAware {

	protected Set<String> namespaces;
	protected PackageHandler packageGenerator;

	public ContentTypeItemAnalyzer(Map<String, HippoBeanClass> beansOnClassPath,
			Map<String, HippoBeanClass> beansInProject, Set<String> namespaces, PackageHandler packageGenerator) {
		super(beansOnClassPath, beansInProject);
		this.namespaces = namespaces;
		this.packageGenerator = packageGenerator;
	}

	public AnalyzerResult analyze(Item item) {
		return new AnalyzerResult(getType(item), getReturnType(item));
	}

	private Type getType(Item item) {
		Type result;
		String namespace = NamespaceUtils.getNamespace(item.getType());
		if (StringUtils.isBlank(namespace)) {
			result = Type.PROPERTY;
		} else if (NodeType.HIPPO_MIRROR.equals(item.getType()) || NodeType.HIPPO_RESOURCE.equals(item.getType())
				|| NodeType.HIPPOGALLERYPICKER_IMAGELINK.equals(item.getType())) {
			result = Type.LINKED_BEAN;

		} else if (NodeType.HIPPOSTD_HTML.equals(item.getType())) {
			result = Type.HIPPO_HTML;
		} else {
			result = Type.NODE;
		}
		return result;
	}

	private ClassReference getReturnType(Item item) {
		ClassReference result;
		String type = item.getType();
		String namespace = NamespaceUtils.getNamespace(type);
		if (StringUtils.isBlank(namespace)) {
			if (Boolean.class.getSimpleName().equalsIgnoreCase(type)) {
				result = new ClassReference(Boolean.class);
			} else if (Date.class.getSimpleName().equalsIgnoreCase(type)) {
				result = new ClassReference(Calendar.class);
			} else if (Long.class.getSimpleName().equalsIgnoreCase(type)) {
				result = new ClassReference(Long.class);
			} else if (Double.class.getSimpleName().equalsIgnoreCase(type)) {
				result = new ClassReference(Double.class);
			} else {
				result = new ClassReference(String.class);
			}
		} else {
			if (NodeType.HIPPO_MIRROR.equals(item.getType()) || NodeType.HIPPO_RESOURCE.equals(item.getType())) {
				result = new ClassReference(HippoBean.class);
			} else if (NodeType.HIPPOGALLERYPICKER_IMAGELINK.equals(item.getType())) {
				result = new ClassReference(HippoGalleryImageSetBean.class);
			} else if (beansOnClassPath.containsKey(type)) {
				result = new ClassReference(beansOnClassPath.get(type).getFullyQualifiedName());
			} else if (beansInProject.containsKey(type)) {
				result = new ClassReference(beansInProject.get(type).getFullyQualifiedName());
			} else if (namespaces.contains(namespace)) {
				String packageName = packageGenerator.getPackageGenerator(item.getContentType()).getPackageName();
				String className = NammingUtils.stringToClassName(NamespaceUtils.getSimpleName(item.getType()));
				result = new ClassReference(StringUtils.isBlank(packageName) ? className : packageName + "."
						+ className);
			} else {
				result = new ClassReference(HippoBean.class);
			}
		}
		return result;
	}

	public enum Type {
		PROPERTY, HIPPO_HTML, LINKED_BEAN, NODE;
	}

	public static class AnalyzerResult {
		private final Type type;
		private final ClassReference returnType;

		public AnalyzerResult(Type type, ClassReference returnType) {
			this.type = type;
			this.returnType = returnType;
		}

		public Type getType() {
			return type;
		}

		public ClassReference getReturnType() {
			return returnType;
		}

	}
}