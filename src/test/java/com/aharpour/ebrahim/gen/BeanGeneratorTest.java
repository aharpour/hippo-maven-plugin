package com.aharpour.ebrahim.gen;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.xml.bind.JAXB;

import org.junit.Test;

import com.aharpour.ebrahim.jaxb.Node;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.ContentTypeBean.ContentTypeException;
import com.aharpour.ebrahim.model.HippoBeanClass;

import freemarker.template.TemplateException;

public class BeanGeneratorTest {
	private final Map<String, HippoBeanClass> beansOnClassPath = new HashMap<String, HippoBeanClass>() {
		private static final long serialVersionUID = 1L;
		{
			put("hippostd:html", new HippoBeanClass("org.hippoecm.hst.content.beans.standard", "HippoHtml",
					"hippostd:html"));
			put("mavenhippoplugindemo:CompoundType", new HippoBeanClass("com.aharpour.ebrahim.beans", "CompoundType",
					"mavenhippoplugindemo:CompoundType"));
		}
	};
	private final Map<String, HippoBeanClass> beansInProject = new HashMap<String, HippoBeanClass>() {
		private static final long serialVersionUID = 1L;
		{
			put("hippostd:html", new HippoBeanClass("org.hippoecm.hst.content.beans.standard", "HippoHtml2",
					"hippostd:html"));
		}
	};

	private final HashSet<String> namespaces = new HashSet<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("mavenhippoplugindemo");
		}
	};

	@Test
	public void ContentTypeTest() throws ContentTypeException, TemplateException, IOException {
		BeanGenerator beanGenerator = new BeanGenerator(beansOnClassPath, beansInProject, namespaces);
		String generateBean = beanGenerator.generateBean(getContentTypeBean("newsdocumentedited.xml"));
		System.out.println(generateBean);
	}

	@Test
	public void CompoundTypeTest() throws ContentTypeException, TemplateException, IOException {
		BeanGenerator beanGenerator = new BeanGenerator(beansOnClassPath, beansInProject, namespaces);
		String generateBean = beanGenerator.generateBean(getContentTypeBean("MyContentType.xml"));
		System.out.println(generateBean);
	}

	private ContentTypeBean getContentTypeBean(String fileName) {
		Node node = JAXB.unmarshal(ClassLoader.getSystemResourceAsStream(fileName), Node.class);
		ContentTypeBean contentTypeBean = new ContentTypeBean(node);
		return contentTypeBean;
	}
}
