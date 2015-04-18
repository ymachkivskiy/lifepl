package pl.edu.agh.integr.goal;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Monitor stanu spe?nienia celu, jest tworzony na podstawie  @see GoalDefinition,
 * dla którego na pocz?tku wszystkie zadania nie s? spe?nione.
 */
public class GoalCompletionTracker {
  public GoalCompletionTracker(GoalDefinition goalDefinition) {
    //TODO Yarek implement
  }

  /**
   * Zwraca informacje, czy dany cel zosta? w pe?ni osi?gni?ty.
   *
   * @return osi?gni?cie celu
   */
  public boolean goalReached() {
    //TODO Yarek : implement
    return false;
  }

  /**
   * Zwraca kolekcj? zada? zawartych w tym celu. Zadania sa uporzadkowane wed?ug
   * zale?no?ci, tzn. ?e je?li B zale?y od A, to a pojawi si? w kolekcji wcze?niej.
   *
   * @return kolekcja zawartychw ?rodku zada?
   */
  public Collection<TaskStatus> getTasks() {
    //TODO Yarek : implement
    return Collections.emptyList();
  }

  /**
   * S?u?y do pobierania zbioru nie dokonanych akcji, które mog? aktualnie zosta? wykonane,
   * w sensie wszystkie akcje, od których s? zale?ne zosta?y wykonane, lub akcje nie maj? zale?no?ci.
   *
   * @return zbiór niedokonanych akcji które aktualnie mo?na wykona?
   */
  public Set<TaskStatus> getTasksCanBePerformedCurrently() {
    //TODO Yarek : implement
    return Collections.emptySet();
  }

  /**
   * Zwraca status zadania dla podanej akcji jesli taka nalezy do celu, inaczej Optional.empty()
   *
   * @param action akcja dla której zwracamy stan zadania
   * @return stan zadania
   */
  public Optional<TaskStatus> getStatusForAction(Action action) {
    //TODO Yarek : implement
    return null;
  }
}
