package pl.edu.agh.integr10s.lifepl.cli.shell;

public interface ShellNameAware<E extends Enum<E>> {
    String getPrompt();

    String getDescription();

    String getAbbrev();

    /**
     * !!Only one enum constant should return true for this method!!
     * @return flag indication if it is main enum constant
     */
    boolean isMain();
}