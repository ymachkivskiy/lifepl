package pl.edu.agh.integr10s.lifepl.persistance.common;

import pl.edu.agh.integr10s.engine.resolve.Planning;

import java.util.Collection;

public interface PlaningRepository {
    Collection<Planning> getAllPlannings();

    void addPlaning(Planning planning);
}
