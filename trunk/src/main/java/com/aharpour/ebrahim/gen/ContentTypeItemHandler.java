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

import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.model.HippoBeanClass;

/**
 * @author Ebrahim Aharpour
 * 
 */
public abstract class ContentTypeItemHandler extends ClasspathAware {

	protected Set<String> namespaces;
	protected PackageHandler packageHandler;

	public ContentTypeItemHandler(Map<String, HippoBeanClass> beansOnClassPath,
			Map<String, HippoBeanClass> beansInProject, Set<String> namespaces, PackageHandler packageHandler) {
		super(beansOnClassPath, beansInProject);
		this.namespaces = namespaces;
		this.packageHandler = packageHandler;
	}

	/**
	 * 
	 * 
	 * @param item
	 * @param importRegistry
	 * @return null if does not want to handle this item.
	 */
	public abstract HandlerResponse handle(Item item, ImportRegistry importRegistry);

}
