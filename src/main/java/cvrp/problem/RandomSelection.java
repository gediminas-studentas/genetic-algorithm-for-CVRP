package cvrp.problem;

import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

/**
 * Atsitiktinė atranka. Visiškai atsitiktinai atrenka chromosomų porą.
 */
public class RandomSelection implements SelectionPolicy {

    private static RandomGenerator randomGenerator = new JDKRandomGenerator();

    @Override
    public ChromosomePair select(Population population) {
        return new ChromosomePair(
                population.getChromosomes().get(randomGenerator.nextInt(population.getPopulationSize())),
                population.getChromosomes().get(randomGenerator.nextInt(population.getPopulationSize()))
        );
    }
}
