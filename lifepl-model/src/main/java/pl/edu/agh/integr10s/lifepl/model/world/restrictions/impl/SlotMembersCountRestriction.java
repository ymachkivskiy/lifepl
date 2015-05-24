package pl.edu.agh.integr10s.lifepl.model.world.restrictions.impl;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.world.restrictions.SlotRestriction;

public class SlotMembersCountRestriction implements SlotRestriction {

    private static final Logger logger = LoggerFactory.getLogger(SlotMembersCountRestriction.class);

    private final int maxMembersCount;
    private int currentMembersCount;

    public SlotMembersCountRestriction(int maxMembersCount) {
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
        return new HashCodeBuilder(17, 57)
                .append(maxMembersCount)
                .append(currentMembersCount)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SlotMembersCountRestriction) {
            SlotMembersCountRestriction other = (SlotMembersCountRestriction) obj;
            return maxMembersCount == other.maxMembersCount && currentMembersCount == other.currentMembersCount;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Max members count is " + maxMembersCount + " persons";
    }
}
