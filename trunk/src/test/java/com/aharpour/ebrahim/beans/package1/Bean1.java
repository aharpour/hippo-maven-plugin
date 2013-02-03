package com.aharpour.ebrahim.beans.package1;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoDocument;

@Node(jcrType = Bean1.JCR_TYPE)
public class Bean1 extends HippoDocument {
	public static final String JCR_TYPE = "namespace:node1";
}