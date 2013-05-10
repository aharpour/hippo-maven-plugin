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
package com.aharpour.ebrahim.gen;

import java.util.Map;
import java.util.Set;

import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.HippoBeanClass;

/**
 * @author Ebrahim Aharpour
 * 
 */
public abstract class SupperClassHandler extends ClasspathAware {

	protected final ClassLoader classLoader;
	protected final Set<String> namespaces;
	protected ClassNameHandler classNameHandler;

	public SupperClassHandler(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
			ClassLoader classLoader, Set<String> namespaces) {
		super(beansOnClassPath, beansInProject);
		this.classLoader = classLoader;
		this.namespaces = namespaces;
	}

	public void setClassNameHandler(ClassNameHandler classNameHandler) {
		this.classNameHandler = classNameHandler;
	}

	public abstract ClassReference getSupperClass(ContentTypeBean contentTypeBean, ImportRegistry importRegistry,
			String packageName);
}
