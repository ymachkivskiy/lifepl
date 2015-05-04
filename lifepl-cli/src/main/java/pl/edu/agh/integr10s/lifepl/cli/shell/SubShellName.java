package pl.edu.agh.integr10s.lifepl.cli.shell;

public enum SubShellName {
    NONE(
        "",
        "",
        ""
    ),
    MAIN(
        "lifepl",
        "Lifepl main menu",
        ""
    ),
    WORLDS(
        "worlds",
        "Some worlds description",
        "ws"
    );

    private final String prompt;
    private final String description;
    private final String abbrev;

    SubShellName(String promptName, String description, String abbrev) {
        this.prompt = promptName;
        this.description = description;
        this.abbrev = abbrev;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public String getDescription() {
        return this.description;
    }

    public String getAbbrev() {
        return this.abbrev;
    }
}
