public ${type} get${methodName}() {
	if (this.${fieldName} == null) {
		this.${fieldName} = getHippoHtml("${propertyName}");
	}
	return this.${fieldName};
}