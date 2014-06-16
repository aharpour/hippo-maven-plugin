${package.fragment}

<#list importRegistry.imports as import>
import ${import};
</#list>

<#if addTypeAnnotation>@Node(jcrType = ${className}.JCR_TYPE)</#if>
public class ${className} extends ${supperClass} <#if interfaces?has_content>implements <#list interfaces as interface>${interface}<#if interface_has_next>, </#if></#list> </#if>{

    public static final String JCR_TYPE = "${contentType.fullyQualifiedName}";

    <#list properties as property>
    ${property.fragment}
    </#list>

<#list methods as method>
${method.fragment}

</#list>

}