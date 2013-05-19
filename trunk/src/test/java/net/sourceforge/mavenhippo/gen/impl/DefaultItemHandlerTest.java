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
package net.sourceforge.mavenhippo.gen.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXB;

import net.sourceforge.mavenhippo.gen.HandlerResponse;
import net.sourceforge.mavenhippo.gen.ImportRegistry;
import net.sourceforge.mavenhippo.gen.MethodGenerator;
import net.sourceforge.mavenhippo.gen.PackageHandler;
import net.sourceforge.mavenhippo.gen.PropertyGenerator;
import net.sourceforge.mavenhippo.gen.Utils;
import net.sourceforge.mavenhippo.gen.impl.DefaultItemHandler;
import net.sourceforge.mavenhippo.gen.impl.DefaultPackageHandler;
import net.sourceforge.mavenhippo.jaxb.Node;
import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.HippoBeanClass;
import net.sourceforge.mavenhippo.model.ContentTypeBean.Item;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author Ebrahim Aharpour
 * 
 */
public class DefaultItemHandlerTest {

	private final Map<String, HippoBeanClass> beansOnClassPath = new HashMap<String, HippoBeanClass>();
	private final Map<String, HippoBeanClass> beansInProject = new HashMap<String, HippoBeanClass>();
	private final HashSet<String> namespaces = new HashSet<String>();
	private final PackageHandler packageHandler = new DefaultPackageHandler(beansOnClassPath, beansInProject);

	public DefaultItemHandlerTest() {
		beansOnClassPath.put("hippostd:html", new HippoBeanClass("org.hippoecm.hst.content.beans.standard",
				"HippoHtml", "hippostd:html"));
		beansOnClassPath.put("mavenhippoplugindemo:CompoundType", new HippoBeanClass("com.aharpour.ebrahim.beans",
				"CompoundType", "mavenhippoplugindemo:CompoundType"));
		namespaces.add("mavenhippoplugindemo");
	}

	@Test
	public void TestBoolean() {
		ImportRegistry importRegistry = new ImportRegistry();
		ContentTypeBean contentTypeBean = getContentTypeBean();
		Item booleanItem = Utils.getItemByType(contentTypeBean, "Boolean");
		DefaultItemHandler handler = new DefaultItemHandler(beansOnClassPath, beansInProject, namespaces,
				packageHandler);
		HandlerResponse response = handler.handle(booleanItem, importRegistry);
		List<MethodGenerator> methodGenerators = response.getMethodGenerators();
		List<PropertyGenerator> propertyGenerators = response.getPropertyGenerators();

		Assert.assertEquals(1, methodGenerators.size());
		Assert.assertEquals(1, propertyGenerators.size());
		Assert.assertEquals("private Boolean boolean;", propertyGenerators.get(0).getFragment());
		Assert.assertEquals(
				"public Boolean getBoolean() {\r\n\tif (this.boolean == null) {\r\n\t\tthis.boolean = getProperty(\"mavenhippoplugindemo:boolean\");\r\n\t}\r\n\treturn this.boolean;\r\n}",
				methodGenerators.get(0).getFragment());
	}

	private ContentTypeBean getContentTypeBean() {
		Node node = JAXB.unmarshal(ClassLoader.getSystemResourceAsStream("DocumentType.xml"), Node.class);
		ContentTypeBean contentTypeBean = new ContentTypeBean(node, new HashMap<String, String>());
		return contentTypeBean;
	}

}
