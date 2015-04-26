package pl.edu.agh.integr10s.lifepl.model.graph.model;


public interface Translatable<T> {
    public <R> Translatable<R> translateSavingDependencies(IdempotentFunction<T, R> elementTranslationFunction);
}
