package com.aharpour.ebrahim.gen.impl;

import java.util.List;

import org.mockito.Mockito;

import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer.AnalyzerResult;
import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer.Type;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.utils.NamespaceUtils;

public class Utils {
	private Utils() {
	}

	static Item getItemByType(ContentTypeBean contentTypeBean, String itemType) {
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
		return new ContentTypeItemAnalyzer(null, null, null, null).new AnalyzerResult(type, returnType);
	}

	public static Item mockItem(String relativePath, boolean multiple) {
		Item result = Mockito.mock(Item.class);
		Mockito.when(result.getSimpleName()).thenReturn(NamespaceUtils.getSimpleName(relativePath));
		Mockito.when(result.getNamespace()).thenReturn(NamespaceUtils.getNamespace(relativePath));
		Mockito.when(result.getRelativePath()).thenReturn(relativePath);
		Mockito.when(result.isMultiple()).thenReturn(multiple);
		return result;
	}
}
