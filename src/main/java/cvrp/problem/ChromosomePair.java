package cvrp.problem;

/**
 * Chromosomų pora
 *
 */
public class ChromosomePair {
    /** Pirmoji chromosoma poroje */
    private final Chromosome first;

    /** Antroji chromosoma poroje */
    private final Chromosome second;

    public ChromosomePair(final Chromosome c1, final Chromosome c2) {
        super();
        first = c1;
        second = c2;
    }

    public Chromosome getFirst() {
        return first;
    }

    public Chromosome getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", getFirst(), getSecond());
    }
}
