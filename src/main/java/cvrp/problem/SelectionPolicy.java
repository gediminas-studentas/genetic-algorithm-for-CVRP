package cvrp.problem;

public interface SelectionPolicy {
    /**
     * Atrenka dvi chromosomas tolimesnei populiacijai
     */
    ChromosomePair select(Population population);
}
