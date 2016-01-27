package org.tinygroup.aopcache.base;

import java.lang.reflect.Method;

public class MethodDescription {

    private String className;

    private String methodName;

    private String parameterTypes;//参数类型，多个参数类型以分号分隔

    public static MethodDescription createMethodDescription(Method method) {
        MethodDescription description = new MethodDescription();
        description.setClassName(method.getDeclaringClass().getName());
        description.setMethodName(method.getName());
        Class<?>[] types = method.getParameterTypes();
        StringBuilder typeBuilder = new StringBuilder();
        for (int i = 0; i < types.length; i++) {
            Class<?> type = types[i];
            typeBuilder.append(type.getName());
            if (i < types.length - 1) {
                typeBuilder.append(";");
            }
        }
        description.setParameterTypes(typeBuilder.toString());
        return description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(String parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((className == null) ? 0 : className.hashCode());
        result = prime * result
                + ((methodName == null) ? 0 : methodName.hashCode());
        result = prime * result
                + ((parameterTypes == null) ? 0 : parameterTypes.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MethodDescription other = (MethodDescription) obj;
        if (className == null) {
            if (other.className != null)
                return false;
        } else if (!className.equals(other.className))
            return false;
        if (methodName == null) {
            if (other.methodName != null)
                return false;
        } else if (!methodName.equals(other.methodName))
            return false;
        if (parameterTypes == null) {
            if (other.parameterTypes != null)
                return false;
        } else if (!parameterTypes.equals(other.parameterTypes))
            return false;
        return true;
    }


}
