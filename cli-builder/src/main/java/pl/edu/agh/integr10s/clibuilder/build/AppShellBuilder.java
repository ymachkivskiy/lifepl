package pl.edu.agh.integr10s.clibuilder.build;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.ApplicationShell;
import pl.edu.agh.integr10s.clibuilder.shell.ApplicationState;
import pl.edu.agh.integr10s.clibuilder.shell.CliAppConfiguration;
import pl.edu.agh.integr10s.clibuilder.shell.ShellNameAware;
import pl.edu.agh.integr10s.clibuilder.shell.SubShell;
import pl.edu.agh.integr10s.clibuilder.utils.SubShellAnnotationInjectionVisitor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AppShellBuilder<E extends Enum<E> & ShellNameAware<E>, AppStateT extends ApplicationState> {
    private static final Logger logger = LoggerFactory.getLogger(AppShellBuilder.class);

    private final Set<SubShell<E, AppStateT>> subShells = new HashSet<>();
    private final CliAppConfiguration<E, AppStateT> config;

    public AppShellBuilder(CliAppConfiguration<E, AppStateT> shellsProvider) {
        addSubShells(shellsProvider.getConfiguredShells());
        this.config = shellsProvider;
    }

    private AppShellBuilder addSubShell(SubShell<E, AppStateT> subShell) {
        logger.debug("adding sub shell ' {} '", subShell);
        this.subShells.add(subShell);
        return this;
    }

    private AppShellBuilder addSubShells(Collection<SubShell<E, AppStateT>> subShells) {
        for (SubShell<E, AppStateT> subShell : subShells) {
            addSubShell(subShell);
        }
        return this;
    }


    public ApplicationShell<E, AppStateT> build() {
        BuildEngine<E, AppStateT> buildEngine = new BuildEngine<>(config.getClazz());

        logger.debug("start building application shell");

        buildEngine.processSubShells(subShells);

        logger.debug("checking dependencies of declared sub shells");

        SubShell<E, AppStateT> rootSubShell = buildEngine.peekRootSubShell();

        logger.debug("use  ' {} '  sub shell as application shell root", rootSubShell);

        buildEngine.injectSubShellsDependencies(rootSubShell);

        logger.debug("setting initial application state");

        rootSubShell.setApplicationState(config.getInitialState());

        logger.debug("injecting annotations to shell tree");

        SubShellAnnotationInjectionVisitor<E, AppStateT> injector = new SubShellAnnotationInjectionVisitor<>();

        rootSubShell = injector.doInjection(rootSubShell);

        logger.debug("finish building application shell");

        return new ApplicationShell<>(rootSubShell);
    }
}
