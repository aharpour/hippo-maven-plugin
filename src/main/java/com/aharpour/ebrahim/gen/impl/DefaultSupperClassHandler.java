package com.aharpour.ebrahim.gen.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoDocument;

import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.ImportRegistry;
import com.aharpour.ebrahim.gen.SupperClassHandler;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.HippoBeanClass;
import com.aharpour.ebrahim.utils.Constants;

public class DefaultSupperClassHandler extends SupperClassHandler {

	public DefaultSupperClassHandler(Map<String, HippoBeanClass> beansOnClassPath,
			Map<String, HippoBeanClass> beansInProject) {
		super(beansOnClassPath, beansInProject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ClassReference getSupperClass(ContentTypeBean contentTypeBean, ImportRegistry importRegistry) {
		ClassReference result = new ClassReference(HippoDocument.class);
		List<String> supertypes = contentTypeBean.getSupertypes();
		SortedSet<Class<? extends HippoBean>> supperClasses = new TreeSet<Class<? extends HippoBean>>(
				new Comparator<Class<? extends HippoBean>>() {

					@Override
					public int compare(Class<? extends HippoBean> o1, Class<? extends HippoBean> o2) {
						int result;
						if (o1 != null && o2 == null) {
							result = 1;
						} else if (o1 == null && o2 != null) {
							result = -1;
						} else if (o1.equals(o1)) {
							result = 0;
						} else if (o1.isAssignableFrom(o2)) {
							result = 1;
						} else if (o2.isAssignableFrom(o1)) {
							result = -1;
						} else {
							throw new IllegalArgumentException("the given arguments are not comparable");
						}

						return result;
					}
				});
		for (String superType : supertypes) {
			if (Constants.NodeType.HIPPO_COMPOUND.equals(superType)) {
				Class<HippoCompound> hippoCompoundClass = HippoCompound.class;
				supperClasses.add(hippoCompoundClass);
			} else if (beansOnClassPath.containsKey(superType)) {
				HippoBeanClass hippoBeanClass = beansOnClassPath.get(superType);
				Class<?> clazz = getClass(hippoBeanClass);
				if (HippoBean.class.isAssignableFrom(clazz)) {
					supperClasses.add((Class<? extends HippoBean>) clazz);
				}
			}
		}
		if (supperClasses.size() > 0) {
			result = new ClassReference(supperClasses.last());
		}
		importRegistry.register(result);
		return result;
	}

	private Class<?> getClass(HippoBeanClass hippoBeanClass) {
		try {
			return Class.forName(hippoBeanClass.getFullyQualifiedName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
