package cvrp.problem;

import java.util.Arrays;
import java.util.Random;

/**
 * Dalinio atvaizdavimo krosoveris (PMX - angl. partially-mapped crossover).
 * Ši procedūra dirba tik su chromosomų dalimi – atvaizdavimo sritimis – pasiskirsčiusiose tarp dviejų krosoverio taškų
 */
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
            swapPosition(baby[0], gene1, gene2);
            swapPosition(baby[1], gene1, gene2);
        }

        return baby;
    }

    public static void swapPosition(Integer[] genes, int gene1, int gene2) {
        int pos1 = 0;
        int pos2 = 0;
        for (int i=0; i < genes.length; i++) {
            if (genes[i] == gene1) {
                pos1 = i;
            }
            if (genes[i] == gene2) {
                pos2 = i;
            }
        }
        genes[pos1] = gene2;
        genes[pos2] = gene1;
    }
}
