package com.aharpour.ebrahim.gen;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
		{
			put("hippostd:html", new HippoBeanClass("org.hippoecm.hst.content.beans.standard", "HippoHtml",
					"hippostd:html"));
			put("mavenhippoplugindemo:CompoundType", new HippoBeanClass("com.aharpour.ebrahim.beans", "CompoundType",
					"mavenhippoplugindemo:CompoundType"));
		}
	};
	private final Map<String, HippoBeanClass> beansInProject = new HashMap<String, HippoBeanClass>() {
		{
			put("hippostd:html", new HippoBeanClass("org.hippoecm.hst.content.beans.standard", "HippoHtml2",
					"hippostd:html"));
		}
	};

	@Test
	public void basicTest() throws ContentTypeException, TemplateException, IOException {
		BeanGenerator beanGenerator = new BeanGenerator(beansOnClassPath, beansInProject);
		String generateBean = beanGenerator.generateBean(getContentTypeBean());
		System.out.println(generateBean);
	}

	private ContentTypeBean getContentTypeBean() {
		File xml = new File(ClassLoader.getSystemResource("newsdocumentedited.xml").getFile());
		Node node = JAXB.unmarshal(xml, Node.class);
		ContentTypeBean contentTypeBean = new ContentTypeBean(node);
		return contentTypeBean;
	}
}
