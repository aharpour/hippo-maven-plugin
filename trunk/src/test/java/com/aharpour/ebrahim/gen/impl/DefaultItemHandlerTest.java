package com.aharpour.ebrahim.gen.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXB;

import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.gen.HandlerResponse;
import com.aharpour.ebrahim.gen.ImportRegistry;
import com.aharpour.ebrahim.gen.MethodGenerator;
import com.aharpour.ebrahim.gen.PackageHandler;
import com.aharpour.ebrahim.gen.PropertyGenerator;
import com.aharpour.ebrahim.gen.Utils;
import com.aharpour.ebrahim.jaxb.Node;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.model.HippoBeanClass;

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
		ContentTypeBean contentTypeBean = new ContentTypeBean(node);
		return contentTypeBean;
	}

}
