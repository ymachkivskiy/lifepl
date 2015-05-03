package pl.edu.agh.integr10s.lifepl.cli.shell;

public enum SubShellName {
    NONE(""),
    MAIN("lifepl"),
    WORLDS("worlds")

    ;

    private final String shellLevelName;
    SubShellName(String shellLevelName) {
        this.shellLevelName = shellLevelName;
    }

    public String getShellLevelName() {
        return this.shellLevelName;
    }
}
