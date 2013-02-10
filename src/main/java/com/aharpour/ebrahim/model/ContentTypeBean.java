package com.aharpour.ebrahim.model;

import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;

import com.aharpour.ebrahim.jaxb.Node;
import com.aharpour.ebrahim.jaxb.Property;
import com.aharpour.ebrahim.utils.Constants;

public class ContentTypeBean {

	private final Node node;

	public ContentTypeBean(Node node) {
		this.node = node;
	}

	public String getFullyQualifiedName() throws ContentTypeException {
		String result = null;
		Node prototypeNode = getCurrentPrototyeNode();
		if (prototypeNode != null) {
			Property primaryTypeProperty = prototypeNode.getPropertyByName(Constants.PropertyName.JCR_PRIMARY_TYPE);
			if (primaryTypeProperty != null) {
				result = primaryTypeProperty.getSingleValue();
			}
		}
		if (result == null) {
			throw new ContentTypeException("could not obtain fully qualified Name of the content type.");
		}
		return result;
	}

	public String getSimpleName() {
		return node.getName();
	}

	public String getNamespace() throws ContentTypeException {
		String namespace;
		String fullyQualifiedName = getFullyQualifiedName();
		String simpleName = getSimpleName();
		if (fullyQualifiedName.endsWith(":" + simpleName)) {
			namespace = fullyQualifiedName.substring(0, fullyQualifiedName.length() - (simpleName.length() + 1));
		} else {
			throw new ContentTypeException("fullyQualifiedName and simpleName do not match each other.");
		}
		return namespace;
	}

	private Node getCurrentPrototyeNode() {
		Node prototypeNode = null;
		Node prototypeHandle = node.getSubnodeByName(Constants.NodeName.HIPPOSYSEDIT_PROTOTYPES);
		if (prototypeHandle != null) {
			List<Node> nodeList = prototypeHandle.getSubnodesByName(Constants.NodeName.HIPPOSYSEDIT_PROTOTYPE);
			for (Node node : nodeList) {
				Property nodeTypeProperty = node.getPropertyByName(Constants.PropertyName.JCR_PRIMARY_TYPE);
				if (nodeTypeProperty != null
						&& !Constants.NodeType.NT_UNSTRUCTURED.equals(nodeTypeProperty.getSingleValue())) {
					prototypeNode = node;
					break;
				}
			}
		}
		return prototypeNode;
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

	public static class ContentTypeException extends MojoExecutionException {

		private static final long serialVersionUID = 1L;

		public ContentTypeException(String message) {
			super(message);
		}

	}

}
