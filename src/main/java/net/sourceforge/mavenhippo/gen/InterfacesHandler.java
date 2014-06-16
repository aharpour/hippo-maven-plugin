package net.sourceforge.mavenhippo.gen;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.HippoBeanClass;

public abstract class InterfacesHandler extends ClasspathAware {

    private final ClassLoader classLoader;
    private final Set<String> namespaces;
    private final Map<String, ContentTypeBean> mixins;

    public InterfacesHandler(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
            ClassLoader classLoader, Set<String> namespaces, Map<String, ContentTypeBean> mixins) {
        super(beansOnClassPath, beansInProject);
        this.classLoader = classLoader;
        this.namespaces = namespaces;
        this.mixins = mixins;
    }

    public abstract List<ClassReference> getInterfaces(ContentTypeBean contentTypeBean, ImportRegistry importRegistry,
            String packageName);

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public Set<String> getNamespaces() {
        return namespaces;
    }

    public Map<String, ContentTypeBean> getMixins() {
        return mixins;
    }

}
