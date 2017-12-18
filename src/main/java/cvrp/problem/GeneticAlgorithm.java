
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
            throw new IllegalArgumentException("Crossover rate should be between 0..1");
        }
        if (mutationRate < 0 || mutationRate > 1) {
            throw new IllegalArgumentException("Mutation rate should be between 0..1");
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
            // select parent chromosomes
            ChromosomePair pair = selectionPolicy.select(current);

            // crossover?
            if (randomGenerator.nextDouble() < crossoverRate) {
                pair = crossoverPolicy.crossover(pair.getFirst(), pair.getSecond());
            }

            // mutation?
            if (randomGenerator.nextDouble() < mutationRate) {
                pair = new ChromosomePair(
                        mutationPolicy.mutate(pair.getFirst()),
                        mutationPolicy.mutate(pair.getSecond())
                );
            }

            // add the first chromosome to the population
            nextGeneration.addChromosome(pair.getFirst());
            // is there still a place for the second chromosome?
            if (nextGeneration.getPopulationSize() < nextGeneration.getPopulationLimit()) {
                // add the second chromosome to the population
                nextGeneration.addChromosome(pair.getSecond());
            }
        }

        return nextGeneration;
    }
}
