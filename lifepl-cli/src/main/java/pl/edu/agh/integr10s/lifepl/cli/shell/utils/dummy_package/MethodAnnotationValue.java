package pl.edu.agh.integr10s.lifepl.cli.shell.utils.dummy_package;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MethodAnnotationValue {

    private final Map<String, String> annotationFieldToValueMap = new HashMap<>();
    private final Set<String> allowedFields = new HashSet<>();
    private final String methodName;
    private final Class<? extends Annotation> annotationClass;

    public MethodAnnotationValue(String methodName, Class<? extends Annotation> annotationClass) {
        this.methodName = methodName;
        this.annotationClass = annotationClass;
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
