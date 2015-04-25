package pl.edu.agh.integr10s.lifepl.model.definition.goal;

import org.junit.Before;

public class IndependentActionsGoalDefinitionTest extends GoalDefinitionTest {

    @Before
    public void setUpClass() {
        GoalDefinitionBuilder builder = GoalDefinition.Builder(GoalActionDependency.INDEPENDENT);

        for (int i = 0; i < 20; ++i) {
            builder.addAction(createRandomAction());
        }

        tested = builder.build();
    }
}
