package cvrp.problem;

import cvrp.problem.util.GeneticOperations;

import java.util.Arrays;
import java.util.Random;

public class PMXCrossover implements CrossoverPolicy {

    @Override
    public ChromosomePair crossover(Chromosome first, Chromosome second) {
        int length = first.getGenes().length;
        Random random = new Random();
        int begin = random.nextInt(length - 2) + 1;
        int end = random.nextInt(length - 1 - begin) + begin;
        Integer[][] babies = PMX(first.getGenes(), second.getGenes(), begin, end);

        return new ChromosomePair(first.copyWithGenes(babies[0]), second.copyWithGenes(babies[1]));
    }

    private static Integer[][] PMX(Integer[] first, Integer[] second, int begin, int end) {
        Integer[][] baby = new Integer[2][first.length];
        baby[0] = Arrays.copyOf(first, first.length);
        baby[1] = Arrays.copyOf(second, second.length);
        for(int pos=begin; pos<end; pos++) {
            int gene1 = first[pos];
            int gene2 = second[pos];
            GeneticOperations.swapPosition(baby[0], gene1, gene2);
            GeneticOperations.swapPosition(baby[1], gene1, gene2);
        }

        return baby;
    }
}
