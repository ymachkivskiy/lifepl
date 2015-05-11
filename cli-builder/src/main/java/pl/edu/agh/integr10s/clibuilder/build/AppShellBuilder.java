package pl.edu.agh.integr10s.clibuilder.build;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.ApplicationShell;
import pl.edu.agh.integr10s.clibuilder.shell.AppContext;
import pl.edu.agh.integr10s.clibuilder.shell.CategorizedShell;
import pl.edu.agh.integr10s.clibuilder.shell.CliAppConfiguration;
import pl.edu.agh.integr10s.clibuilder.shell.ShellNameAware;
import pl.edu.agh.integr10s.clibuilder.utils.SubShellAnnotationInjectionVisitor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AppShellBuilder<E extends Enum<E> & ShellNameAware<E>, AppStateT extends AppContext> {
    private static final Logger logger = LoggerFactory.getLogger(AppShellBuilder.class);

    private final Set<CategorizedShell<E, AppStateT>> categorizedShells = new HashSet<>();
    private final CliAppConfiguration<E, AppStateT> config;

    public AppShellBuilder(CliAppConfiguration<E, AppStateT> shellsProvider) {
        addSubShells(shellsProvider.getConfiguredShells());
        this.config = shellsProvider;
    }

    private AppShellBuilder addSubShell(CategorizedShell<E, AppStateT> categorizedShell) {
        logger.debug("adding sub shell ' {} '", categorizedShell);
        this.categorizedShells.add(categorizedShell);
        return this;
    }

    private AppShellBuilder addSubShells(Collection<CategorizedShell<E, AppStateT>> categorizedShells) {
        for (CategorizedShell<E, AppStateT> categorizedShell : categorizedShells) {
            addSubShell(categorizedShell);
        }
        return this;
    }


    public ApplicationShell<E, AppStateT> build() {
        BuildEngine<E, AppStateT> buildEngine = new BuildEngine<>(config.getClazz());

        logger.debug("start building application shell");

        buildEngine.processSubShells(categorizedShells);

        logger.debug("checking dependencies of declared sub shells");

        CategorizedShell<E, AppStateT> rootCategorizedShell = buildEngine.peekRootSubShell();

        logger.debug("use  ' {} '  sub shell as application shell root", rootCategorizedShell);

        buildEngine.injectSubShellsDependencies(rootCategorizedShell);

        logger.debug("setting initial application state");

        rootCategorizedShell.setApplicationState(config.getInitialState());

        logger.debug("injecting annotations to shell tree");

        SubShellAnnotationInjectionVisitor<E, AppStateT> injector = new SubShellAnnotationInjectionVisitor<>();

        rootCategorizedShell = injector.doInjection(rootCategorizedShell);

        logger.debug("finish building application shell");

        return new ApplicationShell<>(rootCategorizedShell);
    }
}
