package com.aharpour.ebrahim.model;

import javax.xml.bind.JAXB;

import org.junit.Test;

import com.aharpour.ebrahim.jaxb.Node;

public class ContentTypeBeanTest {

	@Test
	public void test() {
		String xml = ClassLoader.getSystemResource("newsdocumentedited.xml").getFile();
		Node node = JAXB.unmarshal(xml, Node.class);
		ContentTypeBean contentTypeBean = new ContentTypeBean(node);
	}

}
