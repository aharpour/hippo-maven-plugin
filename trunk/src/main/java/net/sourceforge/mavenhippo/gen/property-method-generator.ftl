<#if multiple>public <#if basicType>${type}[]<#else>${list}<${type}></#if> get${methodName}() {
    if (this.${fieldName} == null) {
        this.${fieldName} = getProperty("${propertyName}");
    }
    return this.${fieldName};
}<#else>public ${type} get${methodName}() {
    if (this.${fieldName} == null) {
        this.${fieldName} = getProperty("${propertyName}");
    }
    return this.${fieldName};
}</#if>