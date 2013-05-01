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
package com.aharpour.ebrahim.gen.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoDocument;

import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.ImportRegistry;
import com.aharpour.ebrahim.gen.SupperClassHandler;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.HippoBeanClass;
import com.aharpour.ebrahim.utils.Constants;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class DefaultSupperClassHandler extends SupperClassHandler {

	public DefaultSupperClassHandler(Map<String, HippoBeanClass> beansOnClassPath,
			Map<String, HippoBeanClass> beansInProject) {
		super(beansOnClassPath, beansInProject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ClassReference getSupperClass(ContentTypeBean contentTypeBean, ImportRegistry importRegistry) {
		ClassReference result = new ClassReference(HippoDocument.class);
		List<String> supertypes = contentTypeBean.getSupertypes();
		SortedSet<Class<? extends HippoBean>> supperClasses = new TreeSet<Class<? extends HippoBean>>(
				new Comparator<Class<? extends HippoBean>>() {

					@Override
					public int compare(Class<? extends HippoBean> o1, Class<? extends HippoBean> o2) {
						int result;
						if (o1 != null && o2 == null) {
							result = 1;
						} else if (o1 == null && o2 != null) {
							result = -1;
						} else if (o1.equals(o1)) {
							result = 0;
						} else if (o1.isAssignableFrom(o2)) {
							result = 1;
						} else if (o2.isAssignableFrom(o1)) {
							result = -1;
						} else {
							throw new IllegalArgumentException("the given arguments are not comparable");
						}

						return result;
					}
				});
		for (String superType : supertypes) {
			if (Constants.NodeType.HIPPO_COMPOUND.equals(superType)) {
				Class<HippoCompound> hippoCompoundClass = HippoCompound.class;
				supperClasses.add(hippoCompoundClass);
			} else if (beansOnClassPath.containsKey(superType)) {
				HippoBeanClass hippoBeanClass = beansOnClassPath.get(superType);
				Class<?> clazz = getClass(hippoBeanClass);
				if (HippoBean.class.isAssignableFrom(clazz)) {
					supperClasses.add((Class<? extends HippoBean>) clazz);
				}
			}
		}
		if (supperClasses.size() > 0) {
			result = new ClassReference(supperClasses.last());
		}
		importRegistry.register(result);
		return result;
	}

	private Class<?> getClass(HippoBeanClass hippoBeanClass) {
		try {
			return Class.forName(hippoBeanClass.getFullyQualifiedName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
