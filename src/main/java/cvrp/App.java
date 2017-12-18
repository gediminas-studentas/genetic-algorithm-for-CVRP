package cvrp;

import com.google.common.collect.ImmutableList;
import cvrp.problem.*;

import java.io.IOException;
import java.util.List;

public class App 
{
    public static void main( String[] args ) throws IOException {
        CVRPProblem problem = new CVRPProblem("Instances\\A\\A-n45-k6.vrp");

        List<Chromosome> initialRandomChromosomes = Chromosome.randomize(3000, problem);
        Population population = new Population(initialRandomChromosomes, 1000, 0.7D);

        GeneticAlgorithm ga = new GeneticAlgorithm(
                new PMXCrossover(), 0.8,
                new MutationChain(ImmutableList.of(new InsertMutation(), new SwapMutation())), 0.1,
                new RandomSelection()
        );
        for (int i = 0; i < 1000000000; i++) {
            if (i % 1000 == 0) {
                if (population.getFittestChromosome().fitness() > -955) {
                    break;
                }
                System.out.println(i + ", " + population.toString() + ", " + population.getFittestChromosome());
            }
            population = ga.evolve(population);
        }
    }
}
