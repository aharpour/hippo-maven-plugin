package net.sourceforge.mavenhippo.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.HippoBeanClass;

public class DefaultInterfacesHandler extends InterfacesHandler {

    public DefaultInterfacesHandler(Map<String, HippoBeanClass> beansOnClassPath,
            Map<String, HippoBeanClass> beansInProject, ClassLoader classLoader, Set<String> namespaces,
            Map<String, ContentTypeBean> mixins) {
        super(beansOnClassPath, beansInProject, classLoader, namespaces, mixins);
    }

    @Override
    public List<ClassReference> getInterfaces(ContentTypeBean contentTypeBean, ImportRegistry importRegistry,
            String packageName) {
        return new ArrayList<ClassReference>();
    }

}
