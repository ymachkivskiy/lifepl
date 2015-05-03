package pl.edu.agh.integr10s.lifepl.cli.shell;

public enum SubShellName {
    NONE(
            "",
            ""
    ),
    MAIN(
            "lifepl",
            "Lifepl main menu"
    ),
    WORLDS(
            "worlds",
            ""//TODO
    )

    ;

    private final String prompt;
    private final String description;
    SubShellName(String promptName, String description) {
        this.prompt = promptName;
        this.description = description;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public String getDescription() {
        return description;
    }
}
