package pl.edu.agh.integr10s.lifepl.cli.shell.utils.dummy_package;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AnnotatingMethodAssembly extends AnnotationAssembly {

    public static final String ANNOTATED_SUFFIX = "Ann";

    private final List<MethodAnnotationValue> methodAnnotations;

    private static Annotation getAnnotation(MethodAnnotationValue methodAnnotationValue, ClassFile ccFile, ConstPool constpool) {
        Class<? extends java.lang.annotation.Annotation> annotationClass = methodAnnotationValue.getAnnotationClass();
        Annotation annot = new Annotation(annotationClass.getName(), constpool);

        for (Map.Entry<String, String> annotationFieldValueEntry : methodAnnotationValue.getAnnotationFieldToValueMap().entrySet()) {
            annot.addMemberValue(annotationFieldValueEntry.getKey(), new StringMemberValue(annotationFieldValueEntry.getValue(), ccFile.getConstPool()));
        }

        return annot;
    }

    @Override
    protected <T> void decorateSubClass(CtClass baseClass, CtClass workingSubClass) throws Exception {
        ClassFile ccFile = workingSubClass.getClassFile();
        try {
            for (MethodAnnotationValue methodAnnotation : methodAnnotations) {
                CtMethod methodDescriptor = CtNewMethod.copy(baseClass.getDeclaredMethod(methodAnnotation.getMethodName()), workingSubClass, null);
                workingSubClass.addMethod(methodDescriptor);

                AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
                attr.addAnnotation(getAnnotation(methodAnnotation, ccFile, constpool));

                methodDescriptor.getMethodInfo().addAttribute(attr);
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public AnnotatingMethodAssembly(MethodAnnotationValue... methodAnnotations) {
        super(ANNOTATED_SUFFIX);
        this.methodAnnotations = Arrays.asList(methodAnnotations);
    }
}
