package pl.edu.agh.integr10s.lifepl.model.definition.goal;

import org.junit.Before;

public class IndependentActionsGoalDefinitionTest extends GoalDefinitionTest {

    @Before
    public void setUpClass() {
        GoalDefinition.GoalDefinitionBuilder builder = GoalDefinition.NewBuilder();

        for (int i = 0; i < 20; ++i) {
            builder.addIndependentElement(createRandomAction());
        }

        tested = builder.buildGoalDefinition();
    }
}
