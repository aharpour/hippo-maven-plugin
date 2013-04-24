package com.aharpour.ebrahim.gen;

import java.io.File;
import java.util.List;

import org.mockito.Mockito;

import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer;
import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer.AnalyzerResult;
import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer.Type;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.utils.NamespaceUtils;

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
