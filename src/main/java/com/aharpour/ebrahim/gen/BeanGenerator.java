package com.aharpour.ebrahim.gen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import com.aharpour.ebrahim.gen.impl.DefaultItemHandler;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.ContentTypeBean.ContentTypeException;
import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.model.HippoBeanClass;
import com.aharpour.ebrahim.utils.FreemarkerUtils;
import com.aharpour.ebrahim.utils.ReflectionUtils;

import freemarker.template.TemplateException;

public class BeanGenerator {

	private final Map<String, HippoBeanClass> beansOnClassPath;
	private final Map<String, HippoBeanClass> beansInProject;
	private final String packageToSearch;

	private PackageGenerator packageNameGenerator;
	private List<ContentTypeItemHandler> handlersChain = new ArrayList<ContentTypeItemHandler>();
	private SupperClassHandler supperClassHandler;

	public BeanGenerator(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
		this(beansOnClassPath, beansInProject, "");
	}

	public BeanGenerator(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
			String packageToSearch) {
		this.beansOnClassPath = beansOnClassPath;
		this.beansInProject = beansInProject;
		this.packageToSearch = packageToSearch;
		initialize();
	}

	private void initialize() {
		handlersChain.add(new DefaultItemHandler(beansOnClassPath, beansInProject));
		SortedSet<Class<? extends ContentTypeItemHandler>> classes = ReflectionUtils
				.getListOfHandlerClasses(packageToSearch);

	}

	public String generateBean(ContentTypeBean contentTypeBean) throws ContentTypeException, TemplateException,
			IOException {
		ImportRegistry importRegistry = new ImportRegistry();
		List<MethodGenerator> mehtods = new ArrayList<MethodGenerator>();
		List<PropertyGenerator> properties = new ArrayList<PropertyGenerator>();
		List<Item> items = contentTypeBean.getItems(contentTypeBean.getNamespace());

		for (Item item : items) {
			for (int i = handlersChain.size() - 1; i >= 0; i--) {
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

		String templatePath = BeanGenerator.class.getPackage().getName().replace('.', '/') + "/class-template.ftl";
		Map<String, Object> model = new HashMap<String, Object>();
		return FreemarkerUtils.renderTemplate(templatePath, model);

	}

}
