package cvrp.problem;

import java.util.List;

/**
 * Pagalbinė klasė padedanti lengviau sujungti mutacijos operatorius į eilę
 */
public class MutationChain implements MutationPolicy {

    private final List<MutationPolicy> mutatators;

    public MutationChain(List<MutationPolicy> mutatators) {
        this.mutatators = mutatators;
    }

    @Override
    public Chromosome mutate(Chromosome original) {
        Chromosome mutated = original;
        for (MutationPolicy mutator : mutatators) {
            mutated = mutator.mutate(mutated);
        }

        return mutated;
    }
}
