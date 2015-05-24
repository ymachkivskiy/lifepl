package pl.edu.agh.integr10s.lifepl.model.world.restrictions;

public class EnergyRestriction implements SlotRestriction {

    private final int energyCost;

    public EnergyRestriction(int energyCost) {
        this.energyCost = energyCost;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    @Override
    public String toString() {
        return "Energy cost is " + energyCost + " units";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof EnergyRestriction) {
            EnergyRestriction other = (EnergyRestriction) obj;
            return energyCost == other.energyCost;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return energyCost;
    }
}
