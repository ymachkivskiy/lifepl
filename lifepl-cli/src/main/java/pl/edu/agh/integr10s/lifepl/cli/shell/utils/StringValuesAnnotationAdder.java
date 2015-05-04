package pl.edu.agh.integr10s.lifepl.cli.shell.utils;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

import java.util.Map;

public class StringValuesAnnotationAdder {


    public static <T> T getAnnotatedInstance(Class<T> classToAnnotate, String methodName, AnnotationValue annotationValue) throws Exception {

        //pool creation
        ClassPool pool = ClassPool.getDefault();
        //extracting the class
        CtClass cSuperClass = pool.getCtClass(classToAnnotate.getName());
        CtClass cc = pool.makeClass(cSuperClass.getName() + "Annotated", cSuperClass);

        //looking for the method to apply the annotation on

        CtMethod methodDescriptor = CtNewMethod.copy(cSuperClass.getDeclaredMethod(methodName), cc, null);
        cc.addMethod(methodDescriptor);


        ClassFile ccFile = cc.getClassFile();
        ConstPool constpool = ccFile.getConstPool();

        AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
        attr.addAnnotation(getAnnotation(annotationValue, ccFile, constpool));
        // add the annotation to the method descriptor
        methodDescriptor.getMethodInfo().addAttribute(attr);

        return classToAnnotate.cast(cc.toClass().newInstance());
    }

    private static Annotation getAnnotation(AnnotationValue annotationValue, ClassFile ccFile, ConstPool constpool) {
        Class<? extends java.lang.annotation.Annotation> annotationClass = annotationValue.getAnnotationClass();
        Annotation annot = new Annotation(annotationClass.getName(), constpool);

        for (Map.Entry<String, String> annotationFieldValueEntry : annotationValue.getAnnotationFieldToValueMap().entrySet()) {
            annot.addMemberValue(annotationFieldValueEntry.getKey(), new StringMemberValue(annotationFieldValueEntry.getValue(), ccFile.getConstPool()));

        }

        return annot;
    }
}
