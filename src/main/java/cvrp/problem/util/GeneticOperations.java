package cvrp.problem.util;

public class GeneticOperations {

    public static void swapPosition(Integer[] genes, int gene1, int gene2) {
        int pos1 = 0;
        int pos2 = 0;
        //TODO: Arrays.binarySearch() ??
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
