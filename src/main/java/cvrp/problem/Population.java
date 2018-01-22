package cvrp.problem;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Population {

    private List<Chromosome> chromosomes;
    private int populationLimit;
    //chromosomų, paliekamų kitai kartai, procentas
    private double elitismRate;

    public Population(List<Chromosome> chromosomes, int populationLimit, double elitismRate) {
        this.chromosomes = chromosomes;
        this.populationLimit = populationLimit;
        this.elitismRate = elitismRate;
    }

    public int getPopulationSize() {
        return this.chromosomes.size();
    }

    public int getPopulationLimit() {
        return populationLimit;
    }

    public boolean addChromosome(Chromosome chromosome) {
        if (getPopulationSize() == getPopulationLimit()) {
            return false;
        }
        chromosomes.add(chromosome);

        return true;
    }

    public Population nextGeneration() {
        final List<Chromosome> nextGenerationChromosomes = new LinkedList<>();
        final List<Chromosome> oldChromosomes = chromosomes;
        Collections.sort(oldChromosomes);

        // paskutinės "nepakankamai geros" chromosomos indeksas
        int boundIndex = (int) Math.ceil((1.0 - elitismRate) * oldChromosomes.size());
        for (int i = boundIndex; i < oldChromosomes.size(); i++) {
            nextGenerationChromosomes.add(oldChromosomes.get(i));
        }

        return new Population(nextGenerationChromosomes, populationLimit, elitismRate);
    }

    public Chromosome getFittestChromosome() {
        // geriausia kolkas
        Chromosome bestChromosome = this.chromosomes.get(0);
        for (int i = 1; i < this.chromosomes.size(); i++) {
            Chromosome candidate = this.chromosomes.get(i);
            if (candidate.compareTo(bestChromosome) > 0) {
                // rasta geresnė
                bestChromosome = candidate;
            }
        }

        return bestChromosome;
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    @Override
    public String toString() {
        return "Population{" +
                "chromosomes=" + chromosomes.size() +
                ", populationLimit=" + populationLimit +
                ", elitismRate=" + elitismRate +
                '}';
    }
}
