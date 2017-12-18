package cvrp.problem;

import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

import java.util.List;

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
