package pl.edu.agh.integr10s.lifepl.model.actor.capability;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.model.world.properties.SlotProperty;

public abstract class AbstractCapability<SPT extends  SlotProperty> {

    private final Class<SPT> applicablePropertyClass;
    private AbstractCapability<? extends SlotProperty> delegate;

    private Actor actor;

    public AbstractCapability(Class<SPT> applicablePropertyClass) {
        this.applicablePropertyClass = applicablePropertyClass;
    }

    public abstract boolean applicableTo(SPT slotProperty);

    public abstract void applyOn(SPT slotProperty);

    public abstract void revokeOn(SPT slotProperty);

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 37)
                .append(applicablePropertyClass.hashCode())
                .append(actor.hashCode())
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof AbstractCapability) {
            AbstractCapability other = (AbstractCapability) obj;
            if (this.getClass() != obj.getClass()) {
                return false;
            }

            return actor.equals(other.actor);
        }

        return false;
    }

    @Override
    public String toString() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.model.actor.capability.Capability:: String toString ()
        return super.toString();
    }
}
