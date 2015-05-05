package pl.edu.agh.integr10s.lifepl.cli.shell.build;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.utils.SubShellAnnotationInjectionVisitor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AppShellBuilder {
    private static final Logger logger = LoggerFactory.getLogger(AppShellBuilder.class);

    private final Set<SubShell> subShells = new HashSet<>();

    public AppShellBuilder addSubShell(SubShell level) {
        this.subShells.add(level);
        return this;
    }

    public AppShellBuilder addSubShells(Collection<SubShell> subShells) {
        for (SubShell subShell : subShells) {
            addSubShell(subShell);
        }
        return this;
    }


    public ApplicationShell build() {
        BuildEngine buildEngine = new BuildEngine();

        logger.debug("start building application shell");

        buildEngine.processSubShells(subShells);

        logger.debug("checking dependencies of declared sub shells");

        SubShell rootSubShell = buildEngine.peekRootSubShell();

        logger.debug("use  ' {} '  sub shell as application shell root", rootSubShell);

        buildEngine.injectSubShellsDependencies(rootSubShell);

        logger.debug("injecting annotations to shell tree");

        SubShellAnnotationInjectionVisitor injector = new SubShellAnnotationInjectionVisitor();

        rootSubShell = injector.doInjection(rootSubShell);

        logger.debug("finish building application shell");

        return new ApplicationShell(rootSubShell);
    }
}
