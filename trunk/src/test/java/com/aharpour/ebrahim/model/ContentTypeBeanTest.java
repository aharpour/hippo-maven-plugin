package com.aharpour.ebrahim.model;

import java.io.File;

import javax.xml.bind.JAXB;

import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.jaxb.Node;
import com.aharpour.ebrahim.utils.Constants;

public class ContentTypeBeanTest {



	@Test
	public void getNodeTypeDefinitionsTest() {
		File xml = new File(ClassLoader.getSystemResource("newsdocumentedited.xml").getFile());
		Node node = JAXB.unmarshal(xml, Node.class);
		ContentTypeBean contentTypeBean = new ContentTypeBean(node);
		Assert.assertEquals(5, contentTypeBean.getNodeTypeDefinitions().size());
	}

	@Test
	public void getTemplateDefinitionForTest() {
		File xml = new File(ClassLoader.getSystemResource("newsdocumentedited.xml").getFile());
		Node node = JAXB.unmarshal(xml, Node.class);
		ContentTypeBean contentTypeBean = new ContentTypeBean(node);
		Node titleField = contentTypeBean.getNodeTypeDefinitions().get(0);
		Node titleFieldTemp = contentTypeBean.getTemplateDefinitionFor(titleField.getName());
		Assert.assertEquals("Title", titleFieldTemp.getPropertyByName(Constants.PropertyName.CAPTION).getSingleValue());

	}

}
