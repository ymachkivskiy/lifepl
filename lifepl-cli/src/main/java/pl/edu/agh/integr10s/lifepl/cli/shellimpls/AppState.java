package pl.edu.agh.integr10s.lifepl.cli.shellimpls;

import pl.edu.agh.integr10s.clibuilder.shell.ApplicationState;

public class AppState implements ApplicationState{
    private int value = 13;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
