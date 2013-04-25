package com.aharpour.ebrahim.gen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.hippoecm.hst.content.beans.Node;

import com.aharpour.ebrahim.gen.impl.DefaultClassNameHandler;
import com.aharpour.ebrahim.gen.impl.DefaultItemHandler;
import com.aharpour.ebrahim.gen.impl.DefaultPackageHandler;
import com.aharpour.ebrahim.gen.impl.DefaultSupperClassHandler;
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
	private final String[] basePackage;

	private PackageHandler packageNameGenerator;
	private List<ContentTypeItemHandler> handlersChain = new ArrayList<ContentTypeItemHandler>();
	private SupperClassHandler supperClassHandler;
	private ClassNameHandler classNameHandler;
	private Set<String> namespaces;

	public BeanGenerator(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
			Set<String> namespaces) {
		this(beansOnClassPath, beansInProject, "", new String[] { "generated", "beans" }, namespaces);
	}

	public BeanGenerator(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
			String packageToSearch, String[] basePackage, Set<String> namespaces) {
		this.beansOnClassPath = beansOnClassPath;
		this.beansInProject = beansInProject;
		this.packageToSearch = packageToSearch;
		this.basePackage = basePackage;
		this.namespaces = namespaces;
		initialize();
	}

	private void initialize() {
		initializePackageHandler();
		initializeHandlersChain(packageNameGenerator);
		initializeSupperClassHandler();
		initializeClassNameHandler();

	}

	public String[] getPackage(ContentTypeBean contentType) {
		return packageNameGenerator.getPackageGenerator(contentType).getPackage();
	}

	public String generateBean(ContentTypeBean contentTypeBean) throws ContentTypeException, TemplateException,
			IOException {
		ImportRegistry importRegistry = new ImportRegistry();
		List<MethodGenerator> methods = new ArrayList<MethodGenerator>();
		List<PropertyGenerator> properties = new ArrayList<PropertyGenerator>();
		List<Item> items = contentTypeBean.getItems(contentTypeBean.getNamespace());

		for (Item item : items) {
			for (int i = handlersChain.size() - 1; i >= 0; i--) {
				HandlerResponse resp = handlersChain.get(i).handle(item, importRegistry);
				if (resp != null) {
					if (resp.getMethodGenerators() != null) {
						methods.addAll(resp.getMethodGenerators());
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
		model.put("contentType", contentTypeBean);
		model.put("addTypeAnnotation", addTypeAnnotation(contentTypeBean, importRegistry));
		model.put("className", classNameHandler.getClassName(contentTypeBean));
		model.put("supperClass", supperClassHandler.getSupperClass(contentTypeBean, importRegistry));
		model.put("package", packageNameGenerator.getPackageGenerator(contentTypeBean));
		model.put("methods", methods);
		model.put("properties", properties);
		model.put("importRegistry", importRegistry);
		return FreemarkerUtils.renderTemplate(templatePath, model);

	}

	private boolean addTypeAnnotation(ContentTypeBean contentTypeBean, ImportRegistry importRegistry)
			throws ContentTypeException {
		boolean result = !beansOnClassPath.containsKey(contentTypeBean.getFullyQualifiedName())
				&& !beansInProject.containsKey(contentTypeBean.getFullyQualifiedName());
		if (result) {
			importRegistry.register(new ClassReference(Node.class));
		}
		return result;
	}

	private void initializeSupperClassHandler() {
		SortedSet<Class<? extends SupperClassHandler>> supperClassHandlers = ReflectionUtils.getSubclassesOfType(
				packageToSearch, SupperClassHandler.class);
		if (supperClassHandlers.size() > 0) {
			supperClassHandler = (SupperClassHandler) ReflectionUtils.instantiate(supperClassHandlers.first(),
					beansOnClassPath, beansInProject);
		} else {
			supperClassHandler = new DefaultSupperClassHandler(beansOnClassPath, beansInProject);
		}
	}

	private void initializeClassNameHandler() {
		SortedSet<Class<? extends ClassNameHandler>> classNameHandlers = ReflectionUtils.getSubclassesOfType(
				packageToSearch, ClassNameHandler.class);
		if (classNameHandlers.size() > 0) {
			classNameHandler = (ClassNameHandler) ReflectionUtils.instantiate(classNameHandlers.first(),
					beansOnClassPath, beansInProject);
		} else {
			classNameHandler = new DefaultClassNameHandler(beansOnClassPath, beansInProject);
		}
	}

	private void initializePackageHandler() {
		SortedSet<Class<? extends PackageHandler>> pageckageHandlers = ReflectionUtils.getSubclassesOfType(
				packageToSearch, PackageHandler.class);
		if (pageckageHandlers.size() > 0) {
			packageNameGenerator = (PackageHandler) ReflectionUtils.instantiate(pageckageHandlers.first(),
					beansOnClassPath, beansInProject);
		} else {
			packageNameGenerator = new DefaultPackageHandler(beansOnClassPath, beansInProject);
		}
		packageNameGenerator.setBasePackage(basePackage);
	}

	private void initializeHandlersChain(PackageHandler packageGenerator) {
		handlersChain.add(new DefaultItemHandler(beansOnClassPath, beansInProject, namespaces, packageGenerator));
		SortedSet<Class<? extends ContentTypeItemHandler>> classes = ReflectionUtils.getHandlerClasses(packageToSearch);
		for (Class<? extends ContentTypeItemHandler> clazz : classes) {
			handlersChain.add((ContentTypeItemHandler) ReflectionUtils.instantiate(clazz, beansOnClassPath,
					beansInProject, namespaces, packageGenerator));
		}
	}

}
