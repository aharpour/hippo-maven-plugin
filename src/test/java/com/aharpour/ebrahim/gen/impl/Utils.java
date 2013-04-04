package com.aharpour.ebrahim.gen.impl;

import java.util.List;

import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.ContentTypeBean.Item;

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
}
