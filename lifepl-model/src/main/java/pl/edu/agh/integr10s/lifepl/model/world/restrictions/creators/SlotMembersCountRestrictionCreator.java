package pl.edu.agh.integr10s.lifepl.model.world.restrictions.creators;

import pl.edu.agh.integr10s.lifepl.model.world.restrictions.SlotRestriction;
import pl.edu.agh.integr10s.lifepl.model.world.restrictions.SlotRestrictionCreator;
import pl.edu.agh.integr10s.lifepl.model.world.restrictions.impl.SlotMembersCountRestriction;

public class SlotMembersCountRestrictionCreator extends SlotRestrictionCreator {
    private static final String FORMAT = "int_number_>0";
    private static final String DESCRIPTION = "slot max members restriction";


    public SlotMembersCountRestrictionCreator() {
        super(FORMAT, DESCRIPTION);
    }

    @Override
    public SlotRestriction parseRestriction(String restrictionStr) {
        int intValue = Integer.parseInt(restrictionStr);
        return new SlotMembersCountRestriction(intValue);
    }

    @Override
    public boolean validateInput(String restrictionStr) {
        try {
            int intValue = Integer.parseInt(restrictionStr);
            if (intValue > 0) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
