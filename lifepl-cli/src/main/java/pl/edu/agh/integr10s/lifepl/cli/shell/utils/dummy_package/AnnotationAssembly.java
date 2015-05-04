package pl.edu.agh.integr10s.lifepl.cli.shell.utils.dummy_package;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;

import java.util.Optional;

public abstract class AnnotationAssembly {
    protected ConstPool constpool;
    protected final String subclassSuffix;

    private Optional<AnnotationAssembly> delegate = Optional.empty();
    protected ClassPool pool;

    public AnnotationAssembly(String subclassSuffix) {
        this.subclassSuffix = subclassSuffix;
    }

    public AnnotationAssembly append(AnnotationAssembly innerAssembly) {
        this.delegate = Optional.of(innerAssembly);
        return innerAssembly;
    }

    protected abstract <T> void decorateSubClass(CtClass baseClass, CtClass workingSubClass) throws Exception;


   public final <T> Class<? extends T> assemble(Class<T> clazz) throws Exception {
       Class<? extends  T> workingClazz = clazz;
       if (delegate.isPresent()) {
           workingClazz = delegate.get().assemble(clazz);
       }

       pool = ClassPool.getDefault();
       //extracting the class
       CtClass cSuperClass = pool.getCtClass(workingClazz.getName());
       CtClass cc = pool.makeClass(getAnnotatedClassName(cSuperClass), cSuperClass);

       //looking for the method to apply the annotation on
       ClassFile ccFile = cc.getClassFile();
       constpool = ccFile.getConstPool();

       decorateSubClass(cSuperClass, cc);

       return cc.toClass();
   }

    private String getAnnotatedClassName(CtClass cSuperClass) {
        return cSuperClass.getName() + "_" + subclassSuffix;
    }
}
