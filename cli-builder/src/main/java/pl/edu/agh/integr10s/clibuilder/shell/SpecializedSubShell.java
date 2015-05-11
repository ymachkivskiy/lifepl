package pl.edu.agh.integr10s.clibuilder.shell;

public abstract class SpecializedSubShell extends AbstractShell {

    private final String propmt;
    private final String description;

    public SpecializedSubShell(String prompt, String description) {
        super(prompt);
        this.propmt = prompt;
        this.description = description;
    }


    public String getPrompt() {
        return propmt;
    }

    public String getDescription() {
        return description;
    }
}
