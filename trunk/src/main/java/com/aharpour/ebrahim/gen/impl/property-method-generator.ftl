public ${type} get${methodName}() {
	if (this.${fieldName} == null) {
		this.${fieldName} = getProperty("${propertyName}");
	}
	return this.${fieldName};
}