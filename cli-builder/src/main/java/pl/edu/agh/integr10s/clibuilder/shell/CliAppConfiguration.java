package pl.edu.agh.integr10s.clibuilder.shell;


import java.util.List;

public interface CliAppConfiguration<E extends Enum<E> & ShellNameAware<E>, AppStateT extends AppContext> {
    List<CategorizedShell<E, AppStateT>> getConfiguredShells();

    Class<E> getClazz();

    AppStateT getInitialState();
}
