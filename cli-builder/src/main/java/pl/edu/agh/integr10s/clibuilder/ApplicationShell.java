package pl.edu.agh.integr10s.clibuilder;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.build.AppShellBuilder;
import pl.edu.agh.integr10s.clibuilder.shell.AppContext;
import pl.edu.agh.integr10s.clibuilder.shell.CliAppConfiguration;
import pl.edu.agh.integr10s.clibuilder.shell.ShellNameAware;
import pl.edu.agh.integr10s.clibuilder.shell.SubShell;

import java.io.IOException;

public final class ApplicationShell<E extends Enum<E> & ShellNameAware<E>, AppStateT extends AppContext> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationShell.class);

    private final SubShell<E, AppStateT> rootLevel;

    public ApplicationShell(SubShell<E,AppStateT> rootLevel) {
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

    private static <E extends Enum<E> & ShellNameAware<E>, AppStateT extends AppContext> ApplicationShell<E, AppStateT> createShell(CliAppConfiguration<E, AppStateT> cliAppConfiguration) {
        return new AppShellBuilder<>(cliAppConfiguration).build();
    }


    public static <E extends Enum<E> & ShellNameAware<E>, AppStateT extends AppContext> void startApp(CliAppConfiguration<E, AppStateT> shellsProvider) throws IOException {
        logger.debug("starting application with configured shells ' {} '", shellsProvider);
        createShell(shellsProvider).start();
    }
}
