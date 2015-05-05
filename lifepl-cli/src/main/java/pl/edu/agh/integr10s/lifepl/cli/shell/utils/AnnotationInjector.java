package pl.edu.agh.integr10s.lifepl.cli.shell.utils;


import asg.cliche.Command;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShellName;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class AnnotationInjector {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationInjector.class);

    private static final String BASE_METHOD_NAME = "runChildShellByName";
    public static final String GENERATED_METHODS_CLASS_SUFFIX = "GM";
    public static final String ANNOTATED_METHODS_CLASS_SUFFIX = "Ann";
    private final ClassPool classPool;

    public AnnotationInjector() {
        classPool = ClassPool.getDefault();
    }

    private static final String getAnnotatedClassName(CtClass cSuperClass, String subclassSuffix) {
        return cSuperClass.getName() + "_" + subclassSuffix;
    }

    private static String addCommandInvocationUsingParam(String methodParamValue, CtClass workingSubClass) {
        logger.debug("adding command invocation {}(\"{}\")", BASE_METHOD_NAME, methodParamValue);

        String generatedMethodName = BASE_METHOD_NAME + methodParamValue;

        CtMethod constructedMethod = new CtMethod(CtClass.voidType, generatedMethodName, new CtClass[]{}, workingSubClass);

        try {

            constructedMethod.setBody("{" + BASE_METHOD_NAME + "(\"" + methodParamValue + "\");}");
            workingSubClass.addMethod(constructedMethod);

        } catch (CannotCompileException e) {
            logger.error("cannot add method invocation", e);
        }

        return generatedMethodName;
    }

    private static void addAnnotationToClassMethod(CtClass baseClass, CtClass workingSubClass, MethodAnnotationValue methodAnnotation) {
        logger.debug("adding annotation {} to class ' {} '", methodAnnotation, workingSubClass.getName());

        try {

            CtMethod methodDescriptor = CtNewMethod.copy(baseClass.getDeclaredMethod(methodAnnotation.getMethodName()), workingSubClass, null);
            workingSubClass.addMethod(methodDescriptor);

            ClassFile ccFile = workingSubClass.getClassFile();
            ConstPool constpool = ccFile.getConstPool();

            AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
            attr.addAnnotation(getAnnotation(methodAnnotation, ccFile, constpool));

            methodDescriptor.getMethodInfo().addAttribute(attr);

        } catch (CannotCompileException | NotFoundException e) {
            logger.error("cannot add annotation to sub class", e);
        }
    }

    private static Annotation getAnnotation(MethodAnnotationValue methodAnnotationValue, ClassFile ccFile, ConstPool constpool) {
        Class<? extends java.lang.annotation.Annotation> annotationClass = methodAnnotationValue.getAnnotationClass();
        Annotation annotation = new Annotation(annotationClass.getName(), constpool);

        for (Map.Entry<String, String> annotationFieldValueEntry : methodAnnotationValue.getAnnotationFieldToValueMap().entrySet()) {
            annotation.addMemberValue(annotationFieldValueEntry.getKey(), new StringMemberValue(annotationFieldValueEntry.getValue(), ccFile.getConstPool()));
        }

        return annotation;
    }


    private CtClass createSubClass(CtClass cSuperClass, String subClassNameSuffix) {
        return classPool.makeClass(getAnnotatedClassName(cSuperClass, subClassNameSuffix), cSuperClass);
    }

    public final SubShell injectCommand(SubShell originalObject) {
        logger.debug("trying to inject annotations to sub shell with class ' {} '", originalObject.getClass());

        if (originalObject.childShellsNames().isEmpty()) {
            logger.debug("original sub shell ' {} ' has not children, annotations will not be injected", originalObject);
            return originalObject;
        }

        try {

            CtClass baseClass = classPool.getCtClass(SubShell.class.getName());

            logger.debug("injecting new methods to class ' {} '", originalObject.getClass().getName());

            CtClass workingSubClass = createSubClass(baseClass, GENERATED_METHODS_CLASS_SUFFIX);

            List<MethodAnnotationValue> annotations = new LinkedList<>();

            for (SubShellName subShellName : originalObject.childShellsNames()) {
                String generatedMethodName = addCommandInvocationUsingParam(subShellName.name(), workingSubClass);
                MethodAnnotationValue annotationValue = new MethodAnnotationValue(generatedMethodName, Command.class);

                annotationValue.setValue("name", subShellName.getPrompt());
                annotationValue.setValue("abbrev", subShellName.getAbbrev());
                annotationValue.setValue("description", subShellName.getDescription());

                annotations.add(annotationValue);
            }

            logger.debug("adding annotations to generated methods");

            baseClass = workingSubClass;
            workingSubClass = createSubClass(workingSubClass, ANNOTATED_METHODS_CLASS_SUFFIX);

            for (MethodAnnotationValue annotation : annotations) {
                addAnnotationToClassMethod(baseClass, workingSubClass, annotation);
            }

            //3. create instance of sub class

            SubShell result = SubShell.class.cast(workingSubClass.getClass().newInstance());

            //4. insert values from original object

            result.reconfigureAs(originalObject);


            return result;

        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return originalObject;
    }
}
