package com.aharpour.ebrahim.model;

import java.util.HashMap;

import javax.xml.bind.JAXB;

import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.jaxb.Node;
import com.aharpour.ebrahim.model.ContentTypeBean.ContentTypeException;
import com.aharpour.ebrahim.utils.Constants;

public class ContentTypeBeanTest {

	@Test
	public void getNodeTypeDefinitionsTest() {
		ContentTypeBean contentTypeBean = getContentTypeBean();
		Assert.assertEquals(5, contentTypeBean.getNodeTypeDefinitions().size());
	}

	@Test
	public void getTemplateDefinitionForTest() {
		ContentTypeBean contentTypeBean = getContentTypeBean();
		Node titleField = contentTypeBean.getNodeTypeDefinitions().get(0);
		Node titleFieldTemp = contentTypeBean.getTemplateDefinitionFor(titleField.getName());
		Assert.assertEquals("Title", titleFieldTemp.getPropertyByName(Constants.PropertyName.CAPTION).getSingleValue());
	}

	@Test
	public void getFullyQualifiedNameTest() throws ContentTypeException {
		ContentTypeBean contentTypeBean = getContentTypeBean();
		Assert.assertEquals("mavenhippoplugindemo:newsdocument", contentTypeBean.getFullyQualifiedName());
	}

	@Test
	public void getSimpleNameTest() throws ContentTypeException {
		ContentTypeBean contentTypeBean = getContentTypeBean();
		Assert.assertEquals("newsdocument", contentTypeBean.getSimpleName());
	}

	@Test
	public void getNamespaceTest() throws ContentTypeException {
		ContentTypeBean contentTypeBean = getContentTypeBean();
		Assert.assertEquals("mavenhippoplugindemo", contentTypeBean.getNamespace());
	}

	private ContentTypeBean getContentTypeBean() {
		Node node = JAXB.unmarshal(ClassLoader.getSystemResourceAsStream("newsdocumentedited.xml"), Node.class);
		ContentTypeBean contentTypeBean = new ContentTypeBean(node, new HashMap<String, String>());
		return contentTypeBean;
	}

}
