package pl.edu.agh.integr10s.clibuilder.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.shell.AppContext;
import pl.edu.agh.integr10s.clibuilder.shell.CategorizedShell;
import pl.edu.agh.integr10s.clibuilder.shell.ShellNameAware;

public class SubShellAnnotationInjectionVisitor<E extends Enum<E> & ShellNameAware<E>, AppStateT extends AppContext> implements SubShellVisitor<E, AppStateT> {
    private static final Logger logger = LoggerFactory.getLogger(SubShellAnnotationInjectionVisitor.class);

    private final AnnotationInjector injector;

    @Override
    public void visitSubShell(CategorizedShell<E, AppStateT> parentShell) {
        for (CategorizedShell<E, AppStateT> childShell : parentShell.childShells()) {
            logger.debug("injecting to child sub shell ' {} ' of parent sub shell ' {} '", childShell, parentShell);
            parentShell.addChildSubShellWithReplacement(doInjection(childShell));
        }
    }

    public CategorizedShell<E, AppStateT> doInjection(CategorizedShell<E, AppStateT> originalCategorizedShell) {
        logger.debug("START  -  injection for sub shell ' {} '", originalCategorizedShell);
        visitSubShell(originalCategorizedShell);
        logger.debug("FINISH  -  injection for sub shell '  {} '", originalCategorizedShell);
        return injector.injectCommand(originalCategorizedShell);
    }

    public SubShellAnnotationInjectionVisitor() {
        this.injector = new AnnotationInjector();
    }
}
