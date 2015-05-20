package pl.edu.agh.integr10s.lifepl.cli.shell.worlds;

import asg.cliche.Command;
import asg.cliche.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.shell.SpecializedSubShell;
import pl.edu.agh.integr10s.lifepl.model.world.World;
import pl.edu.agh.integr10s.lifepl.persistance.worlds.WorldsService;

public class WorldEditShell extends SpecializedSubShell {

    private static final Logger logger = LoggerFactory.getLogger(WorldEditShell.class);

    private final WorldsService worldService;
    private final World world;
    private boolean saveBeforeExit;

    @Override
    protected void onShellEnter() {
        saveBeforeExit = false;
        logger.info("save on exit default value is set for FALSE. Change it's value by appropriate command.");
    }

    @Override
    public void onShellExit() {
        if(saveBeforeExit){
            logger.info("saving world model before exit");
            worldService.updateWorld(world);
        }else {
            logger.info("exiting world model view without saving");
        }
    }

    public WorldEditShell(WorldsService service, World world) {
        super(world.getName(), "Editing " + world.getName());
        this.worldService = service;
        this.world = world;
    }

    @Command(name = "set-description", abbrev = "sd", description = "Set new description")
    public void setDescription(@Param(name = "new description") String newDescription) {
        logger.debug("set world {} new description \"{}\"", world, newDescription);
        world.setDescription(newDescription);
    }

    @Command(name = "save-before-exit", abbrev = "save", description = "Save ")
    public void safeBeforeExit(@Param(name = "flag", description = "Flag that indicates saving") boolean doSafe) {
        logger.debug("set save before exit flag {} ", doSafe);
        saveBeforeExit = doSafe;
    }


}
