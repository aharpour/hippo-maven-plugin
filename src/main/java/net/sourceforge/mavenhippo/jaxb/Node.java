//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2013.02.09 at 04:11:15 PM CET
//

package net.sourceforge.mavenhippo.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.sourceforge.mavenhippo.utils.Constants;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.jcp.org/jcr/sv/1.0}node"/>
 *         &lt;element ref="{http://www.jcp.org/jcr/sv/1.0}property"/>
 *       &lt;/choice>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "nodeOrProperty" })
@XmlRootElement(name = "node")
public class Node {

    @XmlAttribute(name = "merge", namespace = "http://www.onehippo.org/jcr/xmlimport")
    protected String merge;

    @XmlElements({ @XmlElement(name = "node", type = Node.class), @XmlElement(name = "property", type = Property.class) })
    protected List<Object> nodeOrProperty;

    @XmlTransient
    protected List<Node> subnodes;
    @XmlTransient
    protected List<Property> properties;

    @XmlAttribute(name = "name", namespace = "http://www.jcp.org/jcr/sv/1.0", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String name;

    /**
     * Gets the value of the nodeOrProperty property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the nodeOrProperty property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getNodeOrProperty().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Node }
     * {@link Property }
     *
     *
     */
    public List<Object> getNodeOrProperty() {
        if (nodeOrProperty == null) {
            nodeOrProperty = new ArrayList<Object>();
        }
        return this.nodeOrProperty;
    }

    /**
     * @param nodeName
     *            Name of a subnode to be fetched.
     * @return the fist subnode with of the given name and null if there is not
     *         any node with this name
     */
    public Node getSubnodeByName(String nodeName) {
        Node result = null;
        List<Node> subnodesList = getSubnodesByName(nodeName);
        if (!subnodesList.isEmpty()) {
            result = subnodesList.get(0);
        }
        return result;
    }

    /**
     * @param nodeName
     *            Name of a subnodes to be fetched.
     * @return returns a list of all subnodes of the given name and an empty
     *         list if there is not any.
     */
    public List<Node> getSubnodesByName(String nodeName) {
        List<Node> result = new ArrayList<Node>();
        List<Node> subnodesList = getSubnodes();
        for (Node node : subnodesList) {
            if (nodeName.equals(node.getName())) {
                result.add(node);
            }
        }
        return result;
    }

    public List<Node> getSubnodesByType(String nodeType) {
        List<Node> result = new ArrayList<Node>();
        List<Node> subnodesList = getSubnodes();
        for (Node node : subnodesList) {
            Property nodeTypeProperty = node.getPropertyByName(Constants.PropertyName.JCR_PRIMARY_TYPE);
            if (nodeType.equals(nodeTypeProperty.getSingleValue())) {
                result.add(node);
            }
        }
        return result;
    }

    public Property getPropertyByName(String propertyName) {
        Property result = null;
        List<Property> propertiesList = getProperties();
        for (Property property : propertiesList) {
            if (propertyName.equals(property.getName())) {
                result = property;
                break;
            }
        }
        return result;
    }

    public List<Node> getSubnodes() {
        if (subnodes == null) {
            subnodes = new ArrayList<Node>();
            List<Object> list = getNodeOrProperty();
            for (Object object : list) {
                if (object instanceof Node) {
                    subnodes.add((Node) object);
                }
            }
        }
        return this.subnodes;
    }

    public List<Property> getProperties() {
        if (properties == null) {
            properties = new ArrayList<Property>();
            List<Object> list = getNodeOrProperty();
            for (Object object : list) {
                if (object instanceof Property) {
                    properties.add((Property) object);
                }
            }
        }
        return this.properties;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the Merge property.
     * 
     * @return the merge
     */
    public String getMerge() {
        return merge;
    }

    /**
     * Sets the value of the Merge property.
     * 
     * @param merge
     *            the merge to set
     */
    public void setMerge(String merge) {
        this.merge = merge;
    }

}
