package com.aharpour.ebrahim.gen.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXB;

import junit.framework.Assert;

import org.junit.Test;

import com.aharpour.ebrahim.jaxb.Node;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.HippoBeanClass;

public class ContentTypeItemAnalyzerTest {
	
	private final Map<String, HippoBeanClass> beansOnClassPath = new HashMap<String, HippoBeanClass>();
	private final Map<String, HippoBeanClass> beansInProject = new HashMap<String, HippoBeanClass>();
	
	public ContentTypeItemAnalyzerTest() {
		beansOnClassPath.put("hippostd:html", new HippoBeanClass("org.hippoecm.hst.content.beans.standard", "HippoHtml", "hippostd:html"));
		beansOnClassPath.put("mavenhippoplugindemo:CompoundType", new HippoBeanClass("com.aharpour.ebrahim.beans", "CompoundType", "mavenhippoplugindemo:CompoundType"));
		
		beansInProject.put("hippostd:html", new HippoBeanClass("org.hippoecm.hst.content.beans.standard", "HippoHtml2", "hippostd:html"));
	}
	
	@Test
	public void AnalyzerTest() {
		ContentTypeItemAnalyzer contentTypeItemAnalyzer = new ContentTypeItemAnalyzer(beansOnClassPath, beansInProject);
		ContentTypeBean contentTypeBean = getContentTypeBean();
		
		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Boolean")).getType().toString());
		Assert.assertEquals("java.lang.Boolean", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Boolean")).getReturnType().getClassName());
		
		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Date")).getType().toString());
		Assert.assertEquals("java.util.Date", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Date")).getReturnType().getClassName());
		
		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Docbase")).getType().toString());
		Assert.assertEquals("java.lang.String", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Docbase")).getReturnType().getClassName());
		
		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Double")).getType().toString());
		Assert.assertEquals("java.lang.Double", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Double")).getReturnType().getClassName());
		
		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Html")).getType().toString());
		Assert.assertEquals("java.lang.String", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Html")).getReturnType().getClassName());
		
		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Long")).getType().toString());
		Assert.assertEquals("java.lang.Long", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Long")).getReturnType().getClassName());
		
		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Password")).getType().toString());
		Assert.assertEquals("java.lang.String", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Password")).getReturnType().getClassName());
		
		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "String")).getType().toString());
		Assert.assertEquals("java.lang.String", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "String")).getReturnType().getClassName());
		
		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Text")).getType().toString());
		Assert.assertEquals("java.lang.String", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Text")).getReturnType().getClassName());
		
		Assert.assertEquals("HIPPO_HTML", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippostd:html")).getType().toString());
		Assert.assertEquals("org.hippoecm.hst.content.beans.standard.HippoHtml", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippostd:html")).getReturnType().getClassName());
		
		Assert.assertEquals("NODE", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippostd:date")).getType().toString());
		Assert.assertEquals("org.hippoecm.hst.content.beans.standard.HippoBean", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippostd:date")).getReturnType().getClassName());
		
		Assert.assertEquals("LINKED_BEAN", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippo:mirror")).getType().toString());
		Assert.assertEquals("org.hippoecm.hst.content.beans.standard.HippoDocumentBean", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippo:mirror")).getReturnType().getClassName());
		
		Assert.assertEquals("LINKED_BEAN", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippo:resource")).getType().toString());
		Assert.assertEquals("org.hippoecm.hst.content.beans.standard.HippoDocumentBean", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippo:resource")).getReturnType().getClassName());
		
		Assert.assertEquals("NODE", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippogallerypicker:imagelink")).getType().toString());
		Assert.assertEquals("org.hippoecm.hst.content.beans.standard.HippoBean", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippogallerypicker:imagelink")).getReturnType().getClassName());
		
		Assert.assertEquals("NODE", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "mavenhippoplugindemo:CompoundType")).getType().toString());
		Assert.assertEquals("com.aharpour.ebrahim.beans.CompoundType", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "mavenhippoplugindemo:CompoundType")).getReturnType().getClassName());

	}
	
	private ContentTypeBean getContentTypeBean() {
		File xml = new File(ClassLoader.getSystemResource("DocumentType.xml").getFile());
		Node node = JAXB.unmarshal(xml, Node.class);
		ContentTypeBean contentTypeBean = new ContentTypeBean(node);
		return contentTypeBean;
	}
}
