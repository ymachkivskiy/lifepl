package pl.edu.agh.integr10s.lifepl.model.world.restrictions;

public class EnergyRestriction implements SlotRestriction {

    private final int energyCost;

    public EnergyRestriction(int energyCost) {
        this.energyCost = energyCost;
    }

    public int getEnergyCost() {
        return energyCost;
    }
}
