package cvrp.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Chromosoma sauganti savyje genus ir galinti apskaičiuoti tinkamumo įvertį.
 */
public class Chromosome implements Comparable<Chromosome>, Fitness {

    private Integer[] genes; //eilės tvarka nurodomi klientai, pas kurį bus važiuojama pirmiausia.
    private Double fitness = NO_FITNESS;

    private final CVRPProblem problem;
    private final Fitness fitnessSupplier;

    private static final double NO_FITNESS = Double.NEGATIVE_INFINITY;  // Reikšmė priskiriama nesant apskaičiavus tinkamumo įverčio

    private static final int PENALIZE_OVERLOAD = 25;

    public Chromosome(CVRPProblem problem) {
        this(getPermutation(problem.getCustomers().size()), problem);
    }

    public Chromosome(Integer[] genes, CVRPProblem problem) {
        this.genes = Arrays.copyOf(genes, genes.length);
        this.problem = problem;
        this.fitnessSupplier = () -> {

            int globalGeneIterator = 0;
            int vehicleGeneStartIndex = globalGeneIterator;

            double fitness = 0.0D;
            //Konversijos algoritmas.
            //Ji paremta sunkvežimių talpos apribojimu. Konversijos algoritmas skaitydamas klientų seką tuo pačiu metu
            //skaičiuoja ir sunkvežimio užpildymą. Viršijus leistiną talpą yra traktuojama, kad genų seka žymi sekančio sunkvežimio maršrutą.
            //Ši logika tęsiama iki kol perskaitomi visi genai. Rezultate aiškiai žinome kokį maršrutą pasirinko kiekvienas iš sunkvežimių,
            for (int vehicleNr = 1; vehicleNr <= this.problem.getVehiclesNumber(); vehicleNr++) {

                int vehicleCoveredDemand = 0;
                for (;globalGeneIterator < genes.length; globalGeneIterator++) {
                    int customerNr = genes[globalGeneIterator];
                    vehicleCoveredDemand += problem.getCustomers().get(customerNr - 1).getDemand();
                    if (vehicleNr < this.problem.getVehiclesNumber() && vehicleCoveredDemand > problem.getMaxVehicleCapacity()) {
                        //Revert if we gone to far
                        vehicleCoveredDemand -= problem.getCustomers().get(customerNr - 1).getDemand();
                        break;
                    }
                }

                //Jei neefektyviai paskirstėme vietas - tuomet paskutinis automobilis viršys talpą
                //Tokiu atveju būtina pridėti baudos taškų prie tinkamumo, tam, kad ši chromosoma nebūtų laikoma tinkama
                if (vehicleCoveredDemand >  problem.getMaxVehicleCapacity()) {
                    fitness += (vehicleCoveredDemand - problem.getMaxVehicleCapacity()) * PENALIZE_OVERLOAD;
                }

                fitness += computeDistance(
                        Arrays.copyOfRange(genes, vehicleGeneStartIndex, globalGeneIterator),
                        problem.getDepot(),
                        problem.getEuclideanDistanceMatrix()
                );

                vehicleGeneStartIndex = globalGeneIterator;
            }
            //tinkakmumo įverčio "apvertimas" dėl problemos specifikos - ieškoma ne didžiausio, o mažiausio atstumo
            return -1 * fitness;

        };

    }

    public Chromosome copyWithGenes(Integer[] genes) {
        return new Chromosome(genes, this.problem);
    }

    @Override
    public double fitness() {
        if (this.fitness == NO_FITNESS) {
            // no cache - compute the fitness
            this.fitness = fitnessSupplier.fitness();
        }

        return this.fitness;
    }

    public int compareTo(final Chromosome another) {
        return Double.compare(fitness(), another.fitness());
    }

    public static List<Chromosome> randomize(int size, CVRPProblem problem) {
        ArrayList<Chromosome> chromosomes = new ArrayList<>(size);
        for (int i = 0; i< size; i++) {
            chromosomes.add(new Chromosome(problem));
        }

        return chromosomes;
    }

    private static double computeDistance(Integer[] genes, Depot depot, double[][] euclideanDistanceMatrix) {
        double distance = 0.0;
        int lastVisited = depot.getNumber();
        for (int i = 0; i < genes.length; i++) {
            distance += euclideanDistanceMatrix[lastVisited][genes[i]];
            lastVisited = genes[i];
        }
        return distance + euclideanDistanceMatrix[genes[genes.length - 1]][depot.getNumber()];
    }

    private static Integer[] getPermutation(int dimension) {
        Integer[] array = new Integer[dimension];
        for (int i = 0; i < dimension; i++) {
            array[i] = i + 1;
        }
        Collections.shuffle(Arrays.asList(array));

        return array;
    }

    public Integer[] getGenes() {
        return this.genes;
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "fitness=" + fitness +
                ", genes=" + Arrays.toString(genes) +
                '}';
    }
}
