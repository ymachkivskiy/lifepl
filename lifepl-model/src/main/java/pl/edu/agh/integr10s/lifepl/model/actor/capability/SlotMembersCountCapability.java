package pl.edu.agh.integr10s.lifepl.model.actor.capability;

import pl.edu.agh.integr10s.lifepl.model.world.properties.SlotMembersCountProperty;

public class SlotMembersCountCapability extends AbstractCapability<SlotMembersCountProperty> {
//    private static final

    @Override
    public boolean applicableTo(SlotMembersCountProperty slotProperty) {
        return slotProperty.canAddNewMember();
    }

    @Override
    public void applyOn(SlotMembersCountProperty slotProperty) {
        //TODO implement pl.edu.agh.integr10s.lifepl.model.actor.capability.SlotMembersCountCapability:: void applyOn()

    }

    @Override
    public void revokeOn(SlotMembersCountProperty slotProperty) {
        //TODO implement pl.edu.agh.integr10s.lifepl.model.actor.capability.SlotMembersCountCapability:: void revokeOn()

    }

    public SlotMembersCountCapability() {
        super(SlotMembersCountProperty.class);
    }
}
