package cvrp.problem;

public interface MutationPolicy {

    /**
     * Mutate the given chromosome.
     * @param original the original chromosome.
     * @return the mutated chromosome.
     */
    Chromosome mutate(Chromosome original);
}
