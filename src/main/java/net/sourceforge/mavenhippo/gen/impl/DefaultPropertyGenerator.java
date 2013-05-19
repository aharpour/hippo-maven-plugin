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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.mavenhippo.gen.AnnotationGenerator;
import net.sourceforge.mavenhippo.gen.ClassReference;
import net.sourceforge.mavenhippo.gen.ImportRegistry;
import net.sourceforge.mavenhippo.gen.PropertyGenerator;
import net.sourceforge.mavenhippo.gen.impl.ContentTypeItemAnalyzer.AnalyzerResult;
import net.sourceforge.mavenhippo.gen.impl.ContentTypeItemAnalyzer.Type;
import net.sourceforge.mavenhippo.model.ContentTypeBean.Item;
import net.sourceforge.mavenhippo.utils.FreemarkerUtils;


/**
 * @author Ebrahim Aharpour
 * 
 */
public class DefaultPropertyGenerator implements PropertyGenerator {

	private final ClassReference type;
	private final String fieldName;
	private final boolean multiple;
	private final Type propertyType;
	private ClassReference listClass;

	public DefaultPropertyGenerator(AnalyzerResult analyzerResult, Item item, ImportRegistry importRegistry) {
		this.type = analyzerResult.getReturnType();
		this.propertyType = analyzerResult.getType();
		this.fieldName = item.getSimpleName();
		this.multiple = item.isMultiple();
		importRegistry.register(type);
		if (this.multiple) {
			listClass = new ClassReference(List.class);
			importRegistry.register(listClass);
		}
	}

	@Override
	public String getFragment() {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("multiple", multiple);
			if (multiple) {
				model.put("list", listClass);
			}
			model.put("type", type);
			model.put("fieldName", fieldName);
			model.put("basicType", Type.PROPERTY == propertyType);
			return FreemarkerUtils
					.renderTemplate("net/sourceforge/mavenhippo/gen/impl/default-property-generator.ftl", model);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<AnnotationGenerator> getAnnotations() {
		return new ArrayList<AnnotationGenerator>();
	}
}