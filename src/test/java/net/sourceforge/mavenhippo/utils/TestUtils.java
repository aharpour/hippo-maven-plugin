package net.sourceforge.mavenhippo.utils;

import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.ContentTypeBean.Item;

import org.easymock.EasyMock;

public class TestUtils {

    private TestUtils() {
    }

    public static Item createMockItem(ContentTypeBean contentTypeBean, String relativePath, String simpleName,
            boolean isMultible, String type) {
        Item item = EasyMock.createMock(Item.class);
        EasyMock.expect(item.getType()).andReturn(type).anyTimes();
        EasyMock.expect(item.getContentType()).andReturn(contentTypeBean).anyTimes();
        EasyMock.expect(item.getRelativePath()).andReturn(relativePath).anyTimes();
        EasyMock.expect(item.getSimpleName()).andReturn(simpleName).anyTimes();
        EasyMock.expect(item.isMultiple()).andReturn(isMultible).anyTimes();
        EasyMock.replay(item);
        return item;
    }
}
