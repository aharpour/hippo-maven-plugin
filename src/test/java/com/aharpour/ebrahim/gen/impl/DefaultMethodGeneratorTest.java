package com.aharpour.ebrahim.gen.impl;

import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.junit.Assert;
import org.junit.Test;

import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.ImportRegistry;
import com.aharpour.ebrahim.gen.Utils;
import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer.Type;

public class DefaultMethodGeneratorTest {

	@Test
	public void hippoHtmlMethodTest() {
		ClassReference returnType = new ClassReference(HippoHtml.class);
		DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(Utils.mockAnalyzerResult(
				Type.HIPPO_HTML, returnType), Utils.mockItem("mavenhippoplugindemo:summary", false),
				new ImportRegistry());
		String fragment = htmlMethodGenerator.getFragment();
		Assert.assertEquals("public HippoHtml getSummary() {\r\n" + "	if (this.summary == null) {\r\n"
				+ "		this.summary = getHippoHtml(\"mavenhippoplugindemo:summary\");\r\n" + "	}\r\n"
				+ "	return this.summary;\r\n" + "}", fragment);
	}

	@Test
	public void linkedItemMethodTest() {
		ClassReference returnType = new ClassReference(HippoDocument.class);
		DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(Utils.mockAnalyzerResult(
				Type.LINKED_BEAN, returnType), Utils.mockItem("mavenhippoplugindemo:conference", false),
				new ImportRegistry());
		String fragment = htmlMethodGenerator.getFragment();
		Assert.assertEquals("public HippoDocument getConference() {\r\n\tif (this.conference == null) {\r\n"
				+ "\t\tthis.conference = getLinkedBean(\"mavenhippoplugindemo:conference\", HippoDocument.class);\r\n"
				+ "	}\r\n\treturn this.conference;\r\n}", fragment);
	}

	@Test
	public void linkedMultiItemMethodTest() {
		ClassReference returnType = new ClassReference(HippoDocument.class);
		DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(Utils.mockAnalyzerResult(
				Type.LINKED_BEAN, returnType), Utils.mockItem("mavenhippoplugindemo:conference", true),
				new ImportRegistry());
		String fragment = htmlMethodGenerator.getFragment();
		Assert.assertEquals(
				"public List<HippoDocument> getConference() {\r\n\tif (this.conference == null) {\r\n\t\tthis.conference = getLinkedBeans(\"mavenhippoplugindemo:conference\", HippoDocument.class);\r\n\t}\r\n\treturn this.conference;\r\n}",
				fragment);
	}

	@Test
	public void propertyMethodTest() {
		ClassReference returnType = new ClassReference(String.class);
		DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(Utils.mockAnalyzerResult(Type.PROPERTY,
				returnType), Utils.mockItem("mavenhippoplugindemo:name", false), new ImportRegistry());
		String fragment = htmlMethodGenerator.getFragment();
		Assert.assertEquals(
				"public String getName() {\r\n\tif (this.name == null) {\r\n\t\tthis.name = getProperty(\"mavenhippoplugindemo:name\");\r\n\t}\r\n\treturn this.name;\r\n}",
				fragment);
	}

	@Test
	public void propertyMultivalueMethodTest() {
		ClassReference returnType = new ClassReference(String.class);
		DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(Utils.mockAnalyzerResult(Type.PROPERTY,
				returnType), Utils.mockItem("mavenhippoplugindemo:address", true), new ImportRegistry());
		String fragment = htmlMethodGenerator.getFragment();
		Assert.assertEquals(
				"public String[] getAddress() {\r\n\tif (this.address == null) {\r\n\t\tthis.address = getProperty(\"mavenhippoplugindemo:address\");\r\n\t}\r\n\treturn this.address;\r\n}",
				fragment);
	}

	@Test
	public void nodeMethodTest() {
		ClassReference returnType = new ClassReference("com.aharpour.ebrahim.beans.Item");
		DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(Utils.mockAnalyzerResult(Type.NODE,
				returnType), Utils.mockItem("mavenhippoplugindemo:item", false), new ImportRegistry());
		String fragment = htmlMethodGenerator.getFragment();
		Assert.assertEquals(
				"public Item getItem() {\r\n\tif (this.item == null) {\r\n\t\tthis.item = getBean(\"mavenhippoplugindemo:item\", Item.class);\r\n\t}\r\n\treturn this.item;\r\n}",
				fragment);
	}

	@Test
	public void nodeMultiItemMethodTest() {
		ClassReference returnType = new ClassReference("com.aharpour.ebrahim.beans.Item");
		DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(Utils.mockAnalyzerResult(Type.NODE,
				returnType), Utils.mockItem("mavenhippoplugindemo:items", true), new ImportRegistry());
		String fragment = htmlMethodGenerator.getFragment();
		Assert.assertEquals(
				"public List<Item> getItems() {\r\n\tif (this.items == null) {\r\n\t\tthis.items = getChildBeansByName(\"mavenhippoplugindemo:items\");\r\n\t}\r\n\treturn this.items;\r\n}",
				fragment);
	}

}
