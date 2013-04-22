package com.aharpour.ebrahim.jaxb;

import javax.xml.bind.JAXB;

import org.junit.Assert;
import org.junit.Test;

public class ParsingTest {

	@Test
	public void parse() {
		Node rootNode = JAXB.unmarshal(ClassLoader.getSystemResourceAsStream("newsdocument.xml"), Node.class);
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
