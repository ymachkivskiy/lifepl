package pl.edu.agh.integr10s.clibuilder.build;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.ApplicationShell;
import pl.edu.agh.integr10s.clibuilder.shell.ConfiguredShellsProvider;
import pl.edu.agh.integr10s.clibuilder.shell.ShellNameAware;
import pl.edu.agh.integr10s.clibuilder.shell.SubShell;
import pl.edu.agh.integr10s.clibuilder.utils.SubShellAnnotationInjectionVisitor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AppShellBuilder<E extends Enum<E> & ShellNameAware<E>> {
    private static final Logger logger = LoggerFactory.getLogger(AppShellBuilder.class);

    private final Set<SubShell<E>> subShells = new HashSet<>();
    private final Class<E> shellNameAwareClass;

    public AppShellBuilder(ConfiguredShellsProvider<E> shellsProvider) {
        addSubShells(shellsProvider.getConfiguredShells());
        this.shellNameAwareClass = shellsProvider.getClazz();
    }

    private AppShellBuilder addSubShell(SubShell<E> subShell) {
        logger.debug("adding sub shell ' {} '", subShell);
        this.subShells.add(subShell);
        return this;
    }

    private AppShellBuilder addSubShells(Collection<SubShell<E>> subShells) {
        for (SubShell<E> subShell : subShells) {
            addSubShell(subShell);
        }
        return this;
    }


    public ApplicationShell<E> build() {
        BuildEngine<E> buildEngine = new BuildEngine<>(shellNameAwareClass);

        logger.debug("start building application shell");

        buildEngine.processSubShells(subShells);

        logger.debug("checking dependencies of declared sub shells");

        SubShell<E> rootSubShell = buildEngine.peekRootSubShell();

        logger.debug("use  ' {} '  sub shell as application shell root", rootSubShell);

        buildEngine.injectSubShellsDependencies(rootSubShell);

        logger.debug("injecting annotations to shell tree");

        SubShellAnnotationInjectionVisitor injector = new SubShellAnnotationInjectionVisitor();

        rootSubShell = injector.doInjection(rootSubShell);

        logger.debug("finish building application shell");

        return new ApplicationShell<>(rootSubShell);
    }
}
