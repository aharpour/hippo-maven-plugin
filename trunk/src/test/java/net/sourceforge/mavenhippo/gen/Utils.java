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
import java.util.List;

import net.sourceforge.mavenhippo.gen.impl.ContentTypeItemAnalyzer.AnalyzerResult;
import net.sourceforge.mavenhippo.gen.impl.ContentTypeItemAnalyzer.Type;
import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.ContentTypeBean.Item;
import net.sourceforge.mavenhippo.utils.NamespaceUtils;

import org.mockito.Mockito;


/**
 * @author Ebrahim Aharpour
 * 
 */
public class Utils {
	private Utils() {
	}

	public static Item getItemByType(ContentTypeBean contentTypeBean, String itemType) {
		Item result = null;
		List<Item> items = contentTypeBean.getItems();
		for (Item item : items) {
			if (itemType.equals(item.getType())) {
				result = item;
				break;
			}
		}
		return result;
	}

	public static AnalyzerResult mockAnalyzerResult(Type type, ClassReference returnType) {
		return new AnalyzerResult(type, returnType);
	}

	public static Item mockItem(String relativePath, boolean multiple) {
		Item result = Mockito.mock(Item.class);
		Mockito.when(result.getSimpleName()).thenReturn(NamespaceUtils.getSimpleName(relativePath));
		Mockito.when(result.getNamespace()).thenReturn(NamespaceUtils.getNamespace(relativePath));
		Mockito.when(result.getRelativePath()).thenReturn(relativePath);
		Mockito.when(result.isMultiple()).thenReturn(multiple);
		return result;
	}

	public static boolean forcefulDeletion(File file) {
		if (file == null) {
			throw new IllegalArgumentException("file argument is required");
		}
		boolean result = true;
		if (file.exists()) {
			if (file.isDirectory()) {
				for (File child : file.listFiles()) {
					result = forcefulDeletion(child) && result;
					if (child.isDirectory()) {
						result = child.delete() && result;
					}
				}
			}
			result = file.delete() && result;
		}
		return result;
	}
}
