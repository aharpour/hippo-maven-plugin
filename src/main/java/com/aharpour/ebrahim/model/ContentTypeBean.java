package com.aharpour.ebrahim.model;

import java.util.List;

import com.aharpour.ebrahim.jaxb.Node;
import com.aharpour.ebrahim.jaxb.Property;
import com.aharpour.ebrahim.utils.Constants;

public class ContentTypeBean {



	private final Node node;

	public ContentTypeBean(Node node) {
		this.node = node;
	}

	public String getFullyQualifiedName() {
		// TODO
		return null;
	}

	public List<Node> getNodeTypeDefinitions() {
		Node nodeTypeDefinitionNode = getCurrentNodeTypeDefinitionNode();
		return nodeTypeDefinitionNode.getSubnodesByType(Constants.NodeType.HIPPOSYSEDIT_FIELD);
	}

	public Node getTemplateDefinitionFor(String nodeTypeDefName) {
		Node result = null;
		Node templateDefNode = getCurrentTemplateDefinitionNode();
		List<Node> subnodes = templateDefNode.getSubnodes();
		for (Node node : subnodes) {
			Property fieldProperty = node.getPropertyByName(Constants.PropertyName.FIELD);
			if (fieldProperty != null && fieldProperty.getSingleValue().equals(nodeTypeDefName)) {
				result = node;
				break;
			}
		}
		return result;
	}

	private Node getCurrentNodeTypeDefinitionNode() {
		Node result = null;
		Node nodeTypeHandle = node.getSubnodeByName(Constants.NodeName.HIPPOSYSEDIT_NODETYPE);
		List<Node> subnodesByName = nodeTypeHandle.getSubnodesByName(Constants.NodeName.HIPPOSYSEDIT_NODETYPE);
		for (Node node : subnodesByName) {
			Property mixinTypes = node.getPropertyByName(Constants.PropertyName.JCR_MIXIN_TYPES);
			if (mixinTypes != null) {
				List<String> values = mixinTypes.getValue();
				if (values.contains(Constants.PropertyValue.HIPPOSYSEDIT_REMODEL)) {
					result = node;
					break;
				}
			}
		}
		return result;
	}

	private Node getCurrentTemplateDefinitionNode() {
		Node tempalteHandle = node.getSubnodeByName(Constants.NodeName.EDITOR_TEMPLATES);
		return tempalteHandle.getSubnodeByName(Constants.NodeName.DEFAULT);
	}

}
