package pl.edu.agh.integr10s.lifepl.model.definition.goal;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Action {
    private final String name;

    public Action(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 137)
                .append(name)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Action) {
            Action other = (Action) obj;
            return name.equals(other.name);
        }
        return false;
    }

    @Override
    public String toString() {
        return "action{ " + name + " }";
    }
}
