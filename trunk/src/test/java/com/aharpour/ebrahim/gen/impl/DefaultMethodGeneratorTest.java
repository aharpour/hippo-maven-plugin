package com.aharpour.ebrahim.gen.impl;

import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.ImportRegistry;
import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer.Type;

public class DefaultMethodGeneratorTest {

	@Test
	public void hippoHtmlMethodTest() {
		ClassReference returnType = new ClassReference(HippoHtml.class);
		DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(new ContentTypeItemAnalyzer(null, null).new AnalyzerResult(Type.HIPPO_HTML, returnType), Utils.mockItem("estro:summary", false), new ImportRegistry());
		String fragment = htmlMethodGenerator.getFragment();
		Assert.assertEquals("public HippoHtml getSummary() {\r\n" + 
				"	if (this.summary == null) {\r\n" + 
				"		this.summary = getHippoHtml(\"estro:summary\");\r\n" + 
				"	}\r\n" + 
				"	return this.summary;\r\n" + 
				"}", fragment);
	}
	
	@Test
	public void linkedItemMethodTest() {
		ClassReference returnType = new ClassReference(HippoDocument.class);
		DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(new ContentTypeItemAnalyzer(null, null).new AnalyzerResult(Type.LINKED_BEAN, returnType), Utils.mockItem("estro:conference", false), new ImportRegistry());
		String fragment = htmlMethodGenerator.getFragment();
		Assert.assertEquals("public HippoDocument getConference() {\r\n" + 
				"	if (this.conference == null) {\r\n" + 
				"		this.conference = getLinkedBean(\"estro:conference\", HippoDocument.class);\r\n" + 
				"	}\r\n" + 
				"	return this.conference;\r\n" + 
				"}", fragment);
	}
	
	@Test
	public void linkedMultiItemMethodTest() {
		ClassReference returnType = new ClassReference(HippoDocument.class);
		DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(new ContentTypeItemAnalyzer(null, null).new AnalyzerResult(Type.LINKED_BEAN, returnType), Utils.mockItem("estro:conference", true), new ImportRegistry());
		String fragment = htmlMethodGenerator.getFragment();
		System.out.println(fragment);
		Assert.assertEquals("public List<HippoDocument> getConference() {\r\n" + 
				"	if (this.conference == null) {\r\n" + 
				"		this.conference = getLinkedBeans(\"estro:conference\", HippoDocument.class);\r\n" + 
				"	}\r\n" + 
				"	return this.conference;\r\n" + 
				"}", fragment);
	}
	

	
}
