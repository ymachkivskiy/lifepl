package pl.edu.agh.integr10s.lifepl.cli.shell.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.cli.shell.ShellNameAware;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShell;

public class SubShellAnnotationInjectionVisitor<E extends Enum<E> & ShellNameAware<E>> implements SubShellVisitor<E> {
    private static final Logger logger = LoggerFactory.getLogger(SubShellAnnotationInjectionVisitor.class);

    private final AnnotationInjector injector;

    @Override
    public void visitSubShell(SubShell<E> parentShell) {
        for (SubShell<E> childShell : parentShell.childShells()) {
            logger.debug("injecting to child sub shell ' {} ' of parent sub shell ' {} '", childShell, parentShell);
            parentShell.addChildSubShellWithReplacement(doInjection(childShell));
        }
    }

    public SubShell<E> doInjection(SubShell<E> originalSubShell) {
        logger.debug("START  -  injection for sub shell ' {} '", originalSubShell);
        visitSubShell(originalSubShell);
        logger.debug("FINISH  -  injection for sub shell '  {} '", originalSubShell);
        return injector.injectCommand(originalSubShell);
    }

    public SubShellAnnotationInjectionVisitor() {
        this.injector = new AnnotationInjector();
    }
}
