package cvrp.problem;

import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

public class InsertMutation implements MutationPolicy {

    private static RandomGenerator randomGenerator = new JDKRandomGenerator();

    @Override
    public Chromosome mutate(Chromosome original) {
        Integer[] genes = original.getGenes();

        int pos = randomGenerator.nextInt(genes.length);
        int gene = randomGenerator.nextInt(genes.length) + 1;

        int genePos = 0;
        for (int i=1; i < genes.length; i++) {
            if (genes[i] == gene) {
                genePos = i;
                break;
            }
        }
        if (genePos < pos) {
            for(int i = genePos; i < pos; i++) {
                genes[i] = genes[i+1];
            }
            genes[pos] = gene;
        } else {
            for(int i = genePos; i>pos; i--) {
                genes[i] = genes[i-1];
            }
            genes[pos] = gene;
        }

        return original.copyWithGenes(genes);
    }
}
