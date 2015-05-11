package pl.edu.agh.integr10s.engine.factory;

public class EngineDescription {

    private final String name;
    private final String description;
    private final String algorithm;
    private final String algorithmComplexity;
    private final String algorithmEfficiency;

    public EngineDescription(String name, String description, String algorithm, String algorithmComplexity, String algorithmEfficiency) {
        this.name = name;
        this.description = description;
        this.algorithm = algorithm;
        this.algorithmComplexity = algorithmComplexity;
        this.algorithmEfficiency = algorithmEfficiency;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getAlgorithmComplexity() {
        return algorithmComplexity;
    }

    public String getAlgorithmEfficiency() {
        return algorithmEfficiency;
    }
}
