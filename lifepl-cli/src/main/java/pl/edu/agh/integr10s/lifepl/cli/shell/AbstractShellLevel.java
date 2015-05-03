package pl.edu.agh.integr10s.lifepl.cli.shell;

public abstract class AbstractShellLevel implements ShellLevel {

    private final String prompt;

    public AbstractShellLevel(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public String getPrompt() {
        return this.prompt;
    }

}
