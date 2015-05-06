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

public final class AnnotationInjector<ST extends SubShell> {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationInjector.class);

    private static final String BASE_METHOD_NAME = "runChildShellByName";
    public static final String GENERATED_CLASS_SUFFIX = "Gen";
    private final ClassPool classPool;

    public AnnotationInjector() {
        classPool = ClassPool.getDefault();
    }

    private static final String getAnnotatedClassName(CtClass cSuperClass, String subclassSuffix) {
        return cSuperClass.getName() + "_" + subclassSuffix;
    }

    private static void addCommandInvocationUsingParam(SubShellName subShellName, CtClass workingSubClass) {
        final String methodParamValue = subShellName.getPrompt();
        final String generatedMethodName = BASE_METHOD_NAME + methodParamValue;

        logger.debug("adding command invocation {}(\"{}\")", BASE_METHOD_NAME, methodParamValue);

        try {
            logger.debug("constructing method invocation");

            CtMethod constructedMethod = new CtMethod(CtClass.voidType, generatedMethodName, new CtClass[]{}, workingSubClass);

            ClassFile ccFile = workingSubClass.getClassFile();
            ConstPool constpool = ccFile.getConstPool();

            constructedMethod.setBody("{" + BASE_METHOD_NAME + "(\"" + methodParamValue + "\");}");
            workingSubClass.addMethod(constructedMethod);

            logger.debug("method ' {} ' added", constructedMethod);

            AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);

            Annotation annotation = new Annotation(Command.class.getName(), constpool);

            annotation.addMemberValue("name", new StringMemberValue(subShellName.getPrompt(), constpool));
            annotation.addMemberValue("abbrev", new StringMemberValue(subShellName.getAbbrev(), constpool));
            annotation.addMemberValue("description", new StringMemberValue(subShellName.getDescription(), constpool));

            attr.addAnnotation(annotation);

            constructedMethod.getMethodInfo().addAttribute(attr);

            logger.debug("annotation ' {} ' added to constructed method", annotation);

        } catch (CannotCompileException e) {
            logger.error("cannot add method invocation", e);
        }

    }

    private CtClass createSubClass(CtClass cSuperClass, String subClassNameSuffix) throws CannotCompileException {
        return classPool.makeClass(getAnnotatedClassName(cSuperClass, subClassNameSuffix), cSuperClass);
    }

    public final ST injectCommand(ST originalObject) {
        final Class<? extends SubShell> originalClass = originalObject.getClass();

        logger.debug("trying to inject annotations to sub shell with class ' {} '", originalClass);

        if (originalObject.childShellsNames().isEmpty()) {
            logger.debug("original sub shell ' {} ' has not children, annotations will not be injected", originalObject);
            return originalObject;
        }

        try {

            CtClass baseClass = classPool.getCtClass(originalClass.getName());
            CtClass workingSubClass = createSubClass(baseClass, GENERATED_CLASS_SUFFIX);

            for (SubShellName subShellName : originalObject.childShellsNames()) {
                addCommandInvocationUsingParam(subShellName, workingSubClass);
            }

            ST result = (ST) originalClass.cast(workingSubClass.toClass().newInstance());

            result.reconfigureAs(originalObject);

            return result;

        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        return originalObject;
    }
}
