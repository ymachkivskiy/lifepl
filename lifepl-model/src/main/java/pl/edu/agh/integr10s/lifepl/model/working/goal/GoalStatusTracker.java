package pl.edu.agh.integr10s.lifepl.model.working.goal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.definition.goal.Action;
import pl.edu.agh.integr10s.lifepl.model.definition.goal.GoalDefinition;
import pl.edu.agh.integr10s.lifepl.model.graph.model.DependencyGraph;
import pl.edu.agh.integr10s.lifepl.model.graph.model.IdempotentFunction;

import java.util.*;

/**
 * Monitor stanu spelnienia celu, jest tworzony na podstawie GoalDefinition,
 * dla ktorego na poczatku wszystkie zadania nie sa spelnione.
 */
public class GoalStatusTracker {

    private final static Logger logger = LoggerFactory.getLogger(GoalStatusTracker.class);
    private final ActionToTaskStatusMapper actionMapper = new ActionToTaskStatusMapper();

    private final DependencyGraph<ActionStatus> taskDependencyGraph;

    public GoalStatusTracker(GoalDefinition goalDefinition) {
        taskDependencyGraph = goalDefinition.getActionDependencyGraph().translateSavingDependencies(actionMapper);
    }

    /**
     * Zwraca informacje, czy dany cel zostal w pelni osiagniety.
     *
     * @return osiagniecie celu
     */
    public boolean goalReached() {
        //TODO Yarek : implement
        return false;
    }

    /**
     * Zwraca kolekcje zadan zawartych w tym celu. Zadania sa uporzadkowane wedlug
     * zaleznosci, tzn. ze jesli B zalezy od A, to a pojawi sie w kolekcji wczesniej.
     *
     * @return kolekcja zawartychw srodku zadan
     */
    public Set<ActionStatus> getTasks() {
        return taskDependencyGraph.getElements();
    }

    /**
     * Zwraca status zadania dla podanej akcji jesli taka nalezy do celu, inaczej Optional.empty()
     *
     * @param action akcja dla kt�rej zwracamy stan zadania
     * @return stan zadania
     */
    public Optional<ActionStatus> getStatusForAction(Action action) {
        return actionMapper.getTaskStatusForAction(action);
    }

    /**
     * Metoda ktora zazwnacza podana skcje (jesli taka nalezy do celu) jako wykonana tylko
     * w przypadku jesli akcje od ktorych ona jest zalezna zostaly wykonane, czyli sa done.
     *
     * @param action akcja ktora probujemy
     * @return jesli akcja zostala oznaczona jako wykonana true,  w przeciwnym przypadku false
     */
    public boolean markAsDone(Action action) {
        //TODO Yarek : implement
        return false;
    }

    /**
     * Metoda ktora zaznacza akcje oraz wzystkie od niej zalezne akcje jako nie zrobione.
     *
     * @param action akcja ktora trzeba oznaczyc jako nie zrobiona
     * @return jesli akcja zostala zaznaczona jako nie wykonana true, w przeciwnym przypadku false
     */
    public boolean markAsNotDone(Action action) {
        //TODO Yarek : implement
        return false;
    }

    /**
     * Sluzy do pobierania zbioru nie wykonanych akcji, ktore moga aktualnie zostac wykonane,
     * w sensie wszystkie akcje, od ktorych sa zalezne zostaly wykonane, lub akcje nie maja zaleznosci.
     *
     * @return zbior nie wykonanych akcji ktore aktualnie mozna wykonac
     */
    public Set<ActionStatus> getTasksCanBePerformedCurrently() {
        //TODO Yarek : implement
        return Collections.emptySet();
    }

    private static class ActionToTaskStatusMapper extends IdempotentFunction<Action, ActionStatus> {

        public Optional<ActionStatus> getTaskStatusForAction(Action action) {
            return getStoredResultForArgument(action);
        }

        @Override
        protected ActionStatus apply(Action argument) {
            return new ActionStatus(argument);
        }

    }
}
