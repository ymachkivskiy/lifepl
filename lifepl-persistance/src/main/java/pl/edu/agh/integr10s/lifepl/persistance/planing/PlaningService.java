package pl.edu.agh.integr10s.lifepl.persistance.planing;

import pl.edu.agh.integr10s.engine.resolve.Planning;

import java.util.Collection;

public interface PlaningService {
    Collection<Planning> getAllPlannings();

    void addPlaning(Planning planning);
}
