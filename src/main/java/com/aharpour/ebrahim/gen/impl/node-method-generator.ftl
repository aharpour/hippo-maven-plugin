<#if multiple>public ${list}<${type}> get${methodName}() {
	if (this.${fieldName} == null) {
		this.${fieldName} = getChildBeansByName("${propertyName}", ${type}.class);
	}
	return this.${fieldName};
}<#else>public ${type} get${methodName}() {
	if (this.${fieldName} == null) {
		this.${fieldName} = getBean("${propertyName}", ${type}.class);
	}
	return this.${fieldName};
}</#if>