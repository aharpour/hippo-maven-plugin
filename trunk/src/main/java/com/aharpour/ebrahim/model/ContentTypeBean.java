package com.aharpour.ebrahim.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;

import com.aharpour.ebrahim.jaxb.Node;
import com.aharpour.ebrahim.jaxb.Property;
import com.aharpour.ebrahim.utils.Constants;
import com.aharpour.ebrahim.utils.NamespaceUtils;

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
	
	public List<String> getSupertypes() {
		Property supertypes = getCurrentNodeTypeDefinitionNode().getPropertyByName(Constants.PropertyName.HIPPOSYSEDIT_SUPERTYPE);
		return supertypes.getValue();
	}
	
	public List<Item> getItems() {
		List<Node> nodeTypeDefinitions = getNodeTypeDefinitions();
		List<Item> result = new ArrayList<Item>(nodeTypeDefinitions.size());
		for (Iterator<Node> iterator = nodeTypeDefinitions.iterator(); iterator.hasNext();) {
			result.add(new Item(iterator.next()));
		}
		return result;
	}
	
	public List<Item> getItems(String namespace) {
		List<Node> nodeTypeDefinitions = getNodeTypeDefinitions(namespace);
		List<Item> result = new ArrayList<Item>(nodeTypeDefinitions.size());
		for (Iterator<Node> iterator = nodeTypeDefinitions.iterator(); iterator.hasNext();) {
			result.add(new Item(iterator.next()));
		}
		return result;
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

	List<Node> getNodeTypeDefinitions() {
		Node nodeTypeDefinitionNode = getCurrentNodeTypeDefinitionNode();
		return nodeTypeDefinitionNode.getSubnodesByType(Constants.NodeType.HIPPOSYSEDIT_FIELD);
	}

	List<Node> getNodeTypeDefinitions(String namespace) {
		List<Node> result = new ArrayList<Node>();
		List<Node> nodeTypeDefinitions = getNodeTypeDefinitions();
		for (Iterator<Node> iterator = nodeTypeDefinitions.iterator(); iterator.hasNext();) {
			Node node = iterator.next();
			if (namespace.equals(NamespaceUtils.getNamespace(getRelativePath(node)))) {
				result.add(node);
			}
		}
		return result;
	}

	Node getTemplateDefinitionFor(String nodeTypeDefName) {
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

	private String getRelativePath(Node node) {
		if (Constants.NodeType.HIPPOSYSEDIT_FIELD.equals(node
				.getPropertyByName(Constants.PropertyName.JCR_PRIMARY_TYPE).getSingleValue())) {
			throw new IllegalArgumentException("node parameter needs to be of type "
					+ Constants.NodeType.HIPPOSYSEDIT_FIELD);
		}
		return node.getPropertyByName(Constants.PropertyName.HIPPOSYSEDIT_PATH).getSingleValue();
	}

	private Node getCurrentTemplateDefinitionNode() {
		Node tempalteHandle = node.getSubnodeByName(Constants.NodeName.EDITOR_TEMPLATES);
		return tempalteHandle.getSubnodeByName(Constants.NodeName.DEFAULT);
	}

	public class Item {

		private Node definition;

		public Item(Node definition) {
			this.definition = definition;
		}

		public String getRelativePath() {
			return ContentTypeBean.this.getRelativePath(definition);
		}

		public String getSimpleName() {
			return NamespaceUtils.getSimpleName(getRelativePath());
		}

		public String getNamespace() {
			return NamespaceUtils.getNamespace(getRelativePath());
		}

	}

	public static class ContentTypeException extends MojoExecutionException {

		private static final long serialVersionUID = 1L;

		public ContentTypeException(String message) {
			super(message);
		}

	}

}
