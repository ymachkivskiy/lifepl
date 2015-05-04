package pl.edu.agh.integr10s.lifepl.cli.shell.utils;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class MethodAnnotationValue {
    private final Map<String, String> annotationFieldToValueMap = new HashMap<>();
    private final String methodName;
    private final Class<? extends Annotation> annotationClass;
    public MethodAnnotationValue(String methodName, Class<? extends Annotation> annotationClass) {
        this.methodName = methodName;
        this.annotationClass = annotationClass;
    }

    @Override
    public String toString() {
        return "annotation{" + annotationClass + "}::" + annotationFieldToValueMap;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setValue(String fieldName, String value) {
        this.annotationFieldToValueMap.put(fieldName, value);
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public Map<String, String> getAnnotationFieldToValueMap() {
        return annotationFieldToValueMap;
    }
}
