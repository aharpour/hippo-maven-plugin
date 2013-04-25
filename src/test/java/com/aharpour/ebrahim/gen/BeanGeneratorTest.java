package com.aharpour.ebrahim.gen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.xml.bind.JAXB;

import junit.framework.Assert;

import org.junit.Test;

import com.aharpour.ebrahim.jaxb.Node;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.ContentTypeBean.ContentTypeException;
import com.aharpour.ebrahim.model.HippoBeanClass;
import com.google.common.io.Files;

import freemarker.template.TemplateException;

public class BeanGeneratorTest {

	private final Map<String, HippoBeanClass> beansOnClassPath = new HashMap<String, HippoBeanClass>() {
		private static final long serialVersionUID = 1L;
		{
			put("hippostd:html", new HippoBeanClass("org.hippoecm.hst.content.beans.standard", "HippoHtml",
					"hippostd:html"));
			put("mavenhippoplugindemo:CompoundType", new HippoBeanClass("com.aharpour.ebrahim.beans", "CompoundType",
					"mavenhippoplugindemo:CompoundType"));
		}
	};
	private final Map<String, HippoBeanClass> beansInProject = new HashMap<String, HippoBeanClass>() {
		private static final long serialVersionUID = 1L;
		{
			put("hippostd:html", new HippoBeanClass("org.hippoecm.hst.content.beans.standard", "HippoHtml2",
					"hippostd:html"));
		}
	};

	private final HashSet<String> namespaces = new HashSet<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("mavenhippoplugindemo");
		}
	};

	private static File tempFolder = Files.createTempDir();
	private static File generatedFolder = new File(tempFolder.getAbsolutePath() + File.separator + "generated");
	private static File beansFolder = new File(generatedFolder.getAbsoluteFile() + File.separator + "beans");
	static {
		if (!generatedFolder.exists() || !generatedFolder.isDirectory()) {
			generatedFolder.mkdir();
		}
		if (!beansFolder.exists() || !beansFolder.isDirectory()) {
			beansFolder.mkdir();
		}
	}

	@Test
	public void compilationTest() throws ContentTypeException, TemplateException, IOException, ClassNotFoundException {
		try {
			BeanGenerator beanGenerator = new BeanGenerator(beansOnClassPath, beansInProject, namespaces);

			File testCompound = generateClass(beanGenerator, "TestCompound.xml");
			File myCompound = generateClass(beanGenerator, "MyCompoundType.xml");
			File newsdocumentedited = generateClass(beanGenerator, "newsdocumentedited.xml");

			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			Assert.assertEquals(0,
					compiler.run(System.in, System.out, System.err, newsdocumentedited.getAbsolutePath()));
			Assert.assertEquals(0, compiler.run(System.in, System.out, System.err, testCompound.getAbsolutePath()));
			URLClassLoader classLoader = new URLClassLoader(new URL[] { tempFolder.toURI().toURL() });

			Assert.assertNotNull(Class.forName("generated.beans.TestCompound", true, classLoader));
			Assert.assertEquals(0, compiler.run(System.in, System.out, System.err, myCompound.getAbsolutePath()));
		} finally {
			Utils.forcefulDeletion(tempFolder);
		}
	}

	private File generateClass(BeanGenerator beanGenerator, String xmlFileName) throws IOException,
			FileNotFoundException, ContentTypeException, TemplateException {
		ContentTypeBean contentType = getContentTypeBean(xmlFileName);
		File myCompound = new File(beansFolder.getAbsoluteFile() + File.separator
				+ beanGenerator.getClassName(contentType) + ".java");
		myCompound.createNewFile();
		PrintWriter out = new PrintWriter(myCompound);
		String generateBean = beanGenerator.generateBean(contentType);
		out.print(generateBean);
		out.close();
		return myCompound;
	}

	private ContentTypeBean getContentTypeBean(String fileName) {
		Node node = JAXB.unmarshal(ClassLoader.getSystemResourceAsStream(fileName), Node.class);
		ContentTypeBean contentTypeBean = new ContentTypeBean(node);
		return contentTypeBean;
	}

}
