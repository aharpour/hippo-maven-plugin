package com.aharpour.ebrahim.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

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
	
	public static String renderTemplate(String templatePath, Map<String, Object> model) throws TemplateException, IOException {
		if (StringUtils.isBlank(templatePath) ||  model == null) {
			throw new IllegalArgumentException("both templatePath and model are required.");
		}
		Template tempalte = FreemarkerUtils.getTempalte(templatePath);
		StringWriter stringWriter = new StringWriter();
		tempalte.process(model, stringWriter);
		return stringWriter.toString();
	}
}
