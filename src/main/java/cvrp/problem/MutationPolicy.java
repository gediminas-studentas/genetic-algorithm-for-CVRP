package cvrp.problem;

public interface MutationPolicy {

    /**
     * Mutuoja dvi chromosomas
     * @param original originali chromosoma.
     * @return mutuota chromosoma
     */
    Chromosome mutate(Chromosome original);
}
