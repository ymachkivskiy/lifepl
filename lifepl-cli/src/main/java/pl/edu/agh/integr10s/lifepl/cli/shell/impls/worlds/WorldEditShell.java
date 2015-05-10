package pl.edu.agh.integr10s.lifepl.cli.shell.impls.worlds;

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

    @Override
    public void cliEnterLoop() {
        // epmty
    }

    @Override
    public void cliLeaveLoop() {
        worldService.updateWorld(world);
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
}
