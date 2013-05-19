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
package net.sourceforge.mavenhippo.gen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.xml.bind.JAXB;

import junit.framework.Assert;

import net.sourceforge.mavenhippo.gen.BeanGenerator;
import net.sourceforge.mavenhippo.jaxb.Node;
import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.HippoBeanClass;
import net.sourceforge.mavenhippo.model.ContentTypeBean.ContentTypeException;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.junit.Test;

import com.google.common.io.Files;

import freemarker.template.TemplateException;

/**
 * @author Ebrahim Aharpour
 * 
 */
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

	@SuppressWarnings("unchecked")
	private final Map<String, String> namespaces = new DualHashBidiMap() {
		private static final long serialVersionUID = 1L;
		{
			put("mavenhippoplugindemo", "http://www.onehippo.org/mavenhippoplugindemo/nt/1.0");
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
			BeanGenerator beanGenerator = new BeanGenerator(beansOnClassPath, beansInProject, namespaces.keySet(), null);

			File baseDocument = generateClass(beanGenerator, "basedocument.xml");
			File testCompound = generateClass(beanGenerator, "TestCompound.xml");
			File myCompound = generateClass(beanGenerator, "MyCompoundType.xml");
			File newsdocumentedited = generateClass(beanGenerator, "newsdocumentedited.xml");

			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			Assert.assertEquals(0, compiler.run(System.in, System.out, System.err, baseDocument.getAbsolutePath()));
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
		@SuppressWarnings("unchecked")
		ContentTypeBean contentTypeBean = new ContentTypeBean(node, ((BidiMap) namespaces).inverseBidiMap());
		return contentTypeBean;
	}
}
