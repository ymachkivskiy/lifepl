package pl.edu.agh.integr10s.lifepl.cli.shell;

import pl.edu.agh.integr10s.clibuilder.shell.AppContext;
import pl.edu.agh.integr10s.lifepl.persistance.actors.ActorsService;

public class ApplicationContext implements AppContext {

    private ActorsService actorsService;

    public ActorsService getActorsService() {
        return actorsService;
    }

    public void setActorsService(ActorsService actorsService) {
        this.actorsService = actorsService;
    }
}
