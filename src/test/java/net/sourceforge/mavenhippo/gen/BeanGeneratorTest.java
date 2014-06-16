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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.xml.bind.JAXB;

import junit.framework.Assert;
import net.sourceforge.mavenhippo.jaxb.Node;
import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.ContentTypeBean.ContentTypeException;
import net.sourceforge.mavenhippo.model.HippoBeanClass;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.junit.After;
import org.junit.Before;
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

    private File tempFolder;
    private File generatedFolder;
    private File beansFolder;
    private File baseDocument;
    private File testCompound;
    private File myCompound;
    private File newsDocument;
    private File carouselBannerPickerMixin;
    static {

    }

    @Before
    public void generate() throws ContentTypeException, FileNotFoundException, IOException, TemplateException {
        tempFolder = Files.createTempDir();
        generatedFolder = new File(tempFolder.getAbsolutePath() + File.separator + "generated");
        beansFolder = new File(generatedFolder.getAbsoluteFile() + File.separator + "beans");

        if (!generatedFolder.exists() || !generatedFolder.isDirectory()) {
            generatedFolder.mkdir();
        }
        if (!beansFolder.exists() || !beansFolder.isDirectory()) {
            beansFolder.mkdir();
        }
        BeanGenerator beanGenerator = new BeanGenerator(beansOnClassPath, beansInProject, namespaces.keySet(),
                new HashMap<String, ContentTypeBean>(), null);

        baseDocument = generateClass(beanGenerator, "basedocument.xml");
        testCompound = generateClass(beanGenerator, "TestCompound.xml");
        myCompound = generateClass(beanGenerator, "MyCompoundType.xml");
        newsDocument = generateClass(beanGenerator, "newsdocumentedited.xml");
        // mix-in
        carouselBannerPickerMixin = generateClass(beanGenerator, "carouselbannerpicker.xml");
    }

    @Test
    public void outputTest() throws ContentTypeException, FileNotFoundException, IOException, TemplateException {
        compareFiles("beans/Basedocument.txt", baseDocument);
        compareFiles("beans/TestCompound.txt", testCompound);
        compareFiles("beans/MyCompoundType.txt", myCompound);
        compareFiles("beans/CarouselBannerPicker.txt", carouselBannerPickerMixin);
        compareFiles("beans/NewsDocument.txt", newsDocument);

    }

    private void compareFiles(String pathToExpectedFile, File generatedFile) throws FileNotFoundException, IOException {
        BufferedReader generated = new BufferedReader(new FileReader(generatedFile));
        BufferedReader expected = new BufferedReader(new InputStreamReader(
                ClassLoader.getSystemResourceAsStream(pathToExpectedFile)));
        String line;
        do {
            line = expected.readLine();
            Assert.assertEquals(line, generated.readLine());
        } while (line != null);

        generated.close();
    }

    @Test
    public void compilationTest() throws ContentTypeException, TemplateException, IOException, ClassNotFoundException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        Assert.assertEquals(0, compiler.run(System.in, System.out, System.err, baseDocument.getAbsolutePath()));
        Assert.assertEquals(0, compiler.run(System.in, System.out, System.err, newsDocument.getAbsolutePath()));
        Assert.assertEquals(0, compiler.run(System.in, System.out, System.err, testCompound.getAbsolutePath()));
        URLClassLoader classLoader = new URLClassLoader(new URL[] { tempFolder.toURI().toURL() });

        Assert.assertNotNull(Class.forName("generated.beans.TestCompound", true, classLoader));
        Assert.assertEquals(0, compiler.run(System.in, System.out, System.err, myCompound.getAbsolutePath()));

        Assert.assertEquals(0,
                compiler.run(System.in, System.out, System.err, carouselBannerPickerMixin.getAbsolutePath()));
    }

    @After
    public void cleanUp() {

        Utils.forcefulDeletion(tempFolder);
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
