package pl.edu.agh.integr.goal;

import org.junit.Before;

public class IndependentActionsGoalDefinitionTest extends GoalDefinitionTest {

  @Before
  public void setUpClass() {
    GoalDefinitionBuilder builder = GoalDefinition.Builder(GoalActionDependency.INDEPENDENT);

    for (int i = 0; i < 1000; ++i) {
      builder.addAction(createRandomAction());
    }

    tested = builder.build();
  }
}
