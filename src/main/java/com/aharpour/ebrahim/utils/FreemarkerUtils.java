package com.aharpour.ebrahim.utils;

import java.io.IOException;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerUtils {

	private FreemarkerUtils() {
	}

	public static Template getTempalte(String path) {
		try {
			Template result;
			Configuration configuration = new Configuration();
			ClassTemplateLoader templateLoader = new ClassTemplateLoader(FreemarkerUtils.class, "/");
			configuration.setTemplateLoader(templateLoader);
			result = configuration.getTemplate(path);
			return result;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
