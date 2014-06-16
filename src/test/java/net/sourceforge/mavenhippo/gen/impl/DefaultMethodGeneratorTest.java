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

import net.sourceforge.mavenhippo.gen.ClassReference;
import net.sourceforge.mavenhippo.gen.ContentTypeItemAnalyzer.Type;
import net.sourceforge.mavenhippo.gen.DefaultMethodGenerator;
import net.sourceforge.mavenhippo.gen.ImportRegistry;
import net.sourceforge.mavenhippo.gen.Utils;
import net.sourceforge.mavenhippo.utils.exceptions.GeneratorException;

import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class DefaultMethodGeneratorTest {

    @Test
    public void hippoHtmlMethodTest() throws GeneratorException {
        ClassReference returnType = new ClassReference(HippoHtml.class);
        DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(Utils.mockAnalyzerResult(
                Type.HIPPO_HTML, returnType), Utils.mockItem("mavenhippoplugindemo:summary", false),
                new ImportRegistry());
        String fragment = htmlMethodGenerator.getFragment();
        Assert.assertEquals("public HippoHtml getSummary() {\r\n" + "    if (this.summary == null) {\r\n"
                + "        this.summary = getHippoHtml(\"mavenhippoplugindemo:summary\");\r\n" + "    }\r\n"
                + "    return this.summary;\r\n" + "}", fragment);
    }

    @Test
    public void linkedItemMethodTest() throws GeneratorException {
        ClassReference returnType = new ClassReference(HippoDocument.class);
        DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(Utils.mockAnalyzerResult(
                Type.LINKED_BEAN, returnType), Utils.mockItem("mavenhippoplugindemo:conference", false),
                new ImportRegistry());
        String fragment = htmlMethodGenerator.getFragment();
        Assert.assertEquals(
                "public HippoDocument getConference() {\r\n    if (this.conference == null) {\r\n"
                        + "        this.conference = getLinkedBean(\"mavenhippoplugindemo:conference\", HippoDocument.class);\r\n"
                        + "    }\r\n    return this.conference;\r\n}", fragment);
    }

    @Test
    public void linkedMultiItemMethodTest() throws GeneratorException {
        ClassReference returnType = new ClassReference(HippoDocument.class);
        DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(Utils.mockAnalyzerResult(
                Type.LINKED_BEAN, returnType), Utils.mockItem("mavenhippoplugindemo:conference", true),
                new ImportRegistry());
        String fragment = htmlMethodGenerator.getFragment();
        Assert.assertEquals(
                "public List<HippoDocument> getConference() {\r\n    if (this.conference == null) {\r\n        this.conference = getLinkedBeans(\"mavenhippoplugindemo:conference\", HippoDocument.class);\r\n    }\r\n    return this.conference;\r\n}",
                fragment);
    }

    @Test
    public void propertyMethodTest() throws GeneratorException {
        ClassReference returnType = new ClassReference(String.class);
        DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(Utils.mockAnalyzerResult(Type.PROPERTY,
                returnType), Utils.mockItem("mavenhippoplugindemo:name", false), new ImportRegistry());
        String fragment = htmlMethodGenerator.getFragment();
        Assert.assertEquals(
                "public String getName() {\r\n    if (this.name == null) {\r\n        this.name = getProperty(\"mavenhippoplugindemo:name\");\r\n    }\r\n    return this.name;\r\n}",
                fragment);
    }

    @Test
    public void propertyMultivalueMethodTest() throws GeneratorException {
        ClassReference returnType = new ClassReference(String.class);
        DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(Utils.mockAnalyzerResult(Type.PROPERTY,
                returnType), Utils.mockItem("mavenhippoplugindemo:address", true), new ImportRegistry());
        String fragment = htmlMethodGenerator.getFragment();
        Assert.assertEquals(
                "public String[] getAddress() {\r\n    if (this.address == null) {\r\n        this.address = getProperty(\"mavenhippoplugindemo:address\");\r\n    }\r\n    return this.address;\r\n}",
                fragment);
    }

    @Test
    public void nodeMethodTest() throws GeneratorException {
        ClassReference returnType = new ClassReference("com.aharpour.ebrahim.beans.Item");
        DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(Utils.mockAnalyzerResult(Type.NODE,
                returnType), Utils.mockItem("mavenhippoplugindemo:item", false), new ImportRegistry());
        String fragment = htmlMethodGenerator.getFragment();
        Assert.assertEquals(
                "public Item getItem() {\r\n    if (this.item == null) {\r\n        this.item = getBean(\"mavenhippoplugindemo:item\", Item.class);\r\n    }\r\n    return this.item;\r\n}",
                fragment);
    }

    @Test
    public void nodeMultiItemMethodTest() throws GeneratorException {
        ClassReference returnType = new ClassReference("com.aharpour.ebrahim.beans.Item");
        DefaultMethodGenerator htmlMethodGenerator = new DefaultMethodGenerator(Utils.mockAnalyzerResult(Type.NODE,
                returnType), Utils.mockItem("mavenhippoplugindemo:items", true), new ImportRegistry());
        String fragment = htmlMethodGenerator.getFragment();
        Assert.assertEquals(
                "public List<Item> getItems() {\r\n    if (this.items == null) {\r\n        this.items = getChildBeansByName(\"mavenhippoplugindemo:items\");\r\n    }\r\n    return this.items;\r\n}",
                fragment);
    }

}
