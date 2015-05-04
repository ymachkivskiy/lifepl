package pl.edu.agh.integr10s.lifepl.cli.shell.utils.dummy_package;

import javassist.CtClass;
import javassist.CtMethod;

public class AddingVoidMethodsForBaseMethodAssembly<ArgT> extends AnnotationAssembly {
    private static final String ANN_SUFFIX = "UM";
    private final ArgT methodParamValue;
    private final String baseMethodName;

    public AddingVoidMethodsForBaseMethodAssembly(String baseMethodName, ArgT paramValue) {
        super(ANN_SUFFIX);
        this.methodParamValue = paramValue;
        this.baseMethodName = baseMethodName;
    }

    @Override
    protected <T> void decorateSubClass(CtClass baseClass, CtClass workingSubClass) throws Exception {
        CtClass paramClass = pool.getCtClass(methodParamValue.getClass().getName());
        CtMethod constructredMethod = new CtMethod(CtClass.voidType, baseMethodName + methodParamValue, new CtClass[]{}, workingSubClass );

        constructredMethod.setBody("{" + baseMethodName + "(\"ON\");}");

        workingSubClass.addMethod(constructredMethod);
    }
}
