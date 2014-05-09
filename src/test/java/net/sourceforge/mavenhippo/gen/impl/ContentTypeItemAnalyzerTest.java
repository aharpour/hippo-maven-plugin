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
import java.util.Map;

import javax.xml.bind.JAXB;

import junit.framework.Assert;
import net.sourceforge.mavenhippo.gen.ContentTypeItemAnalyzer;
import net.sourceforge.mavenhippo.gen.DefaultPackageHandler;
import net.sourceforge.mavenhippo.gen.PackageHandler;
import net.sourceforge.mavenhippo.gen.Utils;
import net.sourceforge.mavenhippo.jaxb.Node;
import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.HippoBeanClass;

import org.junit.Test;


/**
 * @author Ebrahim Aharpour
 * 
 */
public class ContentTypeItemAnalyzerTest {

	private final Map<String, HippoBeanClass> beansOnClassPath = new HashMap<String, HippoBeanClass>();
	private final Map<String, HippoBeanClass> beansInProject = new HashMap<String, HippoBeanClass>();
	private final HashSet<String> namespaces = new HashSet<String>();
	private final PackageHandler packageHandler = new DefaultPackageHandler(beansOnClassPath, beansInProject);

	public ContentTypeItemAnalyzerTest() {
		beansOnClassPath.put("hippostd:html", new HippoBeanClass("org.hippoecm.hst.content.beans.standard",
				"HippoHtml", "hippostd:html"));
		beansOnClassPath.put("mavenhippoplugindemo:CompoundType", new HippoBeanClass("com.aharpour.ebrahim.beans",
				"CompoundType", "mavenhippoplugindemo:CompoundType"));

		beansInProject.put("hippostd:html", new HippoBeanClass("org.hippoecm.hst.content.beans.standard", "HippoHtml2",
				"hippostd:html"));

		namespaces.add("mavenhippoplugindemo");
	}

	@Test
	public void AnalyzerTest() {
		ContentTypeItemAnalyzer contentTypeItemAnalyzer = new ContentTypeItemAnalyzer(beansOnClassPath, beansInProject,
				namespaces, packageHandler);
		ContentTypeBean contentTypeBean = getContentTypeBean();

		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer
				.analyze(Utils.getItemByType(contentTypeBean, "Boolean")).getType().toString());
		Assert.assertEquals("java.lang.Boolean",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Boolean")).getReturnType()
						.getClassName());

		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Date"))
				.getType().toString());
		Assert.assertEquals("java.util.Calendar",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Date")).getReturnType()
						.getClassName());

		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer
				.analyze(Utils.getItemByType(contentTypeBean, "Docbase")).getType().toString());
		Assert.assertEquals("java.lang.String",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Docbase")).getReturnType()
						.getClassName());

		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Double"))
				.getType().toString());
		Assert.assertEquals("java.lang.Double",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Double")).getReturnType()
						.getClassName());

		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Html"))
				.getType().toString());
		Assert.assertEquals("java.lang.String",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Html")).getReturnType()
						.getClassName());

		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Long"))
				.getType().toString());
		Assert.assertEquals("java.lang.Long",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Long")).getReturnType()
						.getClassName());

		Assert.assertEquals("PROPERTY",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Password")).getType().toString());
		Assert.assertEquals("java.lang.String",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Password")).getReturnType()
						.getClassName());

		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "String"))
				.getType().toString());
		Assert.assertEquals("java.lang.String",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "String")).getReturnType()
						.getClassName());

		Assert.assertEquals("PROPERTY", contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Text"))
				.getType().toString());
		Assert.assertEquals("java.lang.String",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "Text")).getReturnType()
						.getClassName());

		Assert.assertEquals("HIPPO_HTML",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippostd:html")).getType()
						.toString());
		Assert.assertEquals("org.hippoecm.hst.content.beans.standard.HippoHtml",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippostd:html")).getReturnType()
						.getClassName());

		Assert.assertEquals("NODE",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippostd:date")).getType()
						.toString());
		Assert.assertEquals("org.hippoecm.hst.content.beans.standard.HippoBean",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippostd:date")).getReturnType()
						.getClassName());

		Assert.assertEquals("LINKED_BEAN",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippo:mirror")).getType()
						.toString());
		Assert.assertEquals("org.hippoecm.hst.content.beans.standard.HippoBean", contentTypeItemAnalyzer
				.analyze(Utils.getItemByType(contentTypeBean, "hippo:mirror")).getReturnType().getClassName());

		Assert.assertEquals("LINKED_BEAN",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippo:resource")).getType()
						.toString());
		Assert.assertEquals("org.hippoecm.hst.content.beans.standard.HippoBean", contentTypeItemAnalyzer
				.analyze(Utils.getItemByType(contentTypeBean, "hippo:resource")).getReturnType().getClassName());

		Assert.assertEquals("LINKED_BEAN",
				contentTypeItemAnalyzer.analyze(Utils.getItemByType(contentTypeBean, "hippogallerypicker:imagelink"))
						.getType().toString());
		Assert.assertEquals("org.hippoecm.hst.content.beans.standard.HippoGalleryImageSetBean", contentTypeItemAnalyzer
				.analyze(Utils.getItemByType(contentTypeBean, "hippogallerypicker:imagelink")).getReturnType()
				.getClassName());

		Assert.assertEquals(
				"NODE",
				contentTypeItemAnalyzer
						.analyze(Utils.getItemByType(contentTypeBean, "mavenhippoplugindemo:CompoundType")).getType()
						.toString());
		Assert.assertEquals(
				"com.aharpour.ebrahim.beans.CompoundType",
				contentTypeItemAnalyzer
						.analyze(Utils.getItemByType(contentTypeBean, "mavenhippoplugindemo:CompoundType"))
						.getReturnType().getClassName());

	}

	private ContentTypeBean getContentTypeBean() {
		Node node = JAXB.unmarshal(ClassLoader.getSystemResourceAsStream("DocumentType.xml"), Node.class);
		ContentTypeBean contentTypeBean = new ContentTypeBean(node, new HashMap<String, String>());
		return contentTypeBean;
	}
}
