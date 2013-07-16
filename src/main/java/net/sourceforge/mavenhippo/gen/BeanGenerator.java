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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import net.sourceforge.mavenhippo.gen.impl.DefaultClassNameHandler;
import net.sourceforge.mavenhippo.gen.impl.DefaultItemHandler;
import net.sourceforge.mavenhippo.gen.impl.DefaultPackageHandler;
import net.sourceforge.mavenhippo.gen.impl.DefaultSupperClassHandler;
import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.ContentTypeBean.ContentTypeException;
import net.sourceforge.mavenhippo.model.ContentTypeBean.Item;
import net.sourceforge.mavenhippo.model.HippoBeanClass;
import net.sourceforge.mavenhippo.utils.FreemarkerUtils;
import net.sourceforge.mavenhippo.utils.ReflectionUtils;

import org.hippoecm.hst.content.beans.Node;

import freemarker.template.TemplateException;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class BeanGenerator {

	private final Map<String, HippoBeanClass> beansOnClassPath;
	private final Map<String, HippoBeanClass> beansInProject;
	private final String packageToSearch;
	private final String[] basePackage;
	private final ClassLoader classLoader;
	private final Set<String> namespaces;
	private final Map<String, ContentTypeBean> mixins;

	private PackageHandler packageNameGenerator;
	private List<ContentTypeItemHandler> handlersChain = new ArrayList<ContentTypeItemHandler>();
	private SupperClassHandler supperClassHandler;
	private ClassNameHandler classNameHandler;

	public BeanGenerator(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
			Set<String> namespaces, Map<String, ContentTypeBean> mixins, ClassLoader projectClassLoader) {
		this(beansOnClassPath, beansInProject, "", new String[] { "generated", "beans" }, namespaces, mixins,
				projectClassLoader);
	}

	public BeanGenerator(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
			String packageToSearch, String[] basePackage, Set<String> namespaces, Map<String, ContentTypeBean> mixins,
			ClassLoader projectClassLoader) {
		this.beansOnClassPath = beansOnClassPath;
		this.beansInProject = beansInProject;
		this.packageToSearch = packageToSearch;
		this.basePackage = basePackage;
		this.namespaces = namespaces;
		this.classLoader = projectClassLoader;
		this.mixins = mixins;
		initialize();
	}

	private void initialize() {
		initializePackageHandler();
		initializeHandlersChain(packageNameGenerator);
		initializeClassNameHandler();
		initializeSupperClassHandler();

	}

	public String[] getPackage(ContentTypeBean contentType) {
		return packageNameGenerator.getPackageGenerator(contentType).getPackage();
	}

	public String getClassName(ContentTypeBean contentType) {
		return classNameHandler.getClassName(contentType);
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
		PackageGenerator packageGenerator = packageNameGenerator.getPackageGenerator(contentTypeBean);
		model.put("package", packageGenerator);
		model.put("supperClass",
				supperClassHandler.getSupperClass(contentTypeBean, importRegistry, packageGenerator.getPackageName()));
		model.put("methods", methods);
		model.put("properties", properties);
		model.put("importRegistry", importRegistry);
		return FreemarkerUtils.renderTemplate(templatePath, model, this.getClass());

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
				packageToSearch, SupperClassHandler.class, classLoader);
		if (supperClassHandlers.size() > 0) {
			supperClassHandler = (SupperClassHandler) ReflectionUtils.instantiate(supperClassHandlers.first(),
					beansOnClassPath, beansInProject, classLoader, namespaces, mixins);
		} else {
			supperClassHandler = new DefaultSupperClassHandler(beansOnClassPath, beansInProject, classLoader,
					namespaces, mixins);
		}
		supperClassHandler.setClassNameHandler(classNameHandler);
	}

	private void initializeClassNameHandler() {
		SortedSet<Class<? extends ClassNameHandler>> classNameHandlers = ReflectionUtils.getSubclassesOfType(
				packageToSearch, ClassNameHandler.class, classLoader);
		if (classNameHandlers.size() > 0) {
			classNameHandler = (ClassNameHandler) ReflectionUtils.instantiate(classNameHandlers.first(),
					beansOnClassPath, beansInProject);
		} else {
			classNameHandler = new DefaultClassNameHandler(beansOnClassPath, beansInProject);
		}
	}

	private void initializePackageHandler() {
		SortedSet<Class<? extends PackageHandler>> pageckageHandlers = ReflectionUtils.getSubclassesOfType(
				packageToSearch, PackageHandler.class, classLoader);
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
		SortedSet<Class<? extends ContentTypeItemHandler>> classes = ReflectionUtils.getHandlerClasses(packageToSearch,
				classLoader);
		for (Class<? extends ContentTypeItemHandler> clazz : classes) {
			handlersChain.add((ContentTypeItemHandler) ReflectionUtils.instantiate(clazz, beansOnClassPath,
					beansInProject, namespaces, packageGenerator));
		}
	}

}
