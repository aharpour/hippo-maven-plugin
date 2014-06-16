package net.sourceforge.mavenhippo.handlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.mavenhippo.gen.ClassReference;
import net.sourceforge.mavenhippo.gen.ImportRegistry;
import net.sourceforge.mavenhippo.gen.InterfacesHandler;
import net.sourceforge.mavenhippo.gen.annotation.Weight;
import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.HippoBeanClass;

@Weight(0)
public class MyInterfacesHandler extends InterfacesHandler {

    public MyInterfacesHandler(Map<String, HippoBeanClass> beansOnClassPath,
            Map<String, HippoBeanClass> beansInProject, ClassLoader classLoader, Set<String> namespaces,
            Map<String, ContentTypeBean> mixins) {
        super(beansOnClassPath, beansInProject, classLoader, namespaces, mixins);
    }

    @Override
    public List<ClassReference> getInterfaces(ContentTypeBean contentTypeBean, ImportRegistry importRegistry,
            String packageName) {
        List<ClassReference> result = new ArrayList<ClassReference>();
        addInterface(importRegistry, result, Cloneable.class);
        addInterface(importRegistry, result, Serializable.class);
        return result;
    }

    private void addInterface(ImportRegistry importRegistry, List<ClassReference> result, Class<?> clazz) {
        ClassReference reference = new ClassReference(clazz);
        importRegistry.register(reference);
        result.add(reference);
    }

}
