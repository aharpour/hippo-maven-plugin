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
package net.sourceforge.mavenhippo.handlers;

import java.util.Map;
import java.util.Set;

import net.sourceforge.mavenhippo.gen.ContentTypeItemHandler;
import net.sourceforge.mavenhippo.gen.HandlerResponse;
import net.sourceforge.mavenhippo.gen.ImportRegistry;
import net.sourceforge.mavenhippo.gen.PackageHandler;
import net.sourceforge.mavenhippo.model.HippoBeanClass;
import net.sourceforge.mavenhippo.model.ContentTypeBean.Item;


/**
 * @author Ebrahim Aharpour
 * 
 */
public class Handler1 extends ContentTypeItemHandler {

	public Handler1(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
			Set<String> namespaces, PackageHandler packageHandler) {
		super(beansOnClassPath, beansInProject, namespaces, packageHandler);
	}

	@Override
	public HandlerResponse handle(Item item, ImportRegistry importRegistry) {
		return null;
	}

}
