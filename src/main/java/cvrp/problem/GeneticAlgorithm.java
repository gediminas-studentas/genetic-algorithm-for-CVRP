
package cvrp.problem;

import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

public class GeneticAlgorithm {

    private static RandomGenerator randomGenerator = new JDKRandomGenerator();

    private final CrossoverPolicy crossoverPolicy;
    private final double crossoverRate;

    private final MutationPolicy mutationPolicy;
    private final double mutationRate;

    private final SelectionPolicy selectionPolicy;

    public GeneticAlgorithm(final CrossoverPolicy crossoverPolicy,
                            final double crossoverRate,
                            final MutationPolicy mutationPolicy,
                            final double mutationRate,
                            final SelectionPolicy selectionPolicy
    ) throws IllegalArgumentException {

        if (crossoverRate < 0 || crossoverRate > 1) {
            throw new IllegalArgumentException("Kryžminimo dažnis turėtų buti tarp 0..1");
        }
        if (mutationRate < 0 || mutationRate > 1) {
            throw new IllegalArgumentException("Mutavimo dažnis turėtų buti tarp 0..1");
        }
        this.crossoverPolicy = crossoverPolicy;
        this.crossoverRate = crossoverRate;
        this.mutationPolicy = mutationPolicy;
        this.mutationRate = mutationRate;
        this.selectionPolicy = selectionPolicy;
    }

    public Population evolve(final Population current) {
        Population nextGeneration = current.nextGeneration();

        while (nextGeneration.getPopulationSize() < nextGeneration.getPopulationLimit()) {
            // pasirenkamos tėvinės chromosomos
            ChromosomePair pair = selectionPolicy.select(current);

            // kryžminti?
            if (randomGenerator.nextDouble() < crossoverRate) {
                pair = crossoverPolicy.crossover(pair.getFirst(), pair.getSecond());
            }

            // mutuoti?
            if (randomGenerator.nextDouble() < mutationRate) {
                pair = new ChromosomePair(
                        mutationPolicy.mutate(pair.getFirst()),
                        mutationPolicy.mutate(pair.getSecond())
                );
            }

            // pridedama pirma chromosomą prie populaicijos
            nextGeneration.addChromosome(pair.getFirst());
            // ar dar yra laisvos vietos ?
            if (nextGeneration.getPopulationSize() < nextGeneration.getPopulationLimit()) {
                // pridedama antra chromosomą prie populaicijos
                nextGeneration.addChromosome(pair.getSecond());
            }
        }

        return nextGeneration;
    }
}
