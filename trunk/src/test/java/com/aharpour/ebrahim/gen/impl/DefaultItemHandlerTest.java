package com.aharpour.ebrahim.gen.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXB;

import org.junit.Test;

import com.aharpour.ebrahim.gen.HandlerResponse;
import com.aharpour.ebrahim.gen.ImportRegistry;
import com.aharpour.ebrahim.jaxb.Node;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.model.HippoBeanClass;

public class DefaultItemHandlerTest {
	
	private final Map<String, HippoBeanClass> beansOnClassPath = new HashMap<String, HippoBeanClass>();
	private final Map<String, HippoBeanClass> beansInProject = new HashMap<String, HippoBeanClass>();
	
	public DefaultItemHandlerTest() {
		beansOnClassPath.put("hippostd:html", new HippoBeanClass("org.hippoecm.hst.content.beans.standard", "HippoHtml", "hippostd:html"));
		beansOnClassPath.put("mavenhippoplugindemo:CompoundType", new HippoBeanClass("com.aharpour.ebrahim.beans", "CompoundType", "mavenhippoplugindemo:CompoundType"));
		
	}
	
	@Test
	public void TestBoolean() {
		ImportRegistry importRegistry = new ImportRegistry();
		ContentTypeBean contentTypeBean = getContentTypeBean();
		Item booleanItem = Utils.getItemByType(contentTypeBean, "Boolean");
		DefaultItemHandler handler = new DefaultItemHandler(beansOnClassPath, beansInProject);
		HandlerResponse response = handler.handle(booleanItem, importRegistry);
		
	}

	private ContentTypeBean getContentTypeBean() {
		File xml = new File(ClassLoader.getSystemResource("DocumentType.xml").getFile());
		Node node = JAXB.unmarshal(xml, Node.class);
		ContentTypeBean contentTypeBean = new ContentTypeBean(node);
		return contentTypeBean;
	}

}
