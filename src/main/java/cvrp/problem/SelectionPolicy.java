package cvrp.problem;

public interface SelectionPolicy {
    /**
     * Select two chromosomes from the population.
     * @param population the population from which the chromosomes are choosen.
     * @return the selected chromosomes.
     */
    ChromosomePair select(Population population);
}
