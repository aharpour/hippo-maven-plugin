package com.aharpour.ebrahim.gen;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.ContentTypeBean.ContentTypeException;
import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.model.HippoBeanClass;
import com.aharpour.ebrahim.utils.FreemarkerUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class BeanGenerator {
	
	private final Map<String, HippoBeanClass> beansOnClassPath;
	private final Map<String, HippoBeanClass> beansInProject;
	
	private PackageGenerator packageNameGenerator;
	private List<ContentTypeItemHandler> handlersChain;
	private SupperClassHandler supperClassHandler;
	
	
	public BeanGenerator(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
		this.beansOnClassPath = beansOnClassPath;
		this.beansInProject = beansInProject;
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		
	}

	
	
	public String generateBean(ContentTypeBean contentTypeBean) throws ContentTypeException, TemplateException, IOException {
		ImportRegistry importRegistry = new ImportRegistry();
		List<MethodGenerator> mehtods = new ArrayList<MethodGenerator>();
		List<PropertyGenerator> properties = new ArrayList<PropertyGenerator>();
		List<Item> items = contentTypeBean.getItems(contentTypeBean.getNamespace());
		
		for (Item item : items) {
			for (int i = handlersChain.size() - 1; i >= 0 ; i--) {
				HandlerResponse resp = handlersChain.get(i).handle(item, importRegistry);
				if (resp != null) {
					if (resp.getMethodGenerators() != null) {
						mehtods.addAll(resp.getMethodGenerators());
					}
					if (resp.getPropertyGenerators() != null) {
						properties.addAll(resp.getPropertyGenerators());
					}
					break;
				}
			}	
		}
		
		Template tempalte = FreemarkerUtils.getTempalte(BeanGenerator.class.getPackage().getName().replace('.', '/') + "/class-template.ftl");
		StringWriter stringWriter = new StringWriter();
		Map<String, Object> model = new HashMap<String, Object>();
		tempalte.process(model, stringWriter);
		return stringWriter.toString();
		
		
	}
	
	
}
