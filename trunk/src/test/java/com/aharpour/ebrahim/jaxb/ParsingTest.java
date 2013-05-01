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
package com.aharpour.ebrahim.jaxb;

import javax.xml.bind.JAXB;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ebrahim Aharpour
 * 
 */
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
