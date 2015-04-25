package pl.edu.agh.integr10s.lifepl.model.working.goal;

import org.apache.log4j.Logger;
import pl.edu.agh.integr10s.lifepl.model.definition.goal.Action;
import pl.edu.agh.integr10s.lifepl.model.definition.goal.GoalDefinition;

import java.util.*;

/**
 * Monitor stanu spelnienia celu, jest tworzony na podstawie GoalDefinition,
 * dla ktorego na poczatku wszystkie zadania nie sa spelnione.
 */
public class GoalCompletionTracker {
    private final static Logger logger = Logger.getLogger(GoalCompletionTracker.class);

    private Map<Action, TaskStatus> actionToTaskMapping = new HashMap<Action, TaskStatus>();

    public GoalCompletionTracker(GoalDefinition goalDefinition) {
        //TODO Yarek implement
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
    public Collection<TaskStatus> getTasks() {
        //TODO Yarek : implement
        return Collections.emptyList();
    }

    /**
     * Zwraca status zadania dla podanej akcji jesli taka nalezy do celu, inaczej Optional.empty()
     *
     * @param action akcja dla kt�rej zwracamy stan zadania
     * @return stan zadania
     */
    public Optional<TaskStatus> getStatusForAction(Action action) {
        return Optional.ofNullable(actionToTaskMapping.get(action));
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
     * Sluzy do pobierania zbioru nie dokonanych akcji, ktore moga aktualnie zostac wykonane,
     * w sensie wszystkie akcje, od ktorych sa zalezne zostaly wykonane, lub akcje nie maja zaleznosci.
     *
     * @return zbior niedokonanych akcji ktore aktualnie mozna wykonac
     */
    public Set<TaskStatus> getTasksCanBePerformedCurrently() {
        //TODO Yarek : implement
        return Collections.emptySet();
    }
}
