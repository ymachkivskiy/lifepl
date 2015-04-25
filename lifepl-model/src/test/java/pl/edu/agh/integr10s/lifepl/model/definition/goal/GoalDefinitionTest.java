package pl.edu.agh.integr10s.lifepl.model.definition.goal;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

public abstract class GoalDefinitionTest {

    protected GoalDefinition tested;
    private Set<Action> containingActions = new HashSet<Action>();

    private int actionCounter = 0;

    protected Action createRandomAction() {
        Action action = new Action("Action - " + actionCounter++);
        containingActions.add(action);
        return action;
    }

    @Test
    public void testContainsAllActions() {

        Set<Action> actualActions = new HashSet<Action>();
        for (Action a : tested) {
            actualActions.add(a);
        }

        assertEquals(containingActions, actualActions);
    }
}
