package cvrp.problem;

/**
 * Kryžminimo politika nurodo, kokia strategija bus naudojama kryžminant
 */
public interface CrossoverPolicy {

    /**
     * Kryžminimo operacijos atlikimas. Tvarka nesvarbi.
     *
     * @param first pirmoji chromosoma
     * @param second antroji chromosoma
     * @return kryžminimo rezultatas - chromosomų pora
     */
    ChromosomePair crossover(Chromosome first, Chromosome second);
}
