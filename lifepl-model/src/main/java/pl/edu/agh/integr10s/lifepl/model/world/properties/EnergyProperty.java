package pl.edu.agh.integr10s.lifepl.model.world.properties;

public class EnergyProperty implements SlotProperty {

    private final int energyCost;

    public EnergyProperty(int energyCost) {
        this.energyCost = energyCost;
    }

    public int getEnergyCost() {
        return energyCost;
    }
}
