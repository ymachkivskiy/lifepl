package pl.edu.agh.integr10s.clibuilder.shell;


import java.util.List;

public interface ConfiguredShellsProvider<E extends Enum<E> & ShellNameAware<E>> {
    List<SubShell<E>> getConfiguredShells();

    Class<E> getClazz();
}
