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
package com.aharpour.ebrahim.model;

import java.util.HashMap;

import javax.xml.bind.JAXB;

import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.jaxb.Node;
import com.aharpour.ebrahim.model.ContentTypeBean.ContentTypeException;
import com.aharpour.ebrahim.utils.Constants;

/**
 * @author Ebrahim Aharpour
 * 
 */
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
