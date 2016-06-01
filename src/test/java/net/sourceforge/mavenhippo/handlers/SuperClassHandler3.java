package net.sourceforge.mavenhippo.handlers;

import java.util.Map;
import java.util.Set;

import net.sourceforge.mavenhippo.gen.ClassReference;
import net.sourceforge.mavenhippo.gen.DefaultSupperClassHandler;
import net.sourceforge.mavenhippo.gen.ImportRegistry;
import net.sourceforge.mavenhippo.gen.annotation.Weight;
import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.HippoBeanClass;

@Weight(value = -6.0)
public class SuperClassHandler3 extends DefaultSupperClassHandler {

    public SuperClassHandler3(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject, ClassLoader classLoader, Set<String> namespaces, Map<String, ContentTypeBean> mixins) {
        super(beansOnClassPath, beansInProject, classLoader, namespaces, mixins);
    }

    @Override
    public ClassReference getSupperClass(ContentTypeBean contentTypeBean, ImportRegistry importRegistry, String packageName) {
        return null;
    }
}
