package pl.edu.agh.integr10s.lifepl.cli.shell.impls;

import pl.edu.agh.integr10s.lifepl.cli.shell.ShellNameAware;

public enum SubShellName implements ShellNameAware<SubShellName> {

    NONE(
        "",
        "",
        ""
    ),


    MAIN(
        "lifepl",
        "Lifepl main menu",
        ""
    ) {
        @Override
        public boolean isMain() {
            return true;
        }
    },


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

    @Override
    public String getPrompt() {
        return this.prompt;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getAbbrev() {
        return this.abbrev;
    }

    @Override
    public boolean isMain() {
        return false;
    }


}
