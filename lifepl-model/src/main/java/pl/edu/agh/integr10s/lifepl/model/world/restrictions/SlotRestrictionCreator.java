package pl.edu.agh.integr10s.lifepl.model.world.restrictions;

public abstract class SlotRestrictionCreator {
    private final String restrictionInputFormat;
    private final String restrictionDescription;

    public SlotRestrictionCreator(String restrictionInputFormat, String restrictionDescription) {
        this.restrictionInputFormat = restrictionInputFormat;
        this.restrictionDescription = restrictionDescription;
    }

    public abstract SlotRestriction parseRestriction(String restrictionStr);

    public abstract boolean validateInput(String restrictionStr);

    public String getRestrictionInputFormat() {
        return restrictionInputFormat;
    }

    public String getRestrictionDescription() {
        return restrictionDescription;
    }
}
