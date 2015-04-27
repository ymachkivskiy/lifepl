package pl.edu.agh.integr10s.lifepl.model.util.graph;


import pl.edu.agh.integr10s.lifepl.model.util.IdempotentFunction;

public interface Translatable<T> {
    public <R> Translatable<R> translateSavingDependencies(IdempotentFunction<T, R> elementTranslationFunction);
}
