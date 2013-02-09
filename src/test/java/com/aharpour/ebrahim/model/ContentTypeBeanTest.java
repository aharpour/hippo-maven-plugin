package com.aharpour.ebrahim.model;

import java.io.File;

import javax.xml.bind.JAXB;

import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.jaxb.Node;

public class ContentTypeBeanTest {

	@Test
	public void getNodeTypeDefinitionsTest() {
		File xml = new File(ClassLoader.getSystemResource("newsdocumentedited.xml").getFile());
		Node node = JAXB.unmarshal(xml, Node.class);
		ContentTypeBean contentTypeBean = new ContentTypeBean(node);
		Assert.assertEquals(5, contentTypeBean.getNodeTypeDefinitions().size());
	}

}
