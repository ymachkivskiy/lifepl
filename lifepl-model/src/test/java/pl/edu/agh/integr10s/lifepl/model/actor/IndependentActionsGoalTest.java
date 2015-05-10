package pl.edu.agh.integr10s.lifepl.model.actor;

import org.junit.Before;

public class IndependentActionsGoalTest extends GoalTest {

    @Before
    public void setUpClass() {
        Goal.GoalDefinitionBuilder builder = Goal.NewBuilder();

        for (int i = 0; i < 20; ++i) {
            builder.addIndependentElement(createRandomAction());
        }

        tested = builder.buildGoalDefinition();
    }
}
