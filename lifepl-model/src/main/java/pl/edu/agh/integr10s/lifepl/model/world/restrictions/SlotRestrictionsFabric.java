package pl.edu.agh.integr10s.lifepl.model.world.restrictions;

import java.util.List;

public class SlotRestrictionsFabric {
    private List<SlotRestrictionCreator> restrictionCreators;

    public List<SlotRestrictionCreator> getRestrictionCreators() {
        return restrictionCreators;
    }

    public void setRestrictionCreators(List<SlotRestrictionCreator> restrictionCreators) {
        this.restrictionCreators = restrictionCreators;
    }
}
