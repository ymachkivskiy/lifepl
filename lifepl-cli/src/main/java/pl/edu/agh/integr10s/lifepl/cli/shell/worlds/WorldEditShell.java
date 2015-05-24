package pl.edu.agh.integr10s.lifepl.cli.shell.worlds;

import asg.cliche.Command;
import asg.cliche.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.shell.SpecializedSubShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationContext;
import pl.edu.agh.integr10s.lifepl.cli.shell.actions.WorldActionSlotEditShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.actions.WorldActionsShell;
import pl.edu.agh.integr10s.lifepl.model.world.World;
import pl.edu.agh.integr10s.lifepl.persistence.common.WorldsRepository;

public class WorldEditShell extends SpecializedSubShell<ApplicationContext> {

    private static final Logger logger = LoggerFactory.getLogger(WorldEditShell.class);

    private final WorldsRepository worldsRepository;
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
            worldsRepository.updateWorld(world);
        }else {
            logger.info("exiting world model view without saving");
        }
    }

    public WorldEditShell(WorldsRepository repository, World world) {
        super(world.getName(), "Editing " + world.getName());
        this.worldsRepository = repository;
        this.world = world;
    }

    @Command(name = "set-description", abbrev = "sd", description = "Set new description")
    public void setDescription(@Param(name = "new description") String newDescription) {
        logger.debug("set world {} new description \"{}\"", world, newDescription);
        world.setDescription(newDescription);
    }

    @Command(name = "actions", abbrev = "act", description = "Edit actions shell")
    public void actions() {
        logger.debug("start edit actions for world {}", world);
        runSpecializedShell(new WorldActionsShell(world));
    }

    @Command(name ="action-slots", abbrev = "slots", description = "Edit action slots")
    public void actionSlots() {
        if (world.getAllowedActions().size() > 0) {
            logger.debug("start edit action slots for world {}", world);
            runSpecializedShell(new WorldActionSlotEditShell(world));
        }else {
            logger.warn("cannot edit action slots for world where is no allowed actions");
        }
    }

    @Command(name = "save-before-exit", abbrev = "save", description = "Save ")
    public void safeBeforeExit(@Param(name = "flag", description = "Flag that indicates saving") boolean doSafe) {
        logger.debug("set save before exit flag {} ", doSafe);
        saveBeforeExit = doSafe;
    }


}
