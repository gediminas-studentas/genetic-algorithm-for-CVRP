package cvrp.problem.util;

import cvrp.problem.Point;

import java.util.List;

public class Euclidean2D {
    public static double calcDistance(Point p1, Point p2) {
        // compute Euclidean distance
        return Math.sqrt(Math.pow((p1.getX() - p2.getX()), 2) + Math.pow((p1.getY() - p2.getY()), 2));
    }

    public static double[][] calcDistanceMatrix(List<Point> points) {
        double[][] matrix = new double[points.size()][points.size()];
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = calcDistance(points.get(i), points.get(j));
                }
            }
        }

        return matrix;
    }
}
