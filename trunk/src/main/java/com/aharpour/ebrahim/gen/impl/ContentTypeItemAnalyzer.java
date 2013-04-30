package com.aharpour.ebrahim.gen.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocumentBean;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSetBean;

import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.ClasspathAware;
import com.aharpour.ebrahim.gen.PackageHandler;
import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.model.HippoBeanClass;
import com.aharpour.ebrahim.utils.Constants.NodeType;
import com.aharpour.ebrahim.utils.NamespaceUtils;
import com.aharpour.ebrahim.utils.NammingUtils;

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
				result = new ClassReference(HippoDocumentBean.class);
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

	public class AnalyzerResult {
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