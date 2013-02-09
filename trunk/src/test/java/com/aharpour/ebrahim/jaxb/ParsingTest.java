package com.aharpour.ebrahim.jaxb;

import java.io.File;
import java.net.URL;

import javax.xml.bind.JAXB;

import org.junit.Assert;
import org.junit.Test;

public class ParsingTest {

	@Test
	public void parse() {
		URL resource = ClassLoader.getSystemResource("newsdocument.xml");
		File xmlFile = new File(resource.getFile());
		Node rootNode = JAXB.unmarshal(xmlFile, Node.class);
		Assert.assertNotNull(rootNode);

		Assert.assertEquals(3, rootNode.getSubnodes().size());
		Assert.assertEquals("newsdocument", rootNode.getName());
		Assert.assertEquals("hipposysedit:templatetype", rootNode.getPropertyByName("jcr:primaryType").getSingleValue());
		Assert.assertEquals("mavenhippoplugindemo:title",
				rootNode.getSubnodeByName("hipposysedit:nodetype").getSubnodeByName("hipposysedit:nodetype")
						.getSubnodeByName("title").getPropertyByName("hipposysedit:path").getSingleValue());

		Assert.assertEquals(1, rootNode.getSubnodesByType("hipposysedit:prototypeset").size());
	}
}
