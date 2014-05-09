<#if multiple>public ${list}<${type}> get${methodName}() {
	if (this.${fieldName} == null) {
		this.${fieldName} = getLinkedBeans("${propertyName}", ${type}.class);
	}
	return this.${fieldName};
}<#else>public ${type} get${methodName}() {
	if (this.${fieldName} == null) {
		this.${fieldName} = getLinkedBean("${propertyName}", ${type}.class);
	}
	return this.${fieldName};
}</#if>