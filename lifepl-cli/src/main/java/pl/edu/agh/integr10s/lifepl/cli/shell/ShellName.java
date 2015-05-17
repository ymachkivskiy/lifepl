package pl.edu.agh.integr10s.lifepl.cli.shell;


import pl.edu.agh.integr10s.clibuilder.shell.ShellNameAware;

public enum ShellName implements ShellNameAware<ShellName> {

    NONE(
            "",
            "",
            ""
    ),


    MAIN(
            "lifepl",
            "Lifepl application menu",
            ""
    ) {
        @Override
        public boolean isMain() {
            return true;
        }
    },


    ACTORS(
            "actors",
            "Actors command line",
            "a"
    ),

    WORLDS(
            "worlds",
            "World models command line",
            "w"
    ),

    PLANING(
            "planing",
            "Planning planing command line",
            "plan"
    )

    ;

    private final String prompt;
    private final String description;
    private final String abbrev;

    ShellName(String promptName, String description, String abbrev) {
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
