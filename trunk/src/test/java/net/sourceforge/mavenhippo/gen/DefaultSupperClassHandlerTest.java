package net.sourceforge.mavenhippo.gen;

import generated.beans.Basedocument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.HippoBeanClass;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.content.beans.standard.HippoItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultSupperClassHandlerTest {

    private static final String EXTERNAL_PARENT_BEAN = "pns:parent";
    private static final String LOCAL_PARENT_BEAN = "lns:localParent";
    private final Map<String, HippoBeanClass> beansOnClassPath = new HashMap<String, HippoBeanClass>();
    private final Map<String, HippoBeanClass> beansInProject = new HashMap<String, HippoBeanClass>();
    private final ImportRegistry importRegistry = new ImportRegistry();
    private final Set<String> namespaces = new HashSet<String>();
    private final Map<String, ContentTypeBean> mixins = new HashMap<String, ContentTypeBean>();
    private final DefaultSupperClassHandler supperClassHandler = new DefaultSupperClassHandler(beansOnClassPath,
            beansInProject, ClassLoader.getSystemClassLoader(), namespaces, mixins);

    @Before
    public void init() {
        beansOnClassPath.put(EXTERNAL_PARENT_BEAN, new HippoBeanClass(Basedocument.class.getPackage().getName(),
                Basedocument.class.getSimpleName(), EXTERNAL_PARENT_BEAN));
        beansInProject.put(LOCAL_PARENT_BEAN, new HippoBeanClass("my.package", "LocalParent", EXTERNAL_PARENT_BEAN));
        namespaces.add("lns");
        supperClassHandler.setClassNameHandler(new DefaultClassNameHandler(beansOnClassPath, beansInProject));
    }

    @Test
    public void mixinTest() {
        ContentTypeBean contentTypeBean = createContentTypeBean(null, true);
        ClassReference supperClass = supperClassHandler.getSupperClass(contentTypeBean, importRegistry, null);
        Assert.assertEquals(HippoItem.class.getName(), supperClass.getClassName());
        Assert.assertEquals(1, importRegistry.getImports().size());
        Assert.assertEquals(HippoItem.class.getName(), importRegistry.getImports().get(0));
    }

    @Test
    public void notParentTest() {

        List<String> supertypes = new ArrayList<String>();
        ContentTypeBean contentTypeBean = createContentTypeBean(supertypes, false);

        ClassReference supperClass = supperClassHandler.getSupperClass(contentTypeBean, importRegistry, null);
        Assert.assertEquals(HippoDocument.class.getName(), supperClass.getClassName());
        Assert.assertEquals(1, importRegistry.getImports().size());
        Assert.assertEquals(HippoDocument.class.getName(), importRegistry.getImports().get(0));
    }

    @Test
    public void externalParentTest() {

        List<String> supertypes = new ArrayList<String>();
        supertypes.add(EXTERNAL_PARENT_BEAN);
        ContentTypeBean contentTypeBean = createContentTypeBean(supertypes, false);
        ClassReference supperClass = supperClassHandler.getSupperClass(contentTypeBean, importRegistry, null);
        Assert.assertEquals(Basedocument.class.getName(), supperClass.getClassName());
        Assert.assertEquals(1, importRegistry.getImports().size());
        Assert.assertEquals(Basedocument.class.getName(), importRegistry.getImports().get(0));
    }

    @Test
    public void localParentTest() {
        List<String> supertypes = new ArrayList<String>();
        supertypes.add(LOCAL_PARENT_BEAN);
        ContentTypeBean contentTypeBean = createContentTypeBean(supertypes, false);
        ClassReference supperClass = supperClassHandler.getSupperClass(contentTypeBean, importRegistry, "my.package");
        Assert.assertEquals("my.package.LocalParent", supperClass.getClassName());
        Assert.assertEquals(1, importRegistry.getImports().size());
        Assert.assertEquals("my.package.LocalParent", importRegistry.getImports().get(0));
    }

    private ContentTypeBean createContentTypeBean(List<String> supertypes, boolean isMixin) {
        ContentTypeBean contentTypeBean = EasyMock.createMock(ContentTypeBean.class);
        EasyMock.expect(contentTypeBean.isMixin()).andReturn(isMixin).anyTimes();
        EasyMock.expect(contentTypeBean.getSupertypes()).andReturn(supertypes).anyTimes();
        EasyMock.replay(contentTypeBean);
        return contentTypeBean;
    }
}
