package cvrp.problem;

import com.google.common.collect.ImmutableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cvrp.problem.util.Euclidean2D.calcDistanceMatrix;

/**
 * Klasė skirta nuskaityti iš standartizuoti failo ir išsaugoti CVRP problemos parametrus
 */
public class CVRPProblem {

    private int vehiclesNumber;
    private int maxVehicleCapacity;

    private List<Customer> customers;
    private Depot depot;

    private double[][] euclideanDistanceMatrix;

    public CVRPProblem(final String file) throws IOException {
        try (final BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            boolean demand = false;

            while ((line = br.readLine()) != null) {
                if (line.contains("DIMENSION")) {
                    int graphDimension = Integer.parseInt(line.split(":")[1].trim());
                    this.customers = new ArrayList<>(graphDimension);
                } else if (line.contains("CAPACITY")) {
                    maxVehicleCapacity = Integer.parseInt(line.split(":")[1].trim());
                    break;
                }
            }


            br.readLine(); // šokame prie "NODE_COORD_SECTION" eilutės

            int i = 0;
            while ((line = br.readLine()) != null) {
                if (line.contains("DEPOT_SECTION"))
                    break;
                if (line.contains("DEMAND_SECTION")) {
                    demand = true;
                    i = 0;
                    line = br.readLine();
                }
                if (!demand) {
                    final String[] tokens = line.trim().split(" ");
                    final int x = Integer.parseInt(tokens[1]);
                    final int y = Integer.parseInt(tokens[2]);
                    if (i == 0) { //if depot
                        depot = new Depot(i++, new Point(x, y));
                        continue;
                    }
                    customers.add(new Customer(i++, new Point(x, y)));
                } else {
                    if (i == 0) { //jeigu sandelis
                        i++;
                        continue;
                    }
                    int demandCount = Integer.parseInt(line.trim().split(" ")[1]);
                    customers.set(i-1, customers.get(i-1).withDemand(demandCount));
                    i++;
                }
            }
        }

        euclideanDistanceMatrix = calcDistanceMatrix(ImmutableList.<Point>builder()
                .add(depot.getPoint())
                .addAll(customers.stream().map(Customer::getPoint).collect(Collectors.toList()))
                .build()
        );

        this.vehiclesNumber = (int) Math.ceil(1.0 * getTotalDemand() / maxVehicleCapacity);
    }

    public CVRPProblem(final String file, final int vehiclesNumber) throws IOException {
        this(file);
        this.vehiclesNumber = vehiclesNumber;
    }

    public double[][] getEuclideanDistanceMatrix() {
        return euclideanDistanceMatrix;
    }

    public int getVehiclesNumber() {
        return vehiclesNumber;
    }

    public int getMaxVehicleCapacity() {
        return maxVehicleCapacity;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Depot getDepot() {
        return depot;
    }

    public int getTotalDemand() {
        return customers.stream().map(Customer::getDemand).reduce((a, b) -> a + b).get();
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("total number of customers: ").append(this.customers.size()).append("\n");
        stringBuilder.append("capacity of vehicles: ").append(this.maxVehicleCapacity).append("\n");
        stringBuilder.append("number of vehicles: ").append(this.vehiclesNumber).append("\n");
        for (int i = 0; i < this.customers.size(); i++) {
            stringBuilder.append("Node ").append(i + 1).append(" ").append(this.customers.get(i)).append("\n");
        }
        stringBuilder.append("total demanded: ").append(this.getTotalDemand()).append("\n");
        return stringBuilder.toString();
    }
}
