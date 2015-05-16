package pl.edu.agh.integr10s.lifepl.model.world.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlotMembersCountProperty implements SlotProperty {

    private static final Logger logger = LoggerFactory.getLogger(SlotMembersCountProperty.class);

    private final int maxMembersCount;
    private int currentMembersCount;

    public SlotMembersCountProperty(int maxMembersCount) {
        this.maxMembersCount = maxMembersCount;
        this.currentMembersCount = 0;
    }

    public boolean canAddNewMember() {
        return currentMembersCount < maxMembersCount;
    }

    public void addNewMember() {
        logger.debug("add new member to property {}", this);
        currentMembersCount++;

        if (currentMembersCount > maxMembersCount) {
            logger.error("adding new member to full slot");
            currentMembersCount = maxMembersCount;
        }
    }

    public void removeMember() {
        logger.debug("remove member from property {}", this);
        currentMembersCount--;

        if (currentMembersCount < 0) {
            logger.error("removing member from empty slot");
            currentMembersCount = 0;
        }
    }

    @Override
    public int hashCode() {
        return maxMembersCount;
    }

    @Override
    public boolean equals(Object obj) {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.world.properties.SlotMembersCountProperty:: boolean equals ()
        return super.equals(obj);
    }

    @Override
    public String toString() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.world.properties.SlotMembersCountProperty:: String toString ()
        return super.toString();
    }
}
