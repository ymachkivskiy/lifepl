package pl.edu.agh.integr10s.clibuilder;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.build.AppShellBuilder;
import pl.edu.agh.integr10s.clibuilder.shell.ConfiguredShellsProvider;
import pl.edu.agh.integr10s.clibuilder.shell.ShellNameAware;
import pl.edu.agh.integr10s.clibuilder.shell.SubShell;

import java.io.IOException;

public final class ApplicationShell<E extends Enum<E> & ShellNameAware<E>> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationShell.class);

    private final SubShell<E> rootLevel;

    public ApplicationShell(SubShell<E> rootLevel) {
        this.rootLevel = rootLevel;
    }


    public void start() {
        try {
            this.rootLevel.runLevel();
        } catch (IOException e) {
            logger.error("error during bootstrapping application shell", e);
            System.exit(1);
        }
    }

    private static <E extends Enum<E> & ShellNameAware<E>> ApplicationShell<E> createShell(ConfiguredShellsProvider<E> shellsProvider) {
        return new AppShellBuilder<>(shellsProvider).build();
    }


    public static <E extends Enum<E> & ShellNameAware<E>> void startApp(ConfiguredShellsProvider<E> shellsProvider) throws IOException {
        logger.debug("starting application with configured shells ' {} '", shellsProvider);
        createShell(shellsProvider).start();
    }
}
