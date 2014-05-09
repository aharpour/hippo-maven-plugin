<#if multiple>public ${list}<${type}> get${methodName}() {
	if (this.${fieldName} == null) {
		this.${fieldName} = getChildBeansByName("${propertyName}");
	}
	return this.${fieldName};
}<#else>public ${type} get${methodName}() {
	if (this.${fieldName} == null) {
		this.${fieldName} = getHippoHtml("${propertyName}");
	}
	return this.${fieldName};
}</#if>